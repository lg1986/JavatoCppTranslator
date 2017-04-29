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
public class HeaderFilePrinter extends Visitor{

    private Printer printer;
    private String packageName;
    private String currentClassName;

    public HeaderFilePrinter(List<GNode> asts) throws IOException{
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/output.h");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            printer = new Printer(w);
        } catch (Exception e){
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
        printer.pln("struct "+ currentClassName+"_VT;");
        printer.pln("typedef __rt::Ptr<__"+currentClassName+"> "+ currentClassName+";");
    }

    public void visitClassDeclaration(GNode n) throws  IOException{
        currentClassName = n.get(1).toString();
        writeClassPreBase();

    }


    public void visit(Node n){
        for(Object o: n){
            if(o instanceof Node) dispatch((Node) o);
        }
    }

    public void collect(List<GNode> asts){
        for(Node ast: asts){
            super.dispatch(ast);
        }
    }






}
