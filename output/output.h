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
struct A_VT;
typedef __rt::Ptr<__A> A;
struct __A
{
    __AVT* __vptr
    A self
    A __init(A)
};
struct __A_VT
{
    Class __is a;
    void (*__delete)(__A*);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
    };
    struct __Test017;
    struct Test017_VT;
    typedef __rt::Ptr<__Test017> Test017;
    struct __Test017
{
    __Test017VT* __vptr
    void main
    void __init(Test017)
};
struct __Test017_VT
{
    Class __is a;
    void (*__delete)(__A*);
    __Test017_VT()
        : __is_a(__Test017::__class()),
          __delete(&__rt::__delete<__Test017>),
    };
    };
    };
    };
