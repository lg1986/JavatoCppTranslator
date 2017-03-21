package edu.nyu.oop;

import xtc.lang.CPrinter;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.lang.CFeatureExtractor;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by charlottephillips on 09/03/17.
 * Traversing the AST and mutating the existing nodes from Java in C++ format
 */
public class CppTraversal extends Visitor {

    // need to get AST from Phase 1 and mutate

    public GNode classNode;
    public GNode packageNode;

    public GNode addNewNode(GNode n, String type, int limit) {
        GNode newNode = GNode.create(type, limit);
        for(int i = 0; i<limit; i+=1) {
                newNode.addNode(n.getNode(i));
        }
        return newNode;
    }

    // @Lina
    // Mutating field declarations into C++ version
    // Building a string with the name, return type & replacing in tree
    public GNode mutateFieldDeclaration(GNode n) {
        String returnType = n.get(0).toString().replace("Type()","").toLowerCase();
        String fieldName = n.get(2).toString();
        String fieldBase = returnType+" "+fieldName;
        System.out.println(fieldBase);
        n.toString().replaceAll("",fieldBase);
        System.out.println(n.getName());
        System.out.println(n.get(0));
        classNode.add(addNewNode(n,fieldBase,5));
        return n;
    }

    // @Lina
    // Mutating the method declaration into C++ verison
    // Building a string with the type, name
    public GNode mutateMethodDeclaration(GNode n) {
        String returnType = n.get(2).toString().replace("Type()","").toLowerCase();
        String methodName = n.get(3).toString();
        String methodBase = returnType+" "+methodName;
        System.out.println(methodBase);
        System.out.println(n.get(0));
        return n;
    }

    //@Charlie
    public GNode mutateConstructorDeclartion(GNode n) {
        String constructorName = "__"+n.get(2).toString().replace("()","");
        String constructorBase = constructorName+"(";
        System.out.println(constructorBase);
        System.out.println(n.get(0));
        return n;
    }

    //@Charlie
    public GNode mutateClassDeclaration(GNode n) {
        String className = "__"+n.get(1).toString().replace("()","");
        // struct classname {} --> syntax
        // omit the {} since that is denoted by child element in AST
        String classBase = "struct "+className;
        System.out.println(classBase);
        System.out.println(n.get(0));
        return n;
    }

    // Visit methods for each scope construct, mutating each node

    public void visitMethodDeclaration(GNode n) {
        mutateMethodDeclaration(n);
        visit(n);
    }

    public void visitFieldDeclaration(GNode n) {
        mutateFieldDeclaration(n);
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        mutateConstructorDeclartion(n);
        visit(n);
    }

    public void visitClassDeclaration(GNode n) {
        mutateClassDeclaration(n);
        visit(n);
        packageNode.addNode(classNode);
    }

    public void visitCompilationUnit(GNode n) {
        System.out.println(n);
        visit(n);
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }
}