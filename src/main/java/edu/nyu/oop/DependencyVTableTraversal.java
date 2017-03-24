package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.lang.reflect.Array;
import java.util.*;

public class DependencyVTableTraversal extends Visitor {

    public vtableAST vtable = new vtableAST();
    public JppObject object;

    public JppObject currentObject;
    public GNode currentClass;


    public DependencyVTableTraversal() {
        object = new JppObject();
        object.methods.add("hashCode");
        object.methods.add("equals");
        object.methods.add("toString");
        object.methods.add("getClass");
    }

    public void visitMethodDeclaration(GNode n) {

        try {
            String method_name = n.get(3).toString();
            currentClass.addNode(GNode.create(method_name));
            currentObject.methods.add(method_name);

        } catch (Exception e) {

        }
        visit(n);

    }
    // Add all inherited methods
    public boolean doesExtend(GNode n) {
        try {
            Node extendExpression = n.getNode(3);
            if (extendExpression != null) {
                String className = extendExpression.getNode(0).getNode(0).get(0).toString();
                JppObject extObj = vtable.objects.get(className);
                for(String objmeth: extObj.methods) {
                    if(!currentObject.methods.contains(objmeth)) {
                        currentObject.methods.add(objmeth);
                    }
                }
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void visitClassDeclaration(GNode n) {
        try {
            currentObject = new JppObject();
            String class_name = (n.get(1).toString());
            currentClass = GNode.create(class_name);
            visit(n);
            doesExtend(n);
            for(String objmeth: object.methods) {
                if(!currentObject.methods.contains(objmeth)) {
                    currentObject.methods.add(objmeth);
                }
            }
            vtable.addASTNode(currentClass);
            vtable.objects.put(class_name, currentObject);
        } catch (Exception e) {
            System.out.println(e);

        }
    }


    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    public vtableAST getSummary(List<Node> dependencyList) {
        for(Node n: dependencyList) {
            super.dispatch(n);
        }
        System.out.println(vtable.toString());
        return vtable;
    }

    static class JppObject {
        public ArrayList<String> methods = new ArrayList<String>();

        public String toString() {
            String s = "";
            for (String d: methods) {
                s += d + "\n ";
            }
            return s;
        }
    }

    static class methodObject {
        String classInherits;
        String methodName;

        public String toString(){
            return classInherits +" "+methodName;
        }
    }

    static class vtableAST {
        public ArrayList<GNode> vtableAsts = new ArrayList<GNode>();
        public HashMap<String, JppObject> objects = new HashMap<String, JppObject>();

        public void addASTNode(GNode n) {
            this.vtableAsts.add(n);
        }

        public String toString() {
            String s = "";
            Iterator it = objects.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                s += pair.getKey() + " = " + pair.getValue().toString()+" ";
                it.remove(); // avoids a ConcurrentModificationException
            }
            return s;
        }
    }



}
