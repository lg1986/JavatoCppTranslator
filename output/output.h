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
int32_t i;
static __A __init( __A __this, int i);
static int32_t get(A );
};
struct __B;
struct __B_VT;
typedef __B* B;
struct __B {
__B_VT* __vptr;
static Class __class();
__B();
static __B_VT __vtable;
static __B __init( __B __this, int i);
static int32_t get(B );
int32_t i;
};
struct __A_VT{ 
Class __is_a; 
void (*__delete)(__A*); 
int32_t(*get)( A ); 
int32_t(*hashCode)( A ); 
bool(*equals)( A, Object  ); 
String(*toString)( A ); 
Class(*getClass)( A ); 

__A_VT() 
	: __is_a(__A::__class()) 
__delete(&__rt::__delete<__A>),
get((int32_t(*)( A ))&__A::get),
hashCode((int32_t(*)( A ))&__Object::hashCode),
equals((bool(*)( A, Object  ))&__Object::equals),
toString((String(*)( A ))&__Object::toString),
getClass((Class(*)( A ))&__Object::getClass)
{
}
};


struct __B_VT{ 
Class __is_a; 
void (*__delete)(__B*); 
int32_t(*get)( B ); 
int32_t(*hashCode)( B ); 
bool(*equals)( B, Object  ); 
String(*toString)( B ); 
Class(*getClass)( B ); 

__B_VT() 
	: __is_a(__B::__class()) 
__delete(&__rt::__delete<__B>),
get((int32_t(*)( B ))&__B::get),
hashCode((int32_t(*)( B ))&__Object::hashCode),
equals((bool(*)( B, Object  ))&__Object::equals),
toString((String(*)( B ))&__Object::toString),
getClass((Class(*)( B ))&__Object::getClass)
{
}
};


};
};
};
