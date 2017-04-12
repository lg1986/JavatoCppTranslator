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
String __A::toString(A __this, ) { 
return __rt::literal("A");
 } 

__B::__B() : __vptr(&__vtable) {}
Class __B::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.B"), __Object::__class());
  return k;
}
__B_VT __B::__vtable;
String __B::toString(B __this, ) { 
return __rt::literal("B");
 } 

__Test005::__Test005() : __vptr(&__vtable) {}
Class __Test005::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.Test005"), __Object::__class());
  return k;
}
__Test005_VT __Test005::__vtable;
int main(){ 
}
}
}
