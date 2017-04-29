package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import edu.nyu.oop.util.TypeUtil;
import xtc.lang.Java;
import xtc.lang.JavaEntities;

import xtc.Constants;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.util.SymbolTable;
import xtc.util.Runtime;
import xtc.type.*;

import java.io.File;
import java.util.*;

public class MemberAccessCompleter extends ContextualVisitor {

    public MemberAccessCompleter(Runtime runtime, SymbolTable table) {
        super(runtime, table);
    }

    public GNode makeThisExpression() {
        GNode _this = GNode.create("ThisExpression", null);
        TypeUtil.setType(_this, JavaEntities.currentType(table));
        return _this;
    }

    public void visitCallExpression(GNode n) {
        visit(n);
        Node receiver = n.getNode(0);
        String methodName = n.getString(2);

        if (receiver == null &&
                !"super".equals(methodName) &&
                !"this".equals(methodName)) {
            Type typeToSearch = JavaEntities.currentType(table);

            List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
            MethodT method =
                JavaEntities.typeDotMethod(table, classpath(), typeToSearch, true, methodName, actuals);
            if (method == null) return;

            // TODO: make 'this' access explicit
            if(TypeUtil.isStaticType(method) == false) {
                n.set(0, makeThisExpression());
            }


            File f = new File("src/test/java/symboltable/Input.java");
            List<File> files = new ArrayList<>();
            files.add(f);
            ClassOrInterfaceT classT = JavaEntities.currentType(table);
            List<MethodT> methods = (JavaEntities.allMethods(table, files,
                                     (ClassT) classT));
            for (MethodT ml : methods) {
                System.out.println(ml.toMethod());
            }
        }
    }


    public Node visitPrimaryIdentifier(GNode n) {
        String fieldName = n.getString(0);

        ClassOrInterfaceT typeToSearch = JavaEntities.currentType(table);
        if (typeToSearch == null) return n;

        VariableT field = null;
        SymbolTable.Scope oldScope = table.current();
        JavaEntities.enterScopeByQualifiedName(table, typeToSearch.getScope());
        for (final VariableT f : JavaEntities.fieldsOwnAndInherited(table, classpath(), typeToSearch)) {
            if (f.getName().equals(fieldName)) {
                field = f;
                break;
            }
        }
        table.setScope(oldScope);


        if (field == null) return n;


        // TODO: make 'this' access explicit
        try {
            // If the scope of the table does not match the field name's scope
            if(!oldScope.toString().equals(
                        table.lookupScope(fieldName).toString())) {
                if(TypeUtil.isStaticType(field) == false) {
                    n = replaceWithThis(field, fieldName);
                }
            }
        } catch (NullPointerException e) {
            if(TypeUtil.isStaticType(field) == false) {
                n = replaceWithThis(field, fieldName);
            }
        }
        return n;
    }

    public GNode replaceWithThis(Type typ, String name) {
        GNode makeThis = makeThisExpression();
        GNode c = GNode.create("SelectionExpression");
        c.addNode(makeThis);
        c.add(name);
        TypeUtil.setType(c, typ);
        return c;
    }

    public List<Type> visitArguments(final GNode n) {
        List<Type> result = new ArrayList<Type>(n.size());
        for (int i = 0; i < n.size(); i++) {
            GNode argi = n.getGeneric(i);
            Type ti = (Type) argi.getProperty(Constants.TYPE);
            if (ti.isVariable()) {
                VariableT vi = ti.toVariable();
                ti = vi.getType();
            }
            result.add(ti);
            Object argi1 = dispatch(argi);
            if (argi1 != null && argi1 instanceof Node) {
                n.set(i, argi1);
            }
        }
        return result;
    }

    public void visit(GNode n) {
        for (int i = 0; i < n.size(); ++i) {
            Object o = n.get(i);
            if (o instanceof Node) {
                Object o1 = dispatch((Node) o);
                if (o1 != null && o1 instanceof Node) {
                    n.set(i, o1);
                }
            }
        }
    }

}