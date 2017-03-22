package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;



/**
 * Created by rishabh on 14/03/17.
 */
public class CreateHeader extends Visitor {

    private Printer printer;
    public ArrayList<GNode> dataLayout = new ArrayList<GNode>();
    PrintWriter out;

    /**
     * Constructor - This initiates the creation of the header file
     * @param current node
     * @throws RuntimeException
     */
    public CreateHeader(Node n) throws IOException {

        try {
            FileWriter file = new FileWriter("src/main/java/edu/nyu/oop/output/output.h", true);
            out = new PrintWriter(file, true);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        getDataLayoutAST(n);
        writeStartBaseLayout();
        collect();
        writeEndBaseLayout();
        out.flush();
    }

    /**
     * This gets all the dataLayoutASTs
     * @param current node
     */
    public void getDataLayoutAST(Node n) {
        DependencyDataLayoutTraversal visitor = new DependencyDataLayoutTraversal();
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
        out.write("using namespace edu::nyu::oop;\n");
        out.write("using namespace edu::nyu::oop;\n");
        out.write("namespace edu{\n");
        out.write("namespace nyu{\n");
        out.write("namespace oop{\n");
    }

    public void writeEndBaseLayout() throws IOException {
        out.write("};");
        out.write("};");
        out.write("};");
    }


    // Write vptr to the respective vtable
    // Write the static class method to retrive the class of the object
    public void writeClassBase(String className) throws IOException {
        String v_ptr = "__"+className.replace("()", "")+"_VT* __vptr";
        out.write(v_ptr);
        out.write("static Class __class()");
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
            out.write(arg_type+" "+arg_name);
        } catch (IndexOutOfBoundsException e) {

        }

        out.write(")");
        visit(n);
    }

    public void visitMethodDeclaration(GNode n) {
        String decorator = null;
        if(n.get(1) != null) {
            decorator = n.get(1).toString().replace("()", "");
        }
        String return_type = n.get(2).toString().replace("Type()", "").toLowerCase();
        String method_name = n.get(3).toString();
        out.write(return_type+" "+method_name+"(");
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        String constructor = "__"+n.get(2).toString().replace("()", "");
        out.write(constructor+"(");
        visit(n);
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        String class_name = "__"+n.get(1).toString().replace("()", "");
        out.write("struct "+class_name+";");
        out.write("struct "+class_name+"_VT;");
        out.write("struct "+class_name+" {");
        writeClassBase(n.get(1).toString());
        visit(n);
        out.write("};");
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

