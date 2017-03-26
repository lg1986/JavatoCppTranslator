package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import edu.nyu.oop.CppTraversal.cppMethodObject;

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
        GNode retNode = GNode.create(methObj.returnType.replace("()", ""));
        GNode nameNode = GNode.create(methObj.methName.replace("()", ""));
        GNode methBlock = GNode.create(methObj.block.replace("()", ""));
        GNode params = GNode.create(methObj.parameters.replace("()", ""));
        methodNode.add(retNode);
        methodNode.add(nameNode);
        methodNode.add(params);
        methodNode.add(methBlock);
    }

    public void visitStringLiteral(GNode n){
        if(current == "ReturnStatement"){
            methObj.block += "return __rt::literal("+n.get(0)+")";
        }
        else if(current == "Block"){
            methObj.block += n.get(0).toString();
        }
        visit(n);
    }

    public void visitIntegerLiteral(GNode n){
        if(current == "ReturnStatement"){
            methObj.block += "return "+n.get(0)+"";
        }
        visit(n);
    }

    public void visitReturnStatement(GNode n){
        current = "ReturnStatement";
        visit(n);
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
        System.out.println(n);
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
}
