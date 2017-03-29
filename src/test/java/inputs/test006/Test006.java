package inputs.test006;

class A {
    private String fld = "A";

    public A() {}
    public A(String s) { }

    public void setFld(String f) {
        fld = f;
    }

    public void almostSetFld(String f) {
        String fld;
        fld = f;
    }

    public String getFld() {
        return fld;
    }
}

class B extends A {
    public B() {
    }
}

public class Test006 {
    public static void main(String[] args) {
        A a = new A("s");
        a.almostSetFld("B");
        System.out.println(a.getFld());
        a.setFld("B");
        System.out.println(a.getFld());
    }
}