package inputs.test003;
import inputs.test004.Test004;
import inputs.test002.Test002;

class A {
    private String fld;

    public A(String f) {
        fld = f;
    }

    public String getFld() {
        return fld;
    }
}

public class Test003 {
    public static void main(String[] args) {
        A a = new A("A");
        System.out.println(a.getFld());
    }
}