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
String __A::toString(A __this )
{
    a = __rt::literal("A");
    return a;
}
__Test001::__Test001() : __vptr(&__vtable) {}
Class __Test001::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.Test001"), __Object::__class());
    return k;
}
__Test001_VT __Test001::__vtable;
int main()
{
    a = A__::init(new __A(),);
    s = a->__vptr->toString(a);
    cout <<(a->__vptr->toString(a));
}
<<<<<<< HEAD
__Test003_VT __Test003::__vtable;
int main(){ 
 a = A__::init(new __A(),"A"); 
cout <<(a->__vptr->getFld(a)); 
} 
=======
>>>>>>> d3fc903edb2f679576356ed627f0e5afb5fcbd65
}
}
}
