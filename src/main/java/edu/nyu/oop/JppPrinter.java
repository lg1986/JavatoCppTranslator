package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 02/05/17.
 */
public class JppPrinter extends Visitor {
    private Printer outputCppPrinter;
    private Printer mainCppPrinter;
    private Printer currentPrinter;

    private List<GNode> asts;
    private String packageName;
    private String currentClassName;
    private GNode currentClassNode;

    private final int MODIFS = 0;
    private final int NAME = 1;
    private final int EXT = 2;
    private final int FIELDS = 3;
    private final int CONSTRS = 4;
    private final int METHOD = 5;
    private final int METHOD_BLOCK = 7;
    private int constNum;

    /**
     * Main JppPrinter traversal utility
     * basics.
     * @param n
     * @throws IOException
     */
    public JppPrinter(Runtime runtime, Node n) throws IOException {
        Writer wMainCpp;
        Writer wOutputCpp;
        try {
            FileOutputStream fosOutputCpp = new FileOutputStream("output/output.cpp");
            OutputStreamWriter oOutputCpp = new OutputStreamWriter(fosOutputCpp, "utf-8");
            wOutputCpp = new BufferedWriter(oOutputCpp);
            this.outputCppPrinter = new Printer(wOutputCpp);
            this.currentPrinter = this.outputCppPrinter;
        } catch (Exception e) {
            throw new RuntimeException("IO Error.");
        }
        getAllASTs(runtime, n);
        writeStartBaseLayout(this.packageName);
        collect();
        writeEndBaseLayout();
        outputCppPrinter.flush();
    }

    /**
     * Utils for printing basic
     * boiler plate for output.cpp
     */
    public void writeEndBaseLayout() {
        outputCppPrinter.pln("}");
        outputCppPrinter.pln("}");
    }

    public void writeStartBaseLayout(String packageName) {
        outputCppPrinter.pln("#include <iostream>");
        outputCppPrinter.pln("#include \"output.h\"");
        outputCppPrinter.pln("using namespace java::lang");
        outputCppPrinter.pln("namespace inputs{");
        outputCppPrinter.pln("namespace "+packageName+"{");
    }

    public void printClassGenerics(Node n) {
        currentPrinter.p(currentClassName+"::"+currentClassName+"() : __vptr(&__vtable) ");
        currentPrinter.pln("Class "+currentClassName+"::__class() {");
        currentPrinter.indentMore();
        currentPrinter.pln("static Class k = ");
        currentPrinter.indentMore().indentMore();
        String nextLine = "new __Class(__rt::literal(\"nyu.edu.oop."+currentClassName.replace("__","")+"\"), __Object::__class());";
        currentPrinter.pln(nextLine);
        currentPrinter.indentMore();
        currentPrinter.pln("return k;");
        currentPrinter.pln("}");
        currentPrinter.pln(currentClassName+"_VT " +currentClassName+"::__vtable;");
    }


    /**
     * PRU and corresponding dispatch methods.
     * @param n
     * @return
     */

