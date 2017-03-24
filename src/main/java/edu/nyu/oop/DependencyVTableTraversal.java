package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.*;

public class DependencyVTableTraversal extends Visitor {

    public vtableAST vtable = new vtableAST();
    public JppObject object;

    public JppObject currentObject;
    public GNode currentClass;
    public String currentClassName;

    /**
     * DependencyVTableTraversal Constructor
     * This initializes the Java "Object" JppObject.
     */
    public DependencyVTableTraversal() {
        object = new JppObject();
        object.methods.add(new MethodObject("Object", "hashCode"));
        object.methods.add(new MethodObject("Object", "equals"));
        object.methods.add(new MethodObject("Object","toString"));
        object.methods.add(new MethodObject("Object", "getClass"));
    }


    /**
     * Helper method. Check if the method was already created
     * or overriden by the subclass while iterating over the
     * methods of the super class.
     * @param methodName
     * @return
     */
    public boolean checkIfContains(String methodName){
        for(MethodObject objmeth: currentObject.methods){
            if(objmeth.methodName == methodName) return true;
        }
        return false;
    }

    /**
     * The doesExtend method is essentially to
     * "inherit" all the methods from the super
     * class into the current class.
     * @param n
     * @return
     */
    public boolean doesExtend(GNode n) {
        try {
            Node extendExpression = n.getNode(3);
            if (extendExpression != null) {
                String className = extendExpression.getNode(0).getNode(0).get(0).toString();
                JppObject extObj = vtable.objects.get(className);
                for(MethodObject objmeth: extObj.methods) {
                    if(!checkIfContains(objmeth.methodName)){
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

    /**
     * visits Method Declaration. To the existing JppObject's
     * MethodObject ArrayList the current method is added
     * @param n
     */
    public void visitMethodDeclaration(GNode n) {
        try {
            String method_name = n.get(3).toString();
            currentClass.addNode(GNode.create(method_name));
            currentObject.methods.add(new MethodObject(currentClassName, method_name));
        } catch (Exception e) {
            System.out.println("Exception: "+e);
        }
        visit(n);
    }

    /**
     *
     * @param n
     */
    public void visitClassDeclaration(GNode n) {
        try {
            currentObject = new JppObject();
            String class_name = (n.get(1).toString());
            currentClassName = class_name;
            currentClass = GNode.create(class_name);
            visit(n);
            doesExtend(n);
            for(MethodObject objmeth: object.methods) {
                if(!checkIfContains(objmeth.methodName)){
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
        return vtable;
    }

    static class JppObject {
        public ArrayList<MethodObject> methods = new ArrayList<MethodObject>();

        public String toString() {
            String s = "";
            for (MethodObject d: methods) {
                s += d.toString() + "\n ";
            }
            return s;
        }
    }

    static class MethodObject {
        private String classInherits;
        private String methodName;

        public MethodObject(String classInherits, String methodName){
            this.classInherits = classInherits;
            this.methodName = methodName;
        }

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
