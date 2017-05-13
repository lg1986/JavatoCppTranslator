#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs{
namespace test001{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A {
__A_VT* __vptr;
__A();
static int32_t hashCode(A);
static bool equals__Object(A,Object);
static Class getClass(A);
static String toString(A);
static String myString__double__int(A,double,int);
static String myString__int(A,int);
static A __init(A __this);
static Class __class();
static __A_VT __vtable;
};
struct __A_VT {
Class __is_a;
void (*__delete)(__A*);
int32_t (*hashCode)(A);
bool (*equals__Object)(A,Object);
Class (*getClass)(A);
String (*toString)(A);
String (*myString__double__int)(A,double,int);
String (*myString__int)(A,int);
__A_VT()
  : __is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A))&__Object::hashCode),
equals__Object((bool (*)(A,Object))&__Object::equals__Object),
getClass((Class (*)(A))&__Object::getClass),
toString(__A::toString),
myString__double__int(__A::myString__double__int),
myString__int(__A::myString__int){}
};
struct __B;
struct __B_VT;
typedef __rt::Ptr<__B> B;
struct __B {
__B_VT* __vptr;
__B();
static int32_t hashCode(B);
static bool equals__Object(B,Object);
static Class getClass(B);
static String toString(B);
static String myString__double__int(B,double,int);
static String myString__int(B,int);
static String myString__String(B,String);
static B __init(B __this);
static Class __class();
static __B_VT __vtable;
};
struct __B_VT {
Class __is_a;
void (*__delete)(__B*);
int32_t (*hashCode)(B);
bool (*equals__Object)(B,Object);
Class (*getClass)(B);
String (*toString)(B);
String (*myString__double__int)(B,double,int);
String (*myString__int)(B,int);
String (*myString__String)(B,String);
__B_VT()
  : __is_a(__B::__class()),
__delete(&__rt::__delete<__B>),
hashCode((int32_t (*)(B))&__Object::hashCode),
equals__Object((bool (*)(B,Object))&__Object::equals__Object),
getClass((Class (*)(B))&__Object::getClass),
toString((String (*)(B))&__A::toString),
myString__double__int((String (*)(B,double,int))&__A::myString__double__int),
myString__int((String (*)(B,int))&__A::myString__int),
myString__String(__B::myString__String){}
};
struct __Test001;
struct __Test001_VT;
typedef __rt::Ptr<__Test001> Test001;
struct __Test001 {
__Test001_VT* __vptr;
__Test001();
static int32_t hashCode(Test001);
static bool equals__Object(Test001,Object);
static Class getClass(Test001);
static String toString(Test001);
static void main__String(__rt::Array<String>);
static Test001 __init(Test001 __this);
static Class __class();
static __Test001_VT __vtable;
};
struct __Test001_VT {
Class __is_a;
void (*__delete)(__Test001*);
int32_t (*hashCode)(Test001);
bool (*equals__Object)(Test001,Object);
Class (*getClass)(Test001);
String (*toString)(Test001);
__Test001_VT()
  : __is_a(__Test001::__class()),
__delete(&__rt::__delete<__Test001>),
hashCode((int32_t (*)(Test001))&__Object::hashCode),
equals__Object((bool (*)(Test001,Object))&__Object::equals__Object),
getClass((Class (*)(Test001))&__Object::getClass),
toString((String (*)(Test001))&__Object::toString){}
};
};
};
