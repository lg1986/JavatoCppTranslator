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
__A_VT __A::__vtable
String fld = __rt::literal("A");
void __A::setFld(A __this,String f)
{
    fld = f;
}
void __A::almostSetFld(A __this,String f)
{
    String fld;
    fld = f;
}
String __A::getFld(A __thisString)
{
    return fld;
}
__B::__B() : __vptr(&__vtable) {}
Class __B::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.nyu.oop.B"), __Object::__class());
    return k;
}
__B_VT __B::__vtable
};
};
};
