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
static __A __init( __A __this);
static __A __init( __A __this, String s);
static void setFld(A , String f);
static void almostSetFld(A , String f);
static String getFld(A );
};
struct __B;
struct __B_VT;
typedef __B* B;
struct __B {
__B_VT* __vptr;
static Class __class();
__B();
static __B_VT __vtable;
static __B __init( __B __this);
String fld;
};
struct __A_VT{ 
Class __is_a; 
void(*setFld)( A, String f ); 
void(*almostSetFld)( A, String f ); 
String(*getFld)( A ); 
int32_t(*hashCode)( A ); 
bool(*equals)( A, Object  ); 
String(*toString)( A ); 
Class(*getClass)( A ); 

__A_VT() 
	: __is_a(__A::__class()),
setFld((void(*)( A, String f ))&__A::setFld),
almostSetFld((void(*)( A, String f ))&__A::almostSetFld),
getFld((String(*)( A ))&__A::getFld),
hashCode((int32_t(*)( A ))&__Object::hashCode),
equals((bool(*)( A, Object  ))&__Object::equals),
toString((String(*)( A ))&__Object::toString),
getClass((Class(*)( A ))&__Object::getClass)
{
}
};


struct __B_VT{ 
Class __is_a; 
int32_t(*hashCode)( B ); 
bool(*equals)( B, Object  ); 
String(*toString)( B ); 
Class(*getClass)( B ); 
void(*setFld)( B, String f ); 
void(*almostSetFld)( B, String f ); 
String(*getFld)( B ); 

__B_VT() 
	: __is_a(__B::__class()),
hashCode((int32_t(*)( B ))&__Object::hashCode),
equals((bool(*)( B, Object  ))&__Object::equals),
toString((String(*)( B ))&__Object::toString),
getClass((Class(*)( B ))&__Object::getClass),
setFld((void(*)( B, String f ))&__A::setFld),
almostSetFld((void(*)( B, String f ))&__A::almostSetFld),
getFld((String(*)( B ))&__A::getFld)
{
}
};


};
};
};
