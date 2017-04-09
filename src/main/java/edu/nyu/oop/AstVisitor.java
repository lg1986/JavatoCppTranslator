package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.Runtime;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by rishabh on 19/02/17.
 */

public class AstVisitor extends Visitor {

    private Runtime runtime;

    private completeAST asts = new completeAST();

    /**
     * This gets the File object
     * the src/test/java folder is where all the dependencies and test files are
     * @param k
     */
    public File getFile(Node k) {
        String file_path = "src/test/java/";
        for(int i = 0; i<k.size()-1; i++) {
            file_path += k.get(i).toString()+"/";
        }
        file_path+=k.get(k.size()-2)+".java";
        File f = new File(file_path);
        return f;
    }

    // Double dispatch visitImport declaration
    public void visitImportDeclaration(GNode n) {
        // Creating new dependency
        Node dependency = NodeUtil.parseJavaFile(getFile(n.getNode(1)));

        // Adding to asts
        asts.addAST(dependency);
        visit(dependency);
    }

    // This is "double" dispatch
    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    // This is the first dispatch
    public completeAST getAllASTs(Node n) {
        asts.addAST(n);
        super.dispatch(n);
        return asts;
    }

    static class completeAST {
        public List<Node> asts = new ArrayList<Node>();

        public List<Node> getDependency() {
            return asts;
        }

        public void addAST(Node n) {
            this.asts.add(n);
        }

        public String toString() {
            String ast_string = "";
            for(Node l: asts) {
                ast_string += l.toString() + "\n";
            }
            return ast_string;
        }
    }



}
