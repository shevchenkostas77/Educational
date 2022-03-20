package collection.map_interface;

import java.util.LinkedHashMap;
/*
LinkedHashMap.
LinkedHashMap является наследником HashMap.
Хранит информацию о порядке добавления элементов или порядке их использования (чего не делает HashMap).
Производительность методов немного ниже, чем у методов HashMap.
Используется данный класс тогда, когда необходимо использовать функционал HashMap, но также хранить данные о
порядке добавления или использования элементов.
*/
public class LinkedHashMapExample {
    public static void main(String[] args) {
        LinkedHashMap<Double, Student> lhm = new LinkedHashMap<>();
        Student st1 = new Student("Ivan", "Petrov", 1);
        Student st2 = new Student("Mariya", "Ivanova", 5);
        Student st3 = new Student("Petr", "Sidorov", 3);
        Student st4 = new Student("Igor", "Slemenko", 2);

//        lhm.put(5.8, st1);
//        lhm.put(6.4, st2);
//        lhm.put(7.2, st3);
//        lhm.put(7.5, st4);
        System.out.println(lhm);
//        В каком порядке добавили элементы, в таком порядке и будут содержаться элементы
//        Вывод:
//        {5.8=Students {name = 'Ivan', surname = 'Petrov', course = 1},
//            6.4=Students {name = 'Mariya', surname = 'Ivanova', course = 5},
//            7.2=Students {name = 'Petr', surname = 'Sidorov', course = 3},
//            7.5=Students {name = 'Igor', surname = 'Slemenko', course = 2}}

//        Меняем порядок добавления
        lhm.put(7.2, st3);
        lhm.put(7.5, st4);
        lhm.put(5.8, st1);
        lhm.put(6.4, st2);
        System.out.println(lhm);
//        В каком порядке добавили элементы, в таком порядке и будут содержаться элементы
//        Вывод:
//        {7.2=Students {name = 'Petr', surname = 'Sidorov', course = 3},
//            7.5=Students {name = 'Igor', surname = 'Slemenko', course = 2},
//            5.8=Students {name = 'Ivan', surname = 'Petrov', course = 1},
//            6.4=Students {name = 'Mariya', surname = 'Ivanova', course = 5}}
        /*
        Элементы LinkedHashMap помимо той информации, которую содержит элемент HashMap еще содержит
        ссылку на предыдущий добавленный элемент и последующий добавленный элемент.
        Такие методы как, remove, contais, работают тут точно так же.
         */

        /*
        В конструкторе LinkedHashMap можно указать три параметра:
        1. Capacity;
        2. Load factor (by default = 0.75f) ОБЯЗАТЕЛЬНО указать букву f;
        3. Access order - будет показывать как мы хотим сохранять порядок (добавление или
           порядок использования элементов).
           Если указать false - будет сохраняться порядок добавления элементов.
           Если указать true - порядок будет постоянно меняться в зависимости от того, какие элементы были
           использованы в каком порядке. К примеру, метод put() и метод get() мы можем считать за
           использование.
         */

//        Указываем access order = false
        LinkedHashMap<Double, Student> lhm2 =
                new LinkedHashMap<>(16, 0.75f, false);
        lhm2.put(7.2, st3);
        lhm2.put(7.5, st4);
        lhm2.put(5.8, st1);
        lhm2.put(6.4, st2);
        System.out.println("lhm2 = " + lhm2);

        System.out.println("Element with key \"5.8\" = " + lhm2.get(5.8));
        System.out.println("Element with key \"7.5\" = " + lhm2.get(7.5));

        System.out.println("lhm2 = " + lhm2);
//        Вывод:
//        lhm2 = {7.2=Students {name = 'Petr', surname = 'Sidorov', course = 3},
//                7.5=Students {name = 'Igor', surname = 'Slemenko', course = 2},
//                5.8=Students {name = 'Ivan', surname = 'Petrov', course = 1},
//                6.4=Students {name = 'Mariya', surname = 'Ivanova', course = 5}}
//        Element with key "5.8" = Students {name = 'Ivan', surname = 'Petrov', course = 1}
//        Element with key "7.5" = Students {name = 'Igor', surname = 'Slemenko', course = 2}
//        lhm2 = {7.2=Students {name = 'Petr', surname = 'Sidorov', course = 3},
//                7.5=Students {name = 'Igor', surname = 'Slemenko', course = 2},
//                5.8=Students {name = 'Ivan', surname = 'Petrov', course = 1},
//                6.4=Students {name = 'Mariya', surname = 'Ivanova', course = 5}}
//        Каков был порядок после добавления элементов, таков и остался после использования метода get()

        System.out.println();

//        Указываем access order = true
        LinkedHashMap<Double, Student> lhm3 =
                new LinkedHashMap<>(16, 0.75f, true);
        lhm3.put(7.2, st3);
        lhm3.put(7.5, st4);
        lhm3.put(5.8, st1);
        lhm3.put(6.4, st2);
        System.out.println("lhm3 = " + lhm3);

        System.out.println("Element with key \"6.4\" = " + lhm3.get(6.4));
        System.out.println("Element with key \"7.5\" = " + lhm3.get(7.5));
        System.out.println("Element with key \"7.2\" = " + lhm3.get(7.2));
        System.out.println("Element with key \"5.8\" = " + lhm3.get(5.8));

        System.out.println("lhm3 = " + lhm3);
//        Вывод:
//        lhm3 = {7.2=Students {name = 'Petr', surname = 'Sidorov', course = 3},
//                7.5=Students {name = 'Igor', surname = 'Slemenko', course = 2},
//                5.8=Students {name = 'Ivan', surname = 'Petrov', course = 1},
//                6.4=Students {name = 'Mariya', surname = 'Ivanova', course = 5}}
//        Element with key "6.4" = Students {name = 'Mariya', surname = 'Ivanova', course = 5}
//        Element with key "7.5" = Students {name = 'Igor', surname = 'Slemenko', course = 2}
//        Element with key "7.2" = Students {name = 'Petr', surname = 'Sidorov', course = 3}
//        Element with key "5.8" = Students {name = 'Ivan', surname = 'Petrov', course = 1}
//        lhm3 = {6.4=Students {name = 'Mariya', surname = 'Ivanova', course = 5},
//                7.5=Students {name = 'Igor', surname = 'Slemenko', course = 2},
//                7.2=Students {name = 'Petr', surname = 'Sidorov', course = 3},
//                5.8=Students {name = 'Ivan', surname = 'Petrov', course = 1}}
        /*
        В примере выше сначала был добавлен элемент с ключом "7.2" (можно считать за использование), далее
        добавлен элемент с ключом "7.5" (можно считать за использование), далее добавлен элемент
        с ключом "5.8" (можно считать за использование), далее добавлен элемент с ключом "6.4" (можно считать
        за использование).
        После добавления элементов происходит вывод на экран и можно увидеть следующую последовательность элементов
        коллекции:
        1. элемент с ключом "7.2"
        2. элемент с ключом "7.5"
        3. элемент с ключом "5.8"
        4. элемент с ключом "6.4"
        После использован метод get() для всех элементов коллекции, сначала для элемента с ключом "6.4", далее для
        элемента с ключом "7.5", далее для элемента с ключом "7.2", далее для элемента с ключом "5.8".
        После вывода на экран последовательность коллекции уже изменилась:
        1. элемент с ключом "6.4"
        2. элемент с ключом "7.5"
        3. элемент с ключом "7.2"
        4. элемент с ключом "5.8"
        Т.е. каждый раз, после использования како-го либо элемента он перемещается в конец коллекции.
         */
    }
}