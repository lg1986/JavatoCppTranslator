#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs
{
namespace test032
{
__A::__A() : __vptr(&__vtable) {}
Class __A::__class()
{
    static Class k =
        new __Class(__rt::literal("inputs.test032.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this)
{
    __Object::__init(__this);
    return __this;
}
int32_t __A::m__int(A __this, int32_t i)
{
    std::cout << __rt::literal("A.m(int)")<< std::endl;
    return i;
}
void __A::m__A(A __this, A a)
{
    std::cout << __rt::literal("A.m(A)")<< std::endl;
}
void __A::m__double(A __this, double d)
{
    std::cout << __rt::literal("A.m(double)")<< std::endl;
}
void __A::m__Object(A __this, Object o)
{
    std::cout << __rt::literal("A.m(Object)")<< std::endl;
}
void __A::m__Object__Object(A __this, Object o1,Object o2)
{
    std::cout << __rt::literal("A.m(Object, Object)")<< std::endl;
}
void __A::m__A__Object(A __this, A a1,Object o2)
{
    std::cout << __rt::literal("A.m(A, Object)")<< std::endl;
}
__Test032::__Test032() : __vptr(&__vtable) {}
Class __Test032::__class()
{
    static Class k =
        new __Class(__rt::literal("inputs.test032.Test032"), __Object::__class());
    return k;
}
__Test032_VT __Test032::__vtable;
Test032 __Test032::__init(Test032 __this)
{
    __Object::__init(__this);
    return __this;
}
void __Test032::main(__rt::Array<String>args)
{
    A a = __A::__init(new __A());
    int32_t b = 1;
    a->__vptr->m__int(a, b);
    a->__vptr->m__alias(A, class inputs.test032.A(a, a);
                        a->__vptr->m__double(a, 1.0);
                        a->__vptr->m__class java.lang.Object(a, (Object ) a);
                        a->__vptr->m__class inputs.test032.A__alias(A, class inputs.test032.A(a, __A::__init(new __A())a);
                                a->__vptr->m__class java.lang.Object__alias(A, class inputs.test032.A(a, __Object::__init(new __Object())a);
}
}
}
