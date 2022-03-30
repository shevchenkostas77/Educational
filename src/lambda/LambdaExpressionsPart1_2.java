package lambda;

public class LambdaExpressionsPart1_2 {
    static void def(I i) {
        System.out.println(i.abc("Hello"));
    }
    public static void main(String[] args) {
        def((String str) -> {return str.length();});
    }
}

interface I {
    int abc(String s);
}

/*
Есть интерфейс I c методом "abc", единственным абстрактным методом, который принимает
в параметре String.
Есть метод "def" в параметре обьект типа I и выводится на экран результат
вызова метода "abc" с параметром "Hello". Что делает метод "abc" с параметром "Hello"
прописано в методе "main" при вызове самого метода "def".
Вызывается метод
def((String str) -> {return str.length();});
он в параметре использует обьект типа I. Вместо того, чтобы создавать класс, который
имплементирует интерфейс I, override метод "abc", сразу при вызове метода "def",
можно считать, происходит override метода "abc":
вместо String s - String str, Java понимает это соответсвие и ждет return type int.
*/