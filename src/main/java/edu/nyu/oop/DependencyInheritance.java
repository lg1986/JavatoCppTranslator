package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.type.JavaAST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 22/04/17.
 */

class TreeNode{
    String className;
    String packageName;
    TreeNode parent;
    List<TreeNode> extendsNodes = new ArrayList<>();
    Node ast;

    public TreeNode(String packageName, String className, Node ast){
        this.className = className;
        this.packageName = packageName;
        this.ast = ast;
    }

    public String toString(){
        return this.className+" "+this.packageName+" ";
    }
}
public class DependencyInheritance extends Visitor {

    public TreeNode inheritanceTree = new TreeNode("Java", "Object", null);
    public List<GNode> headerAsts;

    public void getDependencyInheritance(Node n){
        CreateHeaderAST visitor = new CreateHeaderAST();
        this.headerAsts = visitor.getHeaderAsts(n);
        simulateInheritance();
    }

    public Node inCurrPackage(String extender, String currPackage){

        for(GNode headerAst: headerAsts){
            if(currPackage.equals(headerAst.getString(0)) &&
                    extender.equals(headerAst.getString(1))){
                return headerAst;
            }
        }
        return null;
    }

    public Node notCurrPackage(String extender, String currPackage){
        for(GNode headerAst: headerAsts){
            if(!currPackage.equals(headerAst.getString(0))
                    && extender.equals((headerAst.getString(1)))){
                return headerAst;
            }
        }
        return null;
    }

    public Node getExtenderClass(String extender, String currPackage){

        Node currPackExtender = inCurrPackage(extender, currPackage);

        if(currPackExtender != null){
            return currPackExtender;
        }
        else{

            Node notCurrPackExtender = notCurrPackage(extender, currPackage);
            return notCurrPackExtender;
        }
    }

    public TreeNode createTreeNode(GNode n){
        String packageName = n.getString(0);
        String className =  n.getString(1);
        return new TreeNode(packageName, className, n);
    }

    public void simulateInheritance(){
        for(GNode headerAst:headerAsts){
            TreeNode classTreeNode = createTreeNode(headerAst);

            if(headerAst.get(2) != null){
                Node extenderClass = getExtenderClass(
                        headerAst.getNode(2).getString(0),
                        headerAst.getString(0));
                TreeNode extenderClassTreeNode = createTreeNode((GNode)extenderClass);
                classTreeNode.extendsNodes.add(extenderClassTreeNode);
            } else {
                classTreeNode.extendsNodes = null;
            }
            inheritanceTree.extendsNodes.add(classTreeNode);
        }

        System.out.println(inheritanceTree.extendsNodes.toString());

    }


    public void visit(Node n){
        for(Object o: n){
            if(o instanceof Node) dispatch((Node) o);
        }
    }



}
