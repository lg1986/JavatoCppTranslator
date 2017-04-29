#include "output.h"
using namespace java::lang;
namespace nyu{
namespace edu{
namespace oop{
__A::__A() : __vptr(&__vtable) Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A::__init(new__A(),A __this , int i) { 
 = ; 
__Object::__init((Object)__this);
}
int32_t __A::get(A __this ) { 
return ; 
__B::__B() : __vptr(&__vtable) Class __B::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.B"), __Object::__class());
  return k;
}
__B_VT __B::__vtable;
B::__init(new__B(),B __this , int i) { 
super(); 
}
int32_t __B::get(B __this ) { 
return ; 
__Test025::__Test025() : __vptr(&__vtable) Class __Test025::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.Test025"), __Object::__class());
  return k;
}
__Test025_VT __Test025::__vtable;
int main(){ 
__rt::Array<Object> as = new __rt::_Array<A>(10); 
(*as)[0] = __rt::literal(B__::init(new __B(),3)); 
 k = 0; 
; 
}
}
}
