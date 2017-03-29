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
        printer.pln("static Class __class();");
        printer.pln("__"+className+";");
    }

    public void visitFormalParameters(GNode n) throws IOException {
        String arg_name = null;
        String arg_type = null;


        try {
            Node temp = n.getNode(0);
            arg_name = temp.get(3).toString();
            arg_type = temp.getNode(1).getNode(0).get(0).toString();
            Node arr = temp.getNode(1).getNode(1);
            if(arr != null) {
                arg_type += "[]";
            }
            printer.p(", "+arg_type+" "+arg_name);
        } catch (IndexOutOfBoundsException e) {
        }

        printer.pln(");");
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
            ret = "void";
        }
        String method_name = n.get(3).toString();
        printer.p(ret+" "+method_name.replace("()", "")+"("+currentClassName+" ");
        visit(n);
    }


    public void visitConstructorDeclaration(GNode n) {
        String className = n.get(2).toString().replace("()", "").toString();

        String constructor = "static "+className+" __init";
        printer.p(constructor+"("+className+" __this");
        visit(n);
    }

    public void visitDeclarator(GNode n) {
        printer.pln(n.get(0).toString()+";");
    }
    public void visitDeclarators(GNode n) {
        visit(n);
    }

    public void visitFieldDeclaration(GNode n) {
        if(n.getNode(1).getNode(0).get(0).toString().compareTo("Integer")==0) {
            printer.p("int32_t ");
        } else if(n.getNode(1).getNode(0).get(0).toString().compareTo("int") == 0) {
            printer.p("int32_t ");
        } else if(n.getNode(1).getNode(0).get(0).toString().equals("boolean")) {
            printer.p("bool ");
        } else {
            printer.p(n.getNode(1).getNode(0).get(0).toString() + " ");
        }
        visit(n.getNode(2));
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
            visit(n);
        } catch (Exception ignored) {

        }
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
