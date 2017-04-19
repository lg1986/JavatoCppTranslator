#include <iostream>
#include "java_lang.h"
#include "output.h"
using namespace nyu::edu::oop;
using namespace std;
int main(){ 
A a = new __A(__rt::literal("s")); 
a->__vptr->almostSetFld(a__rt::literal("B")); 
cout <<(a->__vptr->getFld(a)); 
a->__vptr->setFld(a__rt::literal("B")); 
cout <<(a->__vptr->getFld(a)); 
return 0;
}
