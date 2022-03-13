package collection;

import java.util.Vector;

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
