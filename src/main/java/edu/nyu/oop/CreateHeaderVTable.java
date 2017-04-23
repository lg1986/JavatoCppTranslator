package edu.nyu.oop;

import sun.reflect.annotation.ExceptionProxy;
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

    public String currentOuterVTableString = "";
    public String currentInnerVTableString = "";

    public String currentClassName;
    public String packageName;

    public CreateHeaderVTable(Node n, Printer printer) throws IOException {
        this.printer = printer;
        getVTableAST(n);
        collect();
        printer.flush();

    }

    public void getVTableAST(Node n) {
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
        List<Node> dependenceyList = depe.getDependency();

        DependencyVTableTraversal visitor = new DependencyVTableTraversal();
        DependencyVTableTraversal.vtableAST a = visitor.getSummary(dependenceyList);
        this.vtable = a.vtableAsts;
    }

    public void printStarterVTable(String name) {
        currentOuterVTableString = ("struct __"+name+"_VT{ \n");
        currentOuterVTableString +=("Class __is_a; \n");
        currentOuterVTableString +=("void (*__delete)(__" +name+"*); \n");

        currentInnerVTableString = ("__"+name+"_VT() \n");
        currentInnerVTableString += "\t";
        currentInnerVTableString += (": __is_a(__"+name+"::__class())");
        currentInnerVTableString += ("__delete(&__rt::__delete<__"+name+ ">),");

    }

    public String convertIntBool(String ty) {
        String ret = ty;
        if(ty.equals("int") || ty.equals("Integer")) ret = "int32_t";
        if(ty.equals("boolean")) ret = "bool";
        return ret;
    }

    public String getMethodRet(GNode n) {
        String ret = "";
        if(n.get(2).toString().equals("VoidType()")) ret = "void";
        else {
            ret = n.getNode(2).getNode(0).get(0).toString();
            System.out.println(ret);
        }
        ret = convertIntBool(ret);
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
                } catch (Exception ignored) {}
            }
        }
        paramsList = currentClassName+", "+paramsList;
        paramsList = paramsList.replaceAll(", $", "");
        return "( "+paramsList+" )";
    }

    public void visitMethodDeclaration(GNode n) throws IOException {
        System.out.println(n);
        String methodName = n.getNode(3).toString().replace("()", "");
        String ret = getMethodRet(n);
        String paramsList = getMethodParams(n);

        String outerMethDecl = "";
        outerMethDecl += ret + "(*"+methodName+")"+ paramsList+"; \n";
        currentOuterVTableString += outerMethDecl;

        String innerMethDecl = "";
        String superObj = n.getNode(5).get(0).toString().replace("()", "");

        if(superObj.equals(currentClassName)) {
            innerMethDecl = ",\n"+methodName + "(("+ret+"(*)"+paramsList+")";
            innerMethDecl += "&__"+superObj+"::"+methodName+")";
            currentInnerVTableString += innerMethDecl;
        } else {
            innerMethDecl =",\n"+methodName + "(("+ret+"(*)"+paramsList+")";
            innerMethDecl += "&__"+superObj+"::"+methodName+")";
            currentInnerVTableString += innerMethDecl;
        }
    }

    public void visitConstructorDeclaration(GNode n) throws IOException {
        currentClassName= n.get(0).toString().replace("()", "");


    }

    public void visitClassDeclaration(GNode n) throws IOException {
        currentClassName= n.get(0).toString().replace("()", "");

        // Check if the name of the file is the same and thus, has the main method
        if(currentClassName.toLowerCase().equals(this.packageName)) {
            return;
        } else {
            printStarterVTable(currentClassName);
            visit(n);
            printer.pln(currentOuterVTableString);
            printer.pln(currentInnerVTableString);
            printer.pln("{");
            printer.pln("}");
            printer.pln("};");
            printer.pln();
            printer.pln();
        }
    }


    public void visitPackageDeclaration(GNode n) {
        try {
            this.packageName = n.getNode(0).getNode(1).get(1).toString();
            System.out.println("PACKAGE NAME: "+packageName+"\n \n");
            visit(n);
        } catch (Exception ignored) {

        }
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
