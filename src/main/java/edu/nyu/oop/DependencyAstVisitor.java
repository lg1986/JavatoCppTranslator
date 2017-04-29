/*
 * Phase 1 - Parse all dependency classes
 *
 * Author: Team j++
 *
 * In this phase most of the work is done
 * by using the JavaFiveImportParser. The
 * only additional feature that was needed
 * was to do this recursively.
 *
 */

package edu.nyu.oop;

import edu.nyu.oop.util.ContextualVisitor;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;
import xtc.util.SymbolTable;
import xtc.util.Runtime;

import edu.nyu.oop.util.JavaFiveImportParser;

import java.util.*;


/**
 * Class DependencyAstVisitor - Extends the Visitor
 * and provides the required
 * recursive parsing functionality.
 */
public class DependencyAstVisitor  extends Visitor {

    public HashSet<GNode> dependencyAsts = new HashSet<>();




    /**
     * Method to interface with this class.
     * @param n - This is the root node.
     * @return List<GNode> that contains all
     * the parsed dependency nodes.
     */
    public List<GNode> getDependencyAsts(Node n) {
        this.dependencyAsts.add((GNode)n);
        super.dispatch(n);
        return new ArrayList<>(this.dependencyAsts);
    }

    /**
     * Method to parse all the imported classes.
     * @param n - All parent nodes.
     */
    public void visitImportDeclaration(GNode n) {
        List<GNode> dependencies = JavaFiveImportParser.parse(n);
        for (GNode dependency: dependencies) {
            this.dependencyAsts.add(dependency);
            visit(dependency);
        }
    }

    /**
     * Utilizing the Visitor Design pattern.
     * @param n
     */
    public void visit(Node n) {
        for (Object o : n) {
            if (o instanceof Node) dispatch((Node) o);
        }
    }
}
