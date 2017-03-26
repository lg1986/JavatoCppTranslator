package inputs.test001;

class A {
    public String toString(String s) {
        return "A";
    }
}

public class Test001 {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.toString("s"));
    }
}