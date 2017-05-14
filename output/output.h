#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs
{
namespace test035
{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A
{
    __A_VT* __vptr;
    __A();
    static int32_t hashCode(A);
    static bool equals(A,Object);
    static Class getClass(A);
    static String toString(A);
    static int32_t m__int(A,int32_t);
    static void m__double(A,double);
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
    int32_t (*m__int)(A,int32_t);
    void (*m__double)(A,double);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
          hashCode((int32_t (*)(A))&__Object::hashCode),
          equals((bool (*)(A,Object))&__Object::equals),
          getClass((Class (*)(A))&__Object::getClass),
          toString((String (*)(A))&__Object::toString),
          m__int(__A::m__int),
          m__double(__A::m__double) {}
};
struct __Test035;
struct __Test035_VT;
typedef __rt::Ptr<__Test035> Test035;
struct __Test035
{
    __Test035_VT* __vptr;
    __Test035();
    static int32_t hashCode(Test035);
    static bool equals(Test035,Object);
    static Class getClass(Test035);
    static String toString(Test035);
    static void main(__rt::Array<String>);
    static Test035 __init(Test035 __this);
    static Class __class();
    static __Test035_VT __vtable;
};
struct __Test035_VT
{
    Class __is_a;
    void (*__delete)(__Test035*);
    int32_t (*hashCode)(Test035);
    bool (*equals)(Test035,Object);
    Class (*getClass)(Test035);
    String (*toString)(Test035);
    __Test035_VT()
        : __is_a(__Test035::__class()),
          __delete(&__rt::__delete<__Test035>),
          hashCode((int32_t (*)(Test035))&__Object::hashCode),
          equals((bool (*)(Test035,Object))&__Object::equals),
          getClass((Class (*)(Test035))&__Object::getClass),
          toString((String (*)(Test035))&__Object::toString) {}
};
};
};
