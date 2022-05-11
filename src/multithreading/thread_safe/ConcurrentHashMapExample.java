package multithreading.thread_safe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/*
Традиционные коллекции находятся в пакете java.util, concurrent коллекции находятся в пакете java.util.concurrent.
ConcurrentHashMap имплементирует интерфейс ConcurrentMap, который в свою очередь происходит от интерфейса Map. Т.е.
ConcurrentHashMap - это Map. В традиционных коллекциях, которые оборачиваются специальной оберткой для работы с
многопоточностью, как происходит работа? Есть два потока, если один поток, к примеру, проходится по всем элементам
при помощи итератора, а второй поток захочет добавить новый элемент, то вызовется исключение -
ConcurrentModificationException. Чтобы не выбрасывался этот Exception весь код итератора нужно поместить в synchronized
блок. Будет это работать следующим образом, когда один поток проходится по всем элементам "доработанной" коллекции, то
второй поток, в этот же момент времени, не может ничего сделать и вынужден ждать, пока первый поток не закончит все свои
дела. В ConcurrentHashMap все работает по-другому. Любое количество потоков может читать информацию без каких либо
Lock-ов. Так же ConcurrentHashMap делит множество элементов, которые он хранит, на сегменты. При дефолтном создании
HashMap-a создается массив из 16 элементов (bucket-ов). ConcurrentHashMap делит множество элементов (bucket-ов), которые
он хранит, на сегменты. Сегмент - это каждый элемент массива (bucket), который сидит в основе ConcurrentHashMap.
Каждый поток может работать независимо друг от друга с любым сегментом массива, НО не с одним и тем же в одно и то же
время. Т.е. если происходит изменение элемента в третьем сегменте (bucket-те) к примеру, происходит удаление или
наоборот добавление, или изменение элемента, то этот сегмент блокируется (ставится Lock на этот bucket) и пока поток,
который занял этот сегмент не выполнит необходимую ему работу, то другой поток в данном сегменте, в третьем, ничего
изменить не сможет, НО другие потоки могут работать с другими свободными сегментами ConcurrentHashMap. Такая блокировка
называется segment locking или basket locking. В данном случае размер массива равен 16, т.е. 16 сегментов. Если на
сегменте, снова для примера это будет третий сегмент, стоит lock, то вторым потоком невозможно работать в этом сегменте,
зато второй потоком (если всего два потока работают с ConcurrentHashMap) может поработать в любом другом сегменте в
данный момент времени. Все это происходит concurrent, т.е. параллельно. Таким образом, если длина ConcurrentHashMap
составляет 16 bucket-ов, то 16 потоков могут одновременно работать с элементами ConcurrentHashMap, при условии,
что эти элементы находятся в разных сегментах. Таким образом ConcurrentHashMap из-за его сегментирования можно
рассматривать как группу HashMap-ов. В данном примере - 16 HashMap-ов. Естественно, благодаря всем этим возможностям
ConcurrentHashMap работает намного эффективнее, чем synchronizedMap.

В ConcurrentHashMap любое количество потоков может читать элементы не блокируя его.
В ConcurrentHashMap, благодаря его сегментированию, при изменении какого-либо элемента блокируется только bucket,
в котором находится данный элемент. А если бы использовался synchronizedHashMap, если бы какой-то поток изменял любой
элемент в любом bucket-е, то весь synchronizedHashMap был бы заблокирован. И уже второй поток, до окончания первого, не
смог что-либо сделать.

        |_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_| <- 16 элементов/backet-ов/сегментов

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Stas");
        map.put(2, "Oleg");
        map.put(3, "Sergey");
        map.put(4, "Ivan");
        map.put(5, "Igor");
        System.out.println(map);

        Runnable runnable1 = () -> {
            Iterator<Integer> iterator = map.keySet().iterator();
            У Map нет метода iterator. Поэтому, чтобы работать с итератором нужно из Map-a получить множество его ключей
            с помощью метод keySet, а у Set есть итератор. Ключи в данном случае Integer, поэтому итератор будет с
            этим типом данных работать (Iterator<Integer>).

            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer i = iterator.next(); // получаем ключ элемента
                System.out.println(i + " : " + map.get(i));
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(6, "Elena");
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

        System.out.println(map);
    }
}
В коде выше создан обычный HashMap, заполнен элементами и выводится на экран HashMap. Потом запускается два потока. Один
поток будет итерироваться и выводить на экран все элементы HashMap, т.е. все пары ключ-значение. Перед тем как вывести
на экран очередной элемент поток будет 100 миллисекунд спать. Второй поток сначала спит 300 миллисекунд, потом добавляет
в HashMap новый элемент.
Запуск программы. Вывод:
{1=Stas, 2=Oleg, 3=Sergey, 4=Ivan, 5=Igor}
1 : Stas
2 : Oleg
Exception in thread "Thread-0" java.util.ConcurrentModificationException
	at java.base/java.util.HashMap$HashIterator.nextNode(HashMap.java:1584)
	at java.base/java.util.HashMap$KeyIterator.next(HashMap.java:1607)
	at multithreading.thread_safe.ConcurrentHashMapExample.lambda$main$0(ConcurrentHashMapExample.java:131)
	at java.base/java.lang.Thread.run(Thread.java:831)
{1=Stas, 2=Oleg, 3=Sergey, 4=Ivan, 5=Igor, 6=Elena}

Выбрасывается Exception - ConcurrentModificationException. Два потока работают одновременно, первый поток производит
итерацию, выводит на экран элементы. Два элемента были выведены на экран и когда должен был быть выведен третий элемент
на экран, второй поток поспав 300 миллисекунд добавляет элемент в HashMap. А итератор такие действия не прощает и
поэтому выбрасывает ConcurrentModificationException. Что бы исправить ситуацию, нужно использовать ConcurrentHashMap,
в данном случае нужно два раза дописать слово Concurrent к HashMap. Больше в написании HashMap и ConcurrentHashMap не
отличаются.
Запуск программы. Вывод:
{1=Stas, 2=Oleg, 3=Sergey, 4=Ivan, 5=Igor}
1 : Stas
2 : Oleg
3 : Sergey
4 : Ivan
5 : Igor
6 : Elena
{1=Stas, 2=Oleg, 3=Sergey, 4=Ivan, 5=Igor, 6=Elena}

Первый поток не успел вывести все элементы (вывел два или три), а второй поток успел добавить шестой элемент
(6 : Elena). Второй Thread спит 300 миллисекунд, за 300 миллисекунд первый Thread может вывести максимум три элемента
данного map. Допустим, три элемента было выведено на экран и на этой стадии в ConcurrentHashMap был добавлен новый
элемент. В итоге первый поток его тоже вывел на экран. Во время добавления нового элемента был заблокирован один,
только один, сегмент HashMap-a. А при работе первого потока, который использует runnable1, во время итерации вообще
никакой блокировки не было.

В ConcurrentHashMap ни key, ни value не могут быть null!!! Выбросится исключение - NullPointerException.
 */

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "Stas");
        map.put(2, "Oleg");
        map.put(3, "Sergey");
        map.put(4, "Ivan");
        map.put(5, "Igor");
        System.out.println(map);

        Runnable runnable1 = () -> {
            Iterator<Integer> iterator = map.keySet().iterator();
            /*
            У Map нет метода iterator. Поэтому, чтобы работать с итератором нужно из Map-a получить множество его ключей
            с помощью метод keySet, а у Set есть итератор. Ключи в данном случае Integer, поэтому итератор будет с
            этим типом данных работать (Iterator<Integer>).
            */
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer i = iterator.next(); // получаем ключ элемента
                System.out.println(i + " : " + map.get(i));
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(6, "Elena");
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

        System.out.println(map);
    }
}