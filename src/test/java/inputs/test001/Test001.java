package inputs.test001;

class A {
    public String toString() {
        String a = "A";
        return a;
    }
    public A() {

    }

    public String myString(double i, int x) {
        return "i";
    }

    public String myString(int x) {
        return "s";
    }

}

class B extends A {
    public String myString(String s) {
        return "S";
    }
}

public class Test001 {
    public static void main(String[] args) {
        A a = new A();
        String s = a.toString();
        a.myString(1);
        System.out.println(a.toString());
    }
}