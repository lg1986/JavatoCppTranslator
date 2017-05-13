#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs
{
namespace test005
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
    static B __init(B __this);
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
          toString(__B::toString) {}
};
struct __Test005;
struct __Test005_VT;
typedef __rt::Ptr<__Test005> Test005;
struct __Test005
{
    __Test005_VT* __vptr;
    __Test005();
    static int32_t hashCode(Test005);
    static bool equals(Test005,Object);
    static Class getClass(Test005);
    static String toString(Test005);
    static void main(Test005,String);
    static Test005 __init(Test005 __this);
    static Class __class();
    static __Test005_VT __vtable;
};
struct __Test005_VT
{
    Class __is_a;
    void (*__delete)(__Test005*);
    int32_t (*hashCode)(Test005);
    bool (*equals)(Test005,Object);
    Class (*getClass)(Test005);
    String (*toString)(Test005);
    __Test005_VT()
        : __is_a(__Test005::__class()),
          __delete(&__rt::__delete<__Test005>),
          hashCode((int32_t (*)(Test005))&__Object::hashCode),
          equals((bool (*)(Test005,Object))&__Object::equals),
          getClass((Class (*)(Test005))&__Object::getClass),
          toString((String (*)(Test005))&__Object::toString) {}
};
};
};
