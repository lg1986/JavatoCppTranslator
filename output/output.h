using namespace edu::nyu::oop;
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
    __A_VT* __vptr
    static Class __class()
    type(qualifiedidentifier("string"), null) toString()()
};
struct __Test001;
struct __Test001_VT;
struct __Test001
{
    __Test001_VT* __vptr
    static Class __class()
    void main()(String[] args)
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


    struct __Test001_VT
{
    Class __is_a;
    int32_t (*hashCode)(Test001);
    bool (*equals)(Test001, Object);
    Class (*getClass)(Test001);
    String (*toString)(Test001);
    __Test001_VT()
        : __is_a(__Test001::__class()),
          hashCode((int_32(*)( Test001 )) &__Object::hashCode),
          equals((bool(*)( Test001, Object )) &__Object::equals),
          toString((String(*)( Test001 )) &__Object::toString),
          getClass((Class(*)( Test001 )) &__Object::getClass),
    }


    };
    };
    };
