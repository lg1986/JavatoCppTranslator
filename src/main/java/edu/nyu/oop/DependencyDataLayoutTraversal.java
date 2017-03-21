package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;


import java.util.*;

public class DependencyDataLayoutTraversal extends Visitor {

    // created nested static class that will have the new AST with the data layout

    public static dependencyAST dataLayout = new dependencyAST();
    public GNode classNode;
    public GNode packageNode;

    public void visitFieldDeclaration(GNode n) {
        classNode.addNode(n);
        visit(n);
    }

    public GNode collateDetails(GNode n, String type, int limit) {
        GNode details = GNode.create(type, limit);
        for(int i = 0; i<limit; i+=1) {
            try {
                details.addNode(n.getNode(i));
            } catch(java.lang.ClassCastException e) {
                GNode methodName = GNode.create(n.get(i).toString(), 1);
                details.addNode(methodName);
            }
        }
        return details;
    }
    public void visitMethodDeclaration(GNode n) {
        classNode.addNode(collateDetails(n, "MethodDeclaration", 5));
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        classNode.addNode(collateDetails(n, "ConstructorDeclaration", 5));
        visit(n);
    }


    public void visitClassDeclaration(GNode n) {
        classNode = collateDetails(n, "ClassDeclaration", 5);
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

    public dependencyAST getSummary(List<Node> dependencyList) {
        for(Node n: dependencyList) {
            packageNode = GNode.create("PackageDeclaration", 20);
            packageNode.addNode(n.getNode(0));
            super.dispatch(n);
            dataLayout.addASTNode(packageNode);
        }
        return dataLayout;
    }


    static class dependencyAST {

        public ArrayList<GNode> dependencyAsts  = new ArrayList<GNode>();

        public void addASTNode(GNode n) {
            this.dependencyAsts.add(n);
        }

        public String toString() {
            return dependencyAsts.toString();
        }
    }




}
