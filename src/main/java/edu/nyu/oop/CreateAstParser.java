package edu.nyu.oop;

import edu.nyu.oop.util.AstVisitor;
import edu.nyu.oop.util.JavaFiveImportParser;
import edu.nyu.oop.util.NodeUtil;
import org.slf4j.Logger;

import xtc.parser.ParseException;
import xtc.tree.Node;
import xtc.util.Runtime;
import xtc.util.Tool;
import xtc.tree.GNode;
import xtc.tree.Visitor;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;


/**
 * Created by j++ on 19/02/17.
 * Creating a tool to recursively create
 * the ASTs for the all the project files
 * and their dependencies.
 */
public class CreateAstParser extends Tool {

    public String getName() {
        return "Method Counter";
    }

    public String getCopy() {
        return "In-class demo of translator.";
    }

    public void init() {
        super.init();
        // Declare command line arguments.
        runtime.bool("createAllAST", "createAllAST", false, "Create all ASTs");

    }

    public void prepare() {
        super.prepare();
        // Perform consistency checks on command line arguments.
    }

    // The parse method parses the content of the file and then returns the AST that was generated.
    @Override
    public Node parse(Reader in, File file) throws IOException, ParseException {
        return NodeUtil.parseJavaFile(file);
    }

    public void process(Node node){
        new AstVisitor().getAllASTs(node);
    }


    public static void main(String[] args) {
        new CreateAstParser().run(args);
    }

}

