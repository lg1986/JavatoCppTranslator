package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Location;


import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
/**
 * Created by rishabh on 23/02/17.
 */
public class AstVisitorTest {

    private static GNode node = null;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Executing LinkedListVisitorTest");
        node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/homework2/LinkedList.java");

    }

    @Test
    public static void testMainClassImport(){
        System.out.println("Checking if the imports in the MainClass are being imported and parsed correctly");

        AstVisitor visitor = new AstVisitor();
        AstVisitor.completeAST asts = visitor.getAllASTs(node);
        List<Node> dependecyASTList = asts.getDependency();

        Node result0 = dependecyASTList.get(0);

        File dependency_file = new File("src/test/java/inputs/test000/test000.java");
        Node dependency = NodeUtil.parseJavaFile(dependency_file);

        System.out.println(dependency.toString());
        assertEquals(dependency.toString(), result0.toString());
    }




}
