package inputs.test003;
import inputs.test004.Test004;



class B{
    public B(){

    }

}
class A {
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

public class Test003{
    public static void main(String[] args) {
        A a = new A("A");
        System.out.println(a.getFld());
    }
}