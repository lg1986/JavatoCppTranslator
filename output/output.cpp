#include "output.h"
using namespace java::lang;
namespace nyu{
namespace edu{
namespace oop{
__A::__A() : __vptr(&__vtable) {}
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
__B::__B() : __vptr(&__vtable) {}
Class __B::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.B"), __Object::__class());
  return k;
}
__B_VT __B::__vtable;
__C::__C() : __vptr(&__vtable) {}
Class __C::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.C"), __Object::__class());
  return k;
}
__C_VT __C::__vtable;
__ConstructorTest::__ConstructorTest() : __vptr(&__vtable) {}
Class __ConstructorTest::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.ConstructorTest"), __Object::__class());
  return k;
}
__ConstructorTest_VT __ConstructorTest::__vtable;
}
}
}
