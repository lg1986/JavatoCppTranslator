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


    struct __B1_VT
{
    Class __is_a;
    int32_t (*hashCode)(B1);
    bool (*equals)(B1, Object);
    Class (*getClass)(B1);
    String (*toString)(B1);
    __B1_VT()
        : __is_a(__B1::__class()),
          hashCode((int_32(*)( B1 )) &__Object::hashCode),
          equals((bool(*)( B1, Object )) &__Object::equals),
          toString((String(*)( B1 )) &__Object::toString),
          getClass((Class(*)( B1 )) &__Object::getClass),
    }


    struct __B2_VT
{
    Class __is_a;
    int32_t (*hashCode)(B2);
    bool (*equals)(B2, Object);
    Class (*getClass)(B2);
    String (*toString)(B2);
    __B2_VT()
        : __is_a(__B2::__class()),
          hashCode((int_32(*)( B2 )) &__Object::hashCode),
          equals((bool(*)( B2, Object )) &__Object::equals),
          toString((String(*)( B2 )) &__Object::toString),
          getClass((Class(*)( B2 )) &__Object::getClass),
    }


    struct __C_VT
{
    Class __is_a;
    int32_t (*hashCode)(C);
    bool (*equals)(C, Object);
    Class (*getClass)(C);
    String (*toString)(C);
    __C_VT()
        : __is_a(__C::__class()),
          hashCode((int_32(*)( C )) &__Object::hashCode),
          equals((bool(*)( C, Object )) &__Object::equals),
          toString((String(*)( C )) &__Object::toString),
          getClass((Class(*)( C )) &__Object::getClass),
    }


    struct __Test011_VT
{
    Class __is_a;
    int32_t (*hashCode)(Test011);
    bool (*equals)(Test011, Object);
    Class (*getClass)(Test011);
    String (*toString)(Test011);
    __Test011_VT()
        : __is_a(__Test011::__class()),
          hashCode((int_32(*)( Test011 )) &__Object::hashCode),
          equals((bool(*)( Test011, Object )) &__Object::equals),
          toString((String(*)( Test011 )) &__Object::toString),
          getClass((Class(*)( Test011 )) &__Object::getClass),
    }


