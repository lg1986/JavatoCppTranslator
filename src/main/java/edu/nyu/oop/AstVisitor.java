package edu.nyu.oop;

import org.slf4j.Logger;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.Runtime;

/**
 * Created by rishabh on 19/02/17.
 */
public class AstVisitor extends Visitor {

    private Runtime runtime;

    public void visitImportDeclaration(GNode n){
        System.out.println(n);
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    public void getAllASTs(Node n){
        super.dispatch(n);
    }

}
