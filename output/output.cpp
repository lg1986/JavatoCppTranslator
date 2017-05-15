#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test021{
__A::__A() : __vptr(&__vtable){}
int32_t __A::x = 4 ;
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test021.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this) {
__Object::__init(__this);
return __this; 
 }
__Test021::__Test021() : __vptr(&__vtable){}
Class __Test021::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test021.Test021"), __Object::__class());
  return k;
}
__Test021_VT __Test021::__vtable;
Test021 __Test021::__init(Test021 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test021::main(__rt::Array<String>args)
{int32_t x; 
x=3; 
std::cout << __A::x<< std::endl; 
}
}
}
namespace __rt {
template<>
java::lang::Class __Array<inputs::test021::A>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test021.A;"), 
java::lang::__Object::__class(),
inputs::test021::__A::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test021::Test021>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test021.Test021;"), 
java::lang::__Object::__class(),
inputs::test021::__Test021::__class());return k;
}
}
