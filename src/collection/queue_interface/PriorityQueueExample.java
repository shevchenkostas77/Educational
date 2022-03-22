package collection.queue_interface;

/*
PriorityQueue.
В обычной очереди, когда вызывается элемент, вызывается самый первый добавленный
элемент (правило FIFO). В PriorityQueue при вызове элемента вернется тот элемент,
который по приоритету самый высший. В PriorityQueue идет, если возможно, «натуральная»
сортировка (если в очереди String, Integer и тд. элементы), если же в очереди объекты,
классы которых мы самостоятельно написали, то необходимо реализовать интерфейс Comparable
или же при создании PriorityQueue в конструкторе указать Comparator. Так очередь будет
понимать какой элемент является более приоритетным.

PriorityQueue – это специальный вид очереди, в котором используется натуральная сортировка
или та, которую мы описываем с помощью Comparable или Comparator.
Таким образом используется тот элемент из очереди, приоритет которого выше.
*/

import java.util.Objects;
import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(4);
        priorityQueue.add(1);
        priorityQueue.add(7);
        priorityQueue.add(10);
        priorityQueue.add(8);

        System.out.println("PriorityQueue = " + priorityQueue);
//        Вывод:
//        PriorityQueue = [1, 4, 7, 10, 8]
        /*
        ВАЖНО!
        Когда выводим элементы на экран, то сортировка по приоритету отсутствует,
        только когда используем (с помощью методов remove(), peek() и тд.) приоритетность
        становится актуальна.
         */

//        Метод peek() показывает верхний элемент в очереди (первый стоящий элемент).
        System.out.println("First element in the PriorityQueue = " + priorityQueue.peek());
//        Вывод:
//        First element in the PriorityQueue = 1
        /*
        В PriorityQueue будет возвращаться элемент с наивысшим приоритетом.
        Сортировка идет от наименьшего элемента к большему. Наивысший приоритет
        будет у элемента, который стоит самый первый в очереди после сортировки.
        Сортировка на натуральной основе для данных элементов это 1,4,7,8,10, т.е.
        PriorityQueue не обращает внимания на очередность добавления элементов в очередь.
         */

//        Удаление происходит при помощи метода remove() (возвращает удаленный элемент).
        System.out.println("Element that was removed = " + priorityQueue.remove());
//        Вывод:
//        Element that was removed = 1
//        Удаляется элемент с наивысшим приоритетом

        Students st1 = new Students("Ivan", 5);
        Students st2 = new Students("Roman", 1);
        Students st3 = new Students("Igor", 2);
        Students st4 = new Students("Stepan", 3);
        Students st5 = new Students("Joseph", 4);

        PriorityQueue<Students> priorityQueueStudents = new PriorityQueue<>();
        priorityQueueStudents.add(st1);
        priorityQueueStudents.add(st2);
        priorityQueueStudents.add(st3);
        priorityQueueStudents.add(st4);
        priorityQueueStudents.add(st5);

        System.out.println("priorityQueueStudents = " + priorityQueueStudents);
//        Вывод:
//        priorityQueueStudents = [Student {name = 'Roman', course = 1},
//            Student {name = 'Stepan', course = 3},
//            Student {name = 'Igor', course = 2},
//            Student {name = 'Ivan', course = 5},
//            Student {name = 'Joseph', course = 4}]
//        Если бы при класс Students не имплементировал Comparable, то при добавлении в
//        PriorityQueue студентов выбрасывалось бы исключение - ClassCastException

//        Удаление происходит при помощи метода remove() (возвращает удаленный элемент).
        System.out.println("Element that was removed = " + priorityQueueStudents.remove());
//        Вывод:
//        Element that was removed = Student {name = 'Roman', course = 1}
        /*
        Т.к. идет сравнение через compareTo, а в данном случае метод compareTo сравнивает студентов
        по курсу, то элемент с наивысшим приоритетом будет студент на первом курсе.
        Если вызвать метод больше раз, чем количество элементов в коллекции, то
        выбросится исключение NoSuchElementException.
        Но есть подобный метод - poll(), который удаляет элементы, но в случае, если элементов
        уже больше нет в коллекции (очередь пуста), а метод вызван, то исключение уже
        не выбросится. Метод вернет null.
         */
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

    /*
    Методы hashCode() и equals() здесь не понадобятся.
    Но всегда будет хорошим тоном их переопределение, когда пишется новый класс.
     */
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
}