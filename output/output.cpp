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
        new __Class(__rt::literal("nyu.nyu.oop.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable;
A some;
void __A::printOther(A __this,A other)
{
    cout <<(other->_vptr->toString(other)->data)<< endl
}
__B::__B() : __vptr(&__vtable) {}
Class __B::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.nyu.oop.B"), __Object::__class());
    return k;
}
__B_VT __B::__vtable;
void __B::printOther(B __this,A other)
{
    cout <<(other->_vptr->toString(other)->data)<< endl
}
String __B::toString(B __thisString)
{
    return some->_vptr->toString(some);
}
};
};
};
