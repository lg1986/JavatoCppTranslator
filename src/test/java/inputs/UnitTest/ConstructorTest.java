package inputs.UnitTest;

/**
 * Created by stephaniemcaleer on 4/19/17.
 */
class A {

    public A(String s) {};

    public A() {};
}

class B {
    public int x = 5;

}

class C {
    public C() {};

    public C(int i) {};

    public C(double d) {};

}

public class ConstructorTest {
    public static void main(String[] args) {
        A a1 = new A("test");
        A a = new A();

        B b = new B();

        C c = new C(3);
        C c2 = new C(5.4);
        C c3 = new C();

    }
}
