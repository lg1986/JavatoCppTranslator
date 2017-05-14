#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test042{
__A::__A() : __vptr(&__vtable){} 
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test042.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this) {
__Object::__init(__this);
return __this; 
 }
void __A::m(A __this)
{std::cout << __rt::literal("A.m()")<< std::endl; 
}
A __A::m__A(A __this, A a)
{std::cout << __rt::literal("A.m(A)")<< std::endl; 
return a; 
}
__B::__B() : __vptr(&__vtable){} 
Class __B::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test042.B"),  __A::__class());
  return k;
}
__B_VT __B::__vtable;
B __B::__init(B __this) {
__Object::__init(__this);
return __this; 
 }
void __B::m(B __this)
{std::cout << __rt::literal("B.m()")<< std::endl; 
}
B __B::m__B(B __this, B b)
{std::cout << __rt::literal("B.m(B)")<< std::endl; 
return b; 
}
A __B::m__A(B __this, A a)
{std::cout << __rt::literal("B.m(A)")<< std::endl; 
return a; 
}
__Test042::__Test042() : __vptr(&__vtable){} 
Class __Test042::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test042.Test042"), __Object::__class());
  return k;
}
__Test042_VT __Test042::__vtable;
Test042 __Test042::__init(Test042 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test042::main(__rt::Array<String>args)
{A a = __A::__init(new __A()); 
a->__vptr->m__A(a,a)->__vptr->m(a); 
B b = __B::__init(new __B()); 
b->__vptr->m__B(b,b)->__vptr->m(b); 
b->__vptr->m__A(b,(A ) b)->__vptr->m(b); 
}
}
}
namespace __rt {
template<>
java::lang::Class __Array<inputs::test042::A>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test042.A;"), 
java::lang::__Object::__class(),
inputs::test042::__A::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test042::B>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test042.B;"), 
inputs::test042::__A::__class(), 
inputs::test042::__B::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test042::Test042>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test042.Test042;"), 
java::lang::__Object::__class(),
inputs::test042::__Test042::__class());return k;
}
}
