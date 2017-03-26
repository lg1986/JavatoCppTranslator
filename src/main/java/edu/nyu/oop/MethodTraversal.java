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
        System.out.println(n);
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
        System.out.println(n);
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
            Node k = n.getNode(2);
            if(k.toString().compareTo("VoidType()") == 0){
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

    public cppMethodObject getBlock(Node n){
        super.dispatch(n);
        return methObj;
    }
}
