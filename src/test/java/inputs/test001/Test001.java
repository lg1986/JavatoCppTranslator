package inputs.test001;

class A {
    public String toString() {
        String a = "A";
        return a;
    }
}

public class Test001 {
    public static void main(String[] args) {
        A a = new A();
        String s = a.toString();
        System.out.println(a.toString());
    }
}