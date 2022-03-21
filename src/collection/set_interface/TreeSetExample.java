package collection.set_interface;

/*
TreeSet.

TreeSet хранит элементы в отсортированном по возрастанию порядке.
В основе TreeSet лежит TreeMap (красно-черное дерево). У элементов данного
TreeMap: ключи – это элементы TreeSet, значение – это константа-заглушка.
Правило, которое необходимо соблюдать:
При переопределения метода equals(), а его желательно всегда делать, если создается
какой-то класс и, если Object1.equals(Object2) возвращает true, то
Object1.compareTo(Object2) должен возвращать 0 (ноль).
*/

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetExample {
    public static void main(String[] args) {
        Set<Integer> treeSet = new TreeSet<>();

        treeSet.add(5);
        treeSet.add(8);
        treeSet.add(2);
        treeSet.add(1);
        treeSet.add(10);

        System.out.println("treeSet = " + treeSet);
        // Вывод:
        // treeSet = [1, 2, 5, 8, 10]

        // Попытка добавить null
        // treeSet.add(null);
        // System.out.println("treeSet with null = " + treeSet);
        // Вывод:
        // Exception java.lang.NullPointerException

        // Удаление происходит при помощи метода remove()
        treeSet.remove(2);
        System.out.println("treeSet = " + treeSet);
        // Вывод:
        // treeSet = [1, 5, 8, 10]

        // Узнать есть ли определенный элемент в множестве можно при помощи метода contains()
        System.out.println("Is there an element \"1\" in the collection? " + treeSet.contains(1));
        // Вывод:
        // Is there an element "1" in the collection? true

        TreeSet<Students> treeSets = new TreeSet<>();

        Students st1 = new Students("Ivan", 5);
        Students st2 = new Students("Roman", 1);
        Students st3 = new Students("Igor", 2);
        Students st4 = new Students("Stepan", 3);
        Students st5 = new Students("Joseph", 4);

        treeSets.add(st1);
        treeSets.add(st2);
        treeSets.add(st3);
        treeSets.add(st4);
        treeSets.add(st5);

        /*
        Попытка вывода коллекции treeSets без имплементации классом Students
        интерфейса Comparable или же интерфейса Comparator
        */
        // System.out.println("treeSets = " + treeSets);
        // Вывод:
        // ClassCastException
        /*
        Деревья работают используя compareTo, поэтому обязательно должны
        имплементировать интерфейс Comparable либо же при создании TreeSet в
        конструкторе необходимо указать Comparator
        В классе Students можем не переопределять, для правильной работы, методы
        hashCode() и equals(), т.к. работаем с коллекцией TreeSet (тоже самое в
        TreeMap).
        */

        /*
        После имплементации классом интерфейса Comparable и переопределения метода
        compareTo выводим результат на экран (так же переопределен метод toString)
        */
        System.out.println("treeSets = " + treeSets);
        // Вывод:
        // treeSets = [Student {name = 'Roman', course = 1},
        //         Student {name = 'Igor', course = 2},
        //         Student {name = 'Stepan', course = 3},
        //         Student {name = 'Joseph', course = 4},
        //         Student {name = 'Ivan', course = 5}]
        // treeSets содержит отсортированных студентов по курсу

        // Метод first() выводит первый элемент
        System.out.println("First element = " + treeSets.first());
        // Вывод:
        // First element = Student {name = 'Roman', course = 1}

        // Метод last() выводит последний элемент
        System.out.println("Last element = " + treeSets.first());
        // Вывод:
        // Last element = Student {name = 'Roman', course = 1}

        // Метод headSet() находит все элементы, которые "меньше" (до) переданному
        // в параметр методу значения.
        Students st6 = new Students("Oleg", 3);
        System.out.println("All students below 3rd year = " + treeSets.headSet(st6));
        // Вывод:
        // All students below 3rd year = [Student {name = 'Roman', course = 1},
        //         Student {name = 'Igor', course = 2}]

        // Метод tailSet() выведет все элементы равные или "больше" переданному в
        // параметр методу значения.
        System.out.println("All students from the 3rd year and above = " + treeSets.tailSet(st6));
        // Вывод:
        // All students from the 3rd year and above = [Student {name = 'Stepan', course = 3},
        //     Student {name = 'Joseph', course = 4},
        //     Student {name = 'Ivan', course = 5}]

        // Метод subSet() позволяет из TreeSet получить множество, элементы которого
        // находятся между двумя каким-то показателями
        Students st7 = new Students("Olga", 2);
        Students st8 = new Students("Ivan", 4);
        System.out.println("SubSet = " + treeSets.subSet(st7, st8));
        // Вывод:
        // SubSet = [Student {name = 'Igor', course = 2}, Student {name = 'Stepan', course = 3}]
        // В данном случае вывелось множество студентов, которые учатся на курсе ниже, чем 4-ый (st8)
        // и выше или же равный 2-ому курсу (st7). В итоге вывелось множество студентов  2 <= курс < 4.


        // ***
        Students st9 = new Students("Olga", 3);
        Students st10 = new Students("Olga", 3);
        System.out.println("st9 equals st10? " + st9.equals(st10)); // true
        System.out.println("st9 hashcode == st10 hashcode? " + (st9.hashCode() == st10.hashCode())); // true
        // Методы compareTo, equals, hashCode правильно переопределены в классе Students и гармонично
        // дополняют друг друга
    }
}

class Students implements Comparable<Students> {
    String name;
    int course;

    public Students(String name, int course) {
        this.name = name;
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student {name = '" +
                name + '\'' +
                ", course = " + course +
                '}';
    }

    @Override
    public int compareTo(Students other) {
        return this.course - other.course;
    }

    @Override
    public int hashCode() {
        return Objects.hash(course);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return this.name == students.name;
    }
    //***
    /*
    Переопределенный метод compareTo в классе Students проверяет только
    курс. Сравнение имен не происходит, поэтому в методе equals() сравнивать имена
    так же не нужно, чтобы соблюдалось следующее правило:
    При переопределении метода equals(), а его желательно всегда делать, если создается
    какой-то класс и, если Object1.equals(Object2) возвращает true, то
    Object1.compareTo(Object2) должен возвращать 0 (ноль).
    Соответственно при переопределении метода hashCode() не должны сравниваться имена
    (в данном случае).
    */
}