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
        try {
            CreateHeaderDataLayout head = new CreateHeaderDataLayout(n);
            jppPrinter jpp = new jppPrinter(n, true);
        } catch (IOException e) {

        }
    }


    @Test
    public void testConstructor() {
        try {
            // setup
            File f = new File("output/constructors.cpp");
            ArrayList<String> lines = new ArrayList<String>();
            Scanner s = new Scanner(f);

            // read lines
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }

            // asserts
            // lines are line - 1
            assertEquals("A __A::__init(new__A(),A __this , String s) { ", lines.get(1 - 1));    // vtable implementation for A(String s)
            assertEquals("A __A::__init(new__A(),A __this ) { ", lines.get(4 - 1));    // vtable implementation for A()
            assertEquals("B __B::__init(new__B(),B __this ) { ", lines.get(7 - 1));    // vtable implementation for B()
            assertEquals("C __C::__init(new__C(),C __this ) { ", lines.get(10 - 1));    // vtable implementation for C()
            assertEquals("C __C::__init(new__C(),C __this , int i) { ", lines.get(13 - 1));    // vtable implementation for C(int i)
            assertEquals("C __C::__init(new__C(),C __this , double d) { ", lines.get(16 - 1)); // vtable implementation for C(double d)
            assertEquals("a1 = A__::init(new __A(),\"test\");", lines.get(21 - 1));     // use of A(String s)
            assertEquals("a = A__::init(new __A());", lines.get(22 - 1));   // use of A()
            assertEquals("b = B__::init(new __B());", lines.get(23 - 1));  // use of B()
            assertEquals("c = C__::init(new __C(),3);", lines.get(24 - 1));  // use of C(3)
            assertEquals("c2 = C__::init(new __C(),5.4);", lines.get(25 - 1));  // use of C(5.4)
            assertEquals("c3 = C__::init(new __C());", lines.get(26 - 1));  // use of C()


            s.close();
        } catch (FileNotFoundException e) {

        }
    }
}
