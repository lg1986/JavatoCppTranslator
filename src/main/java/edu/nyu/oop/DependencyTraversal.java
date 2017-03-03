package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Location;


import java.util.*;

public class DependencyTraversal extends Visitor {

    // created nested static class that will have the new AST with the data layout

    private dependencyAST asts = new dependencyAST();
    private int count = 0;
    private GNode astNode;
    private GNode currentNode;

    public void visitFieldDeclaration(GNode n){

        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        currentNode.addNode(n.getNode(0));
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n){
        visit(n);
    }

    public void visitClassDeclaration(GNode n){
        currentNode = n.ensureVariable(n);
        if(count == 0){astNode = currentNode.ensureVariable(n);}
        visit(n);
        astNode.addNode(currentNode);
    }

    public void visit(Node n) {

        for (Object o : n) {
            if (o instanceof Node) {dispatch((Node) o);}
        }
    }

    public dependencyAST getSummary(List<Node> dependencyList) {

        for(Node n: dependencyList) {
            super.dispatch(n);
        }
        return asts;
    }


    static class dependencyAST{

        GNode DependencyAst;

        public String toString() {
            return "Rishabh";
//            return DependencyAst.toString();
        }
    }


}
