package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * Created by rishabh on 27/03/17.
 */
public class ArgumentsTraversal extends Visitor {

    public String arg;

    public void visitPrimaryIdentifier(GNode n) {
        arg = n.get(0).toString();
        visit(n);
    }

    public void visitSelectionExpression(GNode n) {
        arg += n.getNode(0).get(0).toString();
        arg += "." + n.get(1).toString();
        visit(n);
    }

    public void visitArguments(GNode n){
        visit(n);
    }

    public void visitCallExpression(GNode n){
        System.out.println(n);
        visit(n);
        arg += "." + n.get(2);
    }

    public void visit(Node n){
        for(Object o : n){
            if(o instanceof Node){
                dispatch((Node) o);
            }
        }
    }

    public String getArgDetails(Node n){
        super.dispatch(n);
        return arg;
    }

}
