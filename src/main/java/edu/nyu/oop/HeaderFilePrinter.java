package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;



import java.io.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

/**

 * Created by rishabh on 23/04/17.

 */

public class HeaderFilePrinter extends Visitor {

    private Printer printer;
    private String packageName;
    private String currentClassName;

    public HeaderFilePrinter(List<GNode> asts) throws IOException {
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/output.h");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            printer = new Printer(w);

        } catch (Exception e) {
            throw new RuntimeException("Output loc");
        }
        writeStartBaseLayout();
        collect(asts);
        writeEndBaseLayout();
        printer.flush();
    }

    /**
     * Helper method to write the start
     * base layout for the header file.
     * @throws IOException
     */

    public void writeStartBaseLayout() throws IOException {
        printer.pln("#pragma once");
        printer.pln("#include \"java_lang.h\"");
        printer.pln("using namespace nyu::edu::oop;\n");
        printer.pln("namespace nyu{");
        printer.pln("namespace edu{");
        printer.pln("namespace oop{");
    }

    /**
     * Helper method to write the end layout of
     * the header file.
     * @throws IOException
     */

    public void writeEndBaseLayout() throws IOException {
        printer.pln("};");
        printer.pln("};");
        printer.pln("};");
    }



    /**
     * This writes the vptr to the respective
     * vtable and writes the static class method to retrieve
     * the class object
     * @param className
     */

    public void writeClassBase(String className) throws IOException {
        String v_ptr = "__"+className.replace("()", "")+"_VT* __vptr;";
        printer.pln(v_ptr);
        printer.pln("static Class __class();");
        printer.pln("__"+className+";");
        printer.pln("static __"+className.replace("()", "")+"_VT __vtable;");
    }

    public void writeClassPreBase() throws IOException {
        printer.pln("struct __"+ currentClassName+";");
        printer.pln("struct __"+ currentClassName+"_VT;");
        printer.pln("typedef __rt::Ptr<__"+currentClassName+"> "+ currentClassName+";");
    }



    public void visitDataLayout(GNode n) {
        printer.pln("struct __"+currentClassName+" {");
        printer.pln("__"+currentClassName+"_VT*"+" __vptr");
        System.out.println(n);
//        for(int i = 0; i<n.size(); i++) {
//            visit(n.getNode(i));
//        }
        printer.pln("__" + currentClassName + "();");
        visitMethodDeclarations(n.getNode(2));
        printer.pln("};");
    }


    public void visitVTableNode(GNode n) {
        printer.pln("struct __"+currentClassName+"_VT {");
        printer.pln("Class __is_a;");
        printer.pln("void (*__delete)(__" + currentClassName + "*);");
        visitMethodDeclarationsVTable(n);
        printer.pln("__"+currentClassName+"_VT()");
        printer.indent();
        printer.indentMore();
        printer.pln(": __is_a(__"+currentClassName+"::__class()),");
        printer.pln("__delete(&__rt::__delete<__"+currentClassName+">),");
        visitMethodDeclarationVTableMethods(n);
        printer.indentLess();
        printer.pln("};");
    }



    public boolean checkIfNode(Object n) {
        if(n.getClass().toString().equals("Node")) return true;
        else return false;
    }



    public void visitMethodDeclaration(Node n) {
        System.out.println("METHOD DECLARATION NODE HERE: " + n);
        if(checkIfNode(n.get(0))) {
            String ret = getReturnType(n);
            printer.pln(ret + " " +n.get(1).toString());
        } else {
            printer.pln(n.get(0).toString() + " " + n.get(1).toString());
            /*
            if (n.get(3) != null) {
                printer.p(n.get(3).toString());//above, printing A self instead of A self(A);
            }
            */
        }
        printer.pln(n.get(0) +" " +"__init(" + currentClassName+ ");");
        visit(n);
    }



    public void visitMethodDeclarations(Node  n) {
        System.out.println(" HERE HERE! ");
        for(int i = 0; i <n.size(); i++) {
            visitMethodDeclaration(n.getNode(i));
        }

    }



    public void visitMethodDeclarationVTableMethods(Node n) {
        for(int i = 0; i < n.size(); i++) {
            visitMethodDeclarationVTableMethod((GNode) n.getNode(i));
        }
    }



    public void visitMethodDeclarationVTableMethod(Node n) {
        System.out.println("METHOD DECLARATION VTABLE NODE: " + n);
    }



    public void visitMethodDeclarationsVTable(Node n) {
        for(int i = 0; i<n.size(); i++) {
            visitMethodDeclarationVTable(n.getNode(i));
        }
    }

    public void visitMethodDeclarationVTable(Node n) {

    }

    public void visitConstructorDeclaration(Node n) {
        visit(n);
    }



    public void visitConstructorDeclarations(Node n) {
        for(int i = 0; i <n.size(); i++) {
            visit(n.getNode(i));
        }
    }



    public void visitFieldDeclaration(GNode n) {

    }


    public void visitFieldDeclarations(GNode n) {
        for(int i = 0; i <n.size(); i++) {
            visit(n.getNode(i));
        }
    }

    public String getReturnType(Node n) {
        String ret = n.getNode(2).getNode(0).getString(0);
        return ret;

    }

    public void visitClassDeclaration(GNode n) throws  IOException {
        currentClassName = n.get(1).toString();
        writeClassPreBase();
        visit(n);
    }


    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) dispatch((Node) o);
        }

    }

    public void collect(List<GNode> asts) {
        for(Node ast: asts) {
            super.dispatch(ast);
        }
    }

}