package generics;

import java.util.ArrayList;

public class ParameterizedMethod {
    public static void main(String[] args) {
        ArrayList<Integer> al1 = new ArrayList<>();
        al1.add(10);
        al1.add(20);
        al1.add(30);
        int a = GenMethod.getSecondElement(al1);
        System.out.println(a);

        ArrayList<String> al2 = new ArrayList<>();
        al2.add("abc");
        al2.add("def");
        al2.add("ghi");
        String s = GenMethod.getSecondElement(al2);
        System.out.println(s);

        ArrayList <Double> al3 = new ArrayList<>();
        al3.add(1.1);
        al3.add(2.2);
        al3.add(3.3);
        GenMethod method = new GenMethod();
        System.out.println(method.getSecondElement(al3));

    }
}

class GenMethod {
    public static <T> T getSecondElement(ArrayList<T> al) {   // "static" - здесь не обязательно, для удобства
        return al.get(1);
    }
}

class GenMethod2 <T> {   // если указываем здесь type placeholder то,
                         // в параметризованной методе можно этого не делать
    public T getSecondElement(ArrayList<T> al) {
        return al.get(1);
    }
}
