Team j++ -- Translator 5

Run everything - 
runxtc -jppPrinter src/test/java/inputs/testX/testX.java

Phase 1: 
- Goal: Create the Java AST
- Create the AstVisitor class
- Visit each node using double dispatch 
- Created a completeAST object that stores the AST & the dependencies\
- Command: runxtc -createAllAST <test-file-path-here>

Phase 2: 
- Goal: Creates the DepdencyTree Layout for all the classes.
- Command: runxtc -createDependencyTree <test-file-path-here>
  
Phase 3: 
- Goal: Create the header file output.h from the phase 2 AST
- Command: runxtc -createHeaderFile <test-file-path-here>

Phase 4: 
- Goal: Create the C++ AST from the Java AST
- Created the JppTraversal class
- Traverses the Java AST, visits each scope, expressions, declarations, etc
- Mutates the Java syntax into a C++ syntax 
- Does name mangling
- Member access completer (__this) appending
- Builds the C++ AST in the cppAST objects
- Commmand: runxtc -jppTraversal <test-file-path-here>

Phase 5: 
- Goal: Printing C++ code from the C++ AST
- Completed Phase 5
- Command: runxtc -jppPrinter <test-file-path-here>

Additional Features:
1. Overloading Resolution
2. Constructors
3. Design Patterns (Chain-of-responsibility)
4. Basic Arrays