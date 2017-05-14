package inputs.test001;

class A {
    public String toString() {
        String a = "A";
        return a;
    }
    public A() {

    }

    public int val(double x){
        return 10;
    }

    public int val(double x, int y, int k){
        return 11;
    }
}

class B extends A{
    public int val(double x, double y, double z){
        return 12;
    }
}

public class Test001 {
    public static void main(String[] args) {
        A a = new A();
        String s = a.toString();
        a.val(1, 2, 3);
        System.out.println(a.toString());
    }
}