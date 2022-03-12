package collection;

import java.util.LinkedList;
/*
Почему существует множество разных коллекции?
Все дело в том, что задач, которых необходимо решить может быть огромное количество
и для решения задач могут лучше всего подходить какие-то определенные коллекции;

Строение LinkedList координально отличается от ArrayList
Использование его, а именно базовые методы (добавление, удаление элементов и тд.) такие же как и в ArrayList,
т.к. они имплементируют интерфейс List;

Элементы LinkedList - это звенья одной цепочки. В основе linkedList лежит не массив, как в
ArrayList, а цепочка. Эти элементы хранят определенные данные, а также ссылки на
предыдущий с следующий элементы;
*/

public class LinkedListExample {
    public static void main(String[] args) {
        Student st1 = new Student("Ivan", 3);
        Student st2 = new Student("Nikolay", 2);
        Student st3 = new Student("Elena", 1);
        Student st4 = new Student("Petr", 4);
        Student st5 = new Student("Mariya", 5);

        LinkedList<Student> studentLinkedList = new LinkedList<>();
        studentLinkedList.add(st1);
        studentLinkedList.add(st2);
        studentLinkedList.add(st3);
        studentLinkedList.add(st4);
        studentLinkedList.add(st5);
        /*
        В каком порядке были добавлены элементы, в таком они и будут храниться.
        После добавления элементов образуется цепочка:
        - Ivan имеет сслыку на null (предыдущий) и на Nikolay (следующий)
        - Nikolay имеет сслыку на элементт Ivan (предыдущий) и на Elena (следующий)
        - Elena имеет сслыку на элементт Nikolay (предыдущий) и на Petr (следующий)
        - Petr имеет сслыку на элементт Elena (предыдущий) и на Mariya (следующий)
        - Mariya имеет сслыку на элементт Petr (предыдущий) и на null (следующий)
        т.е. каждый элемент LinkedList в Java знает своих соседей и только их
        Если элементы ссылается на null (следующий), так LinkedList понимает, что это конец ("хвост" (tail)),
        Если элементы ссылается на null (предыдущий), так LinkedList понимает, что это начало ("голова" (head))
        */
        System.out.println(studentLinkedList);
        /*
        Если хотим "достучаться" до Elena, процесс будет происходить следующий:
        LinkedList понимает в какой области памяти находится голова списка
        и с помощью метода "get()" начинатся поиск вдоль списка, пока не находится нужный элемент.
        Doubly - поиск может вестись как с начала, так и с конца;
        Singly - у элементов есть ссылка только на послеующий элемент(ы);
        В Java реализован Doubly LinkedList;
        */
        studentLinkedList.get(2); // здесь индекс - это порядковый номер элемента

        Student st6 = new Student("Igor", 1);
        Student st7 = new Student("Jack", 3);
        studentLinkedList.add(st6);

        studentLinkedList.add(1, st7); // как и в ArrayList,добавляется на определенную позицию элемент
        System.out.println(studentLinkedList);

        studentLinkedList.remove(3); // при удалении элемента из цепочки, он не исчезает памяти,
        System.out.println(studentLinkedList); // а просто перестает быть составной частью данного списка;

        /*
        LinkedList имеет абсалюбно другое строение и все операции работают по другому.
        LinkedList лучше будет использовать (как правило):
        - Невелико количество операций получения элементов
        - Велико количесвто операций добавления и удаления элементов в начале списка
        */
    }
}

class Student {
    String name;
    int course;

    Student(String name, int course) {
        this.name = name;
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student {name = " + name +
                ", " + "course = " + course + "}";
    }
}