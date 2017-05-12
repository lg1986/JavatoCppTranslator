#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs
{
namespace test001
{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A
{
    __A_VT* __vptr;
    __A();
    static String toString(A);
    static A __init(A __this);
    static Class __class();
    static __A_VT __vtable;
};
struct __A_VT
{
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
          toString(__A::toString) {}
};
struct __Test001;
struct __Test001_VT;
typedef __rt::Ptr<__Test001> Test001;
struct __Test001
{
    __Test001_VT* __vptr;
    __Test001();
    static void main(__rt::Array<String> args);
    static Test001 __init(Test001 __this);
    static Class __class();
    static __Test001_VT __vtable;
};
struct __Test001_VT
{
    Class __is_a;
    void (*__delete)(__Test001*);
    int32_t (*hashCode)(Test001);
    bool (*equals)(Test001,Object);
    Class (*getClass)(Test001);
    String (*toString)(Test001);
    __Test001_VT()
        : __is_a(__Test001::__class()),
          __delete(&__rt::__delete<__Test001>),
          hashCode((int32_t (*)(Test001))&__Object::hashCode),
          equals((bool (*)(Test001,Object))&__Object::equals),
          getClass((Class (*)(Test001))&__Object::getClass),
          toString((String (*)(Test001))&__Object::toString) {}
};
};
};
