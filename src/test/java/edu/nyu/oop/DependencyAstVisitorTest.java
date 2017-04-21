package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Node;

import org.slf4j.Logger;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.util.List;


public class DependencyAstVisitorTest {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(DependencyAstVisitor.class);

    private static GNode node;
    private static DependencyAstVisitor visitor;

    @BeforeClass
    public static void beforeClass() {
        node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/test001/Test001.java");
    }

    @Before
    public void beforeTest() {
        System.out.println("Executing LinkedListVisitorTest");
        visitor = new DependencyAstVisitor();
    }


    @Test
    public void testMainClassImport() {
//        DependencyAstVisitor.completeAST asts = visitor.getAllASTs(node);
//        List<Node> dependecyASTList = asts.getDependency();
//
//        Node result0 = dependecyASTList.get(0);
//        Node result1 = dependecyASTList.get(1);
//        Node result2 = dependecyASTList.get(2);
//
//        GNode dependecyNode0 = (GNode) XtcTestUtils.loadTestFile(
//                                   "src/test/java/inputs/test004/Test004.java");
//        GNode dependecyNode1 = (GNode) XtcTestUtils.loadTestFile(
//                                   "src/test/java/inputs/test005/Test005.java");
//        GNode dependecyNode2 = (GNode) XtcTestUtils.loadTestFile(
//                                   "src/test/java/inputs/test005/Test005.java");
//
//        assertEquals(dependecyNode0.toString(), result0.toString());
//        assertEquals(dependecyNode1.toString(), result1.toString());
//        assertEquals(dependecyNode2.toString(), result2.toString());
    }
}