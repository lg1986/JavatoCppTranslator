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

    private  HashMap<String, ArrayList<String>> currentClassLayout;


    public void visitFieldDeclaration(GNode n){
        ArrayList<String> fielNodes = currentClassLayout.get("FieldDeclaration");
        fielNodes.add(n.getName().toString());
        currentClassLayout.put("FieldDeclaration", fielNodes);

        asts.nodes += n.getName().toString() + " ";
        asts.count++;
        visit(n);
    }

    public void visitMethodDeclaration(GNode n){
        ArrayList<String> methNodes = currentClassLayout.get("MethodDeclaration");
        methNodes.add(n.getName().toString());
        currentClassLayout.put("MethodDeclaration", methNodes);

        asts.nodes += n.getName().toString() + " ";
        asts.count++;
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n){
        ArrayList<String> consNodes = currentClassLayout.get("ConstructorDeclaration");
        consNodes.add(n.getName().toString());
        currentClassLayout.put("ConstructorDeclaration", consNodes);

        asts.nodes += n.get(0).toString() + " ";
        asts.count++;
        visit(n);
    }

    public void visitClassDeclaration(GNode n){
        currentClassLayout = new HashMap<String, ArrayList<String>>();

        ArrayList<String> listOfNodes = new ArrayList<>();
        listOfNodes.add(n.getName().toString());
        currentClassLayout.put("ClassDeclaration", listOfNodes);
        currentClassLayout.put("FieldDeclaration", new ArrayList<String>());
        currentClassLayout.put("MethodDeclaration", new ArrayList<String>());
        currentClassLayout.put("ConstructorDeclaration", new ArrayList<String>());
        visit(n);
        asts.dataLayout.add(currentClassLayout);
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
        public ArrayList<HashMap<String, ArrayList<String>>> dataLayout =
                new ArrayList<>();

        public int count = 0;
        public String names = "";
        public String nodes = "";

        public String toString() {
            for (HashMap<String, ArrayList<String>> n : dataLayout){
                System.out.println(n.toString());
            }
            return "Method count: " + count + System.lineSeparator() +
                    "Method names: " + names + System.lineSeparator() +
                    "Node names: " + nodes + System.lineSeparator();
        }
    }


}
