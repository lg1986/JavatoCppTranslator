#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs{
namespace test020{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A {
__A_VT* __vptr;
__A();
static int32_t x;
static A __init(A __this);
static int32_t xmethod();
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
__A_VT()
  : __is_a(__A::__class()),
__delete(&__rt::__delete<__A>),
hashCode((int32_t (*)(A))&__Object::hashCode),
equals((bool (*)(A,Object))&__Object::equals),
getClass((Class (*)(A))&__Object::getClass),
toString((String (*)(A))&__Object::toString){}
};
struct __Test020;
struct __Test020_VT;
typedef __rt::Ptr<__Test020> Test020;
struct __Test020 {
__Test020_VT* __vptr;
__Test020();
static Test020 __init(Test020 __this);
static void main(__rt::Array<String>);
static Class __class();
static __Test020_VT __vtable;
};
struct __Test020_VT {
Class __is_a;
void (*__delete)(__Test020*);
int32_t (*hashCode)(Test020);
bool (*equals)(Test020,Object);
Class (*getClass)(Test020);
String (*toString)(Test020);
__Test020_VT()
  : __is_a(__Test020::__class()),
__delete(&__rt::__delete<__Test020>),
hashCode((int32_t (*)(Test020))&__Object::hashCode),
equals((bool (*)(Test020,Object))&__Object::equals),
getClass((Class (*)(Test020))&__Object::getClass),
toString((String (*)(Test020))&__Object::toString){}
};
};
};
