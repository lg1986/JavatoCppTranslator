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

/**
 * Created by rishabh on 26/02/17.
 */
public class AstVisitorTest {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(AstVisitor.class);

    private static GNode node;
    private static AstVisitor visitor;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Executing LinkedListVisitorTest");
        logger.debug("Executing TeamExerciseVisitorTest");
        node = (GNode) XtcTestUtils.loadTestFile("src/test/java/inputs/homework2/LinkedList.java");

    }

    @Before
    public void beforeTest() {
        System.out.println("Executing LinkedListVisitorTest");

        visitor = new AstVisitor();
    }

    public static void main(String[] args){
        System.out.println("here!");
    }


}