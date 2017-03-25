package edu.nyu.oop;

import xtc.lang.CPrinter;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.lang.CFeatureExtractor;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by charlottephillips on 09/03/17.
 * Traversing the AST and mutating the existing nodes from the Java AST to the C++ AST
 */
public class CppTraversal extends Visitor {

    protected cppAST cpp = new cppAST();
    public GNode packageNode;
    public GNode classNode;

    // Add the class declarations to the C++ AST
    // @param GNode of current node visiting
    // @param Name of the class
    // @param Class' type

    GNode addClassCPP(GNode n) {
        classNode = GNode.create("ClassDeclaration");
        classNode.addNode(GNode.create("struct " + n.get(1).toString()));
        if (n.toString().length() > 2) {
            GNode pointerClass = GNode.create("ClassDeclarationPointer");
            pointerClass.addNode(GNode.create(" ->_vptr->"));
            classNode.addNode(pointerClass);

        }
        return classNode;
    }

    // Add the field declarations to the C++ AST
    // @param GNode of current node visiting
    // @param className of the field declaration
    // @param Field's type
    GNode addFieldCPP(GNode node, Class className, String type) {
        GNode field = GNode.create("FieldDeclaration");
        if (!(className.equals(null) || type.equals(null) || node == null) && node != null) {
            field.addNode(GNode.create(node.get(0).toString())); //class
            field.addNode(GNode.create(node.get(2).toString())); //type
        }
        // check if the field calls a function, in which case we need to add a vptr Node in between
        if(node.toString().length() > 2) {
            GNode pointerField = GNode.create("FieldDeclarationPointer");
            pointerField.addNode(GNode.create(" ->_vptr->"));
            field.addNode(pointerField);
        }
        return field;
    }

    // Add the method declarations to the C++ AST
    // @param GNode of current node visiting
    // @param className of the method
    // @param Method's type
    GNode addMethodCPP(GNode node, String name, String type) {
        GNode method = GNode.create("MethodDeclaration");
        if (!(name.equals(null) || type.equals(null) || node == null)) {
            method.addNode(GNode.create(node.get(3).toString())); //get(3) will access method name
            method.addNode(GNode.create(node.get(2).toString())); //access type
        }
        GNode pointerObject = GNode.create((node.get(3).toString()).replace("()",""));
        method.addNode(pointerObject);
        return method;
    }

    // Adds the constructor node to the C++ AST
    // @param GNode of current node visiting
    // @param className of the constructor
    GNode addConstructorCPP(GNode node, Class className) {
        GNode constructor = GNode.create("ConstructorDeclaration");
        constructor.addNode(GNode.create(node.get(0).toString()));
        return constructor;

    }

    GNode addForStatement(GNode n) {
        GNode forStatement = GNode.create("ForStatement");
        forStatement.addNode(addForControl(n));
        return forStatement;
    }

    GNode addBlockDeclaration(GNode n) {
        GNode block = GNode.create("BlockDeclaration");
        block.addNode(n);
        return block;
    }

    GNode addWhileStatement(GNode n) {
        GNode whileStatement = GNode.create("WhileStatement");
        whileStatement.addNode(n);
        return whileStatement;
    }

    GNode addSubscriptExpression(GNode n) {
        GNode subscriptExpression = GNode.create("SubscriptExpression");
        subscriptExpression.add(n);
        return subscriptExpression;
    }

    GNode addSelectionExpression(GNode n) {
        GNode selectionExpression = GNode.create("SelectionExpression");
        selectionExpression.addNode(GNode.create("cout<<"));
        selectionExpression.addNode(addSubscriptExpression(n));
        return selectionExpression;
    }

    GNode addSelectionExpressionExit(GNode n) {
        GNode selectionExpression = GNode.create("SelectionExpression");
        selectionExpression.addNode(GNode.create("end<<"));
        return selectionExpression;
    }

    GNode addForControl(GNode n) {
        GNode selectionExpression = GNode.create("BasicForControl");
        selectionExpression.addNode(n);
        return selectionExpression;
    }
    // Visit methods for each scope construct, mutating each node

    public void visitMethodDeclaration(GNode n) {
        classNode.addNode(addMethodCPP(n,n.getName(),n.getClass().getTypeName()));
        visit(n);
    }

    public void visitFieldDeclaration(GNode n) {
        classNode.addNode(addFieldCPP(n,n.getClass(),n.getClass().getTypeName()));
        visit(n);
    }

    public void visitBasicForControl(GNode n) {
        classNode.addNode(addForControl(n));
        visit(n);
    }

    public void visitPackageDeclaration(GNode n) {
        try{
            classNode.addNode(n);

        }
        catch(Exception e){
            e.getMessage();
        }
        visit(n);
    }

    public void visitBlockDeclaration(GNode n) {
        classNode.addNode(addBlockDeclaration(n));
        visit(n);
    }

    public void visitForStatement(GNode n) {
        classNode.addNode(addForStatement(n));
        visit(n);
    }

    public void visitWhileStatement(GNode n) {
        classNode.addNode(addWhileStatement(n));
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        classNode.addNode(addConstructorCPP(n,n.getClass()));
        visit(n);
    }

    public void visitClassDeclaration(GNode n) {
        GNode k = addClassCPP(n);
        if(k != null){
            classNode.addNode(k);
        }
        visit(n);

    }

    public void visitSubscriptExpression(GNode n) {
        classNode.addNode(GNode.create(n));
        visit(n);
    }

    public void visitExpressionStatement(GNode n) {
        classNode.addNode(GNode.create(n));
        visit(n);
    }

    public void visitCallExpression(GNode n) {
        classNode.addNode(GNode.create(n));
        visit(n);
    }

    public void visitSelectionExpression(GNode n) {
        classNode.addNode(addSelectionExpression(n));
        visit(n);
        classNode.addNode(addSelectionExpressionExit(n));
    }

    public void visitArguments(GNode n) {
        classNode.addNode(GNode.create(n));
        visit(n);
    }

    public void visitCompilationUnit(GNode n) {
        // System.out.println(n);
        visit(n);
    }

    // The double dispatch
    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }


    public cppAST getSummary(List<Node> cppList) {

        for(Node n: cppList) {
            //packageNode = GNode.create("PackageDeclaration", 20);
            //packageNode.addNode(n);
            super.dispatch(n);
            System.out.println("CLASSNODE : "+ classNode.toString());
            cpp.addAST(classNode);
        }
        return cpp;
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