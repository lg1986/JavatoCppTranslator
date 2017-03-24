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
        this.vtable = visitor.getSummary(dependenceyList).vtableAsts;
    }

    public void printStarterVTable(String name) {
        printer.pln("Class __is_a;");
        printer.pln("int32_t (*hashCode)("+name+");");
        printer.pln("bool (*equals)("+name+", Object);");
        printer.pln("Class (*getClass)("+name+";");
        printer.pln("String (*toString)("+name+";");

        printer.pln("__"+name+"_VT()");
        printer.incr();
        printer.pln(": __is_a(__"+name+"::__class()),");

    }

    public void visitMethodDeclaration(GNode n) throws IOException {
        visit(n);
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        visit(n);
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
