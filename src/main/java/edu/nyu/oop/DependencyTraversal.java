package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.tree.Location;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Charlotte Phillips & Stephanie McAleer on 26/02/17.
 */
public class DependencyTraversal extends Visitor {

    // created nested static class that will have the new AST with the data layout

    private AstVisitor astVisitor;
    private dependencyAST asts = new dependencyAST();


    public void getAllDependencyAsts(Node node){
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST visitor = astVisitor.getAllASTs(node);
    }

    public void visitClassDeclaration(Node n){
        asts.nodes += n.getName() + " ";
        asts.nodes += n.getString(3) + " ";
        asts.count++;
        visit(n);
    }

    public void visitFieldDeclaration(Node n){
        astVisitor.asts.addAST(n);
        visit(n);
    }

    public void visitMethodDeclaration(Node n){
        asts.nodes += n.getName() + " ";
        asts.nodes += n.getString(3) + " ";
        asts.count++;
        visit(n);
    }

    public void visitConstructorDeclaration(Node n){
        asts.nodes += n.getName() + " ";
        asts.nodes += n.getString(3) + " ";
        asts.count++;
        visit(n);
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }


    public dependencyAST getSummary(Node n) {
        super.dispatch(n);
        return asts;
    }

    static class dependencyAST {
        int count = 0;
        String names = "";
        String nodes = "";

        public String toString() {
            return "Method count: " + count + System.lineSeparator() +
                    "Method names: " + names + System.lineSeparator() +
                    "Node names: " + nodes + System.lineSeparator();
        }
    }

}

