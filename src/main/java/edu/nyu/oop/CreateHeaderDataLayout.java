package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by rishabh on 14/03/17.
 */
public class CreateHeaderDataLayout extends Visitor {

    private Printer printer;
    private ArrayList<GNode> dataLayout = new ArrayList<GNode>();
    private String packageName;
    private String currentClassName;

    /**
     * Constructor - This initiates the creation of the header file
     * @param n
     * @throws IOException
     */
    public CreateHeaderDataLayout(Node n) throws IOException {
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/output.h");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        getDataLayoutAST(n);
        writeStartBaseLayout();
        collect();
        CreateHeaderVTable vtableCreator  = new CreateHeaderVTable(n, printer);
        writeEndBaseLayout();
        printer.flush();
    }

    /**
     * This gets all the dataLayoutASTs
     * @param n
     */

    public void getDataLayoutAST(Node n) {

        ArrayList<GNode> dataLayout = new ArrayList<GNode>();
        ArrayList<GNode> vTable = new ArrayList<GNode>();

        // Phase 1
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependenceyList = depe.getDependency();

        // Phase 2 - Data Layout Traversal
        DependencyDataLayoutTraversal dataLayoutVisitor = new DependencyDataLayoutTraversal();
        this.dataLayout = dataLayoutVisitor.getSummary(dependenceyList).dependencyAsts;

        // Phase 2 - Data Layout VTable
//        DependencyVTableTraversal vTableVisitor = new DependencyVTableTraversal();
//        vTable = vTableVisitor.getSummary(dependenceyList).vtableAsts;

    }


    /**
     * This is to write the starting base layout of the header file
     * @throws IOException
     */
    public void writeStartBaseLayout() throws IOException {
        printer.pln("#pragma once;");
        printer.pln("#include \"java_lang.h\";");

//        printer.pln("using namespace edu::nyu::oop;");
        printer.pln("namespace edu{");
        printer.pln("namespace nyu{");
        printer.pln("namespace oop{");
    }

    public void writeEndBaseLayout() throws IOException {
        printer.pln("};");
        printer.pln("};");
        printer.pln("};");
    }


    // Write vptr to the respective vtable
    // Write the static class method to retrive the class of the object
    public void writeClassBase(String className) throws IOException {
        String v_ptr = "__"+className.replace("()", "")+"_VT* __vptr;";
        printer.pln(v_ptr);
        printer.pln("__"+className+"();");
        printer.pln("static Class __class();");
        printer.pln("static __"+className+"_VT __vtable;");
    }

    public void visitFormalParameters(GNode n) throws IOException {
        String arg_name = null;
        String arg_type = null;


        try {
            Node temp = n.getNode(0);
            arg_type = temp.getNode(1).getNode(0).get(0).toString();
            Node arr = temp.getNode(1).getNode(1);
            if(arr != null) {
                arg_type = arr.toString() + "[]";
            }
            printer.p(arg_type);
        } catch (IndexOutOfBoundsException e) {
            printer.p(currentClassName);
        }

        printer.pln(")");
        visit(n);
    }

    public void visitMethodDeclaration(GNode n) {
        String decorator = null;
        if(n.get(1) != null) {
            decorator = n.get(1).toString().replace("()", "");
        }
        Node return_type = n.getNode(2);

        String ret = "";
        if(return_type.size() > 0) {
            ret = return_type.getNode(0).get(0).toString().replace("Type", "").replace("()", "");
        } else {
            ret = return_type.toString().replace("Type", "").replace("()", "").toLowerCase();
        }
        String method_name = n.get(3).toString().replace("()", "");
        printer.p(ret+" "+method_name+"(");
        System.out.println(n);
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        String constructor = "__"+n.get(2).toString().replace("()", "");
        printer.p(constructor+"(");
        visit(n);
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        String class_name = n.get(1).toString().replace("()", "");
        currentClassName = class_name;
        if(class_name.toLowerCase().compareTo(packageName) == 0) {
            return;
        } else {
            class_name = "__" + class_name;
            printer.pln("struct " + class_name + ";");
            printer.pln("struct " + class_name + "_VT;");

            printer.pln("struct " + class_name + " {");
            writeClassBase(n.get(1).toString());
            visit(n);
            printer.pln("};");
        }
    }

    public void visitPackageDeclaration(GNode n) {
        try {
            this.packageName = n.getNode(0).getNode(1).get(1).toString();
        } catch (Exception ignored) {
        }
        visit(n);
    }


    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    public void collect() {
        for(Node n: this.dataLayout) {
            super.dispatch(n);
        }
    }
}