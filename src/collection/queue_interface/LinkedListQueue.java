package collection.queue_interface;

/*
Queue.

У интерфейса Queue есть наследник интерфейс Deque.
У Deque есть два класса, которые его имплементируют:
1.	LinkedList (LinkedList имплементирует не только интерфейс List), not synchronized;
2.	ArrayDeque, not synchronized;
Так же от Queue происходит интерфейс AbstractQueue, который имплементируется
классом PriorityQueue, not synchronized.

	         Queue
	    /		      \
      Deque		       AbstractQueue
   /	    \			        |
LinkedList	ArrayDeque	    PriorityQueue

Queue (переводится как "очередь") – это базовый интерфейс, который хранит
последовательность элементов для какой-либо обработки. Используется для того,
чтобы проводить операции с самым первым стоящим в очереди элементом.
Пример из жизни - простоя очередь, где соблюдается правило FIFO (first in first out),
т.е. первый кто пришел, первый и выйдет.
LinkedList является одним из самых распространенных классов, который используется,
когда нужна очередь.
*/

import java.util.LinkedList;
import java.util.Queue;

public class LinkedListQueue {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();

//        Метод add() добавляет элемент в конец очереди
        queue.add("Roman");
        queue.add("Ivan");
        queue.add("Stepan");
        queue.add("Anton");
        queue.add("Bogdan");
        System.out.println("Queue = " + queue);
//        Выаод:
//        Queue = [Roman, Ivan, Stepan, Anton, Bogdan]
//        Размер очереди неограничен
        /*
        Разница между методами add() и offer().

        Когда добавляется элемент в коллекцию с помощью метода add() в ограниченную и уже
        заполненную очередь, то выбросится исключение.
        Если добавляется элемент в коллекцию с помощью метода offer() в ограниченную и уже
        заполненную очередь, то исключение, в таком случае, не выбросится, но элемент
        добавлен не будет.

        */

//        Удаление происходит при помощи метода remove() (возвращает удаленный элемент).
        System.out.println("Queue before = " + queue);
        System.out.println("Element that was removed = " + queue.remove());
        System.out.println("Queue after = " + queue);
        /*
        Вывод:
        Queue before = [Roman, Ivan, Stepan, Anton, Bogdan]
        Element that was removed = Roman
        Queue after = [Ivan, Stepan, Anton, Bogdan]

        Удалился первый элемент, который стоит в очереди (FIFO)
        Если вызвать метод больше раз, чем количество элементов в коллекции, то
        выбросится исключение NoSuchElementException.
        Но есть подобный метод - poll(), который удаляет элементы, но в случае, если элементов
        уже больше нет в коллекции (очередь пуста), а метод вызван, то исключение уже
        не выбросится, а вернет null.

        В параметре метода remove можно указать, какой конкретно элемент необходимо удалить
        из очереди (местоположение в очереди его может быть любым).
        Но если часто использовать удаление из середины, смысла использовать Queue нет.
        Queue - это удаление из начала очереди, а добавление в конец.
         */
        System.out.println("Queue before = " + queue);
        System.out.println("Need to remove the element \"Anton\" " + queue.remove("Anton"));
        System.out.println("Queue after = " + queue);
//        Вывод:
//        Queue before = [Ivan, Stepan, Anton, Bogdan]
//        Need to remove the element "Anton" true
//        Queue after = [Ivan, Stepan, Bogdan]


//        Метод element() показывает верхний элемент в очереди (первый стоящий элемент)
        System.out.println("Current queue = " + queue);
        System.out.println("First element in the queue = " + queue.element());
        /*
        Вывод:
        Current queue = [Ivan, Stepan, Anton, Bogdan]
        First element in the queue = Ivan

        Если попытаться вызвать метод element() у пустой очереди выбросится
        исключение NoSuchElementException.
        Есть похожий метод - peek(), при вызове его у пустой очереди исключение
        не выбросится, а вернет null.
         */
    }
}