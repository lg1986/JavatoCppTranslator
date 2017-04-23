package inputs.test003;

class A {
    private String fld;


    public A() {

    }

    public String getFld() {
        return fld;
    }
}

public class Test003 {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.getFld());
    }
}