#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace inputs
{
namespace test003
{
struct __B;
struct __B_VT;
typedef __rt::Ptr<__B> B;
struct __B
{
    __B_VT* __vptr;
    __B();
    static int meth(B,int);
    String fld;
    String x;
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
    String (*getFld)(B);
    int (*meth)(B,int);
    __B_VT()
        : __is_a(__B::__class()),
          __delete(&__rt::__delete<__B>),
          hashCode((int32_t (*)(B))&__Object::hashCode),
          equals((bool (*)(B,Object))&__Object::equals),
          getClass((Class (*)(B))&__Object::getClass),
          toString((String (*)(B))&__Object::toString),
          getFld((String (*)(B))&__A::getFld),
          meth(__B::meth) {}
};
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A
{
    __A_VT* __vptr;
    __A();
    static String getFld(A);
    String fld;
    static A __init(A __this);
    static A __init(A __this,String);
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
    String (*getFld)(A);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
          hashCode((int32_t (*)(A))&__Object::hashCode),
          equals((bool (*)(A,Object))&__Object::equals),
          getClass((Class (*)(A))&__Object::getClass),
          toString((String (*)(A))&__Object::toString),
          getFld(__A::getFld) {}
};
struct __Test003;
struct __Test003_VT;
typedef __rt::Ptr<__Test003> Test003;
struct __Test003
{
    __Test003_VT* __vptr;
    __Test003();
    static void main(Test003,String);
    static Test003 __init(Test003 __this);
    static Class __class();
    static __Test003_VT __vtable;
};
struct __Test003_VT
{
    Class __is_a;
    void (*__delete)(__Test003*);
    int32_t (*hashCode)(Test003);
    bool (*equals)(Test003,Object);
    Class (*getClass)(Test003);
    String (*toString)(Test003);
    __Test003_VT()
        : __is_a(__Test003::__class()),
          __delete(&__rt::__delete<__Test003>),
          hashCode((int32_t (*)(Test003))&__Object::hashCode),
          equals((bool (*)(Test003,Object))&__Object::equals),
          getClass((Class (*)(Test003))&__Object::getClass),
          toString((String (*)(Test003))&__Object::toString) {}
};
};
};
