package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import edu.nyu.oop.util.TypeUtil;
import org.slf4j.Logger;
import xtc.lang.JavaEntities;

import xtc.Constants;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.util.SymbolTable;
import xtc.util.Runtime;
import xtc.type.*;

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
            // find type to search for relevant methods
            Type typeToSearch = JavaEntities.currentType(table);

            // find type of called method
            List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
            MethodT method =
                JavaEntities.typeDotMethod(table, classpath(), typeToSearch, true, methodName, actuals);

            if (method == null) return;

            // TODO: make 'this' access explicit
            if (!TypeUtil.isStaticType(method)) {
                n.set(0, makeThisExpression());
            }
        }
    }


    public Node visitPrimaryIdentifier(GNode n) {
        String fieldName = n.getString(0);

        // find type to search for relevant fields
        ClassOrInterfaceT typeToSearch = JavaEntities.currentType(table);
        if (typeToSearch == null) return n;

        // find type of
        VariableT field = null;
        SymbolTable.Scope oldScope = table.current();
        JavaEntities.enterScopeByQualifiedName(table, typeToSearch.getScope());
        for (final VariableT f : JavaEntities.fieldsOwnAndInherited(table, classpath(), typeToSearch))
            if (f.getName().equals(fieldName)) {
                field = f;
                break;
            }
        table.setScope(oldScope);

        if (field == null) return n;

        // TODO: make 'this' access explicit
        Type t = (Type) table.lookup(fieldName);
        if (t == null || !t.isVariable()) {
            t = field;
        }

        if (JavaEntities.isFieldT(t) && !TypeUtil.isStaticType(t)) {
            GNode n1 = GNode.create("SelectionExpression", makeThisExpression(), fieldName);
            TypeUtil.setType(n1, TypeUtil.getType(n));
            return n1;
        }

        return n;
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