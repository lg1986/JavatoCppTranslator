/**
 *
 */

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


public class OverloadingResolver extends ContextualVisitor {
    protected Node t;
    public OverloadingResolver(Runtime runtime, SymbolTable table, Node t) {
        super(runtime, table);
        this.t = t;
    }

    /**
     * Methods that we do not want to mangle.
     * @param name
     * @return
     */
    public boolean checkMeth(String name) {
        if(!name.equals("println") && !name.equals("main") && !name.equals("toString") &&
                !name.equals("equals") && !name.equals("getClass"))
            return true;
        return false;
    }

    /**
     * VisitCallExpressions -- This is similar to Member Access Completer.
     * The following will take a call exprssion, get the best candidate method
     * and change the name of the method to the appropriate mangled name.
     * @param n
     */
    public void visitCallExpression(GNode n) {
        visit(n);
        Node receiver = n.getNode(0);
        String methodName = n.getString(2);

        Type typeToSearch = TypeUtil.getType(receiver);
        if((typeToSearch.deannotate().isClass())) {
            n.set(2, n.getString(2) + "method");
        } else {

            if (checkMeth(methodName)) {
                if (typeToSearch.isVariable()) {
                    VariableT vt = typeToSearch.toVariable();
                    typeToSearch = vt.getType();
                }
                // find type of called method
                List<Type> actuals = JavaEntities.typeList((List) dispatch(n.getNode(3)));
                MethodT method =
                    JavaEntities.typeDotMethod(table, classpath(), typeToSearch,
                                               true, methodName, actuals);
                if(method == null) return;
                List<Type> params = method.getParameters();
                String overload_params = "";

                for (Type param : params) {
                    overload_params += "__";
                    String[] s = param.toString().split(", class");
                    String over = (s[0].replace("param(alias(", ""));
                    over = over.split(", ")[0].toString().replace("param(", "");
                    overload_params += over;
                }
                n.set(2, n.getString(2) + "method" + overload_params);
            }
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