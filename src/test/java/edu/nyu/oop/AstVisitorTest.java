package edu.nyu.oop;

import xtc.tree.GNode;
import xtc.tree.Location;


import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        // XtcTestUtils.prettyPrintAst(node);
    }


}
