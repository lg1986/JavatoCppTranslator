#pragma once;
#include "java_lang.h";
namespace edu
{
namespace nyu
{
namespace oop
{
struct __A;
struct __A_VT;
struct __A
{
    __A_VT* __vptr;
    __A()();
    static Class __class();
    static __A()_VT __vtable;
    void setA(String)
    void printOther(A)
    String toString(A)
};
struct __B1;
struct __B1_VT;
struct __B1
{
    __B1_VT* __vptr;
    __B1()();
    static Class __class();
    static __B1()_VT __vtable;
};
struct __B2;
struct __B2_VT;
struct __B2
{
    __B2_VT* __vptr;
    __B2()();
    static Class __class();
    static __B2()_VT __vtable;
};
struct __C;
struct __C_VT;
struct __C
{
    __C_VT* __vptr;
    __C()();
    static Class __class();
    static __C()_VT __vtable;
};
struct __A_VT
{
    Class __is_a;
    int32_t (*hashCode)(A);
    bool (*equals)(A, Object);
    Class (*getClass)(A);
    String (*toString)(A);
    __A_VT()
        : __is_a(__A::__class()),
          toString(__A::toString),
          hashCode((int_32(*)( A )) &__Object::hashCode),
          equals((bool(*)( A, Object )) &__Object::equals),
          getClass((Class(*)( A )) &__Object::getClass),
    }


    struct __B1_VT
{
    Class __is_a;
    int32_t (*hashCode)(B1);
    bool (*equals)(B1, Object);
    Class (*getClass)(B1);
    String (*toString)(B1);
    __B1_VT()
        : __is_a(__B1::__class()),
          hashCode((int_32(*)( B1 )) &__Object::hashCode),
          equals((bool(*)( B1, Object )) &__Object::equals),
          toString((String(*)( B1 )) &__Object::toString),
          getClass((Class(*)( B1 )) &__Object::getClass),
    }


    struct __B2_VT
{
    Class __is_a;
    int32_t (*hashCode)(B2);
    bool (*equals)(B2, Object);
    Class (*getClass)(B2);
    String (*toString)(B2);
    __B2_VT()
        : __is_a(__B2::__class()),
          hashCode((int_32(*)( B2 )) &__Object::hashCode),
          equals((bool(*)( B2, Object )) &__Object::equals),
          toString((String(*)( B2 )) &__Object::toString),
          getClass((Class(*)( B2 )) &__Object::getClass),
    }


    struct __C_VT
{
    Class __is_a;
    int32_t (*hashCode)(C);
    bool (*equals)(C, Object);
    Class (*getClass)(C);
    String (*toString)(C);
    __C_VT()
        : __is_a(__C::__class()),
          hashCode((int_32(*)( C )) &__Object::hashCode),
          equals((bool(*)( C, Object )) &__Object::equals),
          toString((String(*)( C )) &__Object::toString),
          getClass((Class(*)( C )) &__Object::getClass),
    }


    };
    };
    };
