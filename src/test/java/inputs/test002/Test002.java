package inputs.test002;

class A {
    public String a;
    public String toString(int i) {
        return "A";
    }
}

public class Test002 {
    public static void main(String[] args) {
        A a = new A();
        Object o = a;
        System.out.println(a);
        System.out.println(o.toString());
    }
}