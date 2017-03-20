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
 */
public class CppTraversal extends Visitor {

    // need to get AST from Phase 1 and mutate

    public GNode classNode;
    public GNode packageNode;


    public GNode mutateFieldDeclaration(GNode n) {
        String nodeString = n.toString();

        String[] splittedDeclaration = nodeString.split(" ");
        System.out.println(splittedDeclaration[0]);
        return n;
    }

    public GNode mutateMethodDeclaration(GNode n) {
        String nodeString = n.toString();
        String[] splittedDeclaration = nodeString.split(" ");
        System.out.println(splittedDeclaration[0]);
        return n;
    }

    public GNode mutateConstructorDeclartion(GNode n) {
        String nodeString = n.toString();
        String[] splittedDeclaration = nodeString.split(" ");
        System.out.println(splittedDeclaration[0]);
        return n;
    }

    public GNode mutateClassDeclaration(GNode n) {
        String className = "__"+n.get(1).toString().replace("()","");
        // struct class name {} --> syntax
        // omit the {} since that is denoted by child element in AST
        String classBase = "struct"+className;
        String cppSyntax = n.get(1).toString().replace("",className);
        return n;
    }


    public void visitMethodDeclaration(GNode n) {
        String nodeString = n.toString();
        String[] splittedDeclaration = nodeString.split(" ");
        System.out.println(splittedDeclaration[0]);
        mutateMethodDeclaration(n);
        visit(n);

    }

    public void visitFieldDeclaration(GNode n) {
        classNode.addNode(n);
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