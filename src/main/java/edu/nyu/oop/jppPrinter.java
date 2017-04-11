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

    private Printer printer;
    private List<Node> jppList;
    private String packageName;

    private String currentClassName;

    /**
     * Constructor - This initiates the creation of the header file
     * @param n
     * @throws IOException
     */
    public jppPrinter(Node n) throws IOException {
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/output.cpp");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        getOutputImplementations(n);
        writeStartBaseLayout();
        collect();
        writeEndBaseLayout();
        printer.flush();
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
        printer.pln("#include \"output.h\"");
        printer.pln("using namespace nyu::edu::oop;");
        printer.pln("namespace nyu{");
        printer.pln("namespace edu{");
        printer.pln("namespace oop{");
    }

    public void writeEndBaseLayout() {
        printer.pln("}");
        printer.pln("}");
        printer.pln("}");
    }



    public void printClassGenerics() {
        currentClassName = "__"+currentClassName;
        printer.pln(currentClassName+"::"+currentClassName+"() : __vptr(&__vtable) {}");
        printer.pln("Class "+currentClassName+"::__class() {");
        printer.indentMore();
        printer.pln("static Class k = ");
        printer.indentMore().indentMore();
        String nextLine = "new __Class(__rt::literal(\"nyu.edu.oop."+currentClassName.replace("__","")+"\"), __Object::__class());";
        printer.pln(nextLine);
        printer.indentMore();
        printer.pln("return k;");
        printer.pln("}");
        printer.pln(currentClassName+"_VT " +currentClassName+"::__vtable;");

    }

    public boolean checkIfNode(Object n) {
        System.out.println(n.getClass());
        if(n instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    public void printCheckStatementNode(Node n) {
        if(n.hasName("ReturnType")) {
            printer.p(n.get(0).toString());
        }
        else if(n.hasName("FormalParameter")) {
            printCheckStatementNode(n.getNode(0));
            printer.p(" "+n.get(1));
        }
        else if(n.hasName("FormalParameters")) {
            printer.p("(");
            for(int i =0; i<n.size(); i++) {
                printCheckStatementNode(n.getNode(i));
                if(i != n.size()-1) printer.p(", ");
            }
            printer.p(")");
        }
    }

    public void visitMethodDeclaration(GNode n) {
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                printCheckStatementNode(n.getNode(i));
            } else if(n.get(i) != null && !checkIfNode(n.get(i))) {
                printer.p(" "+currentClassName+"::"+
                          n.get(i).toString());
            }
        }

    }

    public void visitClassDeclaration(GNode n) {
        currentClassName = n.get(0).toString();

        if(!currentClassName.equals("Test001")) {
            printClassGenerics();
            visit(n.getNode(1));
        }
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