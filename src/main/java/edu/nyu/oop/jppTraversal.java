package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import java.util.ArrayList;
import java.util.List;


public class jppTraversal extends Visitor {


    protected cppAST cpp = new cppAST();
    protected GNode classNode;

    public void visitConstructorDeclaration(GNode n) {
        try {
            GNode constructorNode = GNode.create("ConstructorDeclaration");
            if(n.getNode(3) != null) {
                n.set(1,n.getNode(3).getNode(0).getNode(1).getNode(0).get(0).toString()); // get the type
                n.set(2,n.getNode(3).getNode(0).get(3).toString()); // get the params
            }


        } catch (NullPointerException e) {
            visit(n);
        } catch (IndexOutOfBoundsException e) {
            visit(n);
        }
    }

    public void visitStringLiteral(GNode n) {
        n.set(0, "__rt::literal("+n.get(0)+")");
        visit(n);
    }

    public void visitIntegerLiteral(GNode n) {
        visit(n);
    }

    public void visitQualifiedIdentifier(GNode n) {
        if(n.get(0).toString().compareTo("Integer")==0) {
            n.set(0, "int32_t");
        } else if(n.get(0).toString().compareTo("int") == 0) {
            n.set(0, "int32_t");
        }
        visit(n);
    }

    public void visitArguments(GNode n) {
        visit(n);
    }

    public void visitSelectionExpression(GNode n) {
        n.set(0, n.getNode(0).get(0).toString()+" "+n.get(1).toString());
        n.set(1, null);
        visit(n);
    }
    /**
     * This translates Call Expressions and Selection Expressions
     * @param n
     */
    public void fixArg(Node n) {

        if(n != null && n.size()>0) {
            Node s = n.getNode(0);
            if(s.getName().compareTo("CallExpression")==0) {
                String obj = s.get(0).toString();
                String call = s.get(2).toString();
                String exp = obj+"->"+"_vptr->"+call+"("+obj+")"+"->data";
                s.set(0, exp);
                n.set(0, n.getNode(0).get(0));
            } else if (s.getName().compareTo("SelectionExpression")==0) {
                String[] obj = s.get(0).toString().split(" ");
                String exp = obj[0]+"->_vptr->"+obj[1]+"->data";
                s.set(0, exp);
                n.set(0, n.getNode(0).get(0));
            }
        }
    }
    public void visitCallExpression(GNode n) {
        visit(n);
        n.set(0, n.getNode(0).get(0).toString());
        fixArg(n.getNode(n.size()-1));
    }


    public void visitType(GNode n) {
        visit(n);
    }

    public void visitFormalParameter(GNode n) {
        visit(n);
    }

    public void visitClassDeclaration(GNode n) {
        classNode = GNode.ensureVariable(n);
        visit(n);
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }


    public List<Node> getSummary(List<Node> cppList) {
        for(Node n: cppList) {
            super.dispatch(n);
        }
        return cppList;
    }


    static class cppAST {
        protected static List<Node> cppasts = new ArrayList<Node>();

        public List<Node> getDependency() {
            return cppasts;
        }

        public void addAST(GNode n) {
            this.cppasts.add(n);
        }

        public String toString() {
            String ast_string = "";
            for(Node l: cppasts) {
                ast_string += l.toString() + "\n";
            }
            return ast_string;
        }
    }
}