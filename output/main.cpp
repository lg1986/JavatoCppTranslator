#include <iostream>
#include "java_lang.h"
#include "output.h"
using namespace nyu::edu::oop;
using namespace std;
int main()
{
    A a = new __A();
    String s = a->__vptr->toString(a);
    cout <<(a->__vptr->toString(a));
    return 0;
}
