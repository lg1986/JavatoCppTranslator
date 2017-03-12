package edu.nyu.oop;

import com.sun.org.apache.xpath.internal.operations.Variable;
import xtc.tree.*;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.tree.Location;


import java.util.*;

public class DependencyTraversal extends Visitor {

    // created nested static class that will have the new AST with the data layout

    public static dependencyAST dataLayout = new dependencyAST();
    public GNode currentNode;
    public GNode classNode;

    public void visitFieldDeclaration(GNode n){
        classNode.addNode(n);
        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        GNode methodDetails = GNode.create("MethodDeclaration", 5);

        for(int i = 0; i<5; i++){
            try{
                methodDetails.addNode(n.getNode(i));
            } catch(java.lang.ClassCastException e){
                GNode methodName = GNode.create(n.get(i).toString(), 1);
                methodDetails.addNode(methodName);
            }

        }
        classNode.addNode(methodDetails);
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n){
        visit(n);
    }


    public void visitClassDeclaration(GNode n){
        classNode = GNode.create("ClassDeclaration", 20);
        visit(n);
        dataLayout.addASTNode(classNode);

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
            super.dispatch(n);
        }
        return dataLayout;
    }


    static class dependencyAST{

        public ArrayList<GNode> dependencyAsts  = new ArrayList<GNode>();

        public void addASTNode(GNode n) {this.dependencyAsts.add(n);}

        public String toString() {
            return dependencyAsts.toString();
        }
    }


}
