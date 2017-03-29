Team j++ -- Translator 5

Phase 1: 
- Goal: Create the Java AST
- Create the AstVisitor class
- Visit each node using double dispatch 
- Created a completeAST object that stores the AST & the dependencies

Phase 2: 
- Goal: Create the data layout and vtable that represents the Java AST
- Created the DependencyDataLayoutTraversal & DependencyVTableTraversal classes
- Data Layout 
  - Visits the fields, method and class declarations and compilation unit
  - Traverses AST, adds all the fields to a fields arrayList 
  - If a class extends another class, checks if the field already exists in the superclass
- VTable 
  - Use a hashmap<String, JppObject> to store the values for easier and more efficient access
  - Uses the class name to access the object
  - Each class has an object representation with all of its methods
  - Allows to check inheritance
  
Phase 3: 
- Goal: Create the header file output.h from the phase 2 ASTs 
- Created the CreateHeaderDataLayout & CreateHeaderVTable classes
- Generates the C++ syntax for the header file
- Traverse to get the data layout AST
- Visits each node of AST, prints the C++ version 

Phase 4: 
- Goal: Create the C++ AST from the Java AST
- Created the JppTraversal class
- Traverses the Java AST, visits each scope, expressions, declarations, etc
- Mutates the Java syntax into a C++ syntax 
- Builds the C++ AST in the cppAST objects

Phase 5: 
- Goal: Printing C++ code from the C++ AST
- Created the JppPrinter class
- Traverses the C++ AST from phase 4 and prints the correct C++ syntax
- Still in development


