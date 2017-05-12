#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace null
{
A::A() : __vptr(&__vtable) Class A::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
    return k;
}
A_VT A::__vtable;
A __A::__init(A __this, int x)
{
    __Object::__init(__this);
    __this->self=__this;
    return __this;
}
A __A::self(A __this)
{
    return __this->self;
}
Test017::Test017() : __vptr(&__vtable) Class Test017::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test017"), __Object::__class());
    return k;
}
Test017_VT Test017::__vtable;
__Test017::main(Test017 __this, String args)
{
    A a = __A::__init(new __A(), 5);
    System->out->__vptr->println(a->__vptr->self()->__vptr->toString());
}
}
}
