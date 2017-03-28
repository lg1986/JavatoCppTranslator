package edu.nyu.oop;


import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
        printer.pln("namespace edu{");
        printer.pln("namespace nyu{");
        printer.pln("namespace oop{");
    }

    public void writeEndBaseLayout() {
        printer.pln("};");
        printer.pln("};");
        printer.pln("};");
    }



    public void printClassGenerics(String currentClassName) {
        currentClassName = "__"+currentClassName;
        printer.pln(currentClassName+"::"+currentClassName+"() : __vptr(&__vtable) {}");

        printer.pln("Class "+currentClassName+"::__class() {");
        printer.indentMore();
        printer.pln("static Class k = ");
        printer.indentMore().indentMore();
        String nextLine = "new __Class(__rt::literal(\"nyu.nyu.oop."+currentClassName.replace("__","")+"\"), __Object::__class());";
        printer.pln(nextLine);
        printer.indentMore();
        printer.pln("return k;");
        printer.pln("}");
        printer.pln(currentClassName+"_VT " +currentClassName+"::__vtable");

    }

    public void visitStringLiteral(GNode n) {
        printer.p(n.get(0).toString().replace("", "")+";");
        visit(n);
    }
    public void visitIntegerLiteral(GNode n) {
        printer.p(n.get(0).toString().replace("\"", "")+";");
        visit(n);
    }

    public void visitCallExpression(GNode n) {
        String[] secp = n.get(0).toString().split(" ");
        String call = "";
        for(String s: secp) {
            call += s+".";
        }
        call += n.get(2).toString();
        if(n.getNode(3).size() > 0) {
            call += "("+n.getNode(3).get(0).toString()+")";
        } else {
            call += "();";
        }
        if(call.contains("System.out.println") || call.contains("System.out.print")) {

            call = call.replace("System.out.println", "cout <<");
            call = call.replace("System.out.print", "cout <<");
            call += "<< endl";
        }
        printer.pln(call+";");
        visit(n);
    }

    public void visitNewClassExpression(GNode n) {
        System.out.println(n);
        printer.p("new __"+n.getNode(2).get(0).toString()+"()");
    }


    public void visitDeclarator(GNode n) {
        if(n.get(2) == null) {
            printer.p(" " + n.get(0).toString()+";");
        } else {
            printer.p(" " + n.get(0).toString()+" = ");
            visit(n);
        }
        printer.pln();
    }

    public void visitQualifiedIdentifier(GNode n) {
        printer.p(n.get(0).toString());
        visit(n);
    }

    public void visitPrimaryIdentifier(GNode n) {
        visit(n);
    }
    public void visitReturnStatement(GNode n) {
        printer.p("return ");
        visit(n);
        printer.pln(";");
    }
    public void visitBlock(GNode n) {
        System.out.println(n);
        visit(n);
    }

    public void visitFormalParameter(GNode n) {
        printer.p(","+n.getNode(1).getNode(0).get(0)+" "+n.get(3));
        visit(n);
    }

    public void visitFormalParameters(GNode n) {
        visit(n);
        printer.pln("){");
    }

    public String getRetType(GNode n) {

        String retType = "";
        if(n.getNode(2).size() == 0) {
            retType = "void";
        } else {
            retType = n.getNode(2).getNode(0).get(0).toString();
            if (retType.compareTo("int") == 0) retType = "int32_t";
        }
        return retType;
    }
    public void visitMethodDeclaration(GNode n) throws IOException {

        String methodName = n.get(3).toString();
        String retType = getRetType(n);
        printer.p(retType+" __"+currentClassName+"::"+methodName);
        String paramList = "("+currentClassName+" __this";
        printer.p(paramList);
        visit(n);
        printer.pln("}");
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        String className = n.get(1).toString().replace("()", "");
        currentClassName = className;
        if(className.toLowerCase().compareTo(this.packageName) == 0) {
            return;
        } else {
            printClassGenerics(className);
        }
        visit(n);

    }

    public void visitPackageDeclaration(GNode n) {
        try {
            this.packageName = n.getNode(1).get(1).toString();
            visit(n);
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
