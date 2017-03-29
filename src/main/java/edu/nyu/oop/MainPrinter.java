package edu.nyu.oop;


import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;


public class MainPrinter extends Visitor {
    private Printer printer;

    public void visitDeclarator(GNode n){

    }

    public void visitDeclarators(GNode n){
        visit(n);
    }

    public void visitPrimitiveType(GNode n){
        printer.p(n.get(0).toString());
    }
    public void visitQualifiedIdentifier(GNode n){
        printer.p(n.get(0).toString());
    }

    public void visitFieldDeclaration(GNode n){
        // get type
        visit(n.getNode(1).getNode(0));

        // get declarator
    }

    public void visitClassDeclaration(GNode n){
        System.out.println(n);
        visit(n);
    }


    public void visit(Node n) {
        for(Object o:n) {
            if(o instanceof Node) dispatch((Node) o );
        }
    }

    public void collect(GNode n) {
        super.dispatch(n);

    }

    public MainPrinter(GNode n) {
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/main.cpp");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        writeMainBase();
        collect(n);
        writeMainEnd();
        printer.flush();
    }

    public void writeMainBase() {

        printer.pln("#include <iostream> \n");
        printer.pln("#include \"java_lang.h\" \n");
        printer.pln("#include \"output.h\" \n");

        printer.pln("using namespace nyu::edu::oop; \n");
        printer.pln("using namespace java::lang;\n");
        printer.pln("using namespace std;\n");
        printer.pln("int main(void){");
    }

    public void writeMainEnd() {
        printer.pln("}");

    }

}