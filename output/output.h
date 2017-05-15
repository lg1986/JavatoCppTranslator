#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs{
namespace test042{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A {
__A_VT* __vptr;
__A();
static A __init(A __this);
static void mmethod(A);
static A mmethod__A(A,A);
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
void (*mmethod)(A);
A (*mmethod__A)(A,A);
__A_VT()
  : __is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A))&__Object::hashCode),
equals((bool (*)(A,Object))&__Object::equals),
getClass((Class (*)(A))&__Object::getClass),
toString((String (*)(A))&__Object::toString),
mmethod(__A::mmethod),
mmethod__A(__A::mmethod__A){}
};
struct __B;
struct __B_VT;
typedef __rt::Ptr<__B> B;
struct __B {
__B_VT* __vptr;
__B();
static B __init(B __this);
static void mmethod(B);
static A mmethod__A(B,A);
static A mmethod__B(B,B);
static Class __class();
static __B_VT __vtable;
};
struct __B_VT {
Class __is_a;
void (*__delete)(__B*);
int32_t (*hashCode)(B);
bool (*equals)(B,Object);
Class (*getClass)(B);
String (*toString)(B);
void (*mmethod)(B);
A (*mmethod__A)(B,A);
A (*mmethod__B)(B,B);
__B_VT()
  : __is_a(__B::__class()),
__delete(&__rt::__delete<__B>),
hashCode((int32_t (*)(B))&__Object::hashCode),
equals((bool (*)(B,Object))&__Object::equals),
getClass((Class (*)(B))&__Object::getClass),
toString((String (*)(B))&__Object::toString),
mmethod(__B::mmethod),
mmethod__A(__B::mmethod__A),
mmethod__B(__B::mmethod__B){}
};
struct __Test042;
struct __Test042_VT;
typedef __rt::Ptr<__Test042> Test042;
struct __Test042 {
__Test042_VT* __vptr;
__Test042();
static Test042 __init(Test042 __this);
static void main(__rt::Array<String>);
static Class __class();
static __Test042_VT __vtable;
};
struct __Test042_VT {
Class __is_a;
void (*__delete)(__Test042*);
int32_t (*hashCode)(Test042);
bool (*equals)(Test042,Object);
Class (*getClass)(Test042);
String (*toString)(Test042);
__Test042_VT()
  : __is_a(__Test042::__class()),
__delete(&__rt::__delete<__Test042>),
hashCode((int32_t (*)(Test042))&__Object::hashCode),
equals((bool (*)(Test042,Object))&__Object::equals),
getClass((Class (*)(Test042))&__Object::getClass),
toString((String (*)(Test042))&__Object::toString){}
};
};
};
