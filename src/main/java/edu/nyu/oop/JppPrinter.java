package edu.nyu.oop;

import edu.nyu.oop.util.TypeUtil;
import xtc.lang.JavaEntities;
import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;
import xtc.type.MethodT;
import xtc.type.Type;
import xtc.util.Runtime;
import xtc.util.SymbolTable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 02/05/17.
 */
public class JppPrinter extends Visitor {
    private Printer outputCppPrinter;
    private Printer mainCppPrinter;
    private Printer currentPrinter;
    protected SymbolTable table;
    protected Runtime runtime;

    private List<GNode> asts;
    private String packageName;
    private String currentClassName;
    private GNode currentClassNode;

    private final int MODIFS = 0;
    private final int NAME = 1;
    private final int EXT = 2;
    private final int FIELDS = 3;
    private final int CONSTRS = 4;
    private final int METHOD = 5;
    private final int METHOD_BLOCK = 7;
    private int constNum;

    private String callExpPrim = "";
    protected List<String> changedStrings = new ArrayList<>();

    public void printMain() {
        Writer wMainCpp;

        try {
            FileOutputStream fosMain = new FileOutputStream("output/main.cpp");
            OutputStreamWriter oMainCpp = new OutputStreamWriter(fosMain, "utf-8");
            wMainCpp = new BufferedWriter(oMainCpp);
        } catch (Exception e) {
            throw new RuntimeException("IO Error.");
        }
        Printer mainPrinter = new Printer(wMainCpp);
        mainPrinter.pln("#include \"output.h\"");
        mainPrinter.pln("using namespace java::lang;");
        mainPrinter.pln("int main(int argc, char* argv[]) {");
        mainPrinter.pln("  __rt::Array<String> args = new __rt::__Array<String>(argc - 1);\n");
        mainPrinter.pln("  for (int32_t i = 1; i < argc; i++) {");
        mainPrinter.pln("    (*args)[i] = __rt::literal(argv[i]);\n }");
        mainPrinter.pln("inputs::"+currentClassName.toLowerCase()+"::__"+currentClassName+"::main(args);");
        mainPrinter.pln("return 0;");
        mainPrinter.pln("}");
        mainPrinter.flush();

    }
    /**
     * Main JppPrinter traversal utility
     * basics.
     * @param n
     * @throws IOException
     */


    public JppPrinter(Runtime runtime, Node n, String packageName) throws IOException {
        this.packageName = packageName;
        Writer wMainCpp;
        Writer wOutputCpp;
        try {
            FileOutputStream fosOutputCpp = new FileOutputStream("output/output.cpp");
            OutputStreamWriter oOutputCpp = new OutputStreamWriter(fosOutputCpp, "utf-8");
            wOutputCpp = new BufferedWriter(oOutputCpp);
            this.outputCppPrinter = new Printer(wOutputCpp);
            this.currentPrinter = this.outputCppPrinter;
        } catch (Exception e) {
            throw new RuntimeException("IO Error.");
        }
        getAllASTs(runtime, n);
        writeStartBaseLayout(this.packageName);

//        for(Node s: asts) {
//            ResolveDuplicatesOutput k = new ResolveDuplicatesOutput();
//            changedStrings.addAll(k.resolveDups(s));
//        }
        collect();
        writeEndBaseLayout();
        outputCppPrinter.flush();
    }


    /**
     * Utils for printing basic
     * boiler plate for output.cpp
     */
    public void writeEndBaseLayout() {
        outputCppPrinter.pln("}");
        outputCppPrinter.pln("}");
        getAllTemplateSpecializations();

    }

    public void writeStartBaseLayout(String packageName) {
        outputCppPrinter.pln("#include <iostream>");
        outputCppPrinter.pln("#include \"output.h\"");
        outputCppPrinter.pln("using namespace java::lang;");
        outputCppPrinter.pln("namespace inputs{");
        outputCppPrinter.pln("namespace "+packageName+"{");
    }

