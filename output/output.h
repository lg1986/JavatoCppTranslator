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
    static Class __class();
    __A();
    String toString(A );
    void mainSub(A );
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


    };
    };
    };
