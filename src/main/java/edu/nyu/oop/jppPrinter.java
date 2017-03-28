package edu.nyu.oop;


import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class jppPrinter extends Visitor{

    private Printer printer;
    private List<Node> jppList;

    /**
     * Constructor - This initiates the creation of the header file
     * @param n
     * @throws IOException
     */
    public jppPrinter(Node n) throws IOException {
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/output.h");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        getOutputImplementations(n);

        collect();
        CreateHeaderVTable vtableCreator  = new CreateHeaderVTable(n, printer);
        printer.flush();
    }

    public void getOutputImplementations(Node n){
        ArrayList<GNode> output = new ArrayList<GNode>();

        // Phase 1
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependencyList = depe.getDependency();

        //Phase 4
        jppTraversal jppTraversalVisitor = new jppTraversal();
        this.jppList = jppTraversalVisitor.getSummary(dependencyList);

    }


    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) dispatch((Node) o);
        }
    }

    public void collect(){
        for(Node n:jppList){
            super.dispatch(n);
        }
    }
}
