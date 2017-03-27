package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import edu.nyu.oop.CppTraversal.cppMethodObject;
import xtc.type.MethodT;

/**
 * Created by rishabh on 25/03/17.
 */
public class MethodTraversal extends Visitor {

    public String block;
    public cppMethodObject methObj;

    public String current;
    GNode methodNode;


    /**
     * Creates the TranslatedMethodNodeBlock
     */
    public void createCppMethodBlock(){
        methodNode.add(methObj.returnType.replace("()", ""));
        methodNode.add(methObj.methName.replace("()", ""));
        methodNode.add(methObj.block.replace("()", ""));
        methodNode.add(methObj.parameters.replace("()", ""));
    }

    public void visitStringLiteral(GNode n){
        if(current == "ReturnStatement" ){
            methObj.block += "return __rt::literal("+n.get(0)+")";
        }
        else if(current == "Block"){
            methObj.block += n.get(0).toString();
        }
        else if(current == "Arguments"){
            String arg =
            methObj.block += "("+n.get(0).toString()+")";
        }
        visit(n);
    }

    public void visitIntegerLiteral(GNode n){
        if(current == "ReturnStatement"){
            methObj.block += "return "+n.get(0);
        }
        visit(n);
    }

    public void visitReturnStatement(GNode n){
        current = "ReturnStatement";
        visit(n);
    }


    public void getCallDetails(GNode n) {
        String callStatement = "";
        Node secp = n.getNode(0);
        callStatement += secp.getNode(0).get(0);
        callStatement += "." + secp.get(1).toString();
        callStatement += "." + n.get(2);
        if (callStatement.compareTo("System.out.println") == 0) {
            callStatement = callStatement.replace("System.out.println", "cout << ");
        }
        methObj.block += callStatement;
    }

    public void visitSelectionExpression(GNode n) {
        methObj.block += n.getNode(0).get(0).toString()+".";
        methObj.block += n.get(1).toString();
    }


    public void visitPrimaryIdentifier(GNode n){
        methObj.block += "."+n.get(0).toString();
    }



    public void visitArguments(GNode n){
        visit(n);
    }

    public void visitCallExpression(GNode n){

        // If we have a call expression as an arg
        try {

            System.out.println(n.getNode(n.size()-1));
            if (n.getNode(n.size() - 1) != null
                    && n.getNode(n.size() - 1).getNode(0).getName().toString().compareTo("CallExpression") == 0) {

                Node secp = n.getNode(0);
                String call = secp.getNode(0).get(0).toString() + "." + secp.get(1).toString();
                methObj.block += call;
                methObj.block += "." + n.get(2);
                ArgumentsTraversal x = new ArgumentsTraversal();
                String arg = x.getArgDetails(n.getNode(3));
            } else {
                visit(n);
            }
        } catch (Exception e){
            visit(n);
            methObj.block += "."+n.get(2).toString();
        }
    }

    public void visitDeclarator(GNode n){
        if(current == "Block"){
            methObj.block += n.get(0).toString().replace("\"", "");
            methObj.block += "= ";
        }
        visit(n);
    }

    public void visitDeclarators(GNode n){
        if(current == "Block"){

        }
        visit(n);
    }
    public void visitExpression(GNode n){
        if(current == "Block"){
            String var = n.getNode(0).get(0).toString();
            String val = n.getNode(2).get(0).toString();
            String exp = n.get(1).toString();
            methObj.block += var + " " + exp +" "+val+" \n";
        }
    }

    public void visitExpressionStatement(GNode n){
        visit(n);
    }

    public void visitFieldDeclaration(GNode n){
        if(current == "Block"){
            String typ = n.getNode(1).getNode(0).get(0).toString();
            methObj.block += typ + " ";
        }
        visit(n);
        methObj.block +=" \n";
    }


    public void visitBlock(GNode n){
        current = "Block";
        visit(n);
    }

    public void visitFormalParameter(GNode n){
        methObj.parameters += n.getNode(1).getNode(0).get(0).toString();
        methObj.parameters += n.get(3).toString();

        visit(n);
    }
    public void visitFormalParameters(GNode n){
        visit(n);
    }


    public void visitMethodDeclaration(GNode n){
        try {
            methObj = new cppMethodObject();
            methObj.methName = n.get(3).toString();
            Node retNode = n.getNode(2);
            if(retNode.toString().compareTo("VoidType()") == 0){
                methObj.returnType = "void";
            } else {
                methObj.returnType = n.getNode(2).getNode(0).get(0).toString();
            }
        } catch (Exception e){
            System.out.println("Error: "+n);
        }

        visit(n);
    }

    public void visit(Node n){
        for(Object o : n){
            if(o instanceof Node){
                dispatch((Node) o);
            }
        }
    }

    public cppMethodObject getBlock(Node n, GNode methNode){
        super.dispatch(n);
        this.methodNode = methNode;
        createCppMethodBlock();
        return methObj;
    }

    static class CallExpObject {
        String call = "";
        String args = "";
    }
}
