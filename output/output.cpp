#include "output.h"
using namespace java::lang;
namespace nyu{
namespace edu{
namespace oop{
__A::__A() : __vptr(&__vtable) ,fld(__rt::literal("")){}
Class __A::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
  return k;
}
__A_VT __A::__vtable;
A::__init(A __this ) { 
__Object::__init((Object)__this);
cout <<(__rt::literal("hello")); 
} 
A::__init(A __this , String fld)) { 
; ex
} 
String __A::getFld(A __this ) { 
return fld; 
} 
__Test004::__Test004() : __vptr(&__vtable) {}
Class __Test004::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.Test004"), __Object::__class());
  return k;
}
__Test004_VT __Test004::__vtable;
int main(){ 
 a = A__::init(new __A(),"A"); 
cout <<(a->__vptr->getFld(a)); 
} 
}
}
}
