package collection;

import java.util.ArrayList;

public class ArrayListMethods2 {
    public static void main(String[] args) {
        /*
        Добавляет
        addAll(ArrayList aL)  -> boolean
        addAll(int index, ArrayList aL)  -> boolean
        */
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Ivan");
        list1.add("Sergey");
        list1.add("Igor");
        list1.add("Ivan");
        System.out.println(list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("!!!");
        list2.add("???");
        System.out.println(list2);

        // list1.addAll(list2); // добавляется list2 в конец list1
        // System.out.println(list1);
        list1.addAll(1, list2); // добавляется list2 начиная с индекса 1, остальные элементы list1 смещаются вправо
        System.out.println(list1);

        /*
        Очищает
        clear()  -> void
        */
        // list1.clear();
        // System.out.println("List: " + list1);

        /*
        Возвращает
        indexOf(Object element)  -> int     возвращает -1, если обьект в ArrayList не найден.
                                            Если обьект найден, выведет позицию первого дубликата.
                                            Для сравнения используется метод Equals!!!
        lastIndexOf(Object element) -> int  возвращает позицию искомого обьекта с конца
        */
        System.out.println("indexOf = " + list1.indexOf("Ivan"));
        System.out.println("lastIndexOf = " + list1.lastIndexOf("Ivan"));

        /*
        Возвращает
        size() -> int возвращает размер
        Проверяет
        isEmpty -> boolean  возвращет true или false (пуст ли список?)
        */
        System.out.println("empty? " + list1.isEmpty());
        System.out.println("size = " + list1.size());

        /*
        Проверяет
        contais(Object element) -> boolean   Для сравнения используется метод Equals!!!
                                             Проверяет еслть ли такой обьект в ArrayList (true, false)
        */
        System.out.println("contains = " + list1.contains("Kolya"));
    }
}

