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
static String toString(A );
};
struct __A_VT{ 
Class __is_a; 
void (*__delete)(__A*); 
String(*toString)( A ); 
int32_t(*hashCode)( A ); 
bool(*equals)( A, Object  ); 
Class(*getClass)( A ); 

__A_VT() 
	: __is_a(__A::__class())__delete(&__rt::__delete<__A>),,
toString((String(*)( A ))&__A::toString),
hashCode((int32_t(*)( A ))&__Object::hashCode),
equals((bool(*)( A, Object  ))&__Object::equals),
getClass((Class(*)( A ))&__Object::getClass)
{
}
};


};
};
};
