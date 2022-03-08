package generics;

public class ParameterizedClass {
    public static void main(String[] args) {
        Info<String> info = new Info<>("Hello");
        System.out.println(info);

        System.out.println("---------------------------");

        Pair<String, Integer> info2 = new Pair<>("The first parameter is string", 2);
        System.out.println(info2.getFirstValue());
        System.out.println(info2.getSecondValue());
        System.out.println(info2);

        System.out.println("---------------------------");

        Pair2<Integer> info3 = new Pair2<>(111, 222);
        System.out.println(info3.getFirstValue());
        System.out.println(info3.getSecondValue());
        System.out.println(info3);
    }
}

class Info<T> {
    private T value; // переменная не может быть статичной, т.к. статичная переменная пренадлежт всему классу;

    Info(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[This value: " + value + "]";
    }
}

class Pair<T1, T2> {          // с разными типами (2 type placeholder)
    private T1 firstValue;
    private T2 secondValue;

    public Pair(T1 firstValue, T2 secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public T1 getFirstValue() {
        return firstValue;
    }

    public T2 getSecondValue() {
        return secondValue;
    }

    @Override
    public String toString() {
        return "these are the values from the \"Pair\" class: \n" +
                "first value = " + firstValue + ";\n" +
                "second value = " + secondValue + ";";
    }
}

class Pair2<T> {              // с одинаковыми типами (1 type placeholder)
    private T firstValue;
    private T secondValue;

    public Pair2(T firstValue, T secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public T getFirstValue() {
        return firstValue;
    }

    public T getSecondValue() {
        return secondValue;
    }

    @Override
    public String toString() {
        return "these are the values from the \"Pair2\" class: \n" +
                "first value = " + firstValue + ";\n" +
                "second value = " + secondValue + ";";
    }
}