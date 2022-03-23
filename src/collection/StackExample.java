package collection;

/*
Stack -  наследник Vector. Устаревший synchonized класс.
Использует принцип LIFO (last in, first out). Принцип работы стека сравнивают с стопкой тарелок.
Не рекомендован для использования.
*/

import java.util.Stack;

public class StackExample {
    static void abc1() {
        System.out.println("abc1 starts");
        System.out.println("abc1 ends");
    }

    static void abc2() {
        System.out.println("abc2 starts");
        abc1();
        System.out.println("abc2 ends");
    }

    static void abc3() {
        System.out.println("abc3 starts");
        abc2();
        System.out.println("abc3 ends");
    }

    public static void main(String[] args) {
        System.out.println("main starts");
        abc3();
        System.out.println("main ends");

        Stack<String> stack = new Stack<>();

//        Добавелние элементов в stack
        stack.push("Ivan");
        stack.push("Misha");
        stack.push("Oleg");
        stack.push("Katya");
        System.out.println("stack: " + stack); // [Ivan, Misha, Oleg, Katya] Katya - на самом верху;

//        Удаляем элемент на самом верху
        System.out.println("pop: " + stack.pop());
        System.out.println("stack: " + stack);
//        Если применить метод pop() к пустому стеку, выкинется исключение EmptyStackException
//        Через метод isEmpty() исключаем этот момент;
//        while (!stack.isEmpty()) {
//            System.out.println(stack.pop());
//        }
//        С помощью метода peek() можно посмотреть какой элемент находится на самом верху стека;
        System.out.println("peek: " + stack.peek());
    }
}