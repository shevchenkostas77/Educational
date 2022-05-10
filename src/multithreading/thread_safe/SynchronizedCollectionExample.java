package multithreading.thread_safe;

import java.util.*;

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
коллекции достигают потокобезопасности благодаря тому, что используют Lock через synchronized блоки для всех методов.
Это означает, что если, допустим, несколько потоков захотят добавить элемент в ArrayList, а какие-то потоки захотят
удалить из этого ArrayList-a элементы, то в ArrayList будет доступ только по одному потоку. Сначала первый поток
поработает, сделает необходимые ему операции, допустим, добавит элемент и только после того, как первый поток закончит
все свои дела с коллекцией (снимет блокировку), только после этого второй поток сможет добавить свои элементы. Т.е.
первый поток когда заходит, он ставит lock и пока он не закончит работу, lock не снимается. Performance, т.е.
производительность, бывает не очень хорошая потому, что постоянно ставятся Lock-и, а это занимает время, многие потоки
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

//public class SynchronizedCollectionExample {
//    public static void main(String[] args) {
//        ArrayList<Integer> source = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            source.add(i);
//        }
//
//        // ArrayList<Integer> target = new ArrayList<>();
//        List<Integer> synchList =
//                Collections.synchronizedList(new ArrayList());
//        // синхронизация новосозданного ArrayList-а, можно использовать ранее созданный ArrayList
//        // переменная synchList будет ссылаться на синхронизированный ArrayList (ArrayList в обертке)
//
//        Runnable runnable = () -> {
//            synchList.addAll(source);
//        };
//
//        Thread thread1 = new Thread(runnable);
//        Thread thread2 = new Thread(runnable);
//
//        thread1.start();
//        thread2.start();
//
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(synchList);
//    }
//}

