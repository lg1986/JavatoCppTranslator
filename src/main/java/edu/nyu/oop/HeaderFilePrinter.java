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
    private static String currentClassName;

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
        printer.pln("using namespace java::lang;\n");
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

        printer.pln("{}");
        printer.pln("};");
    }



    public boolean checkIfNode(Object n) {
        if(n != null) {
            if (n instanceof String) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    public void visitFormalParameter(Node n) {
        printer.p(", "+n.getString(0));
    }

    public boolean checkIfStatic(Node n) {
        if(checkIfNode(n.get(0))) {
            Node modifers = n.getNode(0);

            if (modifers.size() <= 1) return false;
            if (modifers.get(1) != null) {
                if (modifers.getNode(1).getString(0).equals("static")) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public String getParamString(Node n, boolean isStatic) {
        String paramString = "(";
        if(!isStatic) paramString+=currentClassName;
        for(int i = 0; i < n.size(); i++) {
            Node paramNode = n.getNode(i);

            if(paramNode.size() > 2 && paramNode.get(2) != null) {

                if(!isStatic) paramString+=",";
                String arrayParam = "__rt::Array<"+
                                    paramNode.getString(0)+">";
                paramString += arrayParam;
            } else {
                paramString += ","+paramNode.getString(0);
            }
        }
        paramString +=")";
        return paramString;
    }
    public void visitMethodDeclaration(Node n) {
        printer.p("static ");
        if (checkIfNode(n.getNode(0))) {
            String ret = getReturnType(n);
            printer.p(ret + " " + n.get(2).toString());
        } else {
            printer.p(n.get(1).toString() + " " + n.get(2).toString());
        }
        if(checkIfStatic(n)) {
            printer.p(getParamString(n.getNode(3), checkIfStatic(n)));
        } else {
            printer.p(getParamString(n.getNode(3), checkIfStatic(n)));
        }

        printer.pln(";");
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



    public void visitMethodDeclarationsVTable(Node n) {
        for(int i = 0; i<n.size(); i++) {
            visitMethodDeclarationVTable(n.getNode(i));
        }
    }

    public void visitMethodDeclarationVTable(Node n) {
        String methName = n.getString(2);
        String ret;
        if(checkIfNode(n.get(1))) ret = getReturnType(n);
        else  ret = (n.get(1).toString()+" ");
        String paramString = getParamString(n.getNode(3), false);
        printer.pln(ret + "(*" + methName + ")" + paramString + ";");
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
        String paramString = getParamString(n.getNode(3), false);
        if(!n.getString(4).equals(currentClassName))
            printer.p(methName+"(("+ret+"(*)"+paramString+")&__"+n.getString(4)+"::"+methName+")");
        else printer.p(methName+"(__"+currentClassName+"::"+methName+")");
    }




    public String getParamConstructorString(Node n) {
        String paramString = "("+currentClassName+" __this";
        for(int i = 0; i < n.size(); i++) {
            paramString += "," + n.getNode(i).getString(0);
        }
        paramString +=")";
        return paramString;
    }


    public void visitConstructorDeclaration(Node n) {
        printer.pln("static "+n.getString(0)+" __init"+getParamConstructorString(n.getNode(1))+";");
    }


    public void printDefaultConstructor() {
        printer.pln("static "+ currentClassName+" __init("+currentClassName+" __this);");
    }

    public void visitConstructorDeclarations(Node n) {
        if(n.size() == 0) {
            printDefaultConstructor();
        }
        for(int i = 0; i <n.size(); i++) {
            visitConstructorDeclaration(n.getNode(i));
        }
    }




    public void visitFieldDeclaration(Node n) {
        String modif = "";
        if(n.getNode(0).size() > 0) {
            modif = n.getNode(0).getNode(0).getString(0);
        }
        printer.pln(modif+" "+n.getString(1)+" "+n.getString(2)+";");
    }


    public void visitFieldDeclarations(GNode n) {
        for(int i = 0; i <n.size(); i++) {
            visitFieldDeclaration(n.getNode(i));
        }
    }

    public String getReturnType(Node n) {
        String ret = n.getString(1);
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