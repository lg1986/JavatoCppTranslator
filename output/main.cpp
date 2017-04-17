#include <iostream>
#include "java_lang.h"
#include "output.h"
using namespace nyu::edu::oop;
using namespace std;
int main(){ 
A a = new __A(); 
Object o = a; 
cout <<(a->__vptr->); 
cout <<(o->__vptr->toString(o)); 
return 0;
}
