/*
 * Phase 2 - Create DataLayout and VTable ASTs
 *
 * Author: Team j++
 *
 * This module creates a tree that
 * displays the inheritance heirarchy.
 *
 */

package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * TreeNode class that creates the inheritance
 * heirarchy for all the ASTs that have been provided.
 */
class TreeNode{
    String className;
    String packageName;
    TreeNode extendsNodes = null;
    Node ast;

    public TreeNode(String packageName, String className, Node ast){
        this.className = className;
        this.packageName = packageName;
        this.ast = ast;
    }

    public String toString(){
        if(this.extendsNodes != null){
            return this.className+" "+this.packageName+
                    " extends "+this.extendsNodes.toString();
        } else{
            return this.className+" "+this.packageName;
        }
    }
}

/**
 * CreateDependencyTree -- Gets the inheritance relationships
 * and constructs the tree using the TreeNode class.
 */
public class CreateDependencyTree extends Visitor {

    public List<TreeNode> inheritanceTree = new ArrayList<>();
    public List<GNode> headerAsts;
    public List<GNode> inheritanceSimAsts;

    /**
     * Method that allows to interface with the program to
     * generate the dependency tree.
     * @param n
     * @return
     */
    public List<TreeNode> getDependencyInheritance(Node n){
        CreateHeaderAST visitor = new CreateHeaderAST();
        this.headerAsts = visitor.getHeaderAsts(n);
        simulateInheritance();
        return this.inheritanceTree;
    }


    /**
     * Util method.
     * @param n
     * @return
     */
    public TreeNode createTreeNode(GNode n){
        String packageName = n.getString(0);
        String className =  n.getString(1);
        return new TreeNode(packageName, className, n);
    }

    /**
     * This method checks if the class that is being extended
     * is the same package.
     * @param extender
     * @param currPackage
     * @return If it is, it returns the correct AST.
     * If not, returns null.
     */
    public Node inCurrPackage(String extender, String currPackage){
        for(GNode headerAst: headerAsts){
            if(currPackage.equals(headerAst.getString(0)) &&
                    extender.equals(headerAst.getString(1))){
                return headerAst;
            }
        }
        return null;
    }

    /**
     * Complement method for the inCurrPackage method.
     * @param extender
     * @param currPackage
     * @return
     */
    public Node notCurrPackage(String extender, String currPackage){
        for(GNode headerAst: headerAsts){
            if(!currPackage.equals(headerAst.getString(0))
                    && extender.equals((headerAst.getString(1)))){
                return headerAst;
            }
        }
        return null;
    }

    /**
     * This gets the extender class.
     * @param extender
     * @param currPackage
     * @return
     */

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

    public void appendInheritance(GNode headerAst, TreeNode classTreeNode){
        classTreeNode.extendsNodes = null;
        while(headerAst.get(2) != null){
             Node extenderNode = getExtenderClass(
                    headerAst.getNode(2).getString(0),
                    headerAst.getString(0));

             TreeNode extenderClassTree = createTreeNode((GNode)extenderNode);
             classTreeNode.extendsNodes = extenderClassTree;
             classTreeNode = extenderClassTree;
             headerAst = (GNode) extenderNode;
        }
    }
    /**
     * This method essentially constructs the tree by calling the
     * methods above in logical order.
     */
    public void simulateInheritance(){
        for(GNode headerAst:headerAsts){
            TreeNode classTreeNode = createTreeNode(headerAst);
            if(headerAst.get(2) != null){
                appendInheritance(headerAst, classTreeNode);
            } else {
                classTreeNode.extendsNodes = null;
            }
            inheritanceTree.add(classTreeNode);
        }
    }


    public List<TreeNode> reOrderChain(TreeNode n){
        List<TreeNode> correctOrder = new ArrayList<>();
        TreeNode head = n;
        correctOrder.add(head);
        while(head.extendsNodes != null){
            correctOrder.add(head.extendsNodes);
            head = head.extendsNodes;
        }
        Collections.reverse(correctOrder);
        return correctOrder;
    }


    public void stackItUp(Node stack, GNode currNode, GNode orignal){
        Node methNode = stack.getNode(2).getNode(2);
        Node fieldNode = stack.getNode(2).getNode(1);
        Node constrNode = stack.getNode(2).getNode(0);


    }

    public GNode getInheritedStructure(TreeNode n){
        Node orignal = n.ast;
        List<TreeNode> inherit = reOrderChain(n);
        System.out.println(inherit);
        if(inherit.size() == 1) return (GNode)inherit.get(0).ast;
        else{
            GNode inheritSim = GNode.create("ClassDeclaration");
            inheritSim.add(orignal.getString(0));
            inheritSim.add(orignal.getString(1));

        }
        return null;

    }
    public void testing(Node n){
        CreateDependencyTree headerAST = new CreateDependencyTree();
        List<TreeNode> inheritanceTree = headerAST.getDependencyInheritance(n);
        for(TreeNode eachClassNode: inheritanceTree){
            GNode correctRep =  getInheritedStructure(eachClassNode);
//            inheritanceSimAsts.add(correctRep);
        }

    }



}
