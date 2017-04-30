/*
 * Phase 2 - Create DataLayout and VTable ASTs
 *
 * Author: Team j++
 *
 * This module is the core of phase-2.
 * It performs the following two functions:
 *
 *
 * 1. It creates a tree for every class with its inheritance
 * herirachy.
 *
 * 2. It creates the stacked data layout VTable structure that
 * was discussed in class.
 *
 * 3. It maintains the structure of the DataLayout VTable AST design.
 *
 *
 */

package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TreeNode class that creates the inheritance
 * heirarchy for all the ASTs that have been provided.
 */
class TreeNode {
    String className;
    String packageName;
    TreeNode extendsNodes = null;
    Node ast;

    public TreeNode(String packageName, String className, Node ast) {
        this.className = className;
        this.packageName = packageName;
        this.ast = ast;
    }

    public String toString() {
        if(this.extendsNodes != null) {
            return this.className+" "+this.packageName+
                   " extends "+this.extendsNodes.toString();
        } else {
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
    public TreeNode javaObjTreeNode;

    /**
     * Method that allows to interface with the program to
     * generate the dependency tree.
     * @param n
     * @return
     */
    public List<TreeNode> getDependencyInheritance(Node n) {
        CreateHeaderAST visitor = new CreateHeaderAST();
        this.headerAsts = visitor.getHeaderAsts(n);
        javaObjTreeNode = new TreeNode("Java", "Object", createJavaObject());
        simulateInheritance();
        return this.inheritanceTree;
    }


    /**
     * Util method.
     * @param n
     * @return
     */
    public TreeNode createTreeNode(GNode n) {
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
    public Node inCurrPackage(String extender, String currPackage) {
        for(GNode headerAst: headerAsts) {
            if(currPackage.equals(headerAst.getString(0)) &&
                    extender.equals(headerAst.getString(1))) {
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
    public Node notCurrPackage(String extender, String currPackage) {
        for(GNode headerAst: headerAsts) {
            if(!currPackage.equals(headerAst.getString(0))
                    && extender.equals((headerAst.getString(1)))) {
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

    public Node getExtenderClass(String extender, String currPackage) {
        Node currPackExtender = inCurrPackage(extender, currPackage);
        if(currPackExtender != null) {
            return currPackExtender;
        } else {
            Node notCurrPackExtender = notCurrPackage(extender, currPackage);
            return notCurrPackExtender;
        }
    }

    /**
     * This is just a simple linked list traversal
     * that constructs the heirarchy.
     * @param headerAst
     * @param classTreeNode
     */
    public void appendInheritance(GNode headerAst, TreeNode classTreeNode) {
        classTreeNode.extendsNodes = null;
        while(headerAst.get(2) != null) {
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
    public void simulateInheritance() {
        for(GNode headerAst:headerAsts) {
            TreeNode classTreeNode = createTreeNode(headerAst);
            if(headerAst.get(2) != null) {
                appendInheritance(headerAst, classTreeNode);
            } else {
                classTreeNode.extendsNodes = null;
            }
            inheritanceTree.add(classTreeNode);
        }
    }


    /**
     * Util class -- this is to flip the inheritance chain and
     * then perform the stacking.
     * @param n
     * @return
     */
    public List<TreeNode> reOrderChain(TreeNode n) {
        List<TreeNode> correctOrder = new ArrayList<>();
        TreeNode head = n;

        correctOrder.add(head);
        while(head.extendsNodes != null) {
            correctOrder.add(head.extendsNodes);
            head = head.extendsNodes;
        }

        TreeNode javaObj = new TreeNode("Java",
                                        "Object", createJavaObject());
        correctOrder.add(javaObj);
        Collections.reverse(correctOrder);
        return correctOrder;
    }

    /**
     * Helper method for StackMethods
     * This method is for easier comparison of the params
     * of the methods that are being compared.
     * @param paramNodes
     * @return ArrayList<String>
     */
    public ArrayList<String> getParamsList(Node paramNodes) {

        ArrayList<String> paramsList = new ArrayList<>();
        for(int i = 0; i<paramNodes.size(); i++) {
            Node paramNode = paramNodes.getNode(i);
            String paramString  = paramNode.getString(0)+
                                  " "+paramNode.get(1);
            paramsList.add(paramString);
        }
        return paramsList;
    }

    /**
     * Helper method for StackMethods
     * @param stackParams
     * @param currParams
     * @return int --> 1 new method
     * it is. int --> 1 overriden method
     */
    public int checkAllParams(ArrayList<String> stackParams,
                              ArrayList<String> currParams) {
        if(stackParams.size() != currParams.size()) return 1;
        if(stackParams.size() == 0) return 0;
        for(int i = 0; i < stackParams.size(); i++) {
            if(!currParams.contains(stackParams.get(i))) return 1;
        }
        return 0;
    }

    /**
     * Helper method for StackMethods
     * @param stackParams
     * @param currParams
     * @return int --> from checkAllParams
     */

    public int checkParams(Node stackParams, Node currParams) {
        ArrayList<String> stackParamsList = getParamsList(stackParams);
        ArrayList<String> currParamsList = getParamsList(currParams);
        return checkAllParams(stackParamsList, currParamsList);
    }

    /**
     * Helper method for StackMethods
     * @param stackMeth
     * @param currMethsNode
     * @param className
     * @return returns the Node if any that needs to be added.
     */
    public Node checkMeth(Node stackMeth, Node currMethsNode, String className) {
        for(int i = 0; i<currMethsNode.size(); i++) {
            Node currMethNode = currMethsNode.getNode(i);
            String methName = currMethNode.getString(1);
            if(methName.equals(stackMeth.getString(1))) {

                int loadrid = checkParams(stackMeth.getNode(2), currMethNode.getNode(2));

                if(loadrid == 1) {
                    GNode retNode = GNode.ensureVariable((GNode) stackMeth);
                    return retNode;
                } else {
                    GNode retNode = GNode.ensureVariable((GNode) stackMeth);
                    retNode.set(3, className);
                    currMethsNode.set(i, retNode);
                    return null;
                }
            }
        }
        return stackMeth;
    }

    /**
     * Main stackMethods function
     * @param stackMeths
     * @param currNode
     * @param className
     *
     * The StackMeths need to be stacked below the currMeths.
     */

    public void stackMethods(Node stackMeths, Node currNode, String className) {
        for(int i = 0; i<stackMeths.size(); i++) {
            Node stackMeth = stackMeths.getNode(i);
            Node toAdd = checkMeth(stackMeth, currNode.getNode(3).getNode(2), className);
            if(toAdd != null) currNode.getNode(3).getNode(2).addNode(toAdd);
        }
    }


    /**
     * Helper method for stackFields
     * @param fields
     * @return
     *
     * Just returns a list of fields. This is for easier
     * comparison.
     */
    public ArrayList<String> getFieldsList(Node fields) {
        ArrayList<String> fieldsList = new ArrayList<>();
        for(int i = 0; i < fieldsList.size(); i++) {
            Node fieldNode = fields.getNode(i);
            String field = fieldNode.getString(0)+" "+fieldNode.getString(1);
        }
        return fieldsList;
    }

    /**
     * Main method for stackingFields up.
     * @param stackFields
     * @param currNode
     *
     * This is the main method for stacking fields according
     * to the data layout convention.
     */

    public void stackFields(Node stackFields, Node currNode) {
        ArrayList<String> currFields = getFieldsList(currNode);
        for(int i = 0; i < stackFields.size(); i++) {
            Node stackFieldNode = stackFields.getNode(i);
            String stackFieldString  = stackFieldNode.getString(0)+" "+
                                       stackFieldNode.getString(1);
            if(!currFields.contains(stackFieldString)) {
                currNode.getNode(3).getNode(0).addNode(stackFieldNode);
            }
        }

    }

    /**
     * Control unit for stacking methods.
     * @param stack
     * @param currNode
     */

    public void stackItUp(Node stack, Node currNode) {
        Node stackMeths = stack.getNode(3).getNode(2);
        Node stackFields = stack.getNode(3).getNode(0);
        stackFields(stackFields, currNode);
        stackMethods(stackMeths, currNode, currNode.getString(1));
    }

    /**
     * Control Unit for constructing the stacked
     * AST for any class.
     * @param n
     * @return A GNode with the correct dataLayout.
     */
    public GNode getInheritedStructure(TreeNode n) {
        Node orignal = n.ast;
        GNode originalMeths = GNode.ensureVariable((GNode)orignal.getNode(3).getNode(2));
        List<TreeNode> inherit = reOrderChain(n);
        if(inherit.size() == 1) return (GNode)inherit.get(0).ast;
        else {
            GNode inheritSim = GNode.create("ClassDeclaration");
            inheritSim.add(orignal.getString(0));
            inheritSim.add(orignal.getString(1));
            inheritSim.addNode(orignal.getNode(2));

            GNode dataLayoutNode = GNode.create("DataLayout");
            dataLayoutNode.add(GNode.create("FieldDeclarations"));
            dataLayoutNode.add(GNode.create("ConstructorDeclarations"));
            dataLayoutNode.add(GNode.create("MethodDeclarations"));

            inheritSim.addNode(dataLayoutNode);

            for(int i = 0; i<inherit.size(); i++) {
                stackItUp(inherit.get(i).ast, inheritSim);
            }

            GNode vtableNode = GNode.create("VTableNode");
            vtableNode.addNode(dataLayoutNode.getNode(2));
            inheritSim.addNode(vtableNode);

            inheritSim.getNode(3).set(2, originalMeths);
            return inheritSim;
        }
    }

    /**
     * To interface with the visitor/creator.
     * @param n
     * @return List<GNode> with all the correct AST representations
     * of the classes.
     */
    public List<GNode> getStackedHeader(Node n) {
        CreateDependencyTree headerAST = new CreateDependencyTree();
        List<TreeNode> inheritanceTree = headerAST.getDependencyInheritance(n);
        inheritanceSimAsts = new ArrayList<>();
        for(TreeNode eachClassNode: inheritanceTree) {
            GNode correctRep =  getInheritedStructure(eachClassNode);
            inheritanceSimAsts.add(correctRep);
        }
        return inheritanceSimAsts;
    }


    public GNode createJavaObject() {
        GNode javaClass = GNode.create("ClassDeclaration");
        GNode methodDecl = GNode.create("MethodDeclarations");
        GNode constDecl = GNode.create("ConstructorDeclarations");
        GNode fieldDeclaration = GNode.create("FieldDeclarations");

        methodDecl.addNode(
            createMethNode("hashCode",
                           "int32_t", null));
        methodDecl.addNode(
            createMethNode("equals",
                           "bool", "Object"));

        methodDecl.addNode(
            createMethNode("getClass",
                           "Class", null));

        methodDecl.addNode(
            createMethNode("toString",
                           "String", null));


        GNode dataLayout = GNode.create("DataLayoutNode");
        GNode vtable = GNode.create("VTableNode");

        vtable.addNode(methodDecl);

        javaClass.add("Java");
        javaClass.add("Object");
        javaClass.add(null);

        dataLayout.addNode(fieldDeclaration);
        dataLayout.addNode(constDecl);
        dataLayout.addNode(methodDecl);

        javaClass.addNode(dataLayout);
        javaClass.addNode(vtable);

        return javaClass;
    }

    public GNode createMethNode(String methName, String ret, String params) {

        GNode methNode = GNode.create("MethodDeclaration");
        methNode.add(ret);
        methNode.add(methName);

        GNode formalParms = GNode.create("FormalParameters");
        GNode paramNode = createFormParmNode(params);
        if(paramNode != null) {
            formalParms.addNode(paramNode);

        }
        methNode.addNode(formalParms);
        methNode.add("Object");
        return methNode;
    }

    public GNode createTypeNode(String typ) {
        GNode type = GNode.create("Type");
        GNode qfI = GNode.create("QualifiedIdentifier");
        qfI.add(typ);
        qfI.addNode(GNode.create("Dimensions"));
        type.addNode(qfI);
        return type;
    }

    public GNode createFormParmNode(String typ) {
        if(typ != null) {
            GNode formParm = GNode.create("FormalParameter");
            GNode modif = GNode.create("Modifiers");

            GNode type = createTypeNode(typ);

            formParm.addNode(modif);
            formParm.addNode(type);

            return formParm;
        }
        return null;
    }


}
