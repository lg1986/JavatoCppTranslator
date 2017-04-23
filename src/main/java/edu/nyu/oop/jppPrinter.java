package edu.nyu.oop;


import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.IndexOutOfBoundsException;

public class jppPrinter extends Visitor {

    private Printer classPrinter;
    private Printer printer;
    private Printer mainPrinter;
    private List<Node> jppList;
    private String packageName;

    private String currentClassName;
    private String currentC;

    private String callExpIdentifier;
    int constructorCounter = 0;

    /**
     * Constructor - This initiates the creation of the header file
     * @param n
     * @throws IOException
     */
    public jppPrinter(Node n) throws IOException {
        Writer w;
        Writer wMain;
        try {
            FileOutputStream fos = new FileOutputStream("output/output.cpp");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.classPrinter = new Printer(w);

            FileOutputStream fosmain = new FileOutputStream("output/main.cpp");
            OutputStreamWriter owsMain = new OutputStreamWriter(fosmain, "utf-8");
            wMain = new BufferedWriter(owsMain);
            this.mainPrinter = new Printer(wMain);

        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        printer = classPrinter;
        getOutputImplementations(n);
        writeStartBaseLayout();
        collect();
        writeEndBaseLayout();
        classPrinter.flush();
        mainPrinter.flush();
    }

    public void getOutputImplementations(Node n) {
        ArrayList<GNode> output = new ArrayList<GNode>();

        // Phase 1
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependencyList = depe.getDependency();

        //Phase 4
        jppTraversal jppTraversalVisitor = new jppTraversal();
        this.jppList = jppTraversalVisitor.getSummary(dependencyList);

    }

    public void writeStartBaseLayout() {
        classPrinter.pln("#include \"output.h\"");
        classPrinter.pln("using namespace java::lang;");
        classPrinter.pln("namespace nyu{");
        classPrinter.pln("namespace edu{");
        classPrinter.pln("namespace oop{");

        mainPrinter.pln("#include <iostream>");
        mainPrinter.pln("#include \"java_lang.h\"");
        mainPrinter.pln("#include \"output.h\"");
        mainPrinter.pln("using namespace nyu::edu::oop;");
        mainPrinter.pln("using namespace std;");
    }

    public void writeEndBaseLayout() {
        classPrinter.pln("}");
        classPrinter.pln("}");
        classPrinter.pln("}");

        mainPrinter.pln("return 0;");
        mainPrinter.pln("}");
    }

    public void printClassFieldInitializers(Node n) {
        for(int i = 0; i<n.size(); i++) {
            Node k = n.getNode(i);
            printer.p("," + k.get(1).toString() + "(");
            String typ = k.get(0).toString();
            if (typ.equals("String")) {
                printer.p("__rt::literal(\"\"))");
            }
        }

    }

    public void printClassGenerics(Node n) {
        currentClassName = "__"+currentClassName;
        classPrinter.p(currentClassName+"::"+currentClassName+"() : __vptr(&__vtable) ");
        printClassFieldInitializers(n);
        printer.pln("{}");

        classPrinter.pln("Class "+currentClassName+"::__class() {");
        classPrinter.indentMore();
        classPrinter.pln("static Class k = ");
        classPrinter.indentMore().indentMore();
        String nextLine = "new __Class(__rt::literal(\"nyu.edu.oop."+currentClassName.replace("__","")+"\"), __Object::__class());";
        classPrinter.pln(nextLine);
        classPrinter.indentMore();
        classPrinter.pln("return k;");
        classPrinter.pln("}");
        classPrinter.pln(currentClassName+"_VT " +currentClassName+"::__vtable;");
    }

    public boolean checkIfNode(Object n) {
        if(n instanceof String) {
            return false;
        } else {
            return true;
        }
    }


    public void printCheckStatementNode(Node n, String from) {
        if(n.hasName("StringLiteral")) {
            printStringLiteral(n, from);
        } else if(n.hasName("ReturnType")) {
            printType(n, from);
        } else if(n.hasName("Type")) {
            printType(n, from);
        } else if(n.hasName("FormalParameters")) {
            printFormalParameters(n, from);
        } else if(n.hasName("ReturnStatement")) {
            printReturnStatement(n, from);
        } else if(n.hasName("Block")) {
            printBlock(n, from);
        } else if(n.hasName("CallExpression")) {
            printCallExpression(n, from);
        } else if(n.hasName("ExpressionStatement")) {
            printExpressionStatement(n, from);
        } else if(n.hasName("Declarators")) {
            printDeclarators(n, from);
        } else if(n.hasName(("Declarator"))) {
            printDeclarator(n, from);
        } else if(n.hasName("FieldDeclaration")) {
            printFieldDeclaration(n, from);
        } else if(n.hasName("NewClassExpression")) {
            printNewClassExpression(n, from);
        } else if(n.hasName("Arguments")) {
            printArguments(n, from);
        } else if(n.hasName("QualifiedIdentifier")) {
            printQualifiedIdentifier(n, from);
        } else if(n.hasName("PrimaryIdentifier")) {
            printPrimaryIdentifier(n, from);
        } else if(n.hasName("ConstructorDeclaration")) {
            printConstructorDeclaration(n, from);
        } else if(n.hasName("ThisExpression")) {
            printThisExpression(n, from);
        }

    }

    public void printPrimaryIdentifier(Node n, String from) {
        String varName = n.get(0).toString();
        //printer.p(n.get(0).toString().replace("\"", ""));
        if(from.equals("CallExpression")) {
            //printer.p("->__vptr->");
            printer.p(varName);
            callExpIdentifier = n.get(0).toString().replace("\"", "");
        }
        if(from.equals("ReturnStatement")){
            printer.p("->__vptr->");
            printer.p(varName);
        }
    }

    public void printQualifiedIdentifier(Node n, String from) {
        if(from.equals("NewClassExpression")) {
            String classname = n.get(0).toString().replace("()", "").toString();
            printer.p(classname+"__::init(new __"+classname + "(),");
        }
        //printer.p(n.get(0).toString().replace("\"", ""));
        // printer.p(n.get(0).toString().replace("\"", ""));

    }

    public void printArguments(Node n, String from) {
        if(!from.equals("NewClassExpression")) {
            printer.p("(");
            if(from.equals("CallExpression")) {
                printer.p(callExpIdentifier);
            }
            for(int i = 0; i<n.size(); i++) {
                if(n.get(i) != null && checkIfNode(n.getNode(i))) {
                    printCheckStatementNode(n.getNode(i), from);
                }
            }
            printer.p(")");
        } else {
            if(from.equals("CallExpression")) {
                printer.p(callExpIdentifier);
            }
            for(int i = 0; i<n.size(); i++) {
                if(n.get(i) != null && checkIfNode(n.getNode(i))) {
                    printCheckStatementNode(n.getNode(i), from);
                }
            }
            printer.p(")");
        }
    }

    public void printNewClassExpression(Node n, String from) {
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.getNode(i))) {
                printCheckStatementNode(n.getNode(i), "NewClassExpression");
            }
        }
    }

    public void printExpressionStatement(Node n, String from) {
        //System.out.println("\n expression n: " + n + "\n");
        for(int i = 0; i<n.size(); i++) {
            try {
                String one = n.getNode(0).getNode(0).get(0).toString();
                //System.out.println("\n one: " + one);
                if (one.equals("ThisExpression(null)")){
                    String thisKeyword = n.getNode(0).getNode(0).get(0).toString();
                    one = n.getNode(0).getNode(0).get(1).toString();
                    printer.p(thisKeyword + ".");
                    printer.p(one);
                } else if ("PrimaryIdentifier(\"System\")".equals(one)) {
                } else {
                    printer.p(one + " ");
                    String two = n.getNode(0).get(1).toString();
                    printer.p(two + " ");
                    String three = n.getNode(0).getNode(2).get(0).toString();
                    printer.p(three);
                }
            } catch (NullPointerException e){}

            if(n.get(i) != null && checkIfNode(n.getNode(i))) {
                printCheckStatementNode(n.getNode(i), "ExpressionStatement");
            }
        }
    }

    public void printFieldDeclaration(Node n, String from) {
        for(int i = 0; i < n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                printCheckStatementNode(n.getNode(i), "FieldDeclaration");
            }
        }
    }
    public void printDeclarator(Node n, String from) {
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                printCheckStatementNode(n.getNode(i), "Declarator");
            } else if(n.get(i) instanceof String) {
                printer.p(" "+n.get(i).toString().replace("\"", "")+" = ");
            }
        }
    }


    public void printDeclarators(Node n, String from) {

        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                printDeclarator(n.getNode(i), from);
            }
        }
    }

    public void printCallExpression(Node n, String from) {
        callExpIdentifier = "";
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i)!=null && checkIfNode(n.get(i))) {

                printCheckStatementNode(n.getNode(i), "CallExpression");
            } else if(n.get(i) != null) {
                if(n.get(i).equals("println")) {
                    printer.p("cout <<");

                } else {
                    printer.p(n.get(i).toString());
                }
            }
        }
    }

    public void printThisExpression(Node n, String from){
        printer.p("\nIN THIS\n");
        printCheckStatementNode(n.getNode(0), "ThisExpression");
    }

    public void printReturnStatement(Node n, String from) {
        printer.p("return ");
        printCheckStatementNode(n.getNode(0), "ReturnStatement");
    }

    public void printFormalParameters(Node n, String from) {
        printer.p("("+currentC+" __this ");
        if(n.size() > 0) printer.p(", ");
        if (n.size() >= 1) {
            try {
                String paramType =  n.getNode(0).getNode(1).getNode(0).get(0).toString();
                String varName =  n.getNode(0).get(3).toString();
                printer.p(paramType + " " + varName);
            } catch (ClassCastException e) {}
        }


        for(int i =0; i<n.size(); i++) {
            printCheckStatementNode(n.getNode(i), "FormalParameters");
            if(i != n.size()-1) printer.p(" ,");
        }
        printer.p(") { \n");
    }

    public void printFormalParameter(Node n, String from) {
        printCheckStatementNode(n.getNode(0), "FormalParameter");
        printer.p(n.getNode(1).getNode(0).get(0).toString()+ " ");
        printer.p(n.get(3).toString() + ")");
    }

    public void printType(Node n, String from) {
        for(int i =0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                printCheckStatementNode(n.getNode(i), "printType");
            } else if(n.get(i) != null) {
                printer.p(n.get(i).toString());
            }
        }
    }

    public void printStringLiteral(Node n, String from) {
        if(from.equals("NewClassExpression")) {
            printer.p(n.get(0).toString());
        } else {
            printer.p("__rt::literal("+n.get(0).toString()+")");
        }

    }


    public void printBlock(Node n, String from) {
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                printCheckStatementNode(n.getNode(i), "Block");
            } else if(n.get(i) != null) {
                printer.p(n.get(i).toString());
            }
            printer.p("; \n");
        }
        if(constructorCounter == 0){
            printer.p("__Object::__init((Object)__this);\n");
        }
        if(printer != mainPrinter) printer.p("} \n");

    }


    public void printConstructorDeclaration(Node n, String from) {
        System.out.println("\nconstrutor n: " + n + "\n");
        String className = n.get(2).toString().replace("()", "").toString();
        String constructor = className + "::__init(new__" + className + "(),";
        printer.p(constructor);
        printFieldDeclaration(n,from);
        printer.p(")\n");
        constructorCounter++;

    }

    public void visitMethodDeclaration(GNode n) {
        if(!n.get(2).toString().equals("main")) {
            for (int i = 0; i < n.size(); i++) {
                if (n.get(i) != null && checkIfNode(n.get(i))) {
                    printCheckStatementNode(n.getNode(i), "MethodDeclaration");
                } else if (n.get(i) != null && !checkIfNode(n.get(i))) {
                    printer.p(" " + currentClassName + "::" +
                            n.get(i).toString());
                }
            }
        } else {
            printer.pln("int main(){ ");
            printCheckStatementNode(n.getNode(6), "MethodDeclaration");
        }

    }

    public void visitClassDeclaration(GNode n) {

        currentClassName = n.get(0).toString();

        currentC = n.get(0).toString();

        if(currentClassName.equals("Test006")) {
            printer = mainPrinter;
        } else {
            printer = classPrinter;
            printClassGenerics(n.getNode(2));
        }

        Node constructorDeclarations = n.getNode(3);
        for(int i = 0; i< constructorDeclarations.size(); i++) {
            if(constructorDeclarations.get(i) != null && checkIfNode(constructorDeclarations.get(i))) {
                printCheckStatementNode(constructorDeclarations.getNode(i), "Class");
            }
        }

        visit(n.getNode(1));
    }

    public void visitPackageDeclaration(GNode n) {
        try {
            this.packageName = n.getNode(1).get(1).toString();
        } catch (Exception ignored) {

        }
    }


    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) dispatch((Node) o);
        }
    }

    public void collect() {
        for(Node n:jppList) {
            super.dispatch(n);
        }
    }
}