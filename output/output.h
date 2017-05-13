#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs{
namespace test012{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A {
__A_VT* __vptr;
__A();
static int32_t hashCode(A);
static bool equals(A,Object);
static Class getClass(A);
static String toString(A);
static void setA(A,String);
static void printOther(A,A);
static String myToString(A);
String a;
static A __init(A __this);
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
void (*setA)(A,String);
void (*printOther)(A,A);
String (*myToString)(A);
__A_VT()
  : __is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A))&__Object::hashCode),
equals((bool (*)(A,Object))&__Object::equals),
getClass((Class (*)(A))&__Object::getClass),
toString((String (*)(A))&__Object::toString),
setA(__A::setA),
printOther(__A::printOther),
myToString(__A::myToString){}
};
struct __B1;
struct __B1_VT;
typedef __rt::Ptr<__B1> B1;
struct __B1 {
__B1_VT* __vptr;
__B1();
static int32_t hashCode(B1);
static bool equals(B1,Object);
static Class getClass(B1);
static String toString(B1);
static void setA(B1,String);
static void printOther(B1,A);
static String myToString(B1);
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
void (*setA)(B1,String);
void (*printOther)(B1,A);
String (*myToString)(B1);
__B1_VT()
  : __is_a(__B1::__class()),
__delete(&__rt::__delete<__B1>),
hashCode((int32_t (*)(B1))&__Object::hashCode),
equals((bool (*)(B1,Object))&__Object::equals),
getClass((Class (*)(B1))&__Object::getClass),
toString((String (*)(B1))&__Object::toString),
setA((void (*)(B1,String))&__A::setA),
printOther((void (*)(B1,A))&__A::printOther),
myToString((String (*)(B1))&__A::myToString){}
};
struct __B2;
struct __B2_VT;
typedef __rt::Ptr<__B2> B2;
struct __B2 {
__B2_VT* __vptr;
__B2();
static int32_t hashCode(B2);
static bool equals(B2,Object);
static Class getClass(B2);
static String toString(B2);
static void setA(B2,String);
static void printOther(B2,A);
static String myToString(B2);
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
void (*setA)(B2,String);
void (*printOther)(B2,A);
String (*myToString)(B2);
__B2_VT()
  : __is_a(__B2::__class()),
__delete(&__rt::__delete<__B2>),
hashCode((int32_t (*)(B2))&__Object::hashCode),
equals((bool (*)(B2,Object))&__Object::equals),
getClass((Class (*)(B2))&__Object::getClass),
toString((String (*)(B2))&__Object::toString),
setA((void (*)(B2,String))&__A::setA),
printOther((void (*)(B2,A))&__A::printOther),
myToString((String (*)(B2))&__A::myToString){}
};
struct __C;
struct __C_VT;
typedef __rt::Ptr<__C> C;
struct __C {
__C_VT* __vptr;
__C();
static int32_t hashCode(C);
static bool equals(C,Object);
static Class getClass(C);
static String toString(C);
static void setA(C,String);
static void printOther(C,A);
static String myToString(C);
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
void (*setA)(C,String);
void (*printOther)(C,A);
String (*myToString)(C);
__C_VT()
  : __is_a(__C::__class()),
__delete(&__rt::__delete<__C>),
hashCode((int32_t (*)(C))&__Object::hashCode),
equals((bool (*)(C,Object))&__Object::equals),
getClass((Class (*)(C))&__Object::getClass),
toString((String (*)(C))&__Object::toString),
setA((void (*)(C,String))&__A::setA),
printOther((void (*)(C,A))&__A::printOther),
myToString(__C::myToString){}
};
struct __Test012;
struct __Test012_VT;
typedef __rt::Ptr<__Test012> Test012;
struct __Test012 {
__Test012_VT* __vptr;
__Test012();
static int32_t hashCode(Test012);
static bool equals(Test012,Object);
static Class getClass(Test012);
static String toString(Test012);
static void main(__rt::Array<String>);
static Test012 __init(Test012 __this);
static Class __class();
static __Test012_VT __vtable;
};
struct __Test012_VT {
Class __is_a;
void (*__delete)(__Test012*);
int32_t (*hashCode)(Test012);
bool (*equals)(Test012,Object);
Class (*getClass)(Test012);
String (*toString)(Test012);
__Test012_VT()
  : __is_a(__Test012::__class()),
__delete(&__rt::__delete<__Test012>),
hashCode((int32_t (*)(Test012))&__Object::hashCode),
equals((bool (*)(Test012,Object))&__Object::equals),
getClass((Class (*)(Test012))&__Object::getClass),
toString((String (*)(Test012))&__Object::toString){}
};
};
};
