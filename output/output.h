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
    __A_VT* __vptr
    __A();
    A self
    A __init(A);
};
struct __A_VT
{
    Class __is_a;
    void (*__delete)(__A*);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
    };
    struct __Test017;
    struct __Test017_VT;
    typedef __rt::Ptr<__Test017> Test017;
    struct __Test017
{
    __Test017_VT* __vptr
    __Test017();
    void main
    void __init(Test017);
};
struct __Test017_VT
{
    Class __is_a;
    void (*__delete)(__Test017*);
    __Test017_VT()
        : __is_a(__Test017::__class()),
          __delete(&__rt::__delete<__Test017>),
    };
    };
    };
    };
