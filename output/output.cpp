#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test001
{
__A:: __A() : __vptr(&__vtable) {}
Class __A::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this)
{
    __Object::__init(__this);
    return __this;
}
String __A::toString(A __this)
{
    String a = __rt::literal("A");
    return a;
}
__Test001:: __Test001() : __vptr(&__vtable) {}
Class __Test001::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test001"), __Object::__class());
    return k;
}
__Test001_VT __Test001::__vtable;
Test001 __Test001::__init(Test001 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test001::main(__rt::Array<String> args)
{
    A a = __A::__init(new __A());
    String s = a->__vptr->toString(a);
    std::cout << a->__vptr->toString(a)<< std::endl;
}
}
}
