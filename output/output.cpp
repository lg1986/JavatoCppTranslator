#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test005
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
    return __rt::literal("A");
}
__B:: __B() : __vptr(&__vtable) {}
Class __B::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.B"), __Object::__class());
    return k;
}
__B_VT __B::__vtable;
B __B::__init(B __this)
{
    __Object::__init(__this);
    return __this;
}
String __B::toString(B __this)
{
    return __rt::literal("B");
}
__Test005:: __Test005() : __vptr(&__vtable) {}
Class __Test005::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test005"), __Object::__class());
    return k;
}
__Test005_VT __Test005::__vtable;
Test005 __Test005::__init(Test005 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test005::main(Test005 __this, String args)
{
    B b = __B::__init(new __B());
    A a1 = __A::__init(new __A());
    A a2 = b;
    std::cout << a1->__vptr->toString(a1)<< std::endl;
    std::cout << a2->__vptr->toString(a2)<< std::endl;
}
}
}
