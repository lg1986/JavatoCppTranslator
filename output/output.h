#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs
{
namespace test032
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
    static void m__A(A,A);
    static void m__double(A,double);
    static void m__Object(A,Object);
    static void m__Object__Object(A,Object,Object);
    static void m__A__Object(A,A,Object);
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
    void (*m__A)(A,A);
    void (*m__double)(A,double);
    void (*m__Object)(A,Object);
    void (*m__Object__Object)(A,Object,Object);
    void (*m__A__Object)(A,A,Object);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
          hashCode((int32_t (*)(A))&__Object::hashCode),
          equals((bool (*)(A,Object))&__Object::equals),
          getClass((Class (*)(A))&__Object::getClass),
          toString((String (*)(A))&__Object::toString),
          m__int(__A::m__int),
          m__A(__A::m__A),
          m__double(__A::m__double),
          m__Object(__A::m__Object),
          m__Object__Object(__A::m__Object__Object),
          m__A__Object(__A::m__A__Object) {}
};
struct __Test032;
struct __Test032_VT;
typedef __rt::Ptr<__Test032> Test032;
struct __Test032
{
    __Test032_VT* __vptr;
    __Test032();
    static int32_t hashCode(Test032);
    static bool equals(Test032,Object);
    static Class getClass(Test032);
    static String toString(Test032);
    static void main(__rt::Array<String>);
    static Test032 __init(Test032 __this);
    static Class __class();
    static __Test032_VT __vtable;
};
struct __Test032_VT
{
    Class __is_a;
    void (*__delete)(__Test032*);
    int32_t (*hashCode)(Test032);
    bool (*equals)(Test032,Object);
    Class (*getClass)(Test032);
    String (*toString)(Test032);
    __Test032_VT()
        : __is_a(__Test032::__class()),
          __delete(&__rt::__delete<__Test032>),
          hashCode((int32_t (*)(Test032))&__Object::hashCode),
          equals((bool (*)(Test032,Object))&__Object::equals),
          getClass((Class (*)(Test032))&__Object::getClass),
          toString((String (*)(Test032))&__Object::toString) {}
};
};
};
