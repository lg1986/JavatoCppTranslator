package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 14/05/17.
 */
public class ResolveDuplicates extends Visitor {

    protected ArrayList<GNode> fields = new ArrayList<>();
    protected ArrayList<GNode> methods = new ArrayList<>();
    protected ArrayList<String> fieldNames = new ArrayList<>();
    protected ArrayList<String> methodNames = new ArrayList<>();
    protected ArrayList<String> changedStrings = new ArrayList<>();

    public void visitFieldDeclaration(GNode n) {
        fields.add(n);
        fieldNames.add(n.getString(1));
    }

    public void visitMethodDeclaration(GNode n) {
        methods.add(n);
        methodNames.add(n.getString(1));
        System.out.println(n);
    }

    public void visitClassDeclaration(GNode n) {
        visit(n);
    }

    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) dispatch((Node)o);
        }
    }

    public List<String> resolveDups(Node n) {
        super.dispatch(n);
        for(int i = 0; i < fieldNames.size(); i++) {
            if(methodNames.contains(fieldNames.get(i))) {
                Node met = methods.get(methodNames.indexOf(fieldNames.get(i)));
                met.set(2, met.get(2)+"mutate");
                changedStrings.add(fieldNames.get(i));
            }
        }
        return changedStrings;
    }


}
