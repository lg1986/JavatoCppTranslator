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

public class jppPrinter extends Visitor {

    private Printer classPrinter;
    private Printer printer;
    private Printer mainPrinter;
    private List<Node> jppList;
    private String packageName;

    private String currentClassName;
    private String currentC;

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



    public void printClassGenerics() {
        currentClassName = "__"+currentClassName;
        classPrinter.pln(currentClassName+"::"+currentClassName+"() : __vptr(&__vtable) {}");
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

    public void printBlock(Node n) {
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                printCheckStatementNode(n.getNode(i));

            } else if(n.get(i) != null && !checkIfNode(n.get(i))) {
                printer.p(n.get(i).toString());
            }
        }
    }

    public void printReturnStatement(Node n) {
        printer.p("return ");
        printCheckStatementNode(n.getNode(0));
        printer.pln(";\n } \n");
    }

    public void printFormalParameters(Node n) {
        printer.p("("+currentC+" __this, ");

        for(int i =0; i<n.size(); i++) {
            printCheckStatementNode(n.getNode(i));
            if(i != n.size()-1) printer.p(", ");
        }
        printer.p(") { \n");
    }

    public void printFormalParameter(Node n) {
        printCheckStatementNode(n.getNode(0));
        printer.p(" "+n.get(1));
    }

    public void printType(Node n) {
        printer.p(n.get(0).toString());
    }

    public void printStringLiteral(Node n) {
        printer.p(n.get(0).toString());
    }


    public void printCheckStatementNode(Node n) {
        if(n.hasName("StringLiteral")) {
            printStringLiteral(n);
        } else if(n.hasName("ReturnType")) {
            printType(n);
        } else if(n.hasName("Type")) {
            printType(n);
        } else if(n.hasName("FormalParameter")) {
            printFormalParameter(n);
        } else if(n.hasName("FormalParameters")) {
            printFormalParameters(n);
        } else if(n.hasName("ReturnStatement")) {
            printReturnStatement(n);
        } else if(n.hasName("Block")) {
            printBlock(n);
        }

    }

    public void visitMethodDeclaration(GNode n) {
        if(!n.get(2).toString().equals("main")) {
            for (int i = 0; i < n.size(); i++) {
                if (n.get(i) != null && checkIfNode(n.get(i))) {
                    printCheckStatementNode(n.getNode(i));
                } else if (n.get(i) != null && !checkIfNode(n.get(i))) {
                    printer.p(" " + currentClassName + "::" +
                              n.get(i).toString());
                }
            }
        } else {
            printer.pln("int main(){ ");
            System.out.println(n);
            printCheckStatementNode(n.getNode(6));

        }

    }

    public void visitClassDeclaration(GNode n) {
        currentClassName = n.get(0).toString();
        currentC = n.get(0).toString();

        if(currentClassName.equals("Test002")) {
            printer = mainPrinter;
        } else {
            printer = classPrinter;
            printClassGenerics();
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