#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test003
{
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
    Extension(Type(QualifiedIdentifier("A"), null))
    return __this;
}
int32_t __B::meth(B __this, int x)
{
    return 0;
}
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
A __A::__init(A __this, String f)
{
    __init(__this);
    __this->fld=f;
    return __this;
}
String __A::getFld(A __this)
{
    return __this->fld;
}
__Test003:: __Test003() : __vptr(&__vtable) {}
Class __Test003::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test003"), __Object::__class());
    return k;
}
__Test003_VT __Test003::__vtable;
Test003 __Test003::__init(Test003 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test003::main(Test003 __this, String args)
{
    A a = __A::__init(new __A(), __rt::literal("A"));
    std::cout << a->__vptr->getFld(a)<< std::endl;
}
}
}
