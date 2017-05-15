#include <iostream>
#include "output.h"
using namespace java::lang;
namespace inputs{
namespace test000{
__Test000::__Test000() : __vptr(&__vtable),val(0) {}
Class __Test000::__class() {
  static Class k = 
    new __Class(__rt::literal("inputs.test000.Test000"), __Object::__class());
  return k;
}
__Test000_VT __Test000::__vtable;
Test000 __Test000::__init(Test000 __this) {
__Object::__init(__this);
return __this; 
 }
void __Test000::main(__rt::Array<String>args)
{std::cout << __rt::literal("Hello.")<< std::endl; 
}
void __Test000::pmethod__String(String arg)
{std::cout << __rt::literal("here!")<< std::endl; 
}
}
}
namespace __rt {
template<>
java::lang::Class __Array<inputs::test000::Test000>::__class(){
static java::lang::Class k =
new java::lang::__Class(__rt::literal("[Linputs.test000.Test000;"), 
java::lang::__Object::__class(),
inputs::test000::__Test000::__class());return k;
}
}
