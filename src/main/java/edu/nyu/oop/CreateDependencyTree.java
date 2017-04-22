package edu.nyu.oop;

import edu.nyu.oop.util.TypeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


class TreeNode{
    String className;
    String packageName;
    TreeNode parent;
    List<TreeNode> child;

    public String toString(){
        return this.className+" "+this.packageName+" ";
    }
}

public class CreateDependencyTree extends Visitor {

    public TreeNode dependencyTreeNode;
    protected String packageName;

    List<TreeNode> classes = new ArrayList<>();

    /**
     * Method to interface with this class.
     * @param n - This is the root node
     * @return - Returns a Tree that correctly
     * displays the dependency heirarchy.
     */
    public TreeNode getDependencyTree(Node n){
        DependencyAstVisitor visitor = new DependencyAstVisitor();
        List<GNode> dependencyAsts = visitor.getDependencyAsts(n);

        for(GNode dependencyAstNode: dependencyAsts){
            super.dispatch(dependencyAstNode);
        }
        System.out.println(classes.toString());
        return this.dependencyTreeNode;
    }

    public void visitClassDeclaration(GNode n){
        TreeNode varTreeNode = new TreeNode();
        varTreeNode.packageName = packageName;
        varTreeNode.className = n.get(1).toString().replace("()", "");

        String extendClassName = "";
        try {
            Node extendExpression = n.getNode(3);
            extendClassName = extendExpression.getNode(0).
                    getNode(0).get(0).toString();
        } catch (Exception e){
            extendClassName = "";
        }

        classes.add(varTreeNode);
        visit(n);

    }

    public void visitPackageDeclaration(GNode n){
        this.packageName = n.getNode(1).get(1).toString();
        visit(n);
    }

    public void visit(Node n){
        for(Object o: n){
            if(o instanceof Node) dispatch((Node) o);
        }
    }


}
