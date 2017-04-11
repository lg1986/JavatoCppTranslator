package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;


public class jppTraversal extends Visitor {


    protected cppAST cpp = new cppAST();
    private GNode classNode;

    public String getModifier(Node n){
        return n.get(0).toString();
    }

    public void getModifierNode(Node n, GNode currNode){
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
        } else {
            // If no modifier just add a null
            modifiersParent.addNode(null);
        }
        currNode.addNode(modifiersParent);

    }


    public void getDeclaratorNode(Node n, GNode parentDeclaratorNode){
        GNode childDeclaratorNode = GNode.create("Declarator");
        childDeclaratorNode.add(n.get(0).toString());
        childDeclaratorNode.add(null);
        getCheckStatementNode(n.getNode(2), childDeclaratorNode);
        parentDeclaratorNode.addNode(childDeclaratorNode);
    }

    public void getDeclaratorsNode(Node n, GNode currNode){
        int numDeclarators = n.size();
        GNode parentDeclaratorNode = GNode.create("Declarators");
        for(int i = 0; i<numDeclarators; i++){
            getDeclaratorNode(n.getNode(i), parentDeclaratorNode);
        }
        currNode.addNode(parentDeclaratorNode);
    }

    public GNode getStringLiteral(Node n){
        GNode stringLiteralNode = GNode.create("StringLiteral");
        stringLiteralNode.add("__rt::literal("+n.get(0).toString()+")");
        return stringLiteralNode;
    }

    public void getCheckStatementNode(Node n, GNode currNode){
        if(n.hasName("StringLiteral")){
            currNode.addNode(getStringLiteral(n));
        }
        else if(n.hasName("IntegerLiteral")){
            currNode.addNode(n);
        }
        else if(n.hasName("PrimaryIdentifier")){
            currNode.addNode(n);
        }
        else if(n.hasName("FieldDeclaration")){
            GNode fieldDeclarationNode = GNode.create("FieldDeclaration");

            // ModifierNode
            getModifierNode(n.getNode(0), fieldDeclarationNode);

            //TypeNode
            getTypeNode(n.getNode(1), fieldDeclarationNode);

            //Declarator Node
            getDeclaratorsNode(n.getNode(2), fieldDeclarationNode);

            currNode.addNode(fieldDeclarationNode);
        }
        else if(n.hasName("ReturnStatement")){
            GNode returnStatementNode = GNode.create("ReturnStatement");


            getCheckStatementNode(n.getNode(0), returnStatementNode);

            currNode.addNode(returnStatementNode);
        }
    }

    public void getBlock(Node n, GNode currNode){
        GNode blockNode = GNode.create("Block");
        int numStatements = n.size();
        for(int i =0; i<numStatements; i+=1){
            getCheckStatementNode(n.getNode(i), blockNode);
        }
        currNode.addNode(blockNode);
    }

    /**
     * Gets inidividual Formal Parameter
     * @param n
     * @return
     */
    public void getFormalParameter(Node n, GNode currNode){
        GNode paramParentNode = GNode.create("FormalParameter");
        getTypeNode(n.getNode(1), paramParentNode);
        paramParentNode.add(n.get(3).toString());
        currNode.addNode(paramParentNode);
    }

    /**
     * Gets all the formal Parameters
     * @param n
     * @return
     */
    public void getFormalParameters(Node n, GNode currNode){
        int numFormalParameters = n.size();
        GNode formalParamsParent = GNode.create("FormalParameters");
        if(numFormalParameters > 0){
            for(int i = 0; i<numFormalParameters; i++){
                getFormalParameter(n.getNode(i), formalParamsParent);
            }
        }
        currNode.addNode(formalParamsParent);
    }

    /**
     * Gets the TypeNode
     * @param n
     * @return
     */
    public void getTypeNode(Node n, GNode currNode){
        GNode typeParent = GNode.create("Type");
        GNode typeChild;
        String qfIdent;

        if(n.toString().equals("VoidType()")){
            qfIdent = "void";
            typeParent.add(qfIdent);
        } else {
            qfIdent = n.getNode(0).get(0).toString();
            if(n.get(1) != null) qfIdent += "[]";
        }
        typeChild = GNode.create(qfIdent);
        typeParent.addNode(typeChild);
        currNode.addNode(typeParent);
    }

    public void visitMethodDeclaration(GNode n){
        // Creating the MethodDeclaraitonNode
        GNode methodNode = GNode.create("MethodDeclaration");

        // Get return type Node
        getTypeNode(n.getNode(2), methodNode);

        // get name of method
        methodNode.add(n.get(3).toString());

        // Formal Parameters
        getFormalParameters(n.getNode(4),methodNode);

        getBlock(n.getNode(7), methodNode);

        classNode.getNode(1).addNode(methodNode);
    }

    public void visitClassDeclaration(GNode n) {
        // Creating the GNode
        classNode = GNode.create("ClassDeclaration");

        // Name Node -- 0
        classNode.add(n.get(1).toString());

        // MethodDeclarations node -- 1
        GNode methodDeclarationsNode = GNode.create("MethodDeclarations");
        classNode.addNode(methodDeclarationsNode);

        // Don't visit anything else but the ClassBody directly
        visit(n.getNode(5));
        cpp.addAST(classNode);

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
        System.out.println("here!");
        return cpp.cppasts;
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