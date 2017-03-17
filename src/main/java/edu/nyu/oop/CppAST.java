package edu.nyu.oop;

import java.io.*;
import java.util.*;

import edu.nyu.oop.util.XtcProps;
import org.slf4j.Logger;
import xtc.lang.CPrinter;
import xtc.tree.Node;
import xtc.tree.GNode;
import xtc.tree.Printer;
import xtc.lang.CFeatureExtractor;
import xtc.tree.Visitor;
import edu.nyu.oop.util.NodeUtil;
import xtc.tree.Visitor;
import xtc.util.Runtime;
import xtc.tree.Location;
import xtc.util.SymbolTable;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;

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
