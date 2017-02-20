package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.Runtime;


import java.io.File;
import java.io.IOException;
import java.io.Reader;


/**
 * Created by rishabh on 19/02/17.
 */
public class AstVisitor extends Visitor {

    private Runtime runtime;

    public void visitImportDeclaration(GNode n){
        System.out.println(n);
        Node k = n.getNode(1);
        String file_path = "src/test/java/";
        for(int i = 0; i<k.size()-1; i++){
            file_path += k.get(i).toString()+"/";
        }
        file_path+=k.get(k.size()-2)+".java";
        System.out.println(file_path);
        File f = new File(file_path);
        visit(NodeUtil.parseJavaFile(f));
    }

    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }

    public void getAllASTs(Node n){
        super.dispatch(n);
    }

}
