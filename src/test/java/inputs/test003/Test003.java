package inputs.test003;

class B extends A{
    String x;
    public B(){

    }

    public int meth(int x){
        return 0;
    }

    public String getFld(){
        return "A";
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