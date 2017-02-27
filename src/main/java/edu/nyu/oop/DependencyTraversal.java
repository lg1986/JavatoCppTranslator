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
    protected dependencyASTs asts = new dependencyASTs();


    public void getAllDependencyAsts(Node node){
        AstVisitor astVisitor = new AstVisitor();
        AstVisitor.completeAST visitor = astVisitor.getAllASTs(node);
    }

    public void visitClassDeclaration(Node n){
        astVisitor.asts.addAST(n);
        visit(n);
    }

    public void visitFieldDeclaration(Node n){
        astVisitor.asts.addAST(n);
        visit(n);
    }

    public void visitMethodDeclaration(Node n){
        astVisitor.asts.addAST(n);
        visit(n);
    }

    public void visitConstructorDeclaration(Node n){
        astVisitor.asts.addAST(n);
        visit(n);
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    public dependencyASTs getAllASTs(Node n){
        super.dispatch(n);
        return asts;
    }

    static class dependencyASTs {
        private List<Node> asts = new ArrayList<Node>();

        public List<Node> getDependency(){
            return asts;
        }
        public void addAST(Node n) { this.asts.add(n);}

        public String toString(){
            String ast_string = "";
            for(Node l: asts){
                ast_string += l.toString() + "\n";
            }
            return ast_string;
        }
    }

}

