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

    // Constants to refer to indices of GNode to obtain relevant information
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

    /** checkIfNode used to determine if entered object is a node
     *
     * @param n entered object to be checked
     * @return a boolean value determining if entered object is a node
     */
    public boolean checkIfNode(Object n) {
        if(n instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    public void visitFieldDeclaration(GNode n) {
        classNode.getNode(FIELDS).addNode(n);
    }

    /** createConstructorAndAdd method.
     *  Used to iterate through entered GNode from Java AST, checking for constructor declarations to add to
     *  a created constDec GNode to add to an augmented C++ AST.
     *
     * @param n is the entered GNode
     */
    public void createConstructorAndAdd(GNode n) {
        GNode constDec = GNode.create("ConstructorDeclaration");
        for(int i = 0; i < n.size(); i++) {
            if(checkIfNode(n.get(i))) constDec.addNode(n.getNode(i));
            else constDec.add(n.get(i));
        }
        classNode.getNode(CONSTRS).addNode(constDec);
    }

    /** visitMethod for methods, that either passes n to the above createConstructorAndAdd if n has a null EXT field
     * or adds n into the method space in classNode
     *
     * @param n is the entered GNode
     */
    public void visitMethodDeclaration(GNode n) {

        if(n.get(2) == null) {
            createConstructorAndAdd(n);

        } else {
            classNode.getNode(METHOD).addNode(n);
        }
    }

    /** Inserts the entered GNode n into the constructor field of classNode
     *
     * @param n is the entered GNode
     */
    public void visitConstructorDeclaration(GNode n) {
        classNode.getNode(CONSTRS).addNode(n);
    }

    /** visitClassDeclaration method used to create the ClassDeclaration node in the classNode.
     *
     *
     * @param n is the entered GNode
     */
    public void visitClassDeclaration(GNode n) {
        classNode = GNode.create("ClassDeclaration");

        // Getting properties from the entered GNode
        classNode.addNode(n.getNode(0));
        classNode.add(n.getString(1));

        if(n.get(3) != null) {
            classNode.addNode(n.getNode(3));
        } else {
            classNode.add(null);
        }

        //GNodes created for fields, methods and cosntructors
        GNode fields = GNode.create("FieldDeclarations");
        GNode meths = GNode.create("MethodDeclarations");
        GNode consts = GNode.create("ConstructorDeclarations");

        //Fields, methods and constructors nodes added to classNode
        classNode.addNode(fields);
        classNode.addNode(consts);
        classNode.addNode(meths);
        visit(n);
        asts.add(classNode);
    }

    /** Visit method that makes use of dispatch call from XTC library
     *
     * @param n is the entered Node
     */
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