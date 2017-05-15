#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs{
namespace test011{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A {
__A_VT* __vptr;
__A();
 String a;
static A __init(A __this);
static String toString(A);
static void setAmethod__String(A,String);
static void printOthermethod__A(A,A);
static Class __class();
static __A_VT __vtable;
};
struct __A_VT {
Class __is_a;
void (*__delete)(__A*);
int32_t (*hashCode)(A);
bool (*equals)(A,Object);
Class (*getClass)(A);
String (*toString)(A);
void (*setAmethod__String)(A,String);
void (*printOthermethod__A)(A,A);
__A_VT()
  : __is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A))&__Object::hashCode),
equals((bool (*)(A,Object))&__Object::equals),
getClass((Class (*)(A))&__Object::getClass),
toString(__A::toString),
setAmethod__String(__A::setAmethod__String),
printOthermethod__A(__A::printOthermethod__A){}
};
struct __B1;
struct __B1_VT;
typedef __rt::Ptr<__B1> B1;
struct __B1 {
__B1_VT* __vptr;
__B1();
 String a;
 String b;
static B1 __init(B1 __this);
static Class __class();
static __B1_VT __vtable;
};
struct __B1_VT {
Class __is_a;
void (*__delete)(__B1*);
int32_t (*hashCode)(B1);
bool (*equals)(B1,Object);
Class (*getClass)(B1);
String (*toString)(B1);
void (*setAmethod__String)(B1,String);
void (*printOthermethod__A)(B1,A);
__B1_VT()
  : __is_a(__B1::__class()),
__delete(&__rt::__delete<__B1>),
hashCode((int32_t (*)(B1))&__Object::hashCode),
equals((bool (*)(B1,Object))&__Object::equals),
getClass((Class (*)(B1))&__Object::getClass),
toString((String (*)(B1))&__A::toString),
setAmethod__String((void (*)(B1,String))&__A::setAmethod__String),
printOthermethod__A((void (*)(B1,A))&__A::printOthermethod__A){}
};
struct __B2;
struct __B2_VT;
typedef __rt::Ptr<__B2> B2;
struct __B2 {
__B2_VT* __vptr;
__B2();
 String a;
 String b;
static B2 __init(B2 __this);
static Class __class();
static __B2_VT __vtable;
};
struct __B2_VT {
Class __is_a;
void (*__delete)(__B2*);
int32_t (*hashCode)(B2);
bool (*equals)(B2,Object);
Class (*getClass)(B2);
String (*toString)(B2);
void (*setAmethod__String)(B2,String);
void (*printOthermethod__A)(B2,A);
__B2_VT()
  : __is_a(__B2::__class()),
__delete(&__rt::__delete<__B2>),
hashCode((int32_t (*)(B2))&__Object::hashCode),
equals((bool (*)(B2,Object))&__Object::equals),
getClass((Class (*)(B2))&__Object::getClass),
toString((String (*)(B2))&__A::toString),
setAmethod__String((void (*)(B2,String))&__A::setAmethod__String),
printOthermethod__A((void (*)(B2,A))&__A::printOthermethod__A){}
};
struct __C;
struct __C_VT;
typedef __rt::Ptr<__C> C;
struct __C {
__C_VT* __vptr;
__C();
 String a;
 String b;
 String c;
static C __init(C __this);
static Class __class();
static __C_VT __vtable;
};
struct __C_VT {
Class __is_a;
void (*__delete)(__C*);
int32_t (*hashCode)(C);
bool (*equals)(C,Object);
Class (*getClass)(C);
String (*toString)(C);
void (*setAmethod__String)(C,String);
void (*printOthermethod__A)(C,A);
__C_VT()
  : __is_a(__C::__class()),
__delete(&__rt::__delete<__C>),
hashCode((int32_t (*)(C))&__Object::hashCode),
equals((bool (*)(C,Object))&__Object::equals),
getClass((Class (*)(C))&__Object::getClass),
toString((String (*)(C))&__A::toString),
setAmethod__String((void (*)(C,String))&__A::setAmethod__String),
printOthermethod__A((void (*)(C,A))&__A::printOthermethod__A){}
};
struct __Test011;
struct __Test011_VT;
typedef __rt::Ptr<__Test011> Test011;
struct __Test011 {
__Test011_VT* __vptr;
__Test011();
static Test011 __init(Test011 __this);
static void main(__rt::Array<String>);
static Class __class();
static __Test011_VT __vtable;
};
struct __Test011_VT {
Class __is_a;
void (*__delete)(__Test011*);
int32_t (*hashCode)(Test011);
bool (*equals)(Test011,Object);
Class (*getClass)(Test011);
String (*toString)(Test011);
__Test011_VT()
  : __is_a(__Test011::__class()),
__delete(&__rt::__delete<__Test011>),
hashCode((int32_t (*)(Test011))&__Object::hashCode),
equals((bool (*)(Test011,Object))&__Object::equals),
getClass((Class (*)(Test011))&__Object::getClass),
toString((String (*)(Test011))&__Object::toString){}
};
};
};
