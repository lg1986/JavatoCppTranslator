<<<<<<< HEAD
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
A::__init(new__A(),(A __this ) { 
__Object::__init((Object)__this);
A::__init(new__A(),(A __this , String fld)) { 
ThisExpression(null) = fld__this;
} 
)
String __A::getFld(A __this ) { 
return ->__vptr->fld; 
}
__Test003::__Test003() : __vptr(&__vtable) {}
Class __Test003::__class() {
  static Class k = 
    new __Class(__rt::literal("nyu.edu.oop.Test003"), __Object::__class());
  return k;
}
__Test003_VT __Test003::__vtable;
int main(){ 
 a = A__::init(new __A(),); 
cout <<(agetFld(a));

}
}
}

