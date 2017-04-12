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
struct __B;
struct __B_VT;
typedef __B* B;
struct __B {
__B_VT* __vptr;
static Class __class();
__B();
static __B_VT __vtable;
static String toString(B );
};
struct __A_VT{ 
Class __is_a; 
String(*toString)( A ); 
int32_t(*hashCode)( A ); 
bool(*equals)( A, Object  ); 
Class(*getClass)( A ); 

__A_VT() 
	: __is_a(__A::__class()),
toString((String(*)( A ))&__A::toString),
hashCode((int32_t(*)( A ))&__Object::hashCode),
equals((bool(*)( A, Object  ))&__Object::equals),
getClass((Class(*)( A ))&__Object::getClass)
{
}
};


struct __B_VT{ 
Class __is_a; 
String(*toString)( B ); 
int32_t(*hashCode)( B ); 
bool(*equals)( B, Object  ); 
Class(*getClass)( B ); 

__B_VT() 
	: __is_a(__B::__class()),
toString((String(*)( B ))&__B::toString),
hashCode((int32_t(*)( B ))&__Object::hashCode),
equals((bool(*)( B, Object  ))&__Object::equals),
getClass((Class(*)( B ))&__Object::getClass)
{
}
};


};
};
};
