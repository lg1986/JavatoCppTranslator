package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.*;

public class DependencyVTableTraversal extends Visitor {

    public vtableAST vtable = new vtableAST();  // The static object that holds all the details

    public JppObject object; // This is for Java's Object class

    public JppObject currentObject; // This holds the object that is being traversed and updated
    public GNode currentClass; // currentClass this is the Class's AST


    public String currentClassName; // class name for use across different methods
    private String packageName; // current Package to make sure that we don't add as a Struct for VTable


    /**
     * The following creates the methodDeclaration GNode for the
     * 4 generic Java Object methods
     * @param name -- equals, hashCode, toString, getClass
     * @param returnType -- bool, int_32, String, Class
     * @return GNode for the MethodDeclaration
     */
    public GNode createObjectNode(String returnType, String name) {
        GNode genericObjectNode = GNode.create("MethodDeclaration");

        // Modifier
        genericObjectNode.addNode(null);

        // Modi
        genericObjectNode.addNode(null);

        // return type
        GNode type = GNode.create("Type");
        GNode qul = GNode.create("QualifiedIdentifier");
        type.addNode(qul.add(returnType.replace("()", "")));
        genericObjectNode.addNode(type);

        // Name
        genericObjectNode.addNode(GNode.create(name));

        // Param
        GNode fms = GNode.create("FormalParameters");   // This GNode holds all the children GNodes
        if(name == "equals") {
            GNode fm = GNode.create("FormalParameter"); // Creating the FormalParameter GNode
            fm.addNode(null);
            fm.addNode(GNode.create("Type"));
            GNode fmIdentifier = GNode.create("QualifiedIdentifier");
            fmIdentifier.add("Object");
            fm.addNode(fmIdentifier);
            fms.addNode(fm);
        } else {
            // This is the case when there are no additional parameters
            fms.addNode(null);
        }
        genericObjectNode.addNode(fms);

        // Extender i.e., from where was this method inherited
        GNode extendsNode = GNode.create("ExtendsObjectPram");
        GNode inherit = GNode.create("Object");
        extendsNode.add(inherit);
        genericObjectNode.addNode(extendsNode);

        return genericObjectNode;
    }

    /**
     * DependencyVTableTraversal Constructor
     * This initializes the Java "Object" JppObject.
     */
    public DependencyVTableTraversal() {
        object = new JppObject();
        object.methods.add(new MethodObject("Object", "hashCode",
                                            null, "int_32"));
        object.methods.add(new MethodObject("Object", "equals",
                                            null, "bool"));
        object.methods.add(new MethodObject("Object","toString",
                                            null, "String"));
        object.methods.add(new MethodObject("Object", "getClass",
                                            null, "Class"));
    }

    /**
     * Helper method to collate certain details
     * with regards to the method declarations
     * @param n
     * @param type
     * @param limit
     * @return GNode with required details
     */
    public GNode collateDetails(GNode n, String type, int limit) {
        GNode details = GNode.create(type, limit);
        for(int i = 0; i<limit; i+=1) {
            try {
                details.addNode(n.getNode(i));
            } catch(Exception e) {
                GNode methodName = GNode.create(n.get(i).toString(), 1);
                details.addNode(methodName);
            }
        }
        return details;
    }

    /**
     *
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
                    if(!currentObject.methnames.contains(objmeth.methodName)) {
                        currentObject.methods.add(objmeth);
                        currentObject.methnames.add(objmeth.methodName);
                        currentClass.addNode(objmeth.methodDecl);
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
            GNode methDetails = collateDetails(n, "MethodDeclaration", 5);
            MethodObject objmeth = new MethodObject(currentClassName, method_name, methDetails);
            currentObject.methods.add(objmeth);
            currentObject.methnames.add(method_name);

            GNode extendsNode = GNode.create("ExtendsObjectParam");
            GNode inhertis = GNode.create(objmeth.classInherits);
            extendsNode.addNode(inhertis);
            methDetails.addNode(extendsNode);

            currentClass.addNode(methDetails);
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
            String class_name = n.get(1).toString().replace("()", "");

            if(class_name.toLowerCase().compareTo(packageName) == 0) return;
            currentObject = new JppObject();
            currentClassName = (n.get(1).toString());
            currentClass = GNode.create("ClassDeclaration");
            currentClass.addNode(GNode.create(currentClassName));
            visit(n);
            for (MethodObject objmeth : object.methods) {
                if (!currentObject.methnames.contains(objmeth.methodName)) {
                    currentObject.methods.add(objmeth);
                    currentObject.methnames.add(objmeth.methodName);
                    GNode objMethDecl = createObjectNode(objmeth.returnType, objmeth.methodName);
                    currentClass.addNode(objMethDecl);
                }
            }
            doesExtend(n);
            vtable.addASTNode(currentClass);
            vtable.objects.put(currentClassName, currentObject);
        } catch (Exception ignore) {

        }
    }

    public void visitPackageDeclaration(GNode n) {

        try {
            this.packageName = n.getNode(1).get(1).toString();

            visit(n);
        } catch (Exception ignored) {


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
        private GNode methodDecl;
        private String returnType;

        public MethodObject(String classInherits, String methodName, GNode methodDecl) {
            this.classInherits = classInherits;
            this.methodName = methodName;
            this.methodDecl = methodDecl;
        }

        public MethodObject(String classInherits, String methodName, GNode methodDecl, String returnType) {
            this.classInherits = classInherits;
            this.methodName = methodName;
            this.methodDecl = methodDecl;
            this.returnType = returnType;
        }

        public String toString() {
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
        public ArrayList<String> methnames = new ArrayList<String>();

        public String toString() {
            String s = "";
            for (MethodObject d: methods) {
                s += d.classInherits +" "+d.methodName+ "\n ";
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
                s += pair.getKey() + " = " + ((JppObject)pair.getValue()).toString()+" ";
                it.remove();
            }
            return s;
        }
    }



}
