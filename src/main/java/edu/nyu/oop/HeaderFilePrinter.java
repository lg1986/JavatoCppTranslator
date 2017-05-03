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
        writeStartBaseLayout(asts.get(0).getString(0));
        collect(asts);
        writeEndBaseLayout();
        printer.flush();
    }

    /**
     * Helper method to write the start
     * base layout for the header file.
     * @throws IOException
     */

    public void writeStartBaseLayout(String packageName) throws IOException {
        printer.pln("#pragma once");
        printer.pln("#include \"java_lang.h\"");
        printer.pln("using namespace nyu::edu::oop;\n");
        printer.pln("namespace inputs{");
        printer.pln("namespace "+packageName+"{");
    }

    /**
     * Helper method to write the end layout of
     * the header file.
     * @throws IOException
     */

    public void writeEndBaseLayout() throws IOException {
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
        printer.pln("__"+currentClassName+"_VT*"+" __vptr;");

        printer.pln("__" + currentClassName + "();");
        visitMethodDeclarations(n.getNode(2));
        visitFieldDeclarations((GNode)n.getNode(0));
        visitConstructorDeclarations(n.getNode(1));

        printer.pln("static Class __class();");
        printer.pln("static __"+currentClassName+"_VT __vtable;");
        printer.pln("};");
    }


    public void visitVTableNode(GNode n) {
        printer.pln("struct __"+currentClassName+"_VT {");
        printer.pln("Class __is_a;");
        printer.pln("void (*__delete)(__" + currentClassName + "*);");
        visitMethodDeclarationsVTable(n.getNode(0));


        printer.pln("__"+currentClassName+"_VT()");
        printer.indent();
        printer.indentMore();
        printer.pln(": __is_a(__"+currentClassName+"::__class()),");
        printer.p("__delete(&__rt::__delete<__"+currentClassName+">)");
        visitMethodDeclarationVTableMethods(n.getNode(0));
        printer.indentLess();

        printer.pln("};");
    }

    public boolean checkIfNode(Object n) {
        if(n.getClass().toString().equals("Node")) return true;
        else return false;
    }


    public void visitFormalParameter(Node n) {
        printer.p(", "+n.getString(0));
    }
    public void visitMethodDeclaration(Node n) {
        printer.p("static ");
        if(checkIfNode(n.get(0))) {
            String ret = getReturnType(n);
            printer.p(ret + " " +n.get(2).toString());
        } else {
            printer.p(n.get(1).toString() + " " + n.get(2).toString());
        }
        printer.p(getParamString(n.getNode(3)));
        printer.pln(");");
    }

    public void visitMethodDeclarations(Node  n) {
        for(int i = 0; i <n.size(); i++) {
            visitMethodDeclaration(n.getNode(i));
        }

    }

    /**
     * A_VT top part - layout
     * @param n
     */

    public String getParamString(Node n) {
        String paramString = "("+currentClassName;
        for(int i = 0; i < n.size(); i++) {
            paramString += "," + n.getNode(i).getString(0);
        }
        paramString +=")";
        return paramString;
    }

    public void visitMethodDeclarationsVTable(Node n) {
        for(int i = 0; i<n.size(); i++) {
            visitMethodDeclarationVTable(n.getNode(i));
        }
    }

    public void visitMethodDeclarationVTable(Node n) {

        String methName = n.getString(1);
        String ret;
        if(checkIfNode(n.get(1))) ret = getReturnType(n);
        else  ret = (n.get(1).toString()+" ");
        String paramString = getParamString(n.getNode(3));
        if(!n.getString(4).equals(currentClassName))
            printer.pln(ret+"(*"+methName+")"+paramString+";");
        else
            printer.pln(ret+"(__"+currentClassName+"::"+methName+");");
    }

    /**
     * VTable A_VT
     * @param n
     */
    public void visitMethodDeclarationVTableMethods(Node n) {
        for(int i = 0; i < n.size(); i++) {
            visitMethodDeclarationVTableMethod((GNode) n.getNode(i));
        }
    }



    public void visitMethodDeclarationVTableMethod(Node n) {
        printer.pln(",");
        String methName = n.getString(2);
        String ret;
        if(checkIfNode(n.get(1))) ret = getReturnType(n);
        else  ret = (n.get(1).toString()+" ");
        String paramString = getParamString(n.getNode(3));
        if(!n.getString(4).equals(currentClassName))
            printer.p(methName+"(("+ret+"(*)"+paramString+")&__"+n.getString(4)+"::"+methName+")");
        else printer.p(methName+"(__"+currentClassName+"::"+methName+")");
    }





    public void visitConstructorDeclaration(Node n) {
        printer.pln("static "+n.getString(0)+"__init"+getParamString(n.getNode(1))+";");
    }



    public void visitConstructorDeclarations(Node n) {

        for(int i = 0; i <n.size(); i++) {
            visitConstructorDeclaration(n.getNode(i));
        }
    }



    public void visitFieldDeclaration(Node n) {
        printer.pln(n.getString(0)+" "+n.getString(1)+";");
    }


    public void visitFieldDeclarations(GNode n) {
        for(int i = 0; i <n.size(); i++) {
            visitFieldDeclaration(n.getNode(i));
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