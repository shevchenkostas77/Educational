package collection.queue_interface;

/*
Deque and ArrayDeque.
Deque расшифровывается как Double Ended Queue (с англ. двунаправленная очередь).
Интерфейс Deque реализуется классами ArrayDeque и LinkedList.
В обычной очереди есть возможность добавлять элементы только в конец. В Deque есть возможность добавлять,
как в конец очереди, так в начало и так же использовать элементы (можно взять элемент,
который стоит первый в очереди к примеру, удалить после взять элемент, который стоит последним в очереди,
произвести с ним какие-то операции и тд.). При работе с Queue работает правило FIFO (first in, first out),
при работе с Deque можно работать как по правилу FIFO, так и по правилу LIFO (last in, first out).

Правило LIFO работает в устаревшем synchronized классе Stack, наследнике класса Vector (классы интерфейса List).
*/

import java.util.Deque;
import java.util.ArrayDeque;

public class ArrayDequeExample {
    public static void main(String[] args) {
        Deque<Integer> deque = new ArrayDeque<>();
        /*
        Добавление элементов:
           1. При помощи метода addFirst() можно добавлять элементы в Deque в начало (выбросится исключение,
              если есть ограничения по очереди и очередь заполнена);
           2. При помощи метода addLast() можно добавлять элементы в Deque в конец (выбросится исключение,
              если есть ограничения по очереди и очередь заполнена);
           3. При помощи метода offerFirst() можно добавлять элементы в Deque в начало (НЕ выбросится исключение,
              если есть ограничения по очереди и очередь заполнена);
           2. При помощи метода offerLast() можно добавлять элементы в Deque в конец (НЕ выбросится исключение,
              если есть ограничения по очереди и очередь заполнена);
         */
        deque.addFirst(3); // когда добавляем первый элемент нет разницы используется при этом метод addFirst() или
        // addLast(), т.к. элементов еще нет в очереди;
        deque.addFirst(5);
        deque.addLast(7);
        deque.offerFirst(1);
        deque.offerLast(8);
        System.out.println("ArrayDeque = " + deque);
//        Вывод:
//        ArrayDeque = [1, 5, 3, 7, 8]

        /*
        Удаление элементов:
           1. При помощи метода removeFirst() можно удалять элементы в Deque с начала (выбросится исключение,
              если вызвать этот метод у пустой очереди);
           2. При помощи метода removeLast() можно удалять элементы в Deque с конца (выбросится исключение,
              если вызвать этот метод у пустой очереди);
           3. При помощи метода pollFirst() можно удалять элементы в Deque с начала (НЕ выбросится исключение,
              если вызвать этот метод у пустой очереди, вернет null);
           4. При помощи метода pollLast() можно удалять элементы в Deque с конца (НЕ выбросится исключение,
              если вызвать этот метод у пустой очереди, вернет null);
        */
        System.out.println("removeFirst = " + deque.removeFirst());
        System.out.println("removeLast = " + deque.removeLast());
        System.out.println("pollFirst = " + deque.pollFirst());
        System.out.println("pollLast = " + deque.pollLast());
        System.out.println("Deque after removing elements  = " + deque);

        /*
        Возвращение элементов:
           1. Метод getFirst() возвращает первый элементы в Deque (выбросится исключение NoSuchElementException,
              если вызвать этот метод у пустой очереди);
           2. Метод getLast() возвращает последний элементы в Deque (выбросится исключение NoSuchElementException,
              если вызвать этот метод у пустой очереди);
           3. Метод peekFirst() возвращает первый элементы в Deque (при вызове его у пустой очереди исключение
              не выбросится, а вернет null);
           4. Метод peekLast() возвращает последний элементы в Deque (при вызове его у пустой очереди исключение
              не выбросится, а вернет null.);
        */
        deque.addFirst(5);
        deque.addLast(7);
        deque.offerFirst(1);
        deque.offerLast(8);
        System.out.println("Deque = " + deque);
        System.out.println("getFirst = " + deque.getFirst());
        System.out.println("getLast = " + deque.getLast());
        System.out.println("peekFirst = " + deque.peekFirst());
        System.out.println("peekLast = " + deque.peekLast());

//        У LinkedList тоже есть эти двойные методы, т.к. LinkedList тоже имплементирует интерфейс Deque

        /*
        В чем отличия LinkedList от ArrayList?

        Отличие в том, что LinkedList - это ещё и List, чем ArrayDeque не может похвастаться.
        На этом, пожалуй, все преимущества LinkedList заканчиваются, ведь LinkedList почти во всех случаях
        не эффективная коллекция. Если нужна реализация Deque, всегда необходимо использовать ArrayDeque.
        Вообще ArrayDeque - одна из самых быстрых коллекций, ведь в её основе resizable array, поэтому доступ
        к любому элементу вы получаете почти мгновенно. Есть и другие отличия. Например, ArrayDeque не может
        содержать в качестве элемента null, а List может. Так себе плюс, но всё же.
        */
    }
}