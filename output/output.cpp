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
A __A::__init(A __this, int x)
{
    __Object::__init(__this);
    __this->x = 10;
    __this->self=__this;
    return __this;
}
A __A::self(A __this)
{
    return __this->self;
}
__Test017:: __Test017() : __vptr(&__vtable) {}
Class __Test017::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test017"), __Object::__class());
    return k;
}
__Test017_VT __Test017::__vtable;
Test017 __Test017::__init(Test017 __this)
{
    __Object::__init(__this);
    return __this;
}
__Test017::main(Test017 __this, String args)
{
    A a = __A::__init(new __A(), 5);
    std::cout << a->__vptr->self(a)->__vptr->toString(a)<< std::endl;
}
}
}
