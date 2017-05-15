#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test011{
__A::__A() : __vptr(&__vtable),a(0) {}
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test011.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this) {
__Object::__init(__this);
return __this; 
 }
void __A::setAmethod__String(A __this, String x)
{__this->a=x; 
}
void __A::printOthermethod__A(A __this, A other)
{std::cout << other->a->__vptr->toString(other->a)<< std::endl; 
}
String __A::toString(A __this)
{return __this->a; 
}
__B1::__B1() : __vptr(&__vtable),b(0) {}
Class __B1::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test011.B1"),  __A::__class());
  return k;
}
__B1_VT __B1::__vtable;
B1 __B1::__init(B1 __this) {
__Object::__init(__this);
return __this; 
 }
__B2::__B2() : __vptr(&__vtable),b(0) {}
Class __B2::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test011.B2"),  __A::__class());
  return k;
}
__B2_VT __B2::__vtable;
B2 __B2::__init(B2 __this) {
__Object::__init(__this);
return __this; 
 }
__C::__C() : __vptr(&__vtable),c(0) {}
Class __C::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test011.C"),  __B1::__class());
  return k;
}
__C_VT __C::__vtable;
C __C::__init(C __this) {
__Object::__init(__this);
return __this; 
 }
__Test011::__Test011() : __vptr(&__vtable){}
Class __Test011::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test011.Test011"), __Object::__class());
  return k;
}
__Test011_VT __Test011::__vtable;
Test011 __Test011::__init(Test011 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test011::main(__rt::Array<String>args)
{A a = __A::__init(new __A()); 
a->__vptr->setAmethod__String(a,__rt::literal("A")); 
B1 b1 = __B1::__init(new __B1()); 
b1->__vptr->setAmethod__String(b1,__rt::literal("B1")); 
B2 b2 = __B2::__init(new __B2()); 
b2->__vptr->setAmethod__String(b2,__rt::literal("B2")); 
C c = __C::__init(new __C()); 
c->__vptr->setAmethod__String(c,__rt::literal("C")); 
a->__vptr->printOthermethod__A(a,a); 
a->__vptr->printOthermethod__A(a,b1); 
a->__vptr->printOthermethod__A(a,b2); 
a->__vptr->printOthermethod__A(a,c); 
}
}
}
namespace __rt {
template<>
java::lang::Class __Array<inputs::test011::A>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test011.A;"), 
java::lang::__Object::__class(),
inputs::test011::__A::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test011::B1>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test011.B1;"), 
inputs::test011::__A::__class(), 
inputs::test011::__B1::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test011::B2>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test011.B2;"), 
inputs::test011::__A::__class(), 
inputs::test011::__B2::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test011::C>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test011.C;"), 
inputs::test011::__B1::__class(), 
inputs::test011::__C::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test011::Test011>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test011.Test011;"), 
java::lang::__Object::__class(),
inputs::test011::__Test011::__class());return k;
}
}
