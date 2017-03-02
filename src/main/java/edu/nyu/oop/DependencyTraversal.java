package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.tree.Location;


import javax.sound.midi.SysexMessage;
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

    private dependencyAST asts = new dependencyAST();


    public void visitFieldDeclaration(GNode n){
        asts.nodes += n.getName().toString() + " ";
        asts.count++;
        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        asts.nodes += n.getName().toString() + " ";
        asts.count++;
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n){
        asts.nodes += n.get(0).toString() + " ";
        asts.count++;
        visit(n);
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


    static class dependencyAST {
        public int count = 0;
        public String names = "";
        public String nodes = "";

        public String toString() {
            return "Method count: " + count + System.lineSeparator() +
                    "Method names: " + names + System.lineSeparator() +
                    "Node names: " + nodes + System.lineSeparator();
        }
    }


}
