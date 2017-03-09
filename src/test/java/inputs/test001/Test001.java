package inputs.test001;
import inputs.test004.Test004;
class A {
    public String toString() {
        return "A";
    }
}

public class Test001 {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.toString());
    }
}