package collection;

import java.util.Vector;

/*
Vector – еще дин класс, который имплементирует интерфейс List.
Это устаревший synchronized класс. В своей основе содержит массив элементов Object.
Не рекомендован для использования.

ArrayList и LinkedList не синхронизированы, т.е. они по умолчанию не предназначены к примеру, для того,
что бы один человек добавлял элементы, а другой в это же самое время удалял. Но Vector позволяет это сделать,
он ставит блокировку второму пользователю до тех пор, пока первый не сделает все необходимые ему операции.
В следствии чего, методы Vector работают намного медленней, чем в ArrayList и LinkedList.
Так же Vector имеет много старых методов, которые не имеются в других коллекциях.
Vector по своей структуре очень похож с ArrayList.
*/

public class VectorExample {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();

//        Добавление элементов
        vector.add("Ivan");
        vector.add("Sergey");
        vector.add("Ruslan");
        vector.add("Georg");
        System.out.println(vector);

//        Выводим на экран первый элемент
        System.out.println(vector.firstElement());

//        Выводим на экран последний элемент
        System.out.println(vector.lastElement());

//        Удаление по элементу
        vector.remove(2);
        System.out.println(vector);

//        Выводим на экран элемент по индексу
        System.out.println(vector.get(1));
    }
}
