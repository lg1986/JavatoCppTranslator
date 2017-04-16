#include "output.h"
using namespace java::lang;
namespace nyu
{
namespace edu
{
namespace oop
{
__A::__A() : __vptr(&__vtable) {}
Class __A::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable;
String __A::toString(A __this, int32_t i)
{
    return __rt::literal("A");
}

__Test002::__Test002() : __vptr(&__vtable) {}
Class __Test002::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test002"), __Object::__class());
    return k;
}
__Test002_VT __Test002::__vtable;
int main()
{
    A a = new __A();
    Object o = a;
    cout <<(a->__vptr->)cout <<(o->__vptr->toString())
}
}
}
