package edu.nyu.oop;

import xtc.lang.CPrinter;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.lang.CFeatureExtractor;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by charlottephillips on 09/03/17.
 * Traversing the AST and mutating the existing nodes from the Java AST to the C++ AST
 */
public class CppTraversal extends Visitor {

    private Runtime runtime;
    protected cppAST cpp = new cppAST();
    public GNode classNode;
    private Printer printer;

    // Add the class declarations to the C++ AST
    // @param GNode of current node visiting
    // @param Name of the class
    // @param Class' type
    GNode addClassCPP(String className, int capacity) {
        GNode classN = GNode.create("ClassDeclaration");
        classN.add(className);
        return classN;
    }

    // Add the field declarations to the C++ AST
    // @param GNode of current node visiting
    // @param Name of the field declaration
    // @param Field's type
    GNode addFieldCPP(GNode node, String className, String type){
        GNode field = GNode.create("FieldDeclaration");
        if (!(className.equals(null) || type.equals(null) || node == null)){
            field.add(className);
            field.add(type);
        }
        String pointerObject = "__"+className.replace("()","")+"_VT*__vptr";
        field.addNode(GNode.create(pointerObject));
        return field;
    }

    // Add the method declarations to the C++ AST
    // @param GNode of current node visiting
    // @param Name of the method
    // @param Method's type
    GNode addMethodCPP(GNode node, String name, String type){
        GNode method = GNode.create("MethodDeclaration");
        if (name.equals(null) || type.equals(null) || node == null){
            return null;
        }
        method.add(name);
        method.add(type);
        return method;
    }

    // Adds the constructor node to the C++ AST
    // @param GNode of current node visiting
    // @param Name of the constructor
    GNode addConstructorCPP(GNode node, String name) {
        GNode constructor = GNode.create("ConstructorDeclaration");
        constructor.add(name);
        return constructor;

    }

    // Visit methods for each scope construct, mutating each node

    public void visitMethodDeclaration(GNode n) {
        cpp.addAST(addMethodCPP(n,n.getName(),n.getClass().getTypeName()));
        visit(n);
    }

    public void visitFieldDeclaration(GNode n) {
        cpp.addAST(addFieldCPP(n,n.getName(),n.getClass().getTypeName()));
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        cpp.addAST(addConstructorCPP(n,n.getName()));
        visit(n);
    }

    public void visitClassDeclaration(GNode n) {
        cpp.addAST(addConstructorCPP(n,n.getName()));
        visit(n);
    }

    public void visitCompilationUnit(GNode n) {
        System.out.println(n);
        visit(n);
    }

    // The double dispatch
    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    // First dispatch, getting the complete C++ AST
    public cppAST getAllCppAST(Node n){
        super.dispatch(n);
        return cpp;
    }

    static class cppAST {
        protected static List<Node> cppasts = new ArrayList<Node>();

        public List<Node> getDependency() { return cppasts; }

        public void addAST(Node n) { this.cppasts.add(n); }

        public String toString() {
            String ast_string = "";
            for(Node l: cppasts) {
                ast_string += l.toString() + "\n";
            }
            return ast_string;
        }
    }
}