package inputs.constructors;

class A {
    int x = 2;

    boolean b;

    int y;

    {
        y = x + 3;
    }

    A() {
        b = true;
    }

    A(int x1) {
        x = x1;
    }

}

class B extends A {
    int z;

    B(int x1) {
        super(x1);
        z = x1 + 1;
    }
}

public class Constructors {
    public static void main(String[] args) {
        B b = new B(2);

        System.out.println(b.z);
    }
}