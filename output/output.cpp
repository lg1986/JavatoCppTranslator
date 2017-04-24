#include "output.h"
using namespace java::lang;
<<<<<<< HEAD
namespace nyu{
namespace edu{
namespace oop{
__A::__A() : __vptr(&__vtable) ,self({}
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A::__init(new__A(),(A __this ) { 
self = ; 
__Object::__init((Object)__this);
} 
)
__Test009::__Test009() : __vptr(&__vtable) {}
Class __Test009::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.Test009"), __Object::__class());
  return k;
}
__Test009_VT __Test009::__vtable;
int main(){ 
 a = A__::init(new __A(),); 
cout <<(toString()); 
} 
=======
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
A::__init(new__A(),A __this , String s)
{
}
__Object::__init((Object)__this);
)
A::__init(new__A(),A __this )
{
}
)
__B::__B() : __vptr(&__vtable) {}
Class __B::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.B"), __Object::__class());
    return k;
}
__B_VT __B::__vtable;
__C::__C() : __vptr(&__vtable) {}
Class __C::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.C"), __Object::__class());
    return k;
}
__C_VT __C::__vtable;
C::__init(new__C(),C __this )
{
}
)
C::__init(new__C(),C __this , int i)
{
}
)
C::__init(new__C(),C __this , double d)
{
}
)
__ConstructorTest::__ConstructorTest() : __vptr(&__vtable) {}
Class __ConstructorTest::__class()
{
    static Class k =
        new __Class(__rt::literal("nyu.edu.oop.ConstructorTest"), __Object::__class());
    return k;
}
__ConstructorTest_VT __ConstructorTest::__vtable;
int main()
{
    a1 = A__::init(new __A(),"test");
    a = A__::init(new __A(),);
    b = B__::init(new __B(),);
    c = C__::init(new __C(),);
    c2 = C__::init(new __C(),);
    c3 = C__::init(new __C(),);
}
>>>>>>> 85d3940fc963546d92e7c64d204dc6226c93b447
}
}
}
