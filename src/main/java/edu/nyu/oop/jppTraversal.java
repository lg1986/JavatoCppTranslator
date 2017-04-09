package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;


public class jppTraversal extends Visitor {


    protected cppAST cpp = new cppAST();
    protected GNode classNode;
    protected GNode methodNode;




    public String getModifier(Node n){
        return n.get(0).toString();
    }

    public GNode getModifierNode(Node n){

        // Basic Intialization
        int numModifiers = n.size();
        GNode modifiersParent = GNode.create("Modifiers");
        GNode modifiersChild;

        if(numModifiers > 0) {

            // Create the Modifers String
            String modifiersStr = "";
            for (int i = 0; i < numModifiers; i++)
                modifiersStr += getModifier(n.getNode(i)) + " ";

            modifiersChild = GNode.create(modifiersStr);
            modifiersParent.addNode(modifiersChild);

            return modifiersParent;
        } else {
            // If no modifier just add a null
            modifiersParent.addNode(null);

            return modifiersParent;
        }
    }

    // Get any type node from here
    public GNode getTypeNode(Node n){
        GNode typeParent = GNode.create("Type");
        GNode typeChild;
        String qfIdent;
        if(n.get(0).toString().equals("VoidType")){
            qfIdent = "void";
            return typeParent;
        } else {
            qfIdent = n.getNode(0).get(0).toString();
            if(n.get(1) != null) qfIdent += "[]";
        }
        typeChild = GNode.create(qfIdent);
        typeParent.addNode(typeChild);
        return typeParent;
    }

    // Get each FormalParameter
    public GNode getFormalParameter(Node n){
        GNode paramParentNode = GNode.create("FormalParameter");
        GNode paramTypeNode = getTypeNode(n);
        paramParentNode.addNode(paramTypeNode);
        paramParentNode.add(n.get(3).toString());
        return paramParentNode;
    }

    // Get FormalParametersNode
    public GNode getFormalParameters(Node n){
        int numFormalParameters = n.size();
        GNode formalParamsParent = GNode.create("FormalParameters");
        if(numFormalParameters > 0){
            for(int i = 0; i<numFormalParameters; i++){
                formalParamsParent.addNode(getFormalParameter(n.getNode(i)));
            }
        }
        return formalParamsParent;
    }

    public GNode getStringLiteral(Node n){
        GNode stringLiteralNode = GNode.create("StringLiteral");
        stringLiteralNode.add("__rt::literal("+n.get(0).toString()+")");
        return stringLiteralNode;
    }
    public void visitReturnStatement(GNode n){
        GNode retStateNode = GNode.create("ReturnStatement");
        if(n.getNode(0).hasName("StringLiteral")) {
            retStateNode.addNode(getStringLiteral(n.getNode(0)));
        }
        else if(n.getNode(0).hasName("IntegerLiteral")){
            retStateNode.addNode(n.getNode(0));
        }
        else if(n.getNode(0).hasName(("PrimaryIdentifier"))){
            retStateNode.addNode(n.getNode(0));
        }
        methodNode.addNode(retStateNode);
    }

    public void visitMethodDeclaration(GNode n){
        // Creating the MethodDeclaraitonNode
        methodNode = GNode.create("MethodDeclaration");

        // Get return type Node
        methodNode.addNode(getTypeNode(n.getNode(2)));

        // get name of method
        methodNode.add(n.get(3).toString());

        // Formal Parameters
        methodNode.add(getFormalParameters(n.getNode(4)));

        visit(n.getNode(7));
        classNode.addNode(methodNode);
    }

    public void visitClassDeclaration(GNode n) {
        // Creating the GNode
        classNode = GNode.create("ClassDeclaration");

        // Name Node -- 0
        classNode.add(n.get(3).toString());

        // MethodDeclarations node -- 1
        GNode methodDeclarationsNode = GNode.create("MethodDeclarations");
        classNode.addNode(methodDeclarationsNode);

        // Don't visit anything else but the ClassBody directly
        visit(n.getNode(5));

    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }


    public List<Node> getSummary(List<Node> cppList) {
        for(Node n: cppList) {
            super.dispatch(n);
        }
        return cppList;
    }


    static class cppAST {
        protected static List<Node> cppasts = new ArrayList<Node>();

        public List<Node> getDependency() {
            return cppasts;
        }

        public void addAST(GNode n) {
            this.cppasts.add(n);
        }

        public String toString() {
            String ast_string = "";
            for(Node l: cppasts) {
                ast_string += l.toString() + "\n";
            }
            return ast_string;
        }
    }
}