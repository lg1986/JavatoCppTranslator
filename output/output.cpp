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
}
}
}
