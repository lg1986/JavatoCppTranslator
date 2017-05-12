package edu.nyu.oop;

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
        }
        asts = new ArrayList<>();
        collect(tree);
        return this.asts;
    }

    public void visitFieldDeclaration(GNode n) {
        classNode.getNode(FIELDS).addNode(n);
    }
    public void visitMethodDeclaration(GNode n) {
        classNode.getNode(METHOD).addNode(n);
        visit(n);
    }
    public void visitConstructorDeclaration(GNode n) {
        classNode.getNode(CONSTRS).addNode(n);
        visit(n);
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