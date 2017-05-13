#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test015
{
__A::__A() : __vptr(&__vtable) {}
Class __A::__class()
{
    static Class k =
        new __Class(__rt::literal("inputs.test015.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this)
{
    __Object::__init(__this);
    return __this;
}
void __A::printOther(A __this, A other)
{
    std::cout << other->__vptr->toString(other)<< std::endl;
}
__B::__B() : __vptr(&__vtable) {}
Class __B::__class()
{
    static Class k =
        new __Class(__rt::literal("inputs.test015.B"),  __A::__class());
    return k;
}
__B_VT __B::__vtable;
B __B::__init(B __this)
{
    __Object::__init(__this);
    return __this;
}
void __B::printOther(B __this, A other)
{
    std::cout << other->__vptr->toString(other)<< std::endl;
}
String __B::toString(B __this)
{
    return __this->some->__vptr->toString(__this->some);
}
__Test015::__Test015() : __vptr(&__vtable) {}
Class __Test015::__class()
{
    static Class k =
        new __Class(__rt::literal("inputs.test015.Test015"), __Object::__class());
    return k;
}
__Test015_VT __Test015::__vtable;
Test015 __Test015::__init(Test015 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test015::main(__rt::Array<String>args)
{
    A a = __A::__init(new __A());
    B other = __B::__init(new __B());
    other->some=a;
    a->__vptr->printOther(a, other);
}
}
}
