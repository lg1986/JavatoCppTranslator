package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by charlottephillips on 09/03/17.
 * Traversing the AST and mutating the existing nodes from the Java AST to the C++ AST
 */
public class CppTraversal extends Visitor {

    protected cppAST cpp = new cppAST();

    public GNode classNode;
    public String className;
    public String packageName;

    public cppMethodObject methObj;
    public cppClassObject classObj;

    ArrayList<cppClassObject> objects = new ArrayList<cppClassObject>();

    public void visitFieldDeclaration(GNode n){
        visit(n);
    }

    public void visitMethodDeclaration(GNode n) {
        MethodTraversal methTrav = new MethodTraversal();
        GNode methNode = GNode.create("MethodDeclaration");
        methObj = methTrav.getBlock(n, methNode);
        classNode.addNode(methNode);
        classObj.methods.add(methObj);
    }

    public void visitClassDeclaration(GNode n) {
        className  = n.get(1).toString().replace("()", "");
        classObj = new cppClassObject();
        classObj.cName = className;

        // This is so that we can ignore the method that has the main
        // the classes would have the details
        if(className.toLowerCase().compareTo(packageName) != 0) {
            classNode = GNode.create("ClassDeclaration");
            classNode.addNode(GNode.create(className.replace("()", "")));
            visit(n);
            objects.add(classObj);
            cpp.addAST(classNode);
        } else{
            return;
        }

    }
    public void visitPackageDeclaration(GNode n) {
        try {
            this.packageName = n.getNode(1).get(1).toString();
        } catch (Exception ignored){

        }
        visit(n);
    }


    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    /**
     * This essentially gets the cppAST
     * object that was created
     * @param cppList
     * @return cppAST
     */

    public cppAST getSummary(List<Node> cppList) {
        for(Node n: cppList) {
            super.dispatch(n);
        }
        return cpp;
    }

    static class cppMethodObject {
        String returnType = "";
        String parameters = "";
        String block = "";
        String methName = "";
        String cName = "";

        public String toString(){
            return "Return type: "+returnType+" parameters: "+parameters+
                    "block:"+ block +" methName:"+methName;
        }
    }

    static class cppClassObject {
        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<cppMethodObject> methods = new ArrayList<cppMethodObject>();
        String cName;

        public String toString(){
            String s = "";
            return methods.toString();
        }
    }

    /**
     * The static class that holds the important information
     * from the mutation of the Java Nodes.
     */
    static class cppAST {
        protected static List<Node> cppasts = new ArrayList<Node>();

        public List<Node> getDependency() {
            return cppasts;
        }

        public void addAST(GNode n) {
            this.cppasts.add(n);
        }

        public String toString() {
            String ast_string = "";
            for(Node l: cppasts) {
                ast_string += l.toString() + "\n";
            }
            return ast_string;
        }
    }
}