package edu.nyu.oop;


import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class jppPrinter extends Visitor {

    private Printer printer;
    private List<Node> jppList;
    private String packageName;

    private String currentClassName;

    /**
     * Constructor - This initiates the creation of the header file
     * @param n
     * @throws IOException
     */
    public jppPrinter(Node n) throws IOException {
        Writer w;
        try {
            FileOutputStream fos = new FileOutputStream("output/output.cpp");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "utf-8");
            w = new BufferedWriter(ows);
            this.printer = new Printer(w);
        } catch (Exception e) {
            throw new RuntimeException("Output location not found. Create the /output directory.");
        }
        getOutputImplementations(n);
        writeStartBaseLayout();
        collect();
        writeEndBaseLayout();
        printer.flush();
    }

    public void getOutputImplementations(Node n) {
        ArrayList<GNode> output = new ArrayList<GNode>();

        // Phase 1
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependencyList = depe.getDependency();

        //Phase 4
        jppTraversal jppTraversalVisitor = new jppTraversal();
        this.jppList = jppTraversalVisitor.getSummary(dependencyList);

    }

    public void writeStartBaseLayout() {
        printer.pln("#include \"output.h\"");
        printer.pln("using namespace nyu::edu::oop;");
        printer.pln("namespace nyu{");
        printer.pln("namespace edu{");
        printer.pln("namespace oop{");
    }

    public void writeEndBaseLayout() {
        printer.pln("}");
        printer.pln("}");
        printer.pln("}");
    }



    public void printClassGenerics() {
        currentClassName = "__"+currentClassName;
        printer.pln(currentClassName+"::"+currentClassName+"() : __vptr(&__vtable) {}");
        printer.pln("Class "+currentClassName+"::__class() {");
        printer.indentMore();
        printer.pln("static Class k = ");
        printer.indentMore().indentMore();
        String nextLine = "new __Class(__rt::literal(\"nyu.edu.oop."+currentClassName.replace("__","")+"\"), __Object::__class());";
        printer.pln(nextLine);
        printer.indentMore();
        printer.pln("return k;");
        printer.pln("}");
        printer.pln(currentClassName+"_VT " +currentClassName+"::__vtable;");

    }

    public void visitStringLiteral(GNode n) {
        printer.p(n.get(0).toString().replace("", "")+";");
        visit(n);
    }
    public void visitIntegerLiteral(GNode n) {
        printer.p(n.get(0).toString().replace("\"", "")+";");
        visit(n);
    }

    public void visitCallExpression(GNode n) {
        String[] secp = n.get(0).toString().split(" ");
        String call = "";
        if(secp[0].compareTo("System")==0) {
            call = "";
            for (String s : secp) {
                call += s + ".";
            }
            call += n.get(2).toString();
            if (n.getNode(3).size() > 0) {
                call += "(" + n.getNode(3).get(0).toString() + ")";
            } else {
                call += "()";
            }
            if (call.contains("System.out.println") || call.contains("System.out.print")) {

                call = call.replace("System.out.println", "cout <<");
                call = call.replace("System.out.print", "cout <<");
                call += "<< endl";
            }
        } else {
            call = n.get(0)+"->_vptr->"+n.get(2).toString()+"("+n.get(0)+")";
        }
        printer.p(call);
        visit(n);
    }

    public void visitNewClassExpression(GNode n) {
        printer.p("new __"+n.getNode(2).get(0).toString()+"()");
    }

    public void visitDeclarator(GNode n) {
        if(n.get(2) == null) {
            printer.p(" " + n.get(0).toString()+";");
        } else {
            printer.p(" " + n.get(0).toString()+" = ");
            visit(n);
        }
        printer.pln();
    }

    public void visitQualifiedIdentifier(GNode n) {
        visit(n);
    }

    public void visitPrimaryIdentifier(GNode n) {
        System.out.println("here!"+n);
        printer.p(n.get(0).toString());
        visit(n);
    }
    public void visitReturnStatement(GNode n) {
        printer.p("return ");
        visit(n);
        printer.pln(";");
    }

    public void getCorrectExp(GNode n) {
        System.out.println(n);
        if(n.getNode(0).getName().compareTo("PrimaryIdentifier")==0) {
            printer.p(n.getNode(0).get(0)+" = ");
        }
        if(n.getNode(2).getName().compareTo("PrimaryIdentifier")==0) {
            printer.p(n.getNode(2).get(0)+";");
        }
    }

    public void visitExpression(GNode n) {
        getCorrectExp(n);
    }

    public void visitExpressionStatement(GNode n) {
        visit(n);
    }
    public void visitBlock(GNode n) {
        visit(n);
    }



    public void visitFormalParameters(GNode n) {
        visit(n);

    }

    public String getRetType(GNode n) {
        String retType = "";
        if(n.getNode(2).size() == 0) {
            retType = "void";
        } else {
            retType = n.getNode(2).getNode(0).get(0).toString();
            if (retType.compareTo("int") == 0) retType = "int32_t";
        }
        return retType;
    }

    public String convertIntBool(String ty) {
        String ret = ty;
        if(ty.equals("int") || ty.equals("Integer")) ret = "int32_t";
        if(ty.equals("boolean")) ret = "bool";
        return ret;
    }

    public String getMethodParams(GNode n) {
        String paramsList = "";
        Node params = n.getNode(4);
        if(params!=null) {
            for(int i = 0; i<params.size(); i++) {
                try {
                    if (params.getNode(i).getNode(1).size() > 0) {
                        String paramTy = params.getNode(i).getNode(1).getNode(0).get(0).toString();
                        paramTy = convertIntBool(paramTy);
                        paramsList += paramTy + " " + params.getNode(i).get(3) + ", ";
                    } else {
                        paramsList += "Object ";
                    }
                } catch (Exception ignored) {
                    System.out.println(ignored);
                }
            }
        }
        paramsList = currentClassName.replace("__", "")+" __this, "+paramsList;
        paramsList = paramsList.replaceAll(", $", "");
        return "( "+paramsList+" )";
    }

    public void visitConstructorDeclaration(GNode n) throws IOException {

    }
    public void visitMethodDeclaration(GNode n) throws IOException {
        String methodName = n.get(3).toString();
        String retType = getRetType(n);
        printer.p(retType+" "+currentClassName+"::"+methodName);
        String paramList = getMethodParams(n);
        printer.pln(paramList+"{");
        visit(n);
        printer.pln("}");
    }

    public void visitClassDeclaration(GNode n) throws IOException {
        currentClassName = n.get(1).toString().replace("()", "");
        if(currentClassName.toLowerCase().compareTo(this.packageName) == 0) {
            MainPrinter mp = new MainPrinter(n);
            return;
        } else {
            printClassGenerics();
        }
        visit(n.getNode(5));
    }

    public void visitPackageDeclaration(GNode n) {
        try {
            this.packageName = n.getNode(1).get(1).toString();
        } catch (Exception ignored) {

        }
    }

    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) dispatch((Node) o);
        }
    }

    public void collect() {
        for(Node n:jppList) {
            super.dispatch(n);
        }
    }
}