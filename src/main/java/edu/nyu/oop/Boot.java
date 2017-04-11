package edu.nyu.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.oop.util.NodeUtil;
import edu.nyu.oop.util.XtcProps;
import org.slf4j.Logger;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.util.Tool;
import xtc.parser.ParseException;

/**
 * This is the entry point to your program. It configures the user interface, defining
 * the set of valid commands for your tool, provides feedback to the user about their inputs
 * and delegates to other classes based on the commands input by the user to classes that know
 * how to handle them. So, for example, do not put translation code in Boot. Remember the
 * Single Responsiblity Principle https://en.wikipedia.org/wiki/Single_responsibility_principle
 */
public class Boot extends Tool {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public String getName() {
        return XtcProps.get("app.name");
    }

    @Override
    public String getCopy() {
        return XtcProps.get("group.name");
    }

    @Override
    public void init() {
        super.init();
        // Declare command line arguments.

        runtime.bool("createAllAST", "createAllAST", false, "Create all ASTs").
        bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
        bool("createHeaderFile", "createHeaderFile", false, "Create Header File").
        bool("createVTableHeader", "createVTableHeader", false, "Create VTable Header").
        bool("dependencyVTableTraversal", "dependencyVTableTraversal", false, "Gets VTable AST").
        bool("jppPrinter", "jppPrinter", false, "jpp Printer").
        bool("jppTraversal", "jppTraversal", false, "Traverse jpp").
        bool("dependencyTraversal", "dependencyTraversal", false, "Gets Dependency Travel");
    }

    @Override
    public void prepare() {
        super.prepare();
        // Perform consistency checks on command line arguments.
        // (i.e. are there some commands that cannot be run together?)
        // logger.debug("This is a debugging statement."); // Example logging statement, you may delete
    }

    @Override
    public File locate(String name) throws IOException {
        File file = super.locate(name);
        if (Integer.MAX_VALUE < file.length()) {
            throw new IllegalArgumentException("File too large " + file.getName());
        }
        if(!file.getAbsolutePath().startsWith(System.getProperty("user.dir"))) {
            throw new IllegalArgumentException("File must be under project root.");
        }
        return file;
    }

    @Override
    public Node parse(Reader in, File file) throws IOException, ParseException {
        return NodeUtil.parseJavaFile(file);
    }

    @Override
    public void process(Node n) {

        if (runtime.test("printJavaAST")) {
            runtime.console().format(n).pln().flush();
        }

        if(runtime.test("createAllAST")) {
            AstVisitor.completeAST x = new AstVisitor().getAllASTs(n);
            for(Node k: x.asts){
                runtime.console().format(k).pln().flush();
            }


        }

        if(runtime.test("dependencyTraversal")) {
            DependencyDataLayoutTraversal visitor = new DependencyDataLayoutTraversal();
            AstVisitor astVisitor = new AstVisitor();
            AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
            List<Node> dependencyList = depe.getDependency();
            ArrayList<GNode> dataLayout = visitor.getSummary(dependencyList).dependencyAsts;
            for(GNode data:dataLayout) {
                runtime.console().format(data).pln().flush();
            }
        }

        if(runtime.test("createHeaderFile")) {
            try {
                CreateHeaderDataLayout head = new CreateHeaderDataLayout(n);
            } catch (IOException e) {

            }

        }

        if(runtime.test("jppPrinter")) {
            try {
                CreateHeaderDataLayout head = new CreateHeaderDataLayout(n);
//                jppPrinter jpp = new jppPrinter(n);
            } catch (IOException e) {

            }
        }


        if(runtime.test("dependencyVTableTraversal")) {
            DependencyVTableTraversal visitor = new DependencyVTableTraversal();
            AstVisitor astVisitor = new AstVisitor();
            AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
            List<Node> dependencyList = depe.getDependency();
            ArrayList<GNode> vtable = visitor.getSummary(dependencyList).vtableAsts;
        }



        if(runtime.test("jppTraversal")) {
            jppTraversal jppTraversal = new jppTraversal();
            AstVisitor astVisitor = new AstVisitor();
            AstVisitor.completeAST depe = astVisitor.getAllASTs(n);
            List<Node> astList = depe.getDependency();
            List<Node> jppList = jppTraversal.getSummary(astList);
            for(Node element:jppList) {
                runtime.console().format(element).pln().flush();
            }

        }

    }

    /**
     * Run Boot with the specified command line arguments.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Boot().run(args);
    }
}