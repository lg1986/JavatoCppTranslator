#include "output.h"
using namespace nyu::edu::oop;
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
int32_t __A::method( A __this )
{
    return 12345;;
}
String __A::toString( A __this )
{
    return __rt::literal("A");;
}
__B::__B() : __vptr(&__vtable) {}
Class __B::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.B"), __Object::__class());
    return k;
}
__B_VT __B::__vtable;
String __B::toString( B __this )
{
    return __rt::literal("B");;
}
}
}
}