    public void printClassGenerics(Node n, String initial) {
        currentPrinter.p("__"+currentClassName+"::__"+currentClassName+"() : __vptr(&__vtable)"+initial);
        currentPrinter.pln("Class __"+currentClassName+"::__class() {");
        currentPrinter.indentMore();
        currentPrinter.pln("static Class k = ");
        currentPrinter.indentMore().indentMore();
        String nextLine = "new __Class(__rt::literal(\"inputs."+this.packageName+"."+ currentClassName.replace("__", "") + "\"), ";
        if(n.get(EXT) == null) {
            nextLine+="__Object::__class());";
        } else {
            String extClass = n.getNode(EXT).getNode(0).getNode(0).getString(0);
            nextLine+=" __"+extClass+"::__class());";
        }
        currentPrinter.pln(nextLine);
        currentPrinter.indentMore();
        currentPrinter.pln("return k;");
        currentPrinter.pln("}");
        currentPrinter.pln("__"+currentClassName+"_VT __" +currentClassName+"::__vtable;");
    }

    public void printTemplateSpecialization(String className, String extender) {
        currentPrinter.pln("template<>");
        currentPrinter.pln("java::lang::Class __Array<inputs::"+this.packageName+"::"+className+">::__class(){");

        currentPrinter.p("static java::lang::Class k =\n");
        currentPrinter.p("new java::lang::__Class(__rt::literal(\"[Linputs."+packageName+"."+className+";\"), \n");
        if(extender.equals("Object"))
            currentPrinter.p("java::lang::__Object::__class(),\n");
        else
            currentPrinter.p("inputs::"+packageName+"::__"+extender+"::__class(), \n");
        currentPrinter.p("inputs::"+packageName+"::__"+className+"::__class());");

        currentPrinter.pln("return k;");
        currentPrinter.pln("}");

    }

    public void getAllTemplateSpecializations() {
        currentPrinter.pln("namespace __rt {");
        for(Node s:asts) {
            String className = s.getString(1);
            String extender = "Object";
            if(s.get(2)!=null) {

                extender = s.getNode(2).getNode(0).getNode(0).getString(0);
            }
            printTemplateSpecialization(className, extender);

        }
        currentPrinter.pln("}");
    }


    /**
     * PRU and corresponding dispatch methods.
     * @param n
     * @return
     */

