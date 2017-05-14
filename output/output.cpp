#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test024
{
__A::__A() : __vptr(&__vtable) {}
Class __A::__class()
{
    static Class k =
        new __Class(__rt::literal("inputs.test024.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this, int i)
{
    __Object::__init(__this);
    __this->i=i;
    return __this;
}
int32_t __A::get(A __this)
{
    return __this->i;
}
__Test024::__Test024() : __vptr(&__vtable) {}
Class __Test024::__class()
{
    static Class k =
        new __Class(__rt::literal("inputs.test024.Test024"), __Object::__class());
    return k;
}
__Test024_VT __Test024::__vtable;
Test024 __Test024::__init(Test024 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test024::main(__rt::Array<String>args)
{
    __rt::Array<Object> as = new __rt::__Array<A>(10);
    for(int32_t i = 0; i<as->length; i++)
    {
        as->__data[i]=__A::__init(new __A(), i);
    }
    ;
    int32_t k = 0;
    ;
}
}
}
namespace __rt
{
template<>
java::lang::Class __Array<inputs::test024::A>::__class()
{
    static java::lang::Class k =
        new java::lang::__Class(__rt::literal("[Linputs.test024.A;"),
                                java::lang::__Object::__class(),
                                inputs::test024::__A::__class());
    return k;
}
template<>
java::lang::Class __Array<inputs::test024::Test024>::__class()
{
    static java::lang::Class k =
        new java::lang::__Class(__rt::literal("[Linputs.test024.Test024;"),
                                java::lang::__Object::__class(),
                                inputs::test024::__Test024::__class());
    return k;
}
}
