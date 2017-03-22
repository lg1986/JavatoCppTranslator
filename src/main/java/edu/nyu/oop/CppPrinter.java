package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class CppPrinter extends Visitor {

    private Printer printer;
    public ArrayList<GNode> cppContainer = new ArrayList<GNode>();
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public CppPrinter(Node n) throws IOException{
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/cppOutput.h");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        getDataLayoutAST(n);
        writeBeginning();
        writeCpp(n,n.getName().toString());
        writeEnd();
        printer.flush();

    }


    public void getDataLayoutAST(Node n) {
        DependencyDataLayoutTraversal visitor = new DependencyDataLayoutTraversal();
        CppTraversal traversing = new CppTraversal();
        CppTraversal.cppAST everything = traversing.getAllCppAST(n);
        List<Node> list = everything.getDependency();
        //this.cppContainer = visitor.getSummary(dependenceyList).dependencyAsts;
    }

    public void writeBeginning() throws IOException {
        printer.pln("#include <iostream>");
        printer.pln("#include \"java_lang.h\"");
        printer.pln();
        printer.pln("using namespace edu::nyu::oop;");
        printer.pln("namespace edu{");
        printer.pln("namespace nyu{");
        printer.pln("namespace oop{");
    }

    public void writeEnd() throws IOException {
        printer.pln("};");
        printer.pln("};");
        printer.pln("};");
    }

    private void cout(String line) {
    printer.incr().indent().pln("cout << \"" + line + "\" << endl;").decr();
    }

    public void writeCpp(Node n,String className) throws IOException {
        String arg_name = null;
        String arg_type = null;

        printer.pln(className);
        printer.pln("static Class __class()");

        try {
            Node temp = n.getNode(0);
            arg_name = temp.get(3).toString();
            arg_type = temp.getNode(1).getNode(0).get(0).toString();
            Node arr = temp.getNode(1).getNode(1);
            if(arr != null) {
                arg_type += "[]";
            }
            printer.p(arg_type+" "+arg_name);
        } catch (IndexOutOfBoundsException e) {

        }
        printer.pln(")");
        visit(n);
    }

    public void visitMethodDeclaration(GNode n) {
        String decorator = null;
        if(n.get(1) != null) {
            decorator = n.get(1).toString().replace("()", "");
        }
        String return_type = n.get(2).toString().replace("Type()", "").toLowerCase();
        String method_name = n.get(3).toString();
        printer.p(return_type+" "+method_name+"(");
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        String constructor = "__"+n.get(2).toString().replace("()", "");
        printer.p(constructor+"(");
        visit(n);
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        String class_name = "__"+n.get(1).toString().replace("()", "");
        printer.pln("struct "+class_name+";");
        printer.pln("struct "+class_name+"_VT;");
        printer.pln("struct "+class_name+" {");
        visit(n);
        printer.pln("};");
    }


    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    public void collect() {
        for(Node n: cppContainer) {
            super.dispatch(n);
        }
    }
}