    public boolean checkIfNode(Object n) {
        if(n instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    public void loopToDispatch(Node n, String from) {
        for(int i = 0; i < n.size(); i++) {
            dispatchTopru(n.get(i), from);
        }
    }

    /**
     * This works in conjunction with the pru --- indirection
     * pattern. This takes into consideration the different
     * "from" decls and figures out what additional aspects need
     * to be added.
     * @param n
     * @param from
     */
    public void dispatchTopru(Object n, String from) {
        if(n != null && checkIfNode(n)) {
            pru((Node)n, from);
        } else if(n != null) {
            if(from.equals("MethodDeclaration")) {
                currentPrinter.p("__"+currentClassName+"::"+n.toString());
            } else if(from.equals("SelectionExpression")) {
                currentPrinter.p("->"+n.toString());
            } else if(from.equals("CallExpression")) {
                currentPrinter.p("->__vptr->" + n.toString());
            } else if(from.equals("Declarator")) {
                currentPrinter.p(n.toString() +" = ");
            } else if(from.equals("PrimitiveType") || from.equals("Type")) {
                String typ = n.toString();
                if(typ.equals("int")) {
                    typ = "int32_t";
                } else if(typ.equals("boolean")) {
                    typ = "bool";
                } else if(typ.equals("byte")) typ="int8_t";
                currentPrinter.p(typ+" ");
            } else {
                currentPrinter.p(n.toString());
            }
        }
    }

    /**
     * The following is the indirection pattern. What this does is
     * depending on where the Node is coming from it decides what the best
     * action to perform would be. After taking that into consideration,
     * it is able to delgate the work.
     * @param n
     * @param from
     */

    public void pru(Node n, String from) {
        if(n.hasName("StringLiteral")) {
            printStringLiteral(n, from);
        } else if(n.hasName("VoidType")) {
            currentPrinter.p("void ");
        } else if(n.hasName("PrimitiveType")) {
            loopToDispatch(n, "PrimitiveType");
        } else if(n.hasName("ReturnStatement")) {
            printReturnStatement(n, from);
        } else if(n.hasName("Block")) {
            printBlock(n, from);
        } else if(n.hasName("Type")) {
            printType(n, from);
        } else if(n.hasName("QualifiedIdentifier")) {
            printQualifiedIdentifier(n, from);
        } else if(n.hasName("FormalParameters")) {
            currentPrinter.pln(getParamsString(n));
        } else if(n.hasName("SelectionExpression")) {
            printSelectionExpression(n, from);
        } else if(n.hasName("ThisExpression")) {
            currentPrinter.p("__this");
        } else if(n.hasName("PrimaryIdentifier")) {
            printPrimaryIdentifier(n, from);
        } else if(n.hasName("ExpressionStatement")) {
            printExpressionStatement(n, from);
        } else if(n.hasName("Expression")) {
            loopToDispatch(n, "Expression");
        } else if(n.hasName("CallExpression")) {
            printCallExpression(n, from);
        } else if(n.hasName("Arguments")) {
            printArgumentsList(n, from);
        } else if(n.hasName("FieldDeclaration")) {
            printFieldDeclaration(n, from);
        } else if(n.hasName("Declarators")) {
            printDeclarators(n, from);
        } else if(n.hasName("Declarator")) {
            printDeclarator(n, from);
        } else if(n.hasName("NewClassExpression")) {
            printNewClassExpression(n, from);
        } else if(n.hasName("IntegerLiteral")) {
            printIntegerLiteral(n, from);
        } else if(n.hasName("BooleanLiteral")) {
            printBooleanLiteral(n, from);
        } else if(n.hasName("CastExpression")) {
            printCastExpression(n, "from");
        } else if(n.hasName("AdditiveExpression")) {
            printAdditiveExpression(n, "AdditiveExpression");
        } else if(n.hasName("FloatingPointLiteral")) {
            printFloatingPointLiteral(n, "FloatingPointLiteral");
        } else if(n.hasName("ForStatement")) {
            printForStatement(n, "ForStatement");
        } else if(n.hasName("WhileStatement")) {
            printWhileStatement(n, "WhileStatement");
        } else if(n.hasName("ExpressionList")) {
            printExpressionList(n, from);
        } else if(n.hasName("RelationalExpression")) {
            printRelationalExpression(n, from);
        } else if(n.hasName("PostfixExpression")) {
            printPostfixExpression(n, from);
        } else if(n.hasName("BasicForControl")) {
            printBasicForControl(n, "BasicForControl");
        } else if(n.hasName("NewArrayExpression")) {
            printNewArrayExpression(n, from);
        } else if(n.hasName("SubscriptExpression")) {
            printSubscriptExpression(n, from);
        } else if(n.hasName("NullLiteral")) {
            printNullLiteral(n, from);
        }
    }
    public void printNullLiteral(Node n, String from) {
        currentPrinter.p("__rt::null()");
    }

    public void printBooleanLiteral(Node n, String from) {
        currentPrinter.p(n.getString(0));
    }

    public void printNewClassExpression(Node n, String from) {
        loopToDispatch(n, "NewClassExpression");
    }

    public void printIntegerLiteral(Node n, String from) {
        loopToDispatch(n, "IntegerLiteral");
    }


    public void printDeclarator(Node n, String from) {
        if(n.get(2) == null) loopToDispatch(n, "DeclaratorNotAssign");
        else loopToDispatch(n, "Declarator");
    }

    public void printDeclarators(Node n, String from) {
        loopToDispatch(n, "Declarators");
    }

    public void printFieldDeclaration(Node n, String from) {
        loopToDispatch(n, "FieldDeclaration");
    }

    public void printExpressionStatement(Node n, String from) {
        loopToDispatch(n, "ExpressionStatement");
    }



    public void printSubscriptExpression(Node n, String from) {
        printPrimaryIdentifier(n.getNode(0), "SubscriptExpression");
        currentPrinter.p("->__data[");
        printPrimaryIdentifier(n.getNode(1), "SubscriptExpression");
        currentPrinter.p("]");
    }

    public void printCallExpression(Node n, String from) {
        if(n.getNode(0).hasName("CastExpression")) {
            currentPrinter.p("(");
            dispatchTopru(n.get(0), "CallExpression");
            currentPrinter.p(")");
            for(int i = 1; i<n.size(); i++) {
                dispatchTopru(n.get(i), "CallExpression");
            }
        } else {
            if (TypeUtil.getType(n.getNode(0)).deannotate().isClass()) {
                currentPrinter.p("__" + n.getNode(0).getString(0) + "::" + n.get(2) + "()");
            } else {
                if (n.getNode(0).hasName("PrimaryIdentifier")) {
                    callExpPrim = n.getNode(0).getString(0);
                } else if (n.getNode(0).hasName("SelectionExpression")) {
                    callExpPrim = n.getNode(0).getNode(0).getString(0);
                }
                if (n.get(2).equals("println") || n.get(2).equals("print")) {
                    currentPrinter.p("std::cout << ");
                    loopToDispatch(n.getNode(3), "CallExpression");
                    if (n.get(2).equals("println")) currentPrinter.p("<< std::endl");
                } else {
                    loopToDispatch(n, "CallExpression");
                }
            }
        }
    }

    public void printArgumentsList(Node n, String from) {
        if(!from.equals("NewClassExpression")) {
            if(callExpPrim == null) callExpPrim = "__this";
            currentPrinter.p("("+callExpPrim);
        }

        for(int i = 0; i<n.size(); i++) {
            currentPrinter.p(",");
            pru(n.getNode(i), "Arguments");

        }
//        loopToDispatch(n, "Arguments");
        currentPrinter.p(")");
    }

    public void printSelectionExpression(Node n, String from) {
        if(from.equals("CallExpression")) {
            if(n.getNode(0).getString(0) == null) {
                callExpPrim = "__this"+"->"+n.getString(1);
            } else {
                callExpPrim = n.getNode(0).getString(0) + "->";
                if(changedStrings.contains(n.getString(1))) {
                    callExpPrim += n.getString(1)+"mutate";
                } else {
                    callExpPrim += n.getString(1);
                }
            }
        }
        if(n.getNode(0).get(0) != null && n.getNode(0).get(0).equals("System")) {
        } else {
            if(TypeUtil.isStaticType(TypeUtil.getType(n))) {
                currentPrinter.p("__"+n.getNode(0).getString(0)+"::"+
                                 n.getString(1));
            } else {
                loopToDispatch(n, "SelectionExpression");
            }
        }
    }



    public void printPrimaryIdentifier(Node n, String from) {
        if(from.equals("SelectionExpression")
                && n.getString(0).equals("System")) {}
        else if(from.equals("ConstructorDeclaration")) {
            currentPrinter.p(n.get(0).toString()+" ");
        } else {
            currentPrinter.p(n.get(0).toString());
        }
    }

    /**
     * getParamsString -- Util
     * @param n
     * @return
     */
    public String getParamsString(Node n) {
        String paramString = "("+currentClassName+" __this";
        for(int i = 0; i < n.size(); i++) {
            Node paramNode = n.getNode(i);

            paramString += ",";
            Node param = n.getNode(i);
            paramString += " "+(param.getNode(1).getNode(0).get(0));
            paramString += " "+param.getString(3);
        }
        paramString += ")";
        return paramString;
    }

    /**
     * All generic print methods
     * dependening on the nature of the
     * node (Redirectred from printCheckStatement).
     * @param n
     * @param from
     */
    public void printStringLiteral(Node n, String from) {
        if(from.equals("NewClassExpression")) {
            currentPrinter.p(n.get(0).toString());
        } else {
            currentPrinter.p("__rt::literal("+n.get(0).toString()+")");
        }
    }

    public void printReturnStatement(Node n, String from) {

        if(n.size() > 0) {
            currentPrinter.p("return ");
            pru(n.getNode(0), "ReturnStatement");
        }
    }

    public void printConstField(Node n, String from) {
        pru(n.getNode(2), "ConstructorDeclaration");
        currentPrinter.p("; \n");
    }


    public boolean checkIfFieldDeclaration(Node n) {
        Node declNode = n.getNode(2).getNode(0);
        if(declNode.get(2) == null) return false;
        else return true;
    }


    public void fieldInitialiationConstructors(Node n) {
        Node fieldsNode = currentClassNode.getNode(FIELDS);
        for(int i = 0; i<fieldsNode.size(); i++) {
            if(checkIfFieldDeclaration(fieldsNode.getNode(i))) {
                currentPrinter.p("__this->");
                printConstField(fieldsNode.getNode(i),
                                "ConstructorDeclaration");
            }
        }
    }

    public void printRelationalExpression(Node n, String from) {
        loopToDispatch(n, from);
    }

    public void printBasicForControl(Node n, String from) {
        currentPrinter.p("for(");
        for(int i = 0; i<n.size(); i++) {
            loopToDispatch(n.getNode(i), "BasicForControl");
            if(i > 1 && i < n.size()-1)currentPrinter.p("; ");
        }
        currentPrinter.pln(")");
    }

    public void printExpressionList(Node n, String from) {
        loopToDispatch(n, from);
    }

    public void printPostfixExpression(Node n, String from) {
        loopToDispatch(n, from);
    }


    public void printForStatement(Node n, String from) {
        loopToDispatch(n, "ForStatement");
    }

    public void printWhileStatement(Node n, String from) {
        loopToDispatch(n, "ForStatement");

    }

    public void printNewArrayExpression(Node n, String from) {
        Node dim = n.getNode(1);
        String typ = n.getNode(0).getString(0);
        currentPrinter.p("new __rt::__Array<"+typ+">");
        for(int i = 0; i < dim.size(); i++) {
            currentPrinter.p("("+dim.getNode(i).get(0)+")");
        }


    }


    /**
     * TO:DO Fix the cheap solution for __this in field
     * initializations
     * @param n
     * @param from
     */
    public void printBlock(Node n, String from) {
        if(from.equals("ConstructorDeclaration")) {

        } else {
            currentPrinter.p("{");
        }
        for(int i = 0; i<n.size(); i++) {
            dispatchTopru(n.get(i), "Block");
            currentPrinter.p("; \n");
        }
        if(from.equals("ConstructorDeclaration")) {
            currentPrinter.pln("return __this;");
        }
        currentPrinter.pln("}");
    }

    public void printArrayType(Node n, String from) {
        Node dim = n.getNode(1);
        currentPrinter.p("__rt::Array");
        for(int i = 0; i<dim.size(); i++) {
            currentPrinter.p("<"+n.getNode(0).getString(0)+">");
        }
        currentPrinter.p(" ");
    }

    public void printType(Node n, String from) {
        if(n.get(1) != null) {
            printArrayType(n, from);
        } else {
            for (int i = 0; i < n.size(); i++) {
                dispatchTopru(n.get(i), from);
            }
        }
    }
    public void printQualifiedIdentifier(Node n, String from) {
        if(from.equals("NewClassExpression")) {
            currentPrinter.p("__"+n.getString(0)+"::__init("+
                             "new __"+n.getString(0)+"()");
        } else {
            currentPrinter.p(n.getString(0) + " ");
        }
    }

    public void printCastExpression(Node n, String from) {
        currentPrinter.p("(");
        printType(n.getNode(0), "CastExpression");
        currentPrinter.p(")");
        currentPrinter.p(" ");
        dispatchTopru(n.get(1), "CastExpression");
    }

    public boolean checkIfStatic(Node n) {
        if(checkIfNode(n.get(0))) {
            Node modifiers = n.getNode(0);

            if(modifiers.size() <= 1) return false;
            if(modifiers.get(1) != null) {
                if(modifiers.getNode(1).getString(0).equals("static")) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    /**
     * This method could have been easily avoided but
     * we were just trying to get the first order.
     * @param n
     * @param isStatic
     * @return
     */
    public String getParamStringForMethods(Node n, boolean isStatic) {
        String paramString = "(";
        if(!isStatic) paramString += currentClassName+" __this";
        for(int i = 0; i < n.size(); i++) {
            if(!isStatic && i == 0) paramString +=", ";
            Node paramNode = n.getNode(i);

            Node typeNode = paramNode.getNode(1);
            if(typeNode.size() == 2 && typeNode.get(1) != null) {

                String arrayParam = "__rt::Array<"+
                                    typeNode.getNode(0).getString(0)+">";
                paramString += arrayParam + paramNode.getString(3);
            } else {
                String typ = typeNode.getNode(0).getString(0);
                if(typ.equals("int")) {
                    typ = "int32_t";
                } else if(typ.equals("byte")) typ="int8_t";
                paramString += typ+" "+paramNode.getString(3);
            }
            if(i != n.size()-1) paramString += ",";
        }
        paramString +=")";
        return paramString;
    }

    /**
     * This does the name mangling for method calls.
     * @param paramNodes
     * @return
     */
    public String getMangler(Node paramNodes) {
        ArrayList<String> paramsList = new ArrayList<>();
        for(int i = 0; i<paramNodes.size(); i++) {
            Node paramNode = paramNodes.getNode(i);
            String paramString = paramNode.getNode(1).getNode(0).getString(0);
            paramsList.add(paramString);
        }
        String retString = "";
        for(String x : paramsList) {
            retString += "__";
            retString += x;
        }
        return retString;
    }

    public void printFloatingPointLiteral(Node n, String from) {
        currentPrinter.p(n.getString(0));
    }
    public void printAdditiveExpression(Node n, String from) {
        loopToDispatch(n, "AdditiveExpression");
    }
    public boolean checkMeth(String name) {
        if(!name.equals("println") && !name.equals("main") && !name.equals("toString") &&
                !name.equals("equals") && !name.equals("getClass"))
            return true;
        return false;
    }

    public void visitMethodDeclaration(GNode n) {
        int consts = 0;
        if(n.getString(3).equals("main")) {
            printMain();
        }
        if(TypeUtil.isStaticType(TypeUtil.getType(n))) {
            if(checkMeth(n.getString(3)))
                n.set(3, n.get(3)+getMangler(n.getNode(4)));
            dispatchTopru(n.get(2), "MethodDeclaration");
            dispatchTopru(n.get(3), "MethodDeclaration");
            currentPrinter.pln(getParamStringForMethods(n.getNode(4), true));
            dispatchTopru(n.get(7), "MethodDeclaration");

        } else {
            dispatchTopru(n.get(2), "MethodDeclaration");
            currentPrinter.p("__"+currentClassName+"::"+n.getString(3)+getMangler(n.getNode(4)));
            currentPrinter.pln(getParamStringForMethods(n.getNode(4), false));
            dispatchTopru(n.get(7), "MethodDeclaration");
        }

    }

    public void visitMethodDeclarations(GNode n) {
        int constructors = 0;
        for(int i = 0; i<n.size(); i++) {
            visitMethodDeclaration((GNode) n.getNode(i));
        }
    }

    /**
     * Constructor Specific Methods:
     * @param constNum
     */

    public void printSuperConstructorInit(int constNum, String paramsList) {
        if(constNum == 1) {
            if(currentClassNode.get(EXT) != null) {
                String extClass =
                    currentClassNode.getNode(EXT).getNode(0).getNode(0).getString(0);
                currentPrinter.pln("__"+extClass+"::__init( __this);");
            } else {
                currentPrinter.pln("__Object::__init(__this);");
            }
        } else {
            currentPrinter.pln("__init(__this);");
        }
    }

    /**
     * Specific ConstructorDeclartion traversal.
     * @param n
     */
    public void printConstructorDeclaration(Node n) {
        currentPrinter.p(currentClassName+" __"+
                         currentClassName+"::__init"+
                         getParamsString(n.getNode(4)));
        currentPrinter.pln("{");
        printSuperConstructorInit(constNum, getParamsString(n.getNode(4)));
        if(constNum == 1) {
            fieldInitialiationConstructors(n);
        }
        printBlock(n.getNode(METHOD_BLOCK), "ConstructorDeclaration");
    }

    /**
     * Default constructor
     */
    public void printDefaultConstructor() {
        currentPrinter.pln(
            currentClassName+" __"+currentClassName+"::__init("+
            currentClassName+ " __this) {");
        currentPrinter.pln("__Object::__init(__this);");
        currentPrinter.pln("return __this; \n }");

    }
    public void visitConstructorDeclarations(GNode n) {
        if(n.size() == 0) {
            printDefaultConstructor();
        }
        for(int i = 0; i<n.size(); i++) {
            constNum += 1;
            printConstructorDeclaration(n.getNode(i));
        }
    }

    /**
     * Class Decalaration. Use the Visitor Double
     * dispatch pattern.
     * @param n
     */
    public void visitClassDeclaration(GNode n) {
        constNum = 0;
        currentClassName = n.getString(1);
        currentClassNode = n;
        this.currentPrinter = this.outputCppPrinter;
        printClassGenerics(n,getFieldInitializations(n.getNode(3)) );
        visit(n);
    }

    public String getInitVal(String typ) {
        if(typ.equals("int32_t")) return "0";
        else if(typ.equals("int8_t")) return "0";
        else if(typ.equals("double")) return "0";
        else if(typ.equals("char")) return "0";
        else if(typ.equals("bool")) return "false";
        else return "0";
    }

    public String getDeclForFieldInit(Node n, String typ) {
        String initVal = null;
        if(n.getNode(2).getNode(0).get(2) != null) {
            initVal = n.getNode(2).getNode(0).getNode(2).getString(0);
        }
        if(initVal == null)initVal = getInitVal(typ);
        return initVal;
    }

    /**
     * This is for the field initializations for every class. It takes
     * care of both the static and the instance fields.
     * @param n
     * @return
     */
    public String getFieldInitializations(Node n) {

        String initial = "";
        String staticfields = "";
        for(int i = 0; i<n.size(); i++) {
            if(!TypeUtil.isStaticType(TypeUtil.getType(n.getNode(i).getNode(2).getNode(0)))) {
                String typ = n.getNode(i).getNode(1).getNode(0).getString(0);
                String decl = n.getNode(i).getNode(2).getNode(0).getString(0);
                initial += "," + decl + "("+getDeclForFieldInit(n.getNode(i), typ) +") ";
            } else {
                String typ = n.getNode(i).getNode(1).getNode(0).getString(0);
                if(typ.equals("int")) typ = "int32_t";
                String decl = n.getNode(i).getNode(2).getNode(0).getString(0);
                staticfields += typ+" __"+currentClassName+"::"+decl+" = ";

                staticfields += getDeclForFieldInit(n.getNode(i), typ) + " ;\n";
            }
        }
        return initial+"{}\n"+staticfields;
    }


    /**
     * General Visitor Pattern Utils
     * @param n
     */

    public void visit(Node n) {
        for(Object o: n) {
            if(o instanceof Node) {
                dispatch((Node) o);
            }
        }
    }

    public void collect() {
        for(Node n:asts) {
            super.dispatch(n);
        }
    }

    /**
     * This is where the chain-of-responsibility pattern.
     * @param runtime
     * @param n
     */

    public void getAllASTs(Runtime runtime, Node n) {
        CreateDependencyTree headerAST = new CreateDependencyTree();
        List<GNode> tree = headerAST.getStackedHeader(n);
        try {
            HeaderFilePrinter headerFile = new HeaderFilePrinter(tree);

        } catch (IOException e) {
            System.out.println("here!");
        }

        JppTraversal visitor = new JppTraversal();
        List<GNode> newtree = visitor.getModifiedAsts(runtime, n);
        this.asts = newtree;
    }






}
