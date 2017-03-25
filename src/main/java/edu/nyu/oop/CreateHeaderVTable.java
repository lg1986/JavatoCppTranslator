package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 18/03/17.
 */
public class CreateHeaderVTable extends Visitor {

    private Printer printer;
    public ArrayList<GNode> vtable = new ArrayList<GNode>();
    public String currentMethodString = "";
    public String currentClassName;
    public String currentMethodName;

    public CreateHeaderVTable(Node n) throws IOException {
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/outputV.h");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        getVTableAST(n);
        collect();
        printer.flush();

    }

    public void getVTableAST(Node n) {
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependenceyList = depe.getDependency();

        DependencyVTableTraversal visitor = new DependencyVTableTraversal();
        DependencyVTableTraversal.vtableAST a = visitor.getSummary(dependenceyList);
        this.vtable = a.vtableAsts;
    }

    public void printStarterVTable(String name) {
        printer.pln("struct __"+name+"_VT{");
        printer.pln("Class __is_a;");
        printer.pln("int32_t (*hashCode)("+name+");");
        printer.pln("bool (*equals)("+name+", Object);");
        printer.pln("Class (*getClass)("+name+");");
        printer.pln("String (*toString)("+name+");");

        printer.pln("__"+name+"_VT()");
        printer.incr();
        printer.pln(": __is_a(__"+name+"::__class()),");

    }

    // 0 Modifiers
    // 1
    // 2 Return Type - Qualified Identifier
    // 3 name
    // 4 Formal Params

    // Structured needed -  Return type (*nameOfMethod) ()

    // Structure for VTable -
    // name((returnTyoe (*)(name)) &__FROM::name),


    public void visitMethodDeclaration(GNode n) throws IOException {
        try {
            String meth_name = n.getNode(3).toString().replace("()", "");
            String ret_type = n.getNode(2).getNode(0).get(0).toString();
            Node params = n.getNode(4);
            String cl = n.getNode(5).get(0).toString().replace("()", "");
            String paramList = currentClassName +", ";

            if(params != null) {

                for (int i = 0; i < params.size(); i++) {
                    if(params.getNode(i) != null)
                        paramList += params.getNode(i).getNode(2).get(0).toString() + ", ";
                }
            }
            paramList = paramList.replaceAll(", $", "");
            paramList = "( " + paramList + " )";

            if(cl.compareTo(currentClassName) == 0) {
                currentMethodString = meth_name+"("+"__"+currentClassName+"::"+meth_name+"),";
                printer.pln(currentMethodString);
            } else {
                currentMethodString = (meth_name+"(("+ret_type+"(*)"+paramList+")");
                currentMethodString += " &__"+cl+"::"+meth_name+"), ";
                printer.pln(currentMethodString);
            }
        } catch (Exception e) {
        }

        visit(n);
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        String className = n.get(0).toString().replace("()", "");
        printStarterVTable(className);
        currentClassName = className;

        visit(n);
        printer.pln("}");
        printer.pln();
        printer.pln();
    }

    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) dispatch((Node) o);
        }
    }


    public void collect() {
        for(Node n: vtable) {
            super.dispatch(n);
        }

    }
}
