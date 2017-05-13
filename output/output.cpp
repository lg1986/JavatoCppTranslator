#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test008
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
    __this->a=__rt::literal("A");
    std::cout << __this->a<< std::endl;
    return __this;
}
__B:: __B() : __vptr(&__vtable) {}
Class __B::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.B"), __Object::__class());
    return k;
}
__B_VT __B::__vtable;
B __B::__init(B __this, String s)
{
    __A::__init( __this);
    __this->b=__rt::literal("B");
    __this->a=__rt::literal("B");
    std::cout << __this->a<< std::endl;
    return __this;
}
__Test008:: __Test008() : __vptr(&__vtable) {}
Class __Test008::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test008"), __Object::__class());
    return k;
}
__Test008_VT __Test008::__vtable;
Test008 __Test008::__init(Test008 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test008::main(__rt::Array<String> args)
{
    B b = __B::__init(new __B(), __rt::literal("s"));
}
}
}
