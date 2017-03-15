package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 14/03/17.
 */
public class CreateHeader extends Visitor {

    public PrintWriter writer;
    public ArrayList<GNode> dataLayout = new ArrayList<GNode>();

    /**
     * Constructor - This initiates the creation of the header file
     * @param n
     * @throws IOException
     */
    public CreateHeader(Node n) throws IOException {
        writer = new PrintWriter("output/output.h", "UTF-8");
        getDataLayoutAST(n);
        writeStartBaseLayout();
    }

    /**
     * This gets all the dataLayoutASTs
     * @param n
     */

    public void getDataLayoutAST(Node n) {
        DependencyTraversal visitor = new DependencyTraversal();
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependenceyList = depe.getDependency();
        this.dataLayout = visitor.getSummary(dependenceyList).dependencyAsts;
    }


    /**
     * This is to write the starting base layout of the header file
     * @throws IOException
     */
    public void writeStartBaseLayout() throws IOException {
        writer.println("using namespace edu::nyu::oop;");
        writer.println("namespace edu{");
        writer.println("namespace nyu{");
        writer.println("namespace oop{");
    }


    public void collect(Node n) {

    }




}



}
