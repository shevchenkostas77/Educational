package collection;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/*
ListIterator – он расширяет возможности простого Iterator,
и в нем можно работать не только с каждым следующим элементом, используя next,
но и с предыдущим, используя previous.
Данный пример показывает как работает ListIterator, эфективность здесь не учитывается;
*/
public class ListIteratorExample {
    public static void main(String[] args) {
        String s = "madam";
        List<Character> list = new LinkedList<>();

        for (char ch : s.toCharArray()) {
            list.add(ch);
        }
        System.out.println(list);

        char[] array = s.toCharArray(); // с масивом char ListIterator рабоать не будет;

        ListIterator<Character> listIterator = list.listIterator();
        ListIterator<Character> reverseListIterator = list.listIterator(list.size());
        boolean palindrome = true;
        while (listIterator.hasNext() && reverseListIterator.hasPrevious()) {
            if (listIterator.next() != reverseListIterator.previous()) {
                palindrome = false;
                break;
            }
        }
        if (palindrome) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not palindrome");
        }
    }
}

/*
ВАЖНЫЙ И ТОНКИЙ НЬАНС


Character c1 = 'm';
Character c2 = 'm';
System.out.println(c1==c2); //  ЭТО TRUE

Character c3 = 'м';
Character c4 = 'м';
System.out.println(c3==c4); //  А ЭТО FALSE
Вроде, делаем мы одно и то же, а результат получаем разный.

Все мы слышали про String pool и знаем, как он работает.
Оказывается, подобный pool есть не только у String, но например и у Byte, Short, Integer.
А также такой pool есть и для Character, и в нём могут храниться значения для Character,
если его числовое значение находится в интервале [0, 127];

Переведём Character в int и посмотрим на значения:

Character c1 = 'm';
Character c2 = 'м';
int i1 = (int)c1;
int i2 = (int)c2;
System.out.println(i1);  // 109
System.out.println(i2);  // 1084
Таким образом, если мы работаем с 2-мя Character объектами 'm', то создаётся 1 объект в pool-е,
на который ссылаются 2 переменные;

В случае же с Character объектом 'м', ни о каком pool-е речь не идёт.
*/
