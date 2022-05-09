package multithreading.thread_safe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Когда необходима коллекция, с которой будут работать больше одного потока, то можно выбрать между двумя вариантами:
1. Synchronized collections - получаются из традиционных коллекций благодаря их обертыванию;
2. Concurrent collections - изначально созданы для работы с многопоточностью;

Synchronized collections - это те коллекции, которые получаются из not-synchronized коллекции. Например, из коллекции
ArrayList. Класс Collections (не интерфейс, а класс) предоставляет для работы с многопоточностью wrapper-ы, т.е.
обертки. Эти обертки будут оборачивать коллекции, такие как: ArrayList, HashMap и другие.

        Collections.synchronizedXYZ(коллекция)

При помощи класса Collections вызывается метод synchronized (это первая часть имени), вместо XYZ будут писаться такие
слова, как List (synchronizedList), Map (synchronizedMap), Set (synchronizedSet) и тд. Т.е. до этого классическая
коллекция не могла работать с многопоточностью, но обернувшись в обертку - такая возможность появляется. Synchronized
коллекции достигают потокобезопасности благодаря тому, что используют lock через synchronized блоки для всех методов.
Это означает, что если, допустим, несколько потоков захотят добавить элемент в ArrayList, а какие-то потоки захотят
удалить из этого ArrayList-a элементы, то в ArrayList будет доступ только по одному потоку. Сначала первый поток
поработает, сделает необходимые ему операции, допустим, добавит элемент и только после того, как первый поток закончит
все свои дела с коллекцией (снимет блокировку), только после этого второй поток сможет добавить свои элементы. Т.е.
первый поток когда заходит, он ставит lock и пока он не закончит работу, lock не снимается. Performance, т.е.
производительность бывает не очень хорошая потому, что постоянно ставятся Lock-и, а это занимает время, многие потоки
стоят в очереди и ждут. Т.е. производительность у Concurrent collections, как правило, намного выше, чем Synchronized
collections, ведь Concurrent collections были созданы конкретно для работы с многопоточностью. А Synchronized
collections получаются из обычных коллекций с помощью из доработки (их обертки).
 */

/*
В программе два разных потока (thread1 и thread2) будут проделывать одно и тоже одновременно, они будут в ArrayList
target добавлять все элементы из ArrayList-a source. В ArrayList-е source содержаться элементы: 0, 1, 2, 3, 4.
Код:
        public class SynchronizedCollectionExample {
            public static void main(String[] args) {
                ArrayList<Integer> source = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    source.add(i);
                }

                ArrayList<Integer> target = new ArrayList<>();
                Runnable runnable = () -> {
                    target.addAll(source);
                };

                Thread thread1 = new Thread(runnable);
                Thread thread2 = new Thread(runnable);

                thread1.start();
                thread2.start();

                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(target);
            }
        }

Запуск программы. Вывод:
[0, 1, 2, 3, 4, 0, 1, 2, 3, 4]

Вроде как в output-e все нормально. Запуск программы еще раз. Вывод:
[0, 1, 2, 3, 4]

Вывелся другой результат. Что это означает? Это означает, что в данном примере добавляя в ArrayList элементы используя
несколько потоков невозможно предугадать результат. Поэтому ArrayList нужно синхронизировать (создавать обертку):

        public class SynchronizedCollectionExample {
            public static void main(String[] args) {
                ArrayList<Integer> source = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    source.add(i);
                }

                // ArrayList<Integer> target = new ArrayList<>();
                List<Integer> synchList =
                        Collections.synchronizedList(new ArrayList());
                // синхронизация новосозданного ArrayList-а, можно использовать ранее созданный ArrayList
                // переменная synchList будет ссылаться на синхронизированный ArrayList (ArrayList в обертке)

                Runnable runnable = () -> {
                    synchList.addAll(source);
                };

                Thread thread1 = new Thread(runnable);
                Thread thread2 = new Thread(runnable);

                thread1.start();
                thread2.start();

                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(synchList);
            }
        }

Запуск программы. Вывод:
[0, 1, 2, 3, 4, 0, 1, 2, 3, 4]

Первый поток (thread1 или thread2, какой быстрее сработал) начинает добавлять элементы из source в synchList, но когда
он начинает это делать, то доступ для всех других потоков закрыт. Только после того, как этот поток закончит свою
работу, к работе приступит второй поток (thread1 или thread2, в зависимости от того, какой сработал первым). Поэтому
запустив эту программу бесчисленное количество раз, результат будет всегда одним и тем же. Теперь возможно предсказать
результат выполнения программы. Как было выше написано, тут у synchList в каждом методе, в том числе методе addAll стоит
Lock. Теперь с данным листом в один и тот же момент времени может работать только один поток. Даже, если два потока
должны выполнить разную работу, используя разные методы, пока один поток свою работу не закончит, второй не может
приступить к своей. Из-за этого производительность (performance) страдает.
 */

public class SynchronizedCollectionExample {
    public static void main(String[] args) {
        ArrayList<Integer> source = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            source.add(i);
        }

        // ArrayList<Integer> target = new ArrayList<>();
        List<Integer> synchList =
                Collections.synchronizedList(new ArrayList());
        // синхронизация новосозданного ArrayList-а, можно использовать ранее созданный ArrayList
        // переменная synchList будет ссылаться на синхронизированный ArrayList (ArrayList в обертке)

        Runnable runnable = () -> {
            synchList.addAll(source);
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(synchList);
    }
}