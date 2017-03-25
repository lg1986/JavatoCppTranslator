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
    String toString(A)
};
struct __B;
struct __B_VT;
struct __B
{
    __B_VT* __vptr;
    __B()();
    static Class __class();
    static __B()_VT __vtable;
    String toString(B)
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


    struct __B_VT
{
    Class __is_a;
    int32_t (*hashCode)(B);
    bool (*equals)(B, Object);
    Class (*getClass)(B);
    String (*toString)(B);
    __B_VT()
        : __is_a(__B::__class()),
          toString(__B::toString),
          hashCode((int_32(*)( B )) &__Object::hashCode),
          equals((bool(*)( B, Object )) &__Object::equals),
          getClass((Class(*)( B )) &__Object::getClass),
    }


    };
    };
    };
