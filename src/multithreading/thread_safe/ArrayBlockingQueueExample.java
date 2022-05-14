package multithreading.thread_safe;

import java.util.concurrent.ArrayBlockingQueue;

/*
Традиционные коллекции находятся в пакете java.util, concurrent коллекции находятся в пакете java.util.concurrent.

ArrayBlickingQueue - потокобезопасная очередь с ограниченным размером (capacity restricted). ArrayBlockingQueue имеет
помимо лично своих методов (конечно же все методы интерфейса Queue) еще и такие методы, как put и take.
Метод put - при помощи этого метода добавляются элементы в ArrayBlockingQueue. Когда очередь заполнена, метод put будет
ждать, пока не освободится место в заполненной ограниченной очереди (работа с потоками). Выбрасывает исключение -
InterruptedException;
Метод take - при помощи этого метода берутся элементы из ArrayBlockingQueue (из начала очереди). Когда очередь
пустая, метод take будет ждать появления элементов в очереди (работа с потоками);

Когда создается ArrayBlockingQueue обязательно в правой части выражения (после оператора new) нужно указать размер:

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4); <- при создании обязательно указать размер

таким образом очередь может содержать только четыре элемента. Добавление элементов в очередь возможна при помощи методов
add и offer. Метод add выбрасывает Exception, если очередь уже полная, а метод offer, в таком же случае, не выбрасывает
Exception, но и элемент не добавляет.

Добавление элементов в ArrayBlockingQueue при помощи метода add:

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);

        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);

       System.out.println(queue);
    }
}

Запуск программы. Вывод:
[1, 2, 3, 4]

В начале очереди стоит 1, в конец очереди 4 (все как при добавлении). Если с помощью метода add попытаться добавить еще
один элемент в данный ArrayBlockingQueue, а места не будет, то выбросится Exception:

Exception in thread "main" java.lang.IllegalStateException: Queue full
        at java.base/java.util.AbstractQueue.add(AbstractQueue.java:98)
        at java.base/java.util.concurrent.ArrayBlockingQueue.add(ArrayBlockingQueue.java:326)

пишется в этом Exception, что очередь полная (Queue full). Если пятый элемент добавить при помощи метода offer, то
никакой Exception не выбросится, просто пятый элемент не будет добавлен в очередь:

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);

        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.offer(5);

       System.out.println(queue);
    }
}

Запуск программы. Вывод:
[1, 2, 3, 4]

Вот в чем разница между методами add и offer.

Обычно один или несколько потоков добавляют элементы в конец очереди, а другой или другие потоки забирают элементы
из начала очереди. В ArrayBlockingQueue это могут делать несколько потоков.

Задание-пример.
Пусть будет очередь, в которой можно содержать не более четырех элементов. Есть производитель, т.е. producer и он
производит числа. Есть потребитель, т.е. consumer и он потребляет эти числа. Производитель будет добавлять элементы
в конец очереди, а потребитель будет забирать и удалять элементы из начала очереди. Тут соблюдается правило FIFO
(first in, first out), как в обычной повседневной очереди. Работа будет происходить с разными числами: 3, 5, 7, 8
и тд. Производитель будет поставлять число каждую секунду, а потребитель будет забирать число каждые три секунды
(будет работать медленнее). Как будет это работать:

                            |_|_|_|_| <- очередь (максимум 4 элемента)

    Producer (производитель)                    Consumer (потребитель)
    каждую 1 сек. производит                    каждые 3 сек. потребляет


1 сек.: производитель поставил число - 1 и потребитель его сразу забрал, удалил
2 сек.: производитель поставил число - 2, потребитель ждет
3 сек.: производитель поставил еще число - 2 3, потребитель ждет
4 сек.: производитель поставил еще число - 3 4, потребитель забрал 2 из начала очереди
5 сек.: производитель поставил еще число - 3 4 5, потребитель ждет
6 сек.: производитель поставил еще число - 3 4 5 6, потребитель ждет
7 сек.: производитель хочет поставить еще число, НО очередь заполнена, он ждет. На 7 сек. потребитель забирает
        число 3 и только после этого производитель добавляет новое число - 4 5 6 7
8 сек.: производитель не может добавить еще один элемент (очередь уже полная)
9 сек.: производитель не может добавить еще один элемент (очередь уже полная)
Когда очередь уже полностью заполняется, то производитель полностью зависит от потребителя.

Код:

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> arrayBlockingQueue =
                new ArrayBlockingQueue<>(4);

        // Producer
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    arrayBlockingQueue.put(++i); // начало с единицы
                    System.out.println("Producer added: " + i);
                    Thread.sleep(1_000); // сон 1 сек.
                    System.out.println(arrayBlockingQueue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Consumer
        new Thread(() -> {
            while (true) {
                try {
                    Integer j = arrayBlockingQueue.take();
                    System.out.println("Consumer taked: " + j + " and started to sleep");
                    Thread.sleep(3_000); // сон 3 сек
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

Запуск программы. Вывод:

Producer added: 1
Consumer taked: 1 and started to sleep
[]
Producer added: 2
[2]
Producer added: 3
Consumer taked: 2 and started to sleep
[3]
Producer added: 4
[3, 4]
Producer added: 5
[3, 4, 5]
Producer added: 6
Consumer taked: 3 and started to sleep
[4, 5, 6]
Producer added: 7
[4, 5, 6, 7]
Consumer taked: 4 and started to sleep
Producer added: 8
[5, 6, 7, 8]
Consumer taked: 5 and started to sleep
Producer added: 9
[6, 7, 8, 9]
Consumer taked: 6 and started to sleep
Producer added: 10
[7, 8, 9, 10]
*/

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> arrayBlockingQueue =
                new ArrayBlockingQueue<>(4);

        // Producer
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    arrayBlockingQueue.put(++i); // начало с единицы
                    System.out.println("Producer added: " + i);
                    Thread.sleep(1_000); // сон 1 сек.
                    System.out.println(arrayBlockingQueue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Consumer
        new Thread(() -> {
            while (true) {
                try {
                    Integer j = arrayBlockingQueue.take();
                    System.out.println("Consumer taked: " + j + " and started to sleep");
                    Thread.sleep(3_000); // сон 3 сек
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}