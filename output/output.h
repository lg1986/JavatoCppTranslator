#pragma once
#include "java_lang.h"
using namespace java::lang;

namespace nyu
{
namespace edu
{
namespace oop
{
struct __A;
struct __A_VT;
typedef __A* A;
<<<<<<< HEAD
struct __A {
__A_VT* __vptr;
static Class __class();
__A();
static __A_VT __vtable;
A self;
static __A __init( __A __this);
};
struct __A_VT{ 
Class __is_a; 
void (*__delete)(__A*); 
int32_t(*hashCode)( A ); 
bool(*equals)( A, Object  ); 
String(*toString)( A ); 
Class(*getClass)( A ); 

__A_VT() 
	: __is_a(__A::__class())__delete(&__rt::__delete<__A>),,
hashCode((int32_t(*)( A ))&__Object::hashCode),
equals((bool(*)( A, Object  ))&__Object::equals),
toString((String(*)( A ))&__Object::toString),
getClass((Class(*)( A ))&__Object::getClass)
=======
struct __A
{
    __A_VT* __vptr;
    static Class __class();
    __A();
    static __A_VT __vtable;
    static __A __init( __A __this, String s);
    static __A __init( __A __this);
};
struct __B;
struct __B_VT;
typedef __B* B;
struct __B
{
    __B_VT* __vptr;
    static Class __class();
    __B();
    static __B_VT __vtable;
};
struct __C;
struct __C_VT;
typedef __C* C;
struct __C
{
    __C_VT* __vptr;
    static Class __class();
    __C();
    static __C_VT __vtable;
    static __C __init( __C __this);
    static __C __init( __C __this, int i);
    static __C __init( __C __this, double d);
};
struct __ConstructorTest;
struct __ConstructorTest_VT;
typedef __ConstructorTest* ConstructorTest;
struct __ConstructorTest
{
    __ConstructorTest_VT* __vptr;
    static Class __class();
    __ConstructorTest();
    static __ConstructorTest_VT __vtable;
    static void main(ConstructorTest , String[] args);
};
struct __A_VT
{
    Class __is_a;
    void (*__delete)(__A*);
    int32_t(*hashCode)( A );
    bool(*equals)( A, Object  );
    String(*toString)( A );
    Class(*getClass)( A );

    __A_VT()
        : __is_a(__A::__class())__delete(&__rt::__delete<__A>),,
          hashCode((int32_t(*)( A ))&__Object::hashCode),
          equals((bool(*)( A, Object  ))&__Object::equals),
          toString((String(*)( A ))&__Object::toString),
          getClass((Class(*)( A ))&__Object::getClass)
    {
    }
};


struct __B_VT
{
    Class __is_a;
    void (*__delete)(__B*);
    int32_t(*hashCode)( B );
    bool(*equals)( B, Object  );
    String(*toString)( B );
    Class(*getClass)( B );

    __B_VT()
        : __is_a(__B::__class())__delete(&__rt::__delete<__B>),,
          hashCode((int32_t(*)( B ))&__Object::hashCode),
          equals((bool(*)( B, Object  ))&__Object::equals),
          toString((String(*)( B ))&__Object::toString),
          getClass((Class(*)( B ))&__Object::getClass)
    {
    }
};


struct __C_VT
>>>>>>> 85d3940fc963546d92e7c64d204dc6226c93b447
{
    Class __is_a;
    void (*__delete)(__C*);
    int32_t(*hashCode)( C );
    bool(*equals)( C, Object  );
    String(*toString)( C );
    Class(*getClass)( C );

    __C_VT()
        : __is_a(__C::__class())__delete(&__rt::__delete<__C>),,
          hashCode((int32_t(*)( C ))&__Object::hashCode),
          equals((bool(*)( C, Object  ))&__Object::equals),
          toString((String(*)( C ))&__Object::toString),
          getClass((Class(*)( C ))&__Object::getClass)
    {
    }
};


struct __ConstructorTest_VT
{
    Class __is_a;
    void (*__delete)(__ConstructorTest*);
    void(*main)( ConstructorTest, String args );
    int32_t(*hashCode)( ConstructorTest );
    bool(*equals)( ConstructorTest, Object  );
    String(*toString)( ConstructorTest );
    Class(*getClass)( ConstructorTest );

    __ConstructorTest_VT()
        : __is_a(__ConstructorTest::__class())__delete(&__rt::__delete<__ConstructorTest>),,
          main((void(*)( ConstructorTest, String args ))&__ConstructorTest::main),
          hashCode((int32_t(*)( ConstructorTest ))&__Object::hashCode),
          equals((bool(*)( ConstructorTest, Object  ))&__Object::equals),
          toString((String(*)( ConstructorTest ))&__Object::toString),
          getClass((Class(*)( ConstructorTest ))&__Object::getClass)
    {
    }
};


};
};
};
