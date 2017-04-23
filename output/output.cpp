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
A::__init(new__A()){
__Object::__init((Object)__this);
}
String __A::toString(A __this ) { 
 a = __rt::literal("A"); 
return ->__vptr->a; 
} 
__Test001::__Test001() : __vptr(&__vtable) {}
Class __Test001::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.Test001"), __Object::__class());
  return k;
}
__Test001_VT __Test001::__vtable;
Test001::__init(new__Test001()){
__Object::__init((Object)__this);
}
int main(){ 
 a = A__::init(new __A(),); 
 s = atoString(a); 
cout <<(atoString(a)); 
} 
}
}
}
