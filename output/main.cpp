#include <iostream>
#include "java_lang.h"
#include "output.h"
using namespace nyu::edu::oop;
using namespace std;
int main()
{
    A a = new __A(__rt::literal("A"));
    cout <<(a->__vptr->getFld(a));
    return 0;
}
