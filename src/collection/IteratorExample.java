package collection;

import java.util.ArrayList;
import java.util.Iterator;


public class IteratorExample {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Ivan");
        list.add("Georg");
        list.add("Mariya");
        list.add("Kolya");
        list.add("Olga");

        // Итератор - это повторитель
        Iterator<String> iterator = list.iterator();

        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // В отличии от for each loop с помощью Iterator можно удалять элемент коллекции
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        // Написанный выше код не сработает, т.к. итератор примененный к данной коллеции
        // уже закончил свою работу и курсор находится к конечной позиции.
        // Чтобы им опять воспользоваться, нужно создать новый объект: iterator = arrayList1.iterator();
        System.out.println(list); // Выведет на экран элементы list [Ivan, Georg, Mariya, Kolya, Olga]
    }
}

