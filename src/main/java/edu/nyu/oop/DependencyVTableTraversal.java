package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;

public class DependencyVTableTraversal extends Visitor {
    public static vtableAST vtable = new vtableAST();

    public GNode packageNode;

    public void visitPackageDeclaration(GNode n){
        packageNode = GNode.create("PackageDeclaration");
        packageNode.addNode(n.getNode(1));
        visit(n);

    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    public vtableAST getSummary(List<Node> dependencyList){
        for(Node n: dependencyList){
            super.dispatch(n);
        }
        return vtable;
    }


    static class vtableAST {
        public ArrayList<GNode> vtableAsts = new ArrayList<GNode>();

        public void addASTNode(GNode n){
            this.vtableAsts.add(n);
        }

        public String toString(){
            return this.vtableAsts.toString();
        }
    }

}
