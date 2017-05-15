#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test030{
__A::__A() : __vptr(&__vtable){} 
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test030.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this, int i){
__Object::__init(__this);
__this->i=i; 
return __this;
}
int32_t __A::get(A __this)
{return __this->i; 
}
__Test030::__Test030() : __vptr(&__vtable){} 
Class __Test030::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test030.Test030"), __Object::__class());
  return k;
}
__Test030_VT __Test030::__vtable;
Test030 __Test030::__init(Test030 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test030::main(__rt::Array<String>args)
{__rt::Array<<A<<A>>> as = new __rt::__Array<<A<<A>>>(5)(5)(5); 
std::cout << as->__vptr->getClass(as)->__vptr->toString(as)<< std::endl; 
}
}
}
namespace __rt {
template<>
java::lang::Class __Array<inputs::test030::A>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test030.A;"), 
java::lang::__Object::__class(),
inputs::test030::__A::__class());return k;
}
template<>
java::lang::Class __Array<inputs::test030::Test030>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test030.Test030;"), 
java::lang::__Object::__class(),
inputs::test030::__Test030::__class());return k;
}
}
