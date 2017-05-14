package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import edu.nyu.oop.util.SymbolTableBuilder;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class JppTraversal extends Visitor {
    public GNode classNode;
    public List<GNode> asts;

    private final int MODIFS = 0;
    private final int NAME = 1;
    private final int EXT = 2;
    private final int FIELDS = 3;
    private final int CONSTRS = 4;
    private final int METHOD = 5;


    public List<GNode> getModifiedAsts(Runtime runtime, Node n) {
        DependencyAstVisitor visitor = new DependencyAstVisitor();
        List<GNode> tree = visitor.getDependencyAsts(n);
        for(Node t: tree) {
            SymbolTable table = new SymbolTableBuilder(runtime).getTable(t);
            new MemberAccessCompleter(runtime, table).dispatch(t);
            new OverloadingResolver(runtime, table, t).dispatch(t);
        }
        asts = new ArrayList<>();
        collect(tree);
        return this.asts;
    }

    public boolean checkIfNode(Object n) {
        if(n instanceof String) {
            return false;
        } else {
            return true;
        }
    }
    public void visitBlockDeclaration(GNode n) {

    }

    public void visitFieldDeclaration(GNode n) {
        classNode.getNode(FIELDS).addNode(n);
    }

    public void createConstructorAndAdd(GNode n) {
        GNode constDec = GNode.create("ConstructorDeclaration");
        for(int i = 0; i < n.size(); i++) {
            if(checkIfNode(n.get(i))) constDec.addNode(n.getNode(i));
            else constDec.add(n.get(i));
        }
        classNode.getNode(CONSTRS).addNode(constDec);
    }
    public void visitMethodDeclaration(GNode n) {

        if(n.get(2) == null) {
            createConstructorAndAdd(n);

        } else {
            classNode.getNode(METHOD).addNode(n);
        }
    }
    public void visitConstructorDeclaration(GNode n) {
        classNode.getNode(CONSTRS).addNode(n);
    }

    public void visitClassDeclaration(GNode n) {
        classNode = GNode.create("ClassDeclaration");

        classNode.addNode(n.getNode(0));
        classNode.add(n.getString(1));

        if(n.get(3) != null) {
            classNode.addNode(n.getNode(3));
        } else {
            classNode.add(null);
        }

        GNode fields = GNode.create("FieldDeclarations");
        GNode meths = GNode.create("MethodDeclarations");
        GNode consts = GNode.create("ConstructorDeclarations");
        classNode.addNode(fields);
        classNode.addNode(consts);
        classNode.addNode(meths);
        visit(n);
        asts.add(classNode);
    }

    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    public void collect(List<GNode> tree) {
        for(Node n: tree) {
            super.dispatch(n);
        }
    }
}