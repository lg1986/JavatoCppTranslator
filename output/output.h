#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs
{
namespace test008
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
    String a;
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
          toString((String (*)(A))&__Object::toString) {}
};
struct __B;
struct __B_VT;
typedef __rt::Ptr<__B> B;
struct __B
{
    __B_VT* __vptr;
    __B();
    static int32_t hashCode(B);
    static bool equals(B,Object);
    static Class getClass(B);
    static String toString(B);
    String a;
    String b;
    static B __init(B __this,String);
    static Class __class();
    static __B_VT __vtable;
};
struct __B_VT
{
    Class __is_a;
    void (*__delete)(__B*);
    int32_t (*hashCode)(B);
    bool (*equals)(B,Object);
    Class (*getClass)(B);
    String (*toString)(B);
    __B_VT()
        : __is_a(__B::__class()),
          __delete(&__rt::__delete<__B>),
          hashCode((int32_t (*)(B))&__Object::hashCode),
          equals((bool (*)(B,Object))&__Object::equals),
          getClass((Class (*)(B))&__Object::getClass),
          toString((String (*)(B))&__Object::toString) {}
};
struct __Test008;
struct __Test008_VT;
typedef __rt::Ptr<__Test008> Test008;
struct __Test008
{
    __Test008_VT* __vptr;
    __Test008();
    static int32_t hashCode(Test008);
    static bool equals(Test008,Object);
    static Class getClass(Test008);
    static String toString(Test008);
    static void main(__rt::Array<String> args);
    static Test008 __init(Test008 __this);
    static Class __class();
    static __Test008_VT __vtable;
};
struct __Test008_VT
{
    Class __is_a;
    void (*__delete)(__Test008*);
    int32_t (*hashCode)(Test008);
    bool (*equals)(Test008,Object);
    Class (*getClass)(Test008);
    String (*toString)(Test008);
    __Test008_VT()
        : __is_a(__Test008::__class()),
          __delete(&__rt::__delete<__Test008>),
          hashCode((int32_t (*)(Test008))&__Object::hashCode),
          equals((bool (*)(Test008,Object))&__Object::equals),
          getClass((Class (*)(Test008))&__Object::getClass),
          toString((String (*)(Test008))&__Object::toString) {}
};
};
};
