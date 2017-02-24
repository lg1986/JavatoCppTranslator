package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;

import xtc.parser.ParseException;
import xtc.tree.Node;
import xtc.util.Tool;

import java.io.File;
import java.io.IOException;
import java.io.Reader;


/**
 * Created by j++ on 19/02/17.
 * Creating a tool to recursively create
 * the ASTs for the all the project files
 * and their dependencies.
 */
public class CreateAstParser extends Tool {

    public String getName() {
        return "AST Dependencies Creator";
    }

    public String getCopy() {
        return "AST Dependencies Creator";
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

    // Just by the definition of the tool
    public void process(Node node){
        new AstVisitor().getAllASTs(node);
    }

    public static void main(String[] args) {
        new CreateAstParser().run(args);
    }

}

