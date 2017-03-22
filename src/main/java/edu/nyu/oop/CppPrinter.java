package edu.nyu.oop;

import xtc.lang.CPrinter;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.lang.CFeatureExtractor;
import xtc.tree.Visitor;
import xtc.util.Runtime;

public class CppPrinter {

    Writer w;
    try {
        FileOutputStream fos = new FileOutputStream("output/cppOutput.h");
        OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
        w = new BufferedWriter(ows);
        this.printer = new Printer(w);
    } catch (Exception e) {
        throw new RuntimeException("Output location not found. Create the /output directory.");
    }

    public void getDataLayoutAST(Node n) {
        DependencyDataLayoutTraversal visitor = new DependencyDataLayoutTraversal();
        CppTraversal traversing = new CppTraversal();
        CppTraversal.cppAST everything = traversing.getAllCppAST(n);
        List<Node> list = everything.getDependency();
        //this.dataLayout = visitor.getSummary(dependenceyList).dependencyAsts;
    }

    public void writeBeginning() extends IOException {
        printer.pln("using namespace edu::nyu::oop;");
        printer.pln("namespace edu{");
        printer.pln("namespace nyu{");
        printer.pln("namespace oop{");
    }

    public void writeEnd() extends IOException {
        printer.pln("};");
        printer.pln("};");
        printer.pln("};");
    }

    public void writeCpp(String className) extends IO Exception {
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
        writeClassBase(n.get(1).toString());
        visit(n);
        printer.pln("};");
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
