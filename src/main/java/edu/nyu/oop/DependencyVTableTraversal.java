package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.*;

public class DependencyVTableTraversal extends Visitor {

    public vtableAST vtable = new vtableAST();
    public JppObject object;

    public JppObject currentObject;
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
            System.out.println(e);
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
            currentObject.methods.add(new MethodObject(currentClassName, method_name));
        } catch (Exception e) {
            System.out.println("Exception: "+e);
        }
        visit(n);
    }

    /**
     * The visitClassDeclaration is similar to what
     * has been done for other visit methods.
     * The primary difference is that this is where
     * new JppObjects/GNodes are created/reassigned
     * and added to the vtableAST
     * @param n
     */
    public void visitClassDeclaration(GNode n) {
        try {
            currentObject = new JppObject();
            String class_name = (n.get(1).toString());
            currentClassName = class_name;
            visit(n);
            doesExtend(n);
            for(MethodObject objmeth: object.methods) {
                if(!checkIfContains(objmeth.methodName)){
                    currentObject.methods.add(objmeth);
                }
            }
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

    /**
     * Takes in a list of the ASTs of the
     * dependencies and dispatches them to
     * vist the nodes and perform the required
     * steps.
     * @param dependencyList
     * @return
     */
    public vtableAST getSummary(List<Node> dependencyList) {
        for(Node n: dependencyList) {
            super.dispatch(n);
        }
        return vtable;
    }

    /**
     * The Method Object is so that we can keep a track
     * of where the method is being inherited from.
     * This will be important for the vpointers
     * in the VTable of the object.
     */
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


    /**
     * Helper object for the creation of the AST
     * in the future. This is essenitally to hold all
     * the methods
     */
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

    /**
     * Helper static class to keep track of the AST.
     */
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
                it.remove();
            }
            return s;
        }
    }



}
