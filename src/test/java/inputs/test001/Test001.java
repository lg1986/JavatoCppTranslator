package inputs.test001;

class A {
    public String toString() {
        return "A";
    }
    public void mainSub(){
        A a = new A();
        String k = "s";
        String s = a.toString();
        System.out.println(a.toString());
    }
}

public class Test001 {
    public static void main(String[] args) {
        A a = new A();
        String s = a.toString();
        System.out.println(a.toString());
    }
}