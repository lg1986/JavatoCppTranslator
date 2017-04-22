package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 22/04/17.
 */
public class CreateHeaderAST extends Visitor {
    public List<GNode> headerASTs = new ArrayList<GNode>();

    protected GNode dataLayoutNode;
    protected String packageName;

    public List<GNode> getHeaderAsts(Node n){
        DependencyAstVisitor visitor = new DependencyAstVisitor();
        List<GNode> dependencyAsts = visitor.getDependencyAsts(n);

        for(Node dependencyNode:dependencyAsts){
            super.dispatch(dependencyNode);
        }
        return this.headerASTs;
    }

    public GNode collateDetails(GNode n, String type, int limit) {
        GNode details = GNode.create(type, limit);
        for(int i = 0; i<limit; i+=1) {
            try {
                details.addNode(n.getNode(i));
            } catch(java.lang.ClassCastException e) {
                GNode methodName = GNode.create(n.get(i).toString(), 1);
                details.addNode(methodName);
            }
        }
        return details;
    }




    public GNode getFormalParameter(Node n){
        String typ = n.getNode(1).getNode(0).getString(0);
        String name = n.getString(3);
        GNode parameter = GNode.create("FormalParameter");
        parameter.add(typ);
        parameter.add(name);
        return parameter;

    }

    public GNode getFormalParameters(Node n){
        GNode parameters = GNode.create("FormalParameters");
        for(int i = 0; i<n.size(); i++){
            parameters.addNode(getFormalParameter((Node)n.getNode(i)));
        }
        return parameters;
    }

    public String getType(Node n){
        if(n.toString().equals("VoidType()")){
            return "void";
        } else {
            String typ = n.getNode(0).getString(0);
            if (n.get(1) == null) return typ;
            else return typ + "[]";
        }
    }

    public void visitMethodDeclaration(GNode n){
        GNode methodDeclaration = GNode.create("MethodDeclaration");
        methodDeclaration.add(getType(n.getNode(2)));
        methodDeclaration.add(n.getString(3));
        methodDeclaration.addNode(getFormalParameters(n.getNode(4)));
        dataLayoutNode.getNode(2).addNode(methodDeclaration);
    }

    public void visitConstructorDeclaration(GNode n){
        GNode constructorDeclaration = GNode.create("ConstructorDeclaration");
        constructorDeclaration.add(n.getString(2));
        constructorDeclaration.addNode(getFormalParameters(n.getNode(3)));
        dataLayoutNode.getNode(1).addNode(constructorDeclaration);
    }

    public void visitFieldDeclaration(GNode n) {
        String typ = n.getNode(1).getNode(0).getString(0);
        String name = n.getNode(2).getNode(0).getString(0);
        GNode fieldDeclaration = GNode.create("FieldDeclaration");
        fieldDeclaration.add(typ);
        fieldDeclaration.add(name);
        dataLayoutNode.getNode(0).addNode(fieldDeclaration);

    }

    public void visitClassDeclaration(GNode n){
        GNode classNode =GNode.create("ClassDeclaration");

        classNode.add(packageName); // 0
        classNode.add(n.getString(1)); // 1


        dataLayoutNode = GNode.create("DataLayout");
        GNode methodDeclarations = GNode.create("MethodDeclarations");
        GNode constructorDeclarations = GNode.create("ConstructorDeclarations");
        GNode fieldDeclarations = GNode.create("FieldDeclarations");

        dataLayoutNode.addNode(fieldDeclarations); // 0
        dataLayoutNode.addNode(constructorDeclarations); // 1
        dataLayoutNode.addNode(methodDeclarations); // 2

        visit(n);

        classNode.addNode(dataLayoutNode);
        GNode vtableNode = GNode.create("VTableNode");
        vtableNode.addNode(dataLayoutNode.getNode(2));

        classNode.addNode(dataLayoutNode); // 2
        classNode.addNode(vtableNode); // 3

        this.headerASTs.add(classNode);
    }

    public void visitPackageDeclaration(GNode n){
        this.packageName = n.getNode(1).get(1).toString();
        visit(n);
    }

    public void visit(Node n){
        for(Object o: n){
            if(o instanceof Node) dispatch((Node) o);
        }
    }
}
