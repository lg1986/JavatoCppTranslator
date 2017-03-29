void __Test011::main(Test011 __this,String args)
{
    A a = new __A()
    a->_vptr->setA(a)
    __rt::literal("A");
    B1 b1 = new __B1()
    b1->_vptr->setA(b1)
    __rt::literal("B1");
    B2 b2 = new __B2()
    b2->_vptr->setA(b2)
    __rt::literal("B2");
    C c = new __C()
    c->_vptr->setA(c)
    __rt::literal("C");
    a->_vptr->printOther(a)
    aa->_vptr->printOther(a)
    b1a->_vptr->printOther(a)
    b2a->_vptr->printOther(a)
    c
}