/*
Пример №2
        public class SynchronizedCollectionExample {
            public static void main(String[] args) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i = 0; i < 1_000; i++) {
                    arrayList.add(i); // arrayList заполняется 1_000 элементами (от 0 до 999)
                }

                Runnable runnable1 = () -> { // проходится по всем элементам и выводит на экран при помощи Iterator-a
                    Iterator<Integer> iterator = arrayList.iterator();
                    while (iterator.hasNext()) {
                        System.out.println(iterator.next());
                    }
                };

                Runnable runnable2 = () -> { // будет удалять один элемент с индексом 10 из коллекции
                    arrayList.remove(10);
                };

                Thread thread1 = new Thread(runnable1);
                Thread thread2 = new Thread(runnable2);

                thread1.start(); // потоки работают параллельно
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

                System.out.println(arrayList); // в самом конце работы программы увидим как выглядит коллекция arrayList

Запуск программы. Вывод:
0
Exception in thread "Thread-0" java.util.ConcurrentModificationException
	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
	at multithreading.thread_safe.SynchronizedCollectionExample.lambda$main$0(SynchronizedCollectionExample.java:179)
	at java.base/java.lang.Thread.run(Thread.java:831)
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, ..., 999]

Выбросился Exception потому, что с коллекцией arrayList попыталось работать два потока. В данном случае Iterator
пытается вывести на экран элементы и в этот момент удаляется какой-то элемент. Поэтому и выбросилось исключение -
ConcurrentModificationException. Вместо традиционного ArrayList будет использована его synchronized версия.

        public class SynchronizedCollectionExample {
            public static void main(String[] args) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i = 0; i < 1_000; i++) {
                    arrayList.add(i); // arrayList заполняется 1_000 элементами (от 0 до 999)
                }

                List<Integer> synchList = Collections.synchronizedList(arrayList);
                // используется уже готовый ArrayList (даже заполненный)

                Runnable runnable1 = () -> { // проходится по всем элементам и выводит на экран при помощи Iterator-a
                    Iterator<Integer> iterator = synchList.iterator();
                    while (iterator.hasNext()) {
                        System.out.println(iterator.next());
                    }
                };

                Runnable runnable2 = () -> { // будет удалять один элемент с индексом 10 из коллекции
                    synchList.remove(10);
                };

                Thread thread1 = new Thread(runnable1);
                Thread thread2 = new Thread(runnable2);

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

                System.out.println(synchList); // в самом конце работы программы увидим как выглядит коллекция arrayList
            }
        }

Запуск программы. Вывод:
0
Exception in thread "Thread-0" java.util.ConcurrentModificationException
	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
	at multithreading.thread_safe.SynchronizedCollectionExample.lambda$main$0(SynchronizedCollectionExample.java:282)
	at java.base/java.lang.Thread.run(Thread.java:831)
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, ..., 999]

Снова выбросилось исключение - ConcurrentModificationException, но может и не выбросится, т.к. любой поток может начать
свою работу первым. Несмотря на то, что ArrayList был обернут в synchronized обертку, все равно выбрасывается это
исключение. Для перебора элементов коллекции можно использовать итераторы, однако итераторы подвержены сбоям при
работе в многопоточном приложении. Так, например, если один поток изменяет содержимое коллекции (runnable2), а второй
поток обрабатывает коллекцию итератором (runnable1), то при вызове метода итератора hasNext или метода next будет
вызвано исключение ConcurrentModificationException. Чтобы обезопасить приложение от вызова исключения необходимо
целиком блокировать List на время перебора. Что это означает? Когда используется метод remove ставится Lock и в этот
момент никакой другой поток не может изменять ArrayList, добавлять новые элементы и тд. НО когда происходит простая
итерация по ArrayList-у никакого Lock-a не ставится, необходимо этот Lock прописать вручную чтобы все работало как
нужно, чтобы исключение ConcurrentModificationException не вызывалось. Как это сделать? Просто:

        Runnable runnable1 = () -> { // проходится по всем элементам и выводит на экран при помощи Iterator-a
            synchronized (synchList) {
                Iterator<Integer> iterator = synchList.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }
            }
        };

При помощи ключевого слова synchronized синхронизируется этот код:

        Iterator<Integer> iterator = synchList.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }

и когда идет перебор элементов коллекции и вывод их на экран, пока перебор и вывод не закончится метод remove не будет
вызван. Поток thread2, который использует runnable2 будет ждать окончание потока thread1, который использует runnable1.
Запуск программы. Вывод:
0
1
2
3
4
5
6
7
8
9
11
...
999
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, ..., 999]

Все элементы вывелись, после чего был удален элемент с индексом 10 и synchList вывелся на экран. Уже неожиданных
сюрпризов не будет, все будет работать по одному сценарию. Еще отметим такой момент, что в зависимости от того, какой
из потоков первый обработается, то в output-e можно увидит два случая:
0
1
2
3
4
5
6
7
8
9
11      <- тут 10-го элемента уже нет (сработал изначально поток thread2, который использует runnable2)
...
999
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, ..., 999]

или

0
1
2
3
4
5
6
7
8
9
10
11      <- тут 10-го элемента присутствует (сработал изначально поток thread1, который использует runnable1)
...
999
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, ..., 999] <- тут 10-го элемента уже нет

Почему до этого выбрасывалось исключение?
Допустим, работа происходит простым ArrayList (не синхронизированным) один поток просто пробегался по элементам, выводил
их на экран, а второй удалил какой-то элемент. Почему должен был вброситься Exception? Потому, что прохождение по
коллекции с помощью итератора во время того, как другой поток может изменить коллекцию это очень опасная ситуация.
Представим, что происходит проход по элементам с помощью итератора, а после изменения коллекции другим потоком в
коллекции вообще может не остаться ни одного элемента или элементов может стать больше. Как тогда быть итератору?
Чтобы таких неожиданных ситуаций не было выбрасывается исключение, т.к. итератор не знает, что ему делать.
 */

public class SynchronizedCollectionExample {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            arrayList.add(i); // arrayList заполняется 1_000 элементами (от 0 до 999)
        }

        List<Integer> synchList = Collections.synchronizedList(arrayList);
        // используется уже готовый ArrayList (даже заполненный)

        // пока ArrayList не заполнится элементами, создание runnable1 не происходит,
        // все происходит в одном потоке (main) - последовательно друг за другом
        Runnable runnable1 = () -> { // проходится по всем элементам и выводит на экран при помощи Iterator-a
            synchronized (synchList) { // this = synchList
                Iterator<Integer> iterator = synchList.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }
            }
        };

        Runnable runnable2 = () -> { // будет удалять один элемент с индексом 10 из коллекции
            synchList.remove(10); // this = synchList
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

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

        System.out.println(synchList); // в самом конце работы программы увидим как выглядит коллекция arrayList
    }
}