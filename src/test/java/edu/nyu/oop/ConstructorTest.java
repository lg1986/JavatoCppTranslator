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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;

/**
 * Created by nicholas on 4/19/17.
 */
public class ConstructorTest {

    private static GNode n;

    @BeforeClass
    public static void beforeClassTest() {
        n = (GNode) XtcTestUtils.loadTestFile(("src/test/java/inputs/UnitTest/ConstructorTest.java"));
    }

    @Before
    public void beforeTest() {
        System.out.println("Executing ConstructorTest");
//        try {
//                CreateHeaderDataLayout head = new CreateHeaderDataLayout(n);
//                jppPrinter jpp = new jppPrinter(n);
//            } catch (IOException e) {
//
//            }
    }


    @Test
    public void testConstructor() {
        try {
            // setup
            File f = new File("output/output.cpp");
            ArrayList<String> lines = new ArrayList<String>();
            Scanner s = new Scanner(f);

            // read lines
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }

            // asserts
            // lines are line + 1
            // TODO: Add dummy, change line numbers
            assertEquals("A::__init(new__A(), (A __this , String s)) {", lines.get(12));    // vtable implementation for A(String s)
            assertEquals("A::__init(new__A(), (A __this ) {", lines.get(14));    // vtable implementation for A()
            s.close();
        } catch (FileNotFoundException e) {

        }
   }
}