    public boolean checkIfNode(Object n) {
        if(n instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    public void loopToDispatch(Node n, String from) {
        for(int i = 0; i < n.size(); i++) {
            dispatchTopru(n.get(i), from);
        }
    }

    public void dispatchTopru(Object n, String from) {
        if(n != null && checkIfNode(n)) {
            pru((Node)n, from);
        } else if(n != null) {
            if(from.equals("MethodDeclaration")) {
                currentPrinter.p("__"+currentClassName+"::"+n.toString());
            } else if(from.equals("SelectionExpression")) {
                currentPrinter.p("->"+n.toString());
            } else if(from.equals("CallExpression")) {
                currentPrinter.p("->__vptr->"+n.toString());
            } else if(from.equals("Declarator")) {
                currentPrinter.p(n.toString() +" = ");
            } else {
                currentPrinter.p(n.toString());
            }
        }
    }

    public void pru(Node n, String from) {
        if(n.hasName("StringLiteral")) {
            printStringLiteral(n, from);
        } else if(n.hasName("ReturnStatement")) {
            printReturnStatement(n, from);
        } else if(n.hasName("Block")) {
            printBlock(n, from);
        } else if(n.hasName("Type")) {
            printType(n, from);
        } else if(n.hasName("QualifiedIdentifier")) {
            printQualifiedIdentifier(n, from);
        } else if(n.hasName("FormalParameters")) {
            currentPrinter.pln(getParamsString(n));
        } else if(n.hasName("SelectionExpression")) {
            printSelectionExpression(n, from);
        } else if(n.hasName("ThisExpression")) {
            currentPrinter.p("__this");
        } else if(n.hasName("PrimaryIdentifier")) {
            printPrimaryIdentifier(n, from);
        } else if(n.hasName("ExpressionStatement")) {
            printExpressionStatement(n, from);
        } else if(n.hasName("Expression")) {
            loopToDispatch(n, "Expression");
        } else if(n.hasName("CallExpression")) {
            printCallExpression(n, from);
        } else if(n.hasName("Arguments")) {
            printArgumentsList(n, from);
        } else if(n.hasName("FieldDeclaration")) {
            printFieldDeclaration(n, from);
        } else if(n.hasName("Declarators")) {
            printDeclarators(n, from);
        } else if(n.hasName("Declarator")) {
            printDeclarator(n, from);
        } else if(n.hasName("NewClassExpression")) {
            printNewClassExpression(n, from);
        } else if(n.hasName("IntegerLiteral")) {
            printIntegerLiteral(n, from);
        }
    }

    public void printNewClassExpression(Node n, String from) {
        loopToDispatch(n, "NewClassExpression");
    }

    public void printIntegerLiteral(Node n, String from) {
        loopToDispatch(n, "IntegerLiteral");
    }


    public void printDeclarator(Node n, String from) {
        loopToDispatch(n, "Declarator");
    }

    public void printDeclarators(Node n, String from) {
        loopToDispatch(n, "Declarators");
    }

    public void printFieldDeclaration(Node n, String from) {
        loopToDispatch(n, "FieldDeclaration");
    }

    public void printExpressionStatement(Node n, String from) {
        loopToDispatch(n, "ExpressionStatement");
    }

    public void printCallExpression(Node n, String from) {
        loopToDispatch(n, "CallExpression");
    }

    public void printArgumentsList(Node n, String from) {
        if(!from.equals("NewClassExpression")) {
            currentPrinter.p("(");
        } else {
            if(n.size() > 0) currentPrinter.p(", ");
        }
        loopToDispatch(n, "Arguments");
        currentPrinter.p(")");
    }

    public void printSelectionExpression(Node n, String from) {
        loopToDispatch(n, "SelectionExpression");
    }



    public void printPrimaryIdentifier(Node n, String from) {
        currentPrinter.p(n.get(0).toString());
    }
    /**
     * getParamsString -- Util
     * @param n
     * @return
     */
    public String getParamsString(Node n) {
        String paramString = "("+currentClassName+" __this";
        for(int i = 0; i < n.size(); i++) {
            paramString += ",";
            Node param = n.getNode(i);
            paramString += " "+(param.getNode(1).getNode(0).get(0));
            paramString += " "+param.getString(3);
        }
        paramString += ")";
        return paramString;
    }

    /**
     * All generic print methods
     * dependening on the nature of the
     * node (Redirectred from printCheckStatement).
     * @param n
     * @param from
     */
    public void printStringLiteral(Node n, String from) {
        if(from.equals("NewClassExpression")) {
            currentPrinter.p(n.get(0).toString());
        } else {
            currentPrinter.p("__rt::literal("+n.get(0).toString()+")");
        }
    }

    public void printReturnStatement(Node n, String from) {
        if(n.size() > 0) {
            currentPrinter.p("return ");
            pru(n.getNode(0), "ReturnStatement");
        }
    }

    public void printBlock(Node n, String from) {
        if(from.equals("ConstructorDeclaration")) {
            printSuperConstructorInit(constNum);
        }
        for(int i = 0; i<n.size(); i++) {
            dispatchTopru(n.get(i), "Block");
            currentPrinter.p("; \n");
        }
        currentPrinter.pln("}");
    }

    public void printType(Node n, String from) {
        for(int i =0; i<n.size(); i++) {
            dispatchTopru(n.get(i), from);
        }
    }
    public void printQualifiedIdentifier(Node n, String from) {
        if(from.equals("NewClassExpression")) {
            currentPrinter.p("__"+n.getString(0)+"::__init("+
                             "new __"+n.getString(0)+"()");
        } else {
            currentPrinter.p(n.getString(0) + " ");
        }
    }


    public void visitMethodDeclaration(GNode n) {
        int consts = 0;
        for(int i = 0; i<n.size(); i++) {
            dispatchTopru(n.get(i), "MethodDeclaration");
        }
    }

    public void visitMethodDeclarations(GNode n) {
        int constructors = 0;
        for(int i = 0; i<n.size(); i++) {
            visitMethodDeclaration((GNode) n.getNode(i));
        }
    }

    /**
     * Constructor Specific Methods:
     * @param constNum
     */

    public void printSuperConstructorInit(int constNum) {
        if(constNum == 1) {
            if(currentClassNode.get(EXT) != null) {
                currentPrinter.pln(currentClassNode.get(EXT).toString());
            } else {
                currentPrinter.pln("__Object::__init(__this);");
            }
        } else {
            currentPrinter.pln("__init(__this);");
        }
    }

    /**
     * Specific ConstructorDeclartion traversal.
     * @param n
     */
    public void printConstructorDeclaration(Node n) {
        currentPrinter.pln(currentClassName+" __"+
                           currentClassName+"::__init"+
                           getParamsString(n.getNode(4))+"{");
        printBlock(n.getNode(METHOD_BLOCK), "ConstructorDeclaration");
        currentPrinter.pln("}");
    }

    public void visitConstructorDeclarations(GNode n) {
        constNum = n.size();
//        if(constNum == 0)
        for(int i = 0; i<n.size(); i++) {
            printConstructorDeclaration(n.getNode(i));
        }
    }

    /**
     * Class Decalaration. Use the Visitor Double
     * dispatch pattern.
     * @param n
     */
    public void visitClassDeclaration(GNode n) {
        constNum = 0;
        currentClassName = n.getString(1);
        currentClassNode = n;
        this.currentPrinter = this.outputCppPrinter;
        printClassGenerics(n);
        visit(n);
    }


    /**
     * General Visitor Pattern Utils
     * @param n
     */

    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    public void collect() {
        for(Node n:asts) {
            super.dispatch(n);
        }
    }

    public void getAllASTs(Runtime runtime, Node n) {
        JppTraversal visitor = new JppTraversal();
        List<GNode> tree = visitor.getModifiedAsts(runtime, n);
        this.asts = tree;
    }






}
