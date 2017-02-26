package edu.nyu.oop;

import edu.nyu.oop.util.NodeUtil;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Location;

import org.slf4j.Logger;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;


public class AstVisitorTest {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(AstVisitor.class);

    private static GNode node;
    private static AstVisitor visitor;

    @BeforeClass
    public static void beforeClass() {
        node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test001/Test001.java");
    }

    @Before
    public void beforeTest() {
        System.out.println("Executing LinkedListVisitorTest");
        visitor = new AstVisitor();
    }


    @Test
    public void testMainClassImport(){
        AstVisitor.completeAST asts = visitor.getAllASTs(node);
        List<Node> dependecyASTList = asts.getDependency();

        Node result0 = dependecyASTList.get(0);

        GNode dependecy_node = (GNode) XtcTestUtils.loadTestFile(
                "src/test/java/inputs/test004/Test004.java");

        System.out.println(dependecy_node.toString());
        assertEquals(dependecy_node.toString(), result0.toString());
    }




}