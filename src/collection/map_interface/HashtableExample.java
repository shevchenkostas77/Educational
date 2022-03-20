package collection.map_interface;

import java.util.Hashtable;

/*
Hashtable.
Hashtable устаревший класс, который работает по тем же принципам, что и HashMap. В его основе лежит массив.
В отличие от HashMap является synchronized. По этой же причине его методы далеко не такие быстрые.

Ни ключ, ни значение не могут быть null, это его отличительная особенность. В HashMap могут быть.

Даже если нужна поддержка много поточности Hastable лучше не использовать. Следует использовать
ConcurrentHashMap.
*/

public class HashtableExample {
    public static void main(String[] args) {
        Hashtable<Double, Student> ht = new Hashtable<>();
        Student st1 = new Student("Ivan", "Petrov", 1);
        Student st2 = new Student("Mariya", "Ivanova", 5);
        Student st3 = new Student("Petr", "Sidorov", 3);
        Student st4 = new Student("Igor", "Slemenko", 2);

        ht.put(7.8, st1);
        ht.put(9.3, st2);
        ht.put(5.8, st3);
        ht.put(6.0, st4);
//        ht.put(null, st4); // NullPointerException
//        ht.put(6.5, null); // NullPointerException

        System.out.println(ht);
//        Вывод:
//        {6.0=Students {name = 'Igor', surname = 'Slemenko', course = 2},
//            5.8=Students {name = 'Petr', surname = 'Sidorov', course = 3},
//            9.3=Students {name = 'Mariya', surname = 'Ivanova', course = 5},
//            7.8=Students {name = 'Ivan', surname = 'Petrov', course = 1}}

    }
}