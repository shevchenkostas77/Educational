package collection.map_interface;

import java.util.Comparator;
import java.util.TreeMap;

/*
TreeMap.
Tree переводится как дерево.
Элементами TreeMap являются пары ключ/значение.
В TreeMap элементы хранятся в отсортированном по возрастанию порядке.
Сортировка идет по ключу, не по значению.
Основная цель использования TreeMap это нахождение каких либо диапазонов, отрезков (ranges)
*/

public class TreeMapExample {
    public static void main(String[] args) {
        TreeMap<Double, Student> treeMap = new TreeMap<>();
        Student st1 = new Student("Ivan", "Sidorov", 3);
        Student st2 = new Student("Petr", "Ivanov", 1);
        Student st3 = new Student("Mariya", "Petrova", 4);
        Student st4 = new Student("Mariya", "Ivanova", 2);
        Student st5 = new Student("Sergey", "Petrov", 1);
        Student st6 = new Student("Mariya", "Petrova", 5);
        Student st7 = new Student("Ivan", "Ivanov", 4);

        treeMap.put(8.1, st7);
        treeMap.put(5.8, st1);
        treeMap.put(9.2, st2);
        treeMap.put(8.3, st3);
        treeMap.put(6.9, st6);
        treeMap.put(7.8, st5);
        treeMap.put(6.8, st4);

        System.out.println(treeMap);

        /*
        В основе TreeMap лежит красно-черное дерево. Это позволяет методам работать очень быстро,
        но не быстрее, чем методы HashMap.
        */

        // Значения в treeMap могут быть не уникальными
        Student st8 = new Student("Igor", "Stepanov", 1);
        Student st9 = new Student("Igor", "Stepanov", 1);
        treeMap.put(3.2, st8);
        treeMap.put(3.3, st9);
        System.out.println(treeMap);
//        Вывод:
//        {3.2=Students {name = 'Igor', surname = 'Stepanov', course = 1},
//            3.3=Students {name = 'Igor', surname = 'Stepanov', course = 1}, ... }

//        Ключи в TreeMap должны быть уникальными
        Student st10 = new Student("Albert", "Einstein", 4);
        Student st11  = new Student("Reberto", "Bravo", 2);
        treeMap.put(1.1, st10);
        treeMap.put(1.1, st11);
        System.out.println(treeMap);
//        Вывод:
//        {1.1=Students {name = 'Reberto', surname = 'Bravo', course = 2},
//            3.2=Students {name = 'Igor', surname = 'Stepanov', course = 1}, ... }

//        Получение элемента с помощью метода get()
        System.out.println("Element with key \"7.8\" = " + treeMap.get(7.8)); // в параметрах указываем ключ
//        Вывод:
//        Element with key "7.8" = Students {name = 'Sergey', surname = 'Petrov', course = 1}

//        Удаление элемента с помощью метода remove(). В параметрах указываем ключ
        System.out.println("Removed element by key \"1.1\" = " + treeMap.remove(1.1));
//        Вывод:
//        Removed element by key "1.1" = Students {name = 'Reberto', surname = 'Bravo', course = 2}

//        С помощью метода descendingMap() можно "развернуть" treeMap в обратную строну
//        т.е. элементы будут выводиться не по возрастанию, а по убыванию. Сортировка будет происходить по
//        ключам;
        System.out.println("Descending: " + treeMap.descendingMap());
//        Вывод:
//        Descending: {9.2=Students {name = 'Petr', surname = 'Ivanov', course = 1},
//            8.3=Students {name = 'Mariya', surname = 'Petrova', course = 4}, ... }

//        Основная цель использования TreeMap это нахождение каких либо диапазонов, отрезков (ranges)
//        Метод tailMap() находит отрезок коллекции (range), чьи ключи "больше" и равны переданному
//        в параметр методу значение.
        System.out.println("All elements from the key \"8.3\" and above = " + treeMap.tailMap(8.3));
        // указывается в параметре ключ ("от" включительно)
//        Вывод:
//        All elements from the key "8.3" and above = {8.3=Students {name = 'Mariya', surname = 'Petrova', course = 4},
//            9.2=Students {name = 'Petr', surname = 'Ivanov', course = 1}}

//        Метод headMap() находит отрезок коллекции (range), чьи ключи "меньше" переданному
//        в параметр методу значение.
        System.out.println("All elements less than the key \"3.3\" = " + treeMap.headMap(3.3));
//        Вывод:
//        All elements less than the key "3.3" = {3.2=Students {name = 'Igor', surname = 'Stepanov', course = 1}}

//        Метод lastEntry() возвращает самый последний элемент в коллекции
        System.out.println("Last element = " + treeMap.lastEntry());
//        Вывод:
//        Last element = 9.2=Students {name = 'Petr', surname = 'Ivanov', course = 1}

//        Метод firstEntry() возвращает самый первый элемент в коллекции
        System.out.println("First element = " + treeMap.firstEntry());
//        Вывод:
//        First element = 3.2=Students {name = 'Igor', surname = 'Stepanov', course = 1}

        TreeMap<Student, Double> studentDoubleTreeMap = new TreeMap<>();
        studentDoubleTreeMap.put(st1, 5.8);
        studentDoubleTreeMap.put(st7, 9.1);
        studentDoubleTreeMap.put(st2, 6.4);
        studentDoubleTreeMap.put(st4, 7.5);
        studentDoubleTreeMap.put(st3, 7.2);
        studentDoubleTreeMap.put(st6, 8.2);

        /*
        Выбросилось исключение ClassCastException. Далее говорится,
        что класс Student не имплементирует интерфейс Comparable.
        Это означает, что мы не можем использовать в качестве ключа класс не имплементирующий интерфейс
        Comparable.
        TreeMap хранит элементы в отсортированном порядке, за основу беря ключи для сортировки.
        У нас в качестве ключа выступает класс Student и java не может понять какой же из студентов больше.
        Когда стоял в качестве ключа Double, в Double интерфейс Comparable реализован, как и у Integer, как
        и у String и у многих других классах, поэтому работая с коллекцией treeMap сложностей не возникало.
        Все сравнение в TreeMap, поиск с помощью метода get() и тд. идет с помощью сравнения.
        Реализую в классе Student интерфейс Comparable<Student>.
        Имплементировал интерфейс Comparable<Student> и перезаписал метод:

        @Override
        public int compareTo(Student anotherStudent) {
            int result = this.name.compareTo(anotherStudent.name);
            if (result == 0) {
                result = this.surname.compareTo(anotherStudent.surname);
            }
            if (result == 0) {
                result = this.course - anotherStudent.course;
            }
            return result;
        }
         */
        System.out.println("studentDoubleTreeMap = " + studentDoubleTreeMap);
//        Вывод:
//        studentDoubleTreeMap = {Students {name = 'Ivan', surname = 'Ivanov', course = 3}=9.1,
//                Students {name = 'Ivan', surname = 'Ivanov', course = 4}=9.1,
//                Students {name = 'Ivan', surname = 'Sidorov', course = 3}=5.8,
//                Students {name = 'Mariya', surname = 'Ivanova', course = 2}=7.5,
//                Students {name = 'Mariya', surname = 'Petrova', course = 4}=7.2,
//                Students {name = 'Mariya', surname = 'Petrova', course = 5}=8.2,
//                Students {name = 'Petr', surname = 'Ivanov', course = 1}=6.4}
        /*
        Если класс не реализовывает интерфейс Comparable есть еще один вариант:
        при создании TreeMap в его параметре использовать реализацию компаратора
        TreeMap<Student, Double> studentDoubleTreeMap2 = new TreeMap<>(new Comparator<Student>() {
            @Override
            public int compare(Student student, Student anotherStudent) {
                int result = student.getName().compareTo(anotherStudent.getName());
                if (result == 0) {
                    result = student.getSurname().compareTo(anotherStudent.getSurname());
                }
                if (result == 0) {
                    result = student.getCourse() - anotherStudent.getCourse();
                }
                return result;
            }
        });
        */

        /*
        При использовании метода containsKey() в TreeMap необязательно переопределять методы
        equals() и hashCode() (в HashMap важно и нужно переопределение этих методов).
        Поиски, добавления и другие операции происходят при помощи compareTo() метода,
        но это правило не относиться к значениям элементов TreeMap. Например, при работе метода
        containsValue() метод equals() будет использоваться, а метод hashCode() не используется вообще.
        НО хорошим тоном будет всегда их переопределять.
         */

        /*
        Red-black tree (красно-черное дерево) - один из видов самобалансирующихся двоичных деревьев поиска.

        Пример простого дерева:
                    13 - корень дерева
             8                  29
         1   3   5              15
         |   |   |         14   20   25
           листья           |   |    |
                              листья
         Вершины не имеющие потомков называются "листьями"

         Пример двоичного дерева:
         Двоичные деревья - деревья, когда у каждой вершины может быть только два потомка.

                     50
               10         80
             3   15     75  100

         У 50 - 10 и 80 (два потомка);
         У 10 - 3 и 15 (два потомка);
         У 80 - 75 и 100 (два потомка);
         Один или, максимум, два потомка могут быть у двоичного дерева;
         Чтобы поиск шел быстрее слева от вершины (от 50) меньше число, чем на вершине,
         справа больше число, чем на вершине и так повторяется каждый раз вверх по дереву.
         У двоичных деревьев может отсутствовать баланс.
         Если никакого баланса нет, выглядит это следующим образом ("Без баланса"):
                    5.8
                        6.4
                            7.2
                                7.5
                                    7.9
                                        8.2
                                            9.1
         Без баланса все идет в одном направлении. У каждой из вершин нет левой стороны. Если
         для поиска мы будем использовать это дерево, поиск будет идти очень долго. Чтобы найти в таком
         дереве значение "9.1" нужно пройти по всем элементам.

         Красно-черное дерево является не только двоичным, но и самобалансирующимся. Даже если мы
         будем добавлять элементы в последовательном порядке, как из примера выше "Без баланса" э
         то дерево самосбалансируется. На каком-то этапе дерево поймет, что все идет в одну сторону и
         нужно перегруппироваться. В итоге станет:

                    7.5
              6.4           8.2
           5.8   7.2     7.9   9.1

         Вот это дерево переставит свои элементы с помощью определенного алгоритма и получится
         сбалансированное двоичное дерево. Может быть разница в количество шагов в данных примерах 7 и 3
         невелика, но работая с очень большим количеством элементов разница будет колоссальная. С помощью
         красно-черного дерева работа таких методов как, contensKey(), get(), put(), remove() обрабатываются
         за O(log n) (идет бинарный поиск). Да, это жуде чем у HashMap, где перформанс был О(1), но все равно
         очень быстро!

         TreeMap не является синхронизированной коллекцией, поэтому если имеются множество потоков
         работающих с данной коллекцией, то коллекцию нужно синхронизировать самому вручную.
         */
    }
}