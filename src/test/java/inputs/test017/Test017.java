package inputs.test017;

class A {
    A self;
    int x;

    public A(int x) {
        self = this;
        x = x;
    }

    public int self() {
        A a = new A(1);
        a.print(a.getX());
        return x;
    }

    public void print(int x) {

    }

    public int getX() {
        return this.x;
    }
}

public class Test017 {
    public static void main(String[] args) {
        A a = new A(5);
    }
}
