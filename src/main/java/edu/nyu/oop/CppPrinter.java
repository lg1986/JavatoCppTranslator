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
            FileOutputStream fos = new FileOutputStream("output/cppOutput.cpp");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        headOfFile();
        writeCpp(n,n.getName().toString());
        collect();
        writeEnd();
        printer.flush();
    }

    public void headOfFile() throws IOException {
        printer.pln("#include <iostream>");
        printer.pln("#include \"java_lang.h\"");
        printer.pln();
        printer.pln("using namespace edu::nyu::oop;");
        printer.pln("namespace edu{");
        printer.pln("namespace nyu{");
        printer.pln("namespace oop{");
        printer.pln();
        printer.pln("int main(void) {");
    }

    public void writeEnd() throws IOException {
        printer.pln("};");
        printer.pln("};");
        printer.pln("};");
    }

    private void cout(String line) {
    printer.incr().indent().pln("cout << \"" + line + "\" << endl;").decr();
    }

    public void writeCpp(Node n) throws IOException {

    }

    public void visitMethodDeclaration(GNode n) {

    }

    public void visitConstructorDeclaration(GNode n) {
        String constructor = "__"+n.get(2).toString().replace("()", "");
        printer.p(constructor+"(");
        visit(n);
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        visit(n);
        printer.pln("};");
    }


    public void visit(Node n) {
        cout(n.getName());
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
