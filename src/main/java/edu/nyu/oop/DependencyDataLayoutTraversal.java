package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;


import java.util.*;

public class DependencyDataLayoutTraversal extends Visitor {

    // created nested static class that will have the new AST with the data layout

    public static dependencyAST dataLayout = new dependencyAST();
    public GNode classNode;
    public GNode packageNode;

    private HashMap<String, dataClassObj> objs = new HashMap<String, dataClassObj>();
    private dataClassObj currentobj;
    private String currentField;


    public void visitFieldDeclaration(GNode n) {

        if(n.getNode(0).size() > 0) {
            dataFieldObj fi = new dataFieldObj();
            currentField = n.getNode(1).getNode(0).get(0).toString()+" ";
            currentField += n.getNode(2).getNode(0).get(0).toString();
            fi.name = currentField;
            fi.field = n;
            currentobj.fields.add(fi);
        }
        classNode.addNode(n);
        visit(n);
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

    public void visitMethodDeclaration(GNode n) {
        classNode.addNode(collateDetails(n, "MethodDeclaration", 5));
        visit(n);
    }

    public void visitConstructorDeclaration(GNode n) {
        classNode.addNode(collateDetails(n, "ConstructorDeclaration", 5));
        visit(n);
    }

    public boolean checkIfFieldIn(String fieldName) {
        for(dataFieldObj f: currentobj.fields) {
            if(f.name.compareTo(fieldName) == 0) {
                return true;
            }
        }
        return false;
    }
    public void checkFields() {
        if(classNode.get(3) != null) {
            String extClass = classNode.getNode(3).getNode(0).getNode(0).get(0).toString();
            dataClassObj extObj = objs.get(extClass);
            for(dataFieldObj f: extObj.fields) {
                if(!checkIfFieldIn(f.name)) {
                    currentobj.fields.add(f);
                    classNode.add(f.field);
                }
            }
        }
    }


    public void visitClassDeclaration(GNode n) {
        classNode = collateDetails(n, "ClassDeclaration", 5);
        String classname = classNode.get(1).toString().replace("()", "");

        currentobj = new dataClassObj();
        currentobj.cName = classname;
        visit(n);
        checkFields();
        objs.put(classname, currentobj);
        packageNode.addNode(classNode);
    }

    public void visitCompilationUnit(GNode n) {
        visit(n);
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    public dependencyAST getSummary(List<Node> dependencyList) {
        for(Node n: dependencyList) {
            packageNode = GNode.create("PackageDeclaration", 20);
            packageNode.addNode(n.getNode(0));
            super.dispatch(n);
            dataLayout.addASTNode(packageNode);
        }
        return dataLayout;
    }


    static class dependencyAST {

        public ArrayList<GNode> dependencyAsts  = new ArrayList<GNode>();

        public void addASTNode(GNode n) {
            this.dependencyAsts.add(n);
        }

        public String toString() {
            return dependencyAsts.toString();
        }
    }

    static class dataFieldObj {
        Node field;
        String name;

        public String toString() {
            return field.toString();
        }
    }

    static class dataClassObj {
        public String cName;
        public ArrayList<dataFieldObj> fields = new ArrayList<dataFieldObj>();

    }

    static class dataObjs {

    }



}
