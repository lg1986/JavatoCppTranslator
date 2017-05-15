#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test020{
__A::__A() : __vptr(&__vtable){}
int32_t __A::x = 0;
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test020.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this) {
__Object::__init(__this);
return __this; 
 }
int32_t __A::xmethod()
{return 4; 
}
__Test020::__Test020() : __vptr(&__vtable){}
Class __Test020::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test020.Test020"), __Object::__class());
  return k;
}
__Test020_VT __Test020::__vtable;
Test020 __Test020::__init(Test020 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test020::main(__rt::Array<String>args)
{int32_t x; 
x=__A::x; 
std::cout << __A::xmethod()<< std::endl; 
}
}
}
namespace __rt {
template<>
java::lang::Class __Array<inputs::test020::A>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test020.A;"), 
java::lang::__Object::__class(),
inputs::test020::__A::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test020::Test020>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test020.Test020;"), 
java::lang::__Object::__class(),
inputs::test020::__Test020::__class());return k;
}
}
