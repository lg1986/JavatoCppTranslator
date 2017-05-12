#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test002{
__A:: __A() : __vptr(&__vtable){} 
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this) {
__Object::__init(__this);
return __this; 
 }
String __A::toString(A __this)
{return __rt::literal("A"); 
}
__Test002:: __Test002() : __vptr(&__vtable){} 
Class __Test002::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.Test002"), __Object::__class());
  return k;
}
__Test002_VT __Test002::__vtable;
Test002 __Test002::__init(Test002 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test002::main(Test002 __this, String args)
{A a = __A::__init(new __A()); 
Object o = a; 
std::cout << o->__vptr->toString(o)<< std::endl; 
}
}
}
