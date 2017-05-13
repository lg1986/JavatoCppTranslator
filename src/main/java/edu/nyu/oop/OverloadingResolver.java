package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import edu.nyu.oop.util.TypeUtil;
import xtc.Constants;
import xtc.lang.JavaEntities;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.type.MethodT;
import xtc.type.Type;
import xtc.type.VariableT;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 13/05/17.
 */
public class OverloadingResolver extends ContextualVisitor {
    public OverloadingResolver(Runtime runtime, SymbolTable table) {
        super(runtime, table);
    }

    public void visitCallExpression(GNode n) {
        visit(n);
        Node receiver = n.getNode(0);
        String methodName = n.getString(2);
        System.out.println(methodName);

        Type typeToSearch = JavaEntities.currentType(table);

        // find type of called method
        List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
        MethodT method =
                JavaEntities.typeDotMethod(table, classpath(), typeToSearch, true, methodName, actuals);

        System.out.println(method);
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
