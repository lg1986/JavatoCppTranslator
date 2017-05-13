#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test017{
__A::__A() : __vptr(&__vtable){} 
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test017.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A __A::__init(A __this, int x){
__Object::__init(__this);
__this->x = 10; 
__this->self=__this; 
return __this;
}
A __A::self_imp(A __this)
{return __this->self; 
}
__Test017::__Test017() : __vptr(&__vtable){} 
Class __Test017::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test017.Test017"), __Object::__class());
  return k;
}
__Test017_VT __Test017::__vtable;
Test017 __Test017::__init(Test017 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test017::main(__rt::Array<String>args)
{A a = __A::__init(new __A(), 5); 
std::cout << a->__vptr->self_imp(a)->__vptr->toString(a)<< std::endl; 
}
}
}
