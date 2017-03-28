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
    String fld;
    static A __init(A __this);
    static A __init(A __this, String s);
    void setFld(A, String f);
    void almostSetFld(A, String f);
    String getFld(A );
};
struct __B;
struct __B_VT;
struct __B
{
    __B_VT* __vptr;
    static Class __class();
    __B();
    static B __init(B __this);
    String fld;
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
          getFld(__A::getFld),
          hashCode((int_32(*)( A )) &__Object::hashCode),
          equals((bool(*)( A, Object )) &__Object::equals),
          toString((String(*)( A )) &__Object::toString),
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
          hashCode((int_32(*)( B )) &__Object::hashCode),
          equals((bool(*)( B, Object )) &__Object::equals),
          toString((String(*)( B )) &__Object::toString),
          getClass((Class(*)( B )) &__Object::getClass),
          getFld((String(*)( B )) &__A::getFld),
    }


    };
    };
    };
