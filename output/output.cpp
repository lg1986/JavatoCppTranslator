#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test035{
__A::__A() : __vptr(&__vtable){} 
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test035.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this) {
__Object::__init(__this);
return __this; 
 }
int32_t __A::m(A __this, byte b)
{std::cout << __rt::literal("A.m(byte)")<< std::endl; 
return b; 
}
void __A::m(A __this, double d)
{std::cout << __rt::literal("A.m(double)")<< std::endl; 
}
__Test035::__Test035() : __vptr(&__vtable){} 
Class __Test035::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test035.Test035"), __Object::__class());
  return k;
}
__Test035_VT __Test035::__vtable;
Test035 __Test035::__init(Test035 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test035::main(__rt::Array<String>args)
{A a = __A::__init(new __A()); 
byte b = 1; 
double d = ; 
a->__vptr->m(a, b); 
a->__vptr->m(a, ); 
a->__vptr->m(a, d); 
}
}
}
