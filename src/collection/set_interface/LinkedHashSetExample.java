package collection.set_interface;

/*
LinkedHashSet.
LinkedHashSet является наследником HashSet. Хранит информацию о порядке
добавления элементов. Производительность методов немного ниже, чем
у методов HashSet.
В основе LinkedHashSet лежит HashMap. У элементов данного HashMap:
ключи - это элементы LinkedHashSet, значения –это константа-заглушка.
Из-за того, что в основе LinkedHashSet лежит не LinkedHashMap, а просто HashMap
в нем не реализована возможность запоминать последовательность элементов в
зависимости от их последнего использования (данная возможность была
у LinkedHashMap).

Использовать его необходимо тогда, когда необходим HashSet, но который запоминает
последовательность добавления элементов.
*/

import java.util.LinkedHashSet;

public class LinkedHashSetExample {
    public static void main(String[] args) {
        LinkedHashSet<Integer> lhs = new LinkedHashSet<>();
        lhs.add(5);
        lhs.add(3);
        lhs.add(1);
        lhs.add(8);
        lhs.add(10);

        System.out.println("LinkedHashSet = " + lhs);
        // Вывод:
        // LinkedHashSet = [5, 3, 1, 8, 10]
        // соблюдается последовательность добавления элементов

        // Удаление происходит при помощи метода remove()
        lhs.remove(8);
        System.out.println("LinkedHashSet = " + lhs);
        // Вывод:
        // До:
        // LinkedHashSet = [5, 3, 1, 8, 10]
        // После:
        // LinkedHashSet = [5, 3, 1, 10]

        // Узнать есть ли определенный элемент в LinkedHashSet можно при помощи метода contains()
        System.out.println("Is there an element \"8\" in the collection? " + lhs.contains(8));
        // Вывод:
        // Is there an element "8" in the collection? false
    }
}

