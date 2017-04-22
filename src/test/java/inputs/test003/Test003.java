package inputs.test003;
import inputs.test004.Test004;



class B extends A{
    public B(){

    }

}
class A extends Test004{
    private String fld;

    public A(){

    }
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