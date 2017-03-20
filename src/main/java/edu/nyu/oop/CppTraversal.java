package edu.nyu.oop;

import xtc.lang.CPrinter;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.lang.CFeatureExtractor;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.util.List;


/**
 * Created by charlottephillips on 09/03/17.
 */
public class CppTraversal extends Visitor {

    public static DependencyDataLayoutTraversal.dependencyAST dataLayout = new DependencyDataLayoutTraversal.dependencyAST();
    public GNode classNode;
    public GNode packageNode;


    public GNode mutateFieldDeclaration(GNode n) {

    }

    public GNode mutateMethodDeclaration(GNode n) {

    }

    public GNode mutateConstructorDeclartion(GNode n) {

    }

    public GNode mutateClassDeclaration(GNode n) {

    }

    public void visitMethodDeclaration(GNode n) {
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

    public DependencyDataLayoutTraversal.dependencyAST getSummary(List<Node> dependencyList) {
        for (Node n : dependencyList) {
            packageNode = GNode.create("PackageDeclaration", 20);
            packageNode.addNode(n.getNode(0));
            super.dispatch(n);
            dataLayout.addASTNode(packageNode);
        }
        return dataLayout;
    }
}