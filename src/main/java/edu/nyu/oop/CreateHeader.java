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
        collect();
        writeEndBaseLayout();
        writer.close();
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

    public void writeEndBaseLayout() throws IOException {
        writer.println("};");
        writer.println("};");
        writer.println("};");
    }

    public void writeMethodBase(String methodName) throws IOException {
        String v_ptr = "__"+methodName.replace("()", "")+"_VT* __vptr";
        writer.println(v_ptr);
        writer.println("static Class __class()");
    }


    public void visitMethodDeclaration(GNode n) {
        String decorator = null;
        if(n.get(1) != null) {
            decorator = n.get(1).toString().replace("()", "");
        }
        String return_type = n.get(2).toString().replace("Type()", "").toLowerCase();
        String method_name = n.get(3).toString();

        writer.println(return_type+" "+method_name);

        visit(n);
    }


    public void visitClassDeclaration(GNode n) throws IOException{

        String class_name = "__"+n.get(1).toString().replace("()", "");
        writer.println("struct "+class_name+" {");
        writeMethodBase(n.get(1).toString());
        visit(n);
        writer.println("};");
    }

    public void visitConstructorDeclaration(GNode n){
        System.out.println(n.get(2));
        String constructor = "__"+n.get(2).toString();
        writer.println(constructor+";");
        visit(n);
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    public void collect() {
        for(Node n: dataLayout) {
            super.dispatch(n);
        }
    }
}

