#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test009
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
    __this->self=__this;
    return __this;
}
__Test009:: __Test009() : __vptr(&__vtable) {}
Class __Test009::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test009"), __Object::__class());
    return k;
}
__Test009_VT __Test009::__vtable;
Test009 __Test009::__init(Test009 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test009::main(__rt::Array<String> args)
{
    A a = __A::__init(new __A());
    std::cout << a->self->__vptr->toString(a)<< std::endl;
}
}
}
