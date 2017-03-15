package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 14/03/17.
 */
public class CreateHeader extends Visitor {

    ArrayList<GNode> dataLayout = new ArrayList<GNode>();

    public void getDataLayoutAST(Node n) {
        DependencyTraversal visitor = new DependencyTraversal();
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependenceyList = depe.getDependency();
        this.dataLayout = visitor.getSummary(dependenceyList).dependencyAsts;
    }


    public void collect(Node n) {
        getDataLayoutAST(n);
    }

    public void writeBaseLayout() {
        try {
            PrintWriter writer = new PrintWriter("output/output.h", "UTF-8");
            writer.println("using namespace edu::nyu::oop;");
            writer.println("namespace edu{");
            writer.println("namespace nyu{");
            writer.println("namespace oop{");

            writer.close();
        } catch (IOException e) {
            // do something
        }
    }



}
