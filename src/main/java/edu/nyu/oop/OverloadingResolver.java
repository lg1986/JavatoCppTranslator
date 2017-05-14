package edu.nyu.oop;

import edu.nyu.oop.util.*;
import xtc.Constants;
import xtc.lang.Java;
import xtc.lang.JavaEntities;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.type.*;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rishabh on 13/05/17.
 */
public class OverloadingResolver extends ContextualVisitor {
    protected Node t;
    protected SymbolTable oldtable;
    public OverloadingResolver(Runtime runtime, SymbolTable table, Node t) {
        super(runtime, table);
        this.t = t;
    }

    public void visitCallExpression(GNode n) {
        visit(n);
        Node receiver = n.getNode(0);
        String methodName = n.getString(2);

        Type typeToSearch = TypeUtil.getType(receiver);
        if(!methodName.equals("println") && !methodName.equals("main")) {
            if (typeToSearch.isVariable()) {
                VariableT vt = typeToSearch.toVariable();
                typeToSearch = vt.getType();
            }
            // find type of called method
            List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
            MethodT method =
                JavaEntities.typeDotMethod(table, classpath(), typeToSearch,
                                           true, methodName, actuals);

            List<Type> params = method.getParameters();
            String overload_params = "";

            for (Type param : params) {
                overload_params += "__";
                String[] s = param.toString().split(", class");
                String over =  (s[0].replace("param(alias(", ""));
                over = over.split(", ")[0].toString().replace("param(", "");
                overload_params += over;
            }
            System.out.println(method);
            n.set(2, n.getString(2)+overload_params);
        }


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