package edu.nyu.oop;

import xtc.lang.CPrinter;
import xtc.tree.GNode;
import xtc.tree.Printer;
import xtc.lang.CFeatureExtractor;
import xtc.util.Runtime;
import xtc.util.SymbolTable;


/**
 * Created by charlottephillips on 09/03/17.
 */
public class CppAST extends CPrinter{

    private AstVisitor astVisitor;
    private Runtime runtime;
    CFeatureExtractor cextractor = new CFeatureExtractor(runtime);


    public CppAST(Printer printer) {
        super(printer);
    }

    public void nodeParser(GNode n){
        // create new symbol table for each node, where the node will be set as the root's scope name
        // the symbol table will automatically enter and extit the scope in during traversal
        SymbolTable symbolTable = new SymbolTable(n.toString());
        cextractor.visit(n);
        cextractor.process(n,symbolTable);
        String cID = symbolTable.freshCId(n.toString()); // create a C identifier, where n is the base name
        String nodeName = n.getName();
        nodeName = cID;
    }
}
