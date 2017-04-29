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
    String toString
    String __init(A)
};
struct __A_VT
{
    Class __is a;
    void (*__delete)(__A*);
    __A_VT()
        : __is_a(__A::__class()),
          __delete(&__rt::__delete<__A>),
    };
    struct __Test001;
    struct Test001_VT;
    typedef __rt::Ptr<__Test001> Test001;
    struct __Test001
{
    __Test001VT* __vptr
    void main
    void __init(Test001)
};
struct __Test001_VT
{
    Class __is a;
    void (*__delete)(__A*);
    __Test001_VT()
        : __is_a(__Test001::__class()),
          __delete(&__rt::__delete<__Test001>),
    };
    };
    };
    };
