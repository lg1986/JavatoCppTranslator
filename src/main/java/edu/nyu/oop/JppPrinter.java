package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
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


    public JppPrinter(Node n) throws IOException {
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
        getAllASTs(n);
        writeStartBaseLayout(this.packageName);
        collect();
        writeEndBaseLayout();
        outputCppPrinter.flush();
    }

    public void writeEndBaseLayout() {
        outputCppPrinter.pln("}");
        outputCppPrinter.pln("}");
    }

    public void getAllASTs(Node n) {
        JppTraversal visitor = new JppTraversal();
        List<GNode> tree = visitor.getModifiedAsts(n);
        this.asts = tree;
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


//    public void visitConstructorDeclaration(GNode n) {
//
//    }

    public void visitConstructorDeclarations(GNode n){
        System.out.println(n);
        System.out.println(n.size());
    }

    public void visitClassDeclaration(GNode n) {
        currentClassName = n.getString(1);
        this.currentPrinter = this.outputCppPrinter;
        printClassGenerics(n);
        visit(n);
    }

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





}
