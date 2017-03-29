#include "output.h"
namespace edu
{
namespace nyu
{
namespace oop
{
inputs__A::__A() : __vptr(&__vtable) {}
Class __A::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable
String a;
void __A::setA(A __this,String x)
{
    a = x;
}
void __A::printOther(A __this,A other)
{
    cout <<(other->_vptr->a->data)<< endl
}
String __A::toString(A __thisString)
{
    return a;
}
__B1::__B1() : __vptr(&__vtable) {}
Class __B1::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.B1"), __Object::__class());
    return k;
}
__B1_VT __B1::__vtable
String b;
__B2::__B2() : __vptr(&__vtable) {}
Class __B2::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.B2"), __Object::__class());
    return k;
}
__B2_VT __B2::__vtable
String b;
__C::__C() : __vptr(&__vtable) {}
Class __C::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.C"), __Object::__class());
    return k;
}
__C_VT __C::__vtable
String c;
};
};
};
