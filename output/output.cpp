#include <iostream>
#include "output.h"
using namespace java::lang
    namespace inputs
    {
    namespace null
    {
    A::A() : __vptr(&__vtable) Class A::__class()
    {
        static Class k =
            new __Class(__rt::literal("nyu.edu.oop.A"), __Object::__class());
        return k;
    }
    A_VT A::__vtable;
    String __A::toString(A __this)
    {
        String a = __rt::literal("A");
        return a;
    }
    Test001::Test001() : __vptr(&__vtable) Class Test001::__class()
    {
        static Class k =
            new __Class(__rt::literal("nyu.edu.oop.Test001"), __Object::__class());
        return k;
    }
    Test001_VT Test001::__vtable;
    __Test001::main(Test001 __this, String args)
    {
        A a = __A::__init(new __A());
        String s = a->__vptr->toString();
        System->out->__vptr->println(a->__vptr->toString());
    }
    }
    }
