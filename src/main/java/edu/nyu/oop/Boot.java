package edu.nyu.oop;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import edu.nyu.oop.util.NodeUtil;
import edu.nyu.oop.util.SymbolTableBuilder;
import edu.nyu.oop.util.XtcProps;
import org.slf4j.Logger;


import xtc.lang.JavaPrinter;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.util.SymbolTable;
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
        bool("createDependencyTree", "createDependencyTree", false, "Create Dependency Tree").
        bool("createHeaderFile", "createHeaderFile", false, "createHeaderFile").
        bool("jppPrinter", "jppPrinter", false, "jppPrinter").
        bool("jppTraversal", "jppTraversal",false, "jppTraversal").
        bool("completeMemberAccesses", "completeMemberAccesses", false, "Make all receivers of member accesses explicit.").
        bool("printJavaAST", "printJavaAST", false, "Print Java AST.");

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

        /**
         * Phase 1 - Parse all dependency classes
         * Command - "runxtc -createAllAST <path-to-file>"
         */
        if(runtime.test("createAllAST")) {

            DependencyAstVisitor visitor = new DependencyAstVisitor();
            List<GNode> astVisit = visitor.getDependencyAsts(n);
            for(GNode ast: astVisit) {

                runtime.console().format(ast).pln().flush();
            }
        }
        if(runtime.test("createDependencyTree")) {
            CreateDependencyTree headerAST = new CreateDependencyTree();
            List<GNode> tree = headerAST.getStackedHeader(n);
            for(GNode ast: tree) {
                runtime.console().format(ast).pln().flush();
            }
        }

        if(runtime.test("createHeaderFile")) {
            CreateDependencyTree headerAST = new CreateDependencyTree();
            List<GNode> tree = headerAST.getStackedHeader(n);

            try {
                HeaderFilePrinter headerFile = new HeaderFilePrinter(tree);

            } catch (IOException e) {
                System.out.println("here!");
            }

        }

        if (runtime.test("completeMemberAccesses")) {
            SymbolTable table = new SymbolTableBuilder(runtime).getTable(n);
            new MemberAccessCompleter(runtime, table).dispatch(n);
            new JavaPrinter(runtime.console()).dispatch(n);

//            runtime.console().flush();
        }

        if(runtime.test("jppPrinter")) {
            try {
                String packageName = n.getNode(0).getNode(1).getString(1);
                JppPrinter printer = new JppPrinter(runtime, n,packageName);
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        if(runtime.test("jppTraversal")) {
            JppTraversal s = new JppTraversal();
            s.getModifiedAsts(runtime, n);
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