#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs
{
namespace test015
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
    static void printOther__A(A,A);
    static A some;
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
    void (*printOther__A)(A,A);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
          hashCode((int32_t (*)(A))&__Object::hashCode),
          equals((bool (*)(A,Object))&__Object::equals),
          getClass((Class (*)(A))&__Object::getClass),
          toString((String (*)(A))&__Object::toString),
          printOther__A(__A::printOther__A) {}
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
    static void printOther__A(B,A);
    static A some;
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
    void (*printOther__A)(B,A);
    __B_VT()
        : __is_a(__B::__class()),
          __delete(&__rt::__delete<__B>),
          hashCode((int32_t (*)(B))&__Object::hashCode),
          equals((bool (*)(B,Object))&__Object::equals),
          getClass((Class (*)(B))&__Object::getClass),
          toString(__B::toString),
          printOther__A(__B::printOther__A) {}
};
struct __Test015;
struct __Test015_VT;
typedef __rt::Ptr<__Test015> Test015;
struct __Test015
{
    __Test015_VT* __vptr;
    __Test015();
    static int32_t hashCode(Test015);
    static bool equals(Test015,Object);
    static Class getClass(Test015);
    static String toString(Test015);
    static void main(__rt::Array<String>);
    static Test015 __init(Test015 __this);
    static Class __class();
    static __Test015_VT __vtable;
};
struct __Test015_VT
{
    Class __is_a;
    void (*__delete)(__Test015*);
    int32_t (*hashCode)(Test015);
    bool (*equals)(Test015,Object);
    Class (*getClass)(Test015);
    String (*toString)(Test015);
    __Test015_VT()
        : __is_a(__Test015::__class()),
          __delete(&__rt::__delete<__Test015>),
          hashCode((int32_t (*)(Test015))&__Object::hashCode),
          equals((bool (*)(Test015,Object))&__Object::equals),
          getClass((Class (*)(Test015))&__Object::getClass),
          toString((String (*)(Test015))&__Object::toString) {}
};
};
};
