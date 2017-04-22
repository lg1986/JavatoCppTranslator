package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.type.JavaAST;

import java.util.List;

/**
 * Created by rishabh on 22/04/17.
 */
public class DependencyInheritance extends Visitor {

    public List<GNode> headerAsts;

    public void getDependencyInheritance(Node n){
        CreateHeaderAST visitor = new CreateHeaderAST();
        this.headerAsts = visitor.getHeaderAsts(n);

//        for(GNode dependencyAstNode: headerAsts){
//            super.dispatch(dependencyAstNode);
//        }
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

    public void simulateInheritance(){
        for(GNode headerAst:headerAsts){
            if(headerAst.get(2) != null){
                String extender = headerAst.getNode(2).getString(0);
                String currPack = headerAst.getString(0);
                Node extenderClass = getExtenderClass(extender, currPack);
            }
        }
    }


    public void visit(Node n){
        for(Object o: n){
            if(o instanceof Node) dispatch((Node) o);
        }
    }



}
