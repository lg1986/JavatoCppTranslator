#include "output.h"
using namespace java::lang;
namespace nyu
{
namespace edu
{
namespace oop
{
__A::__A() : __vptr(&__vtable),fld(__rt::literal("")) {}
Class __A::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
    return k;
}
__A_VT __A::__vtable;
String __A::getFld(A __this )
{
    return fld;
}
}
}
}
