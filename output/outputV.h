struct __A_VT{
Class __is_a;
int32_t (*hashCode)(A);
bool (*equals)(A, Object);
Class (*getClass)(A);
String (*toString)(A);
__A_VT()
: __is_a(__A::__class()),
toString(__A::toString),
}


struct __B_VT{
Class __is_a;
int32_t (*hashCode)(B);
bool (*equals)(B, Object);
Class (*getClass)(B);
String (*toString)(B);
__B_VT()
: __is_a(__B::__class()),
toString(__B::toString),
}


struct __Test005_VT{
Class __is_a;
int32_t (*hashCode)(Test005);
bool (*equals)(Test005, Object);
Class (*getClass)(Test005);
String (*toString)(Test005);
__Test005_VT()
: __is_a(__Test005::__class()),
}


