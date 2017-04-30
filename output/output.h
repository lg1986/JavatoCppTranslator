#pragma once
#include "java_lang.h"
using namespace nyu::edu::oop;

namespace nyu
{
namespace edu
{
namespace oop
{
struct __A;
struct __A_VT;
typedef __rt::Ptr<__A> A;
struct __A
{
    __A_VT* __vptr;
    __A();
    A self
};
struct __A_VT
{
    Class __is_a;
    void (*__delete)(__A*);
    int32_t (*hashCode)(A);
    bool (*equals)(A);
    Class (*getClass)(A);
    String (*toString)(A);
    A (*self)(A);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
          ((int32_t (*)(A))&__Object::hashCode),
          ((bool (*)(A))&__Object::equals),
          ((Class (*)(A))&__Object::getClass),
          ((String (*)(A))&__Object::toString),
          ((A (*)(A))&__A::self),
    };
    struct __Test017;
    struct __Test017_VT;
    typedef __rt::Ptr<__Test017> Test017;
    struct __Test017
{
    __Test017_VT* __vptr;
    __Test017();
    void main
};
struct __Test017_VT
{
    Class __is_a;
    void (*__delete)(__Test017*);
    int32_t (*hashCode)(Test017);
    bool (*equals)(Test017);
    Class (*getClass)(Test017);
    String (*toString)(Test017);
    void (*main)(Test017);
    __Test017_VT()
        : __is_a(__Test017::__class()),
          __delete(&__rt::__delete<__Test017>),
          ((int32_t (*)(Test017))&__Object::hashCode),
          ((bool (*)(Test017))&__Object::equals),
          ((Class (*)(Test017))&__Object::getClass),
          ((String (*)(Test017))&__Object::toString),
          ((void (*)(Test017))&__Test017::main),
    };
    };
    };
    };
