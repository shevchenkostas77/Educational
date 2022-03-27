package nested_classes;

//Еще один вариант использования local inner класса

public class LocalInnerClass2 {
    public static void main(String[] args) {
        class Addition implements Math3 {
            @Override
            public int doOperation(int a, int b) {
                return a + b;
            }
        }

        Addition addition = new Addition();
        System.out.println(addition.doOperation(5, 3));
        // Вывод:
        // 8
    }
}

interface Math3 {
    int doOperation(int a, int b);
}