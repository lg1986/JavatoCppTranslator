package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;

//TODO: ADD EXPRESSIONSTATEMENT, CALLEXPRESSION, SELECTIONEXPRESSION NODE METHODS
public class jppTraversal extends Visitor {


    /**
     * Class Members
     */
    protected cppAST cpp = new cppAST();
    private GNode classNode;

    //================================================================================
    // Utils
    //================================================================================

    /**
     * Check if the Object in the tre
     * is actually a node or is it a string or a null
     * value
     * @param n
     * @return
     */

    public boolean checkIfNode(Object n) {
        if(n instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    /** Helper method to remove for loops in every get method
     *
     * @param n
     * @param createdNode
     * @return
     */
    //TODO: implement in get methods, figure out how to deal with the methods that use an if after the for loop
    public void throwToCheckStatement(Node n, GNode createdNode) {
        for (int i = 0; i < n.size(); i++) {
            getCheckStatementNode(n.getNode(i), createdNode);
        }
    }

    //================================================================================
    // Controlling Center --> Everything comes here and then keeps growing
    //================================================================================
    // TODO: add additional hasName() for singlular declarators, arguments... ect.
    public void getCheckStatementNode(Node n, GNode currNode) {
        if(n.hasName("StringLiteral")) {
            getStringLiteral(n, currNode);
        } else if(n.hasName("QualifiedIdentifier")) {
            getQualifiedIdentifierNode(n, currNode);
        } else if(n.hasName("Arguments")) {
            getArguments(n, currNode);
        } else if (n.hasName("Argument")) {
            getArgument(n, currNode);
        } else if(n.hasName("IntegerLiteral")) {
            currNode.addNode(n);
        } else if(n.hasName("PrimaryIdentifier")) {
            currNode.addNode(n);
        } else if(n.hasName("Modifiers")) {
            getModifierNode(n, currNode);
        } else if(n.hasName("FormalParameters")) {
            getFormalParameters(n, currNode);
        } else if(n.hasName("FormalParameter")) {
            getFormalParameter(n, currNode);
        } else if(n.hasName("Type")) {
            getTypeNode(n, currNode);
        } else if(n.hasName("Declarators")) {
            getDeclaratorsNode(n, currNode);
        } else if(n.hasName("Block")) {
            getBlock(n, currNode);
        } else if(n.hasName("FieldDeclaration")) {
            getFieldDeclarationNode(n, currNode);
        } else if(n.hasName("ReturnStatement")) {
            getReturnStatementNode(n, currNode);
        } else if(n.hasName("NewClassExpression")) {
            getNewClassExpression(n, currNode);
        } else if(n.hasName("CallExpression")) {
            getCallExpression(n, currNode);
        } else if(n.hasName("ExpressionStatement")) {
            getExpressionStatement(n, currNode);
        }
    }
    //================================================================================
    // MUTATORS, DECORATORS AND GENERATORS
    //
    // Specific Node methods to parse and add the Node to the new AST
    // Each of the methods Mutate and Decorate and generate depending on
    // the structure of the tree.
    //================================================================================

    public void getExpressionStatement(Node n, GNode currNode) {
        GNode expNode = GNode.create("ExpressionStatement");
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                getCheckStatementNode(n.getNode(i), expNode);
            } else {
                expNode.add(n.get(i));
            }
        }
        currNode.addNode(expNode);
    }

    public void getCallExpression(Node n, GNode currNode) {
        GNode callExpNode = GNode.create("CallExpression");
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                getCheckStatementNode(n.getNode(i), callExpNode);
            } else {
                callExpNode.add(n.get(i));
            }
        }
        currNode.addNode(callExpNode);
    }

    public void getArgument(Node n, GNode currNode) {
        GNode argumentParentNode = GNode.create("Argument");
        getTypeNode(n.getNode(1), argumentParentNode);
        argumentParentNode.add(n.get(3).toString());
        currNode.addNode(argumentParentNode);
    }

    public void getArguments(Node n, GNode currNode) {
        GNode argumentsNode = GNode.create("Arguments");
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                getCheckStatementNode(n.getNode(i), argumentsNode);
            } else {
                argumentsNode.add(n.get(i));
            }
        }
        currNode.addNode(argumentsNode);
    }

    public void getQualifiedIdentifierNode(Node n, GNode currNode) {
        currNode.add(n.get(0).toString());
    }

    public void getNewClassExpression(Node n, GNode currNode) {
        GNode newClassNode =GNode.create("NewClassExpression");
        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                getCheckStatementNode(n.getNode(i), newClassNode);
            } else {
                newClassNode.add(n.get(i));
            }
        }
        currNode.addNode(newClassNode);
    }
    /**
     * Iterate through all the Block statements and parse them
     * individually and add them to the tree
     * @param n
     * @param currNode
     */
    public void getBlock(Node n, GNode currNode) {
        GNode blockNode = GNode.create("Block");
        int numStatements = n.size();
        for(int i =0; i<numStatements; i+=1) {
            getCheckStatementNode(n.getNode(i), blockNode);
        }
        currNode.addNode(blockNode);
    }

    /**
     * Gets the field declaration Node
     * @param n
     * @param currNode
     */
    public void getFieldDeclarationNode(Node n, GNode currNode) {
        currNode.addNode(n);
    }

    /**
     * Gets the return statement node and parses it accordingly
     * @param n
     * @param currNode
     */
    public void getReturnStatementNode(Node n, GNode currNode) {
        GNode returnStatementNode = GNode.create("ReturnStatement");
        getCheckStatementNode(n.getNode(0), returnStatementNode);
        currNode.addNode(returnStatementNode);
    }
    /**
     * To get the Modifiers and make the apt node
     * @param n
     * @return
     */
    public String getModifier(Node n) {
        return n.get(0).toString();
    }

    public void getModifierNode(Node n, GNode currNode) {
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


    public void getDeclaratorsNode(Node n, GNode currNode) {
        currNode.addNode(n);
    }




    /**
     * Gets individual Formal Parameter
     * @param n
     * @return
     */
    public void getFormalParameter(Node n, GNode currNode) {
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
    public void getFormalParameters(Node n, GNode currNode) {
        int numFormalParameters = n.size();
        GNode formalParamsParent = GNode.create("FormalParameters");
        if(numFormalParameters > 0) {
            for(int i = 0; i<numFormalParameters; i++) {
                getCheckStatementNode(n.getNode(i), formalParamsParent);
            }
        }
        currNode.addNode(formalParamsParent);
    }

    /**
     * Gets the TypeNode
     * @param n
     * @return
     */
    public void getTypeNode(Node n, GNode currNode) {
        GNode typeParent;
        if(currNode.hasName("MethodDeclaration"))
            typeParent = GNode.create("ReturnType");
        else
            typeParent = GNode.create("Type");
        String qfIdent;
        if(n.toString().equals("VoidType()")) {
            qfIdent = "void";
            typeParent.add(qfIdent);
        } else {
            qfIdent = n.getNode(0).get(0).toString();
            if(qfIdent.equals("int")) qfIdent = "int32_t";
            else if(qfIdent.equals("boolean")) qfIdent = "bool";
            if(n.get(1) != null) qfIdent += "[]";
        }
        typeParent.add(qfIdent);
        currNode.addNode(typeParent);
    }




    /**
     * Mutate StringLiteral to __rt::literal
     * @param n
     * @return
     */
    public void getStringLiteral(Node n, GNode currNode) {
        currNode.addNode(n);
    }

    //================================================================================
    // Generic Visit Methods
    //================================================================================

    public void visitMethodDeclaration(GNode n) {
        // Creating the MethodDeclaraitonNode
        GNode methodNode = GNode.create("MethodDeclaration");

        for(int i = 0; i<n.size(); i++) {
            if(n.get(i) != null && checkIfNode(n.get(i))) {
                getCheckStatementNode(n.getNode(i), methodNode);
            } else {
                methodNode.add(n.get(i));
            }
        }
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
        return cpp.cppasts;
    }


    //================================================================================
    // Static class to hold the information collected
    // in a "container"
    //================================================================================
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