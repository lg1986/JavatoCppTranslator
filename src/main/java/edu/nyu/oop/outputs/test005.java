CompilationUnit(PackageDeclaration(null, QualifiedIdentifier("inputs", "test005")), ClassDeclaration(Modifiers(), "A", null, null, null, ClassBody(MethodDeclaration(Modifiers(Modifier("public")), null, Type(QualifiedIdentifier("String"), null), "toString", FormalParameters(), null, null, Block(ReturnStatement(StringLiteral("\"A\"")))))), ClassDeclaration(Modifiers(), "B", null, Extension(Type(QualifiedIdentifier("A"), null)), null, ClassBody(MethodDeclaration(Modifiers(Modifier("public")), null, Type(QualifiedIdentifier("String"), null), "toString", FormalParameters(), null, null, Block(ReturnStatement(StringLiteral("\"B\"")))))), ClassDeclaration(Modifiers(Modifier("public")), "Test005", null, null, null, ClassBody(MethodDeclaration(Modifiers(Modifier("public"), Modifier("static")), null, VoidType(), "main", FormalParameters(FormalParameter(Modifiers(), Type(QualifiedIdentifier("String"), Dimensions("[")), null, "args", null)), null, null, Block(FieldDeclaration(Modifiers(), Type(QualifiedIdentifier("B"), null), Declarators(Declarator("b", null, NewClassExpression(null, null, QualifiedIdentifier("B"), Arguments(), null)))), FieldDeclaration(Modifiers(), Type(QualifiedIdentifier("A"), null), Declarators(Declarator("a1", null, NewClassExpression(null, null, QualifiedIdentifier("A"), Arguments(), null)))), FieldDeclaration(Modifiers(), Type(QualifiedIdentifier("A"), null), Declarators(Declarator("a2", null, PrimaryIdentifier("b")))), ExpressionStatement(CallExpression(SelectionExpression(PrimaryIdentifier("System"), "out"), null, "println", Arguments(CallExpression(PrimaryIdentifier("a1"), null, "toString", Arguments())))), ExpressionStatement(CallExpression(SelectionExpression(PrimaryIdentifier("System"), "out"), null, "println", Arguments(CallExpression(PrimaryIdentifier("a2"), null, "toString", Arguments())))))))))