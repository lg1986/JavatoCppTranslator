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

    private String callExpPrim = "";

    public void printMain() {
        Writer wMainCpp;

        try {
            FileOutputStream fosMain = new FileOutputStream("output/main.cpp");
            OutputStreamWriter oMainCpp = new OutputStreamWriter(fosMain, "utf-8");
            wMainCpp = new BufferedWriter(oMainCpp);
        } catch (Exception e) {
            throw new RuntimeException("IO Error.");
        }
        Printer mainPrinter = new Printer(wMainCpp);
        mainPrinter.pln("#include \"output.h\"");
        mainPrinter.pln("using namespace java::lang;");
        mainPrinter.pln("int main(int argc, char* argv[]) {");
        mainPrinter.pln("  __rt::Array<String> args = new __rt::__Array<String>(argc - 1);\n");
        mainPrinter.pln("  for (int32_t i = 1; i < argc; i++) {");
        mainPrinter.pln("    (*args)[i] = __rt::literal(argv[i]);\n }");
        mainPrinter.pln("inputs::"+currentClassName.toLowerCase()+"::__"+currentClassName+"::main(args);");
        mainPrinter.pln("return 0;");
        mainPrinter.pln("}");
        mainPrinter.flush();

    }
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
        outputCppPrinter.pln("using namespace java::lang;");
        outputCppPrinter.pln("namespace inputs{");
        outputCppPrinter.pln("namespace "+"test002"+"{");
    }

    public void printClassGenerics(Node n) {
        currentPrinter.p("__"+currentClassName+":: __"+currentClassName+"() : __vptr(&__vtable){} \n");
        currentPrinter.pln("Class __"+currentClassName+"::__class() {");
        currentPrinter.indentMore();
        currentPrinter.pln("static Class k = ");
        currentPrinter.indentMore().indentMore();
        String nextLine = "new __Class(__rt::literal(\"nyu.edu.oop."+currentClassName.replace("__","")+"\"), __Object::__class());";
        currentPrinter.pln(nextLine);
        currentPrinter.indentMore();
        currentPrinter.pln("return k;");
        currentPrinter.pln("}");
        currentPrinter.pln("__"+currentClassName+"_VT __" +currentClassName+"::__vtable;");
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
            } else if(from.equals("PrimitiveType") || from.equals("Type")) {
                String typ = n.toString();
                if(typ.equals("int")) {
                    typ = "int32_t";
                } else if(typ.equals("boolean")) {
                    typ = "bool";
                }
                currentPrinter.p(typ+" ");
            } else {
                currentPrinter.p(n.toString());
            }
        }
    }

    public void pru(Node n, String from) {
        if(n.hasName("StringLiteral")) {
            printStringLiteral(n, from);
        } else if(n.hasName("VoidType")) {
            currentPrinter.p("void ");
        } else if(n.hasName("PrimitiveType")) {
            loopToDispatch(n, "PrimitiveType");
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
        } else if(n.hasName("BooleanLiteral")) {
            printBooleanLiteral(n, from);
        }
    }

    public void printBooleanLiteral(Node n, String from) {
        currentPrinter.p(n.getString(0));
    }

    public void printNewClassExpression(Node n, String from) {
        loopToDispatch(n, "NewClassExpression");
    }

    public void printIntegerLiteral(Node n, String from) {
        loopToDispatch(n, "IntegerLiteral");
    }


    public void printDeclarator(Node n, String from) {
        if(n.get(2) == null) loopToDispatch(n, "DeclaratorNotAssign");
        else loopToDispatch(n, "Declarator");
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
        if(n.getNode(0).hasName("PrimaryIdentifier"))
            callExpPrim = n.getNode(0).getString(0);
        if(n.get(2).equals("println") || n.get(2).equals("print")) {
            currentPrinter.p("std::cout << ");
            loopToDispatch(n.getNode(3), "CallExpression");
            if(n.get(2).equals("println")) currentPrinter.p("<< std::endl");
        } else {
            loopToDispatch(n, "CallExpression");
        }
    }

    public void printArgumentsList(Node n, String from) {
        if(!from.equals("NewClassExpression")) {
            currentPrinter.p("("+callExpPrim);
        }
        if(n.size() > 0) currentPrinter.p(", ");
        loopToDispatch(n, "Arguments");
        currentPrinter.p(")");
    }

    public void printSelectionExpression(Node n, String from) {

        if(n.getNode(0).get(0) != null && n.getNode(0).get(0).equals("System")) {
        } else {
            loopToDispatch(n, "SelectionExpression");
        }
    }



    public void printPrimaryIdentifier(Node n, String from) {
        if(from.equals("SelectionExpression") && n.getString(0).equals("System")) {
        } else if(from.equals("ConstructorDeclaration")) {
            currentPrinter.p(n.get(0).toString()+" ");
        } else {
            currentPrinter.p(n.get(0).toString());
        }
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

    public void printConstField(Node n, String from) {
        pru(n.getNode(2), "ConstructorDeclaration");
        currentPrinter.p("; \n");
    }


    public boolean checkIfFieldDeclaration(Node n) {
        Node declNode = n.getNode(2).getNode(0);
        if(declNode.get(2) == null) return false;
        else return true;
    }


    public void fieldInitialiationConstructors(Node n) {
        Node fieldsNode = currentClassNode.getNode(FIELDS);
        for(int i = 0; i<fieldsNode.size(); i++) {
            if(checkIfFieldDeclaration(fieldsNode.getNode(i))) {
                currentPrinter.p("__this->");
                printConstField(fieldsNode.getNode(i),
                                "ConstructorDeclaration");
            }
        }
    }

    /**
     * TO:DO Fix the cheap solution for __this in field
     * initializations
     * @param n
     * @param from
     */
    public void printBlock(Node n, String from) {
        currentPrinter.p("{");
        if(from.equals("ConstructorDeclaration")) {
            printSuperConstructorInit(constNum);
            if(constNum == 1) {
                fieldInitialiationConstructors(n);
            }
        }
        for(int i = 0; i<n.size(); i++) {
            dispatchTopru(n.get(i), "Block");
            currentPrinter.p("; \n");
        }
        if(from.equals("ConstructorDeclaration")) {
            currentPrinter.pln("return __this;");
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
        if(n.getString(3).equals("main")) {
            printMain();
        }
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
        currentPrinter.p(currentClassName+" __"+
                         currentClassName+"::__init"+
                         getParamsString(n.getNode(4)));
        printBlock(n.getNode(METHOD_BLOCK), "ConstructorDeclaration");
    }


    public void printDefaultConstructor() {
        currentPrinter.pln(
            currentClassName+" __"+currentClassName+"::__init("+
            currentClassName+ " __this) {");
        currentPrinter.pln("__Object::__init(__this);");
        currentPrinter.pln("return __this; \n }");

    }
    public void visitConstructorDeclarations(GNode n) {
        if(n.size() == 0) {
            printDefaultConstructor();
        }
        for(int i = 0; i<n.size(); i++) {
            constNum += 1;
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
        CreateDependencyTree headerAST = new CreateDependencyTree();
        List<GNode> tree = headerAST.getStackedHeader(n);

        try {
            HeaderFilePrinter headerFile = new HeaderFilePrinter(tree);

        } catch (IOException e) {
            System.out.println("here!");
        }

        JppTraversal visitor = new JppTraversal();
        List<GNode> newtree = visitor.getModifiedAsts(runtime, n);
        this.asts = newtree;
    }






}
