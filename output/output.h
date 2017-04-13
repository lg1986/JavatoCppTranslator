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
String a;
static void setA(A , String x);
static void printOther(A , A other);
static String toString(A );
};
struct __B1;
struct __B1_VT;
typedef __B1* B1;
struct __B1 {
__B1_VT* __vptr;
static Class __class();
__B1();
static __B1_VT __vtable;
String b;
String a;
};
struct __B2;
struct __B2_VT;
typedef __B2* B2;
struct __B2 {
__B2_VT* __vptr;
static Class __class();
__B2();
static __B2_VT __vtable;
String b;
String a;
};
struct __C;
struct __C_VT;
typedef __C* C;
struct __C {
__C_VT* __vptr;
static Class __class();
__C();
static __C_VT __vtable;
String c;
String b;
String a;
};
struct __A_VT{ 
Class __is_a; 
void(*setA)( A, String x ); 
void(*printOther)( A, A other ); 
String(*toString)( A ); 
int32_t(*hashCode)( A ); 
bool(*equals)( A, Object  ); 
Class(*getClass)( A ); 

__A_VT() 
	: __is_a(__A::__class()),
setA((void(*)( A, String x ))&__A::setA),
printOther((void(*)( A, A other ))&__A::printOther),
toString((String(*)( A ))&__A::toString),
hashCode((int32_t(*)( A ))&__Object::hashCode),
equals((bool(*)( A, Object  ))&__Object::equals),
getClass((Class(*)( A ))&__Object::getClass)
{
}
};


struct __B1_VT{ 
Class __is_a; 
int32_t(*hashCode)( B1 ); 
bool(*equals)( B1, Object  ); 
String(*toString)( B1 ); 
Class(*getClass)( B1 ); 
void(*setA)( B1, String x ); 
void(*printOther)( B1, A other ); 

__B1_VT() 
	: __is_a(__B1::__class()),
hashCode((int32_t(*)( B1 ))&__Object::hashCode),
equals((bool(*)( B1, Object  ))&__Object::equals),
toString((String(*)( B1 ))&__Object::toString),
getClass((Class(*)( B1 ))&__Object::getClass),
setA((void(*)( B1, String x ))&__A::setA),
printOther((void(*)( B1, A other ))&__A::printOther)
{
}
};


struct __B2_VT{ 
Class __is_a; 
int32_t(*hashCode)( B2 ); 
bool(*equals)( B2, Object  ); 
String(*toString)( B2 ); 
Class(*getClass)( B2 ); 
void(*setA)( B2, String x ); 
void(*printOther)( B2, A other ); 

__B2_VT() 
	: __is_a(__B2::__class()),
hashCode((int32_t(*)( B2 ))&__Object::hashCode),
equals((bool(*)( B2, Object  ))&__Object::equals),
toString((String(*)( B2 ))&__Object::toString),
getClass((Class(*)( B2 ))&__Object::getClass),
setA((void(*)( B2, String x ))&__A::setA),
printOther((void(*)( B2, A other ))&__A::printOther)
{
}
};


struct __C_VT{ 
Class __is_a; 
int32_t(*hashCode)( C ); 
bool(*equals)( C, Object  ); 
String(*toString)( C ); 
Class(*getClass)( C ); 
void(*setA)( C, String x ); 
void(*printOther)( C, A other ); 

__C_VT() 
	: __is_a(__C::__class()),
hashCode((int32_t(*)( C ))&__Object::hashCode),
equals((bool(*)( C, Object  ))&__Object::equals),
toString((String(*)( C ))&__Object::toString),
getClass((Class(*)( C ))&__Object::getClass),
setA((void(*)( C, String x ))&__A::setA),
printOther((void(*)( C, A other ))&__A::printOther)
{
}
};


};
};
};
