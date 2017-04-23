<<<<<<< HEAD
#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace nyu{
namespace edu{
namespace oop{
struct __A;
struct __A_VT;
typedef __A* A;
struct __A {
__A_VT* __vptr;
static Class __class();
__A();
static __A_VT __vtable;
String fld;
<<<<<<< HEAD
static __A __init( __A __this);
=======
static __A __init( __A __this, String fld);
>>>>>>> f569541fb995d6f4ba38d42fe92c19150f7fa459
static String getFld(A );
};
struct __A_VT{ 
Class __is_a; 
String(*getFld)( A ); 
int32_t(*hashCode)( A ); 
bool(*equals)( A, Object  ); 
String(*toString)( A ); 
Class(*getClass)( A ); 

__A_VT() 
	: __is_a(__A::__class()),
getFld((String(*)( A ))&__A::getFld),
hashCode((int32_t(*)( A ))&__Object::hashCode),
equals((bool(*)( A, Object  ))&__Object::equals),
toString((String(*)( A ))&__Object::toString),
getClass((Class(*)( A ))&__Object::getClass)
{
}
};


};
};
};
=======
>>>>>>> 6f53a0b24ecb11ad5a41bbb2ef16a2b0d85c20c5
