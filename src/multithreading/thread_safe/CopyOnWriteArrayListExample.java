package multithreading.thread_safe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/*
Традиционные коллекции находятся в пакете java.util, concurrent коллекции находятся в пакете java.util.concurrent.
Когда происходит работа с многопоточностью, коллекцию ArrayList можно заменить на коллекцию
CopyOnWriteArrayList.
CopyOnWriteArrayList имплементирует интерфейс List.
CopyOnWriteArrayList следует использовать тогда, когда нужно добиться потокобезопасности для коллекции, к которой
будет применяться небольшое количество операций по изменению элементов и большое количество по их чтению.
Почему важно, чтобы операции вставка и удаление элементов были не частыми?
Потому, что коллекция CopyOnWriteArrayList - это коллекция типа ArrayList с алгоритмом CopyOnWrite. Что это
означает? При каждом изменении элементов данной коллекции, создается клон копия коллекции нового вида.

Код (ArrayList):

public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        list.add("Stas");
        list.add("Oleg");
        list.add("Sergey");
        list.add("Ivan");
        list.add("Igor");

        System.out.println(list);

        Runnable runnable1 = () -> {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(iterator.next()); // вывод всех элементов коллекции на экран
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.remove(4); // удаление элемента с индексом 4
            list.add("Elena"); // добавление нового элемента
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

        System.out.println(list);
    }
}

Запуск программы. Вывод:

[Stas, Oleg, Sergey, Ivan, Igor]
Stas
Exception in thread "Thread-0" java.util.ConcurrentModificationException
        at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1042)
        at java.base/java.util.ArrayList$Itr.next(ArrayList.java:996)
        at Main.lambda$main$0(Main.java:96)
        at java.base/java.lang.Thread.run(Thread.java:834)
[Stas, Oleg, Sergey, Ivan, Elena]

Выбрасывается ConcurrentModificationException. Чтобы избежать этого, нужно ArrayList изменить на
CopyOnWriteArrayList.

Код (CopyOnWriteArrayList):

public class Main {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        list.add("Stas");
        list.add("Oleg");
        list.add("Sergey");
        list.add("Ivan");
        list.add("Igor");

        System.out.println(list);

        Runnable runnable1 = () -> {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100); // сон 100 миллисекунд!
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(iterator.next()); // вывод всех элементов коллекции на экран
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(200); // сон 200 миллисекунд!
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.remove(4); // удаление элемента с индексом 4
            list.add("Elena"); // добавление нового элемента
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

        System.out.println(list);
    }
}

Запуск программы. Вывод:

[Stas, Oleg, Sergey, Ivan, Igor]
Stas
Oleg
Sergey
Ivan
Igor
[Stas, Oleg, Sergey, Ivan, Elena]


В самом начале работы программы создается коллекция типа ArrayList - CopyOnWriteArrayList. Заполняется элементами
и после операций заполнения имеет вид [Stas, Oleg, Sergey, Ivan, Igor]. Создаются два потока: thread1, который
который использует runnable1 и thread2, который использует runnable2. После запуска потоков, thread1 выводит на
экран по отдельности каждый элемент коллекции: сначала выводится элемент Stas, замем Oleg. На вывод этих двух
элементов коллекции заняло по времени 200 миллисекунд (на каждый по 100, т.к. поток спит 100 миллисекунд перед
каждой итерацией). Пока thread1 выводил первые два элемента коллекции на экран, thread2 в это время спал, после
пробуждения thread2 параллельно работе потоку thread1 начал удалять элемент с индексом 4, а далее добавил новый
элемент "Elena" в коллекцию CopyOnWriteArrayList, согласно инструкции (runnable2). НО, не смотря на параллельную
работу потоков thread1 и thread2, итератор в потоке thread1 продолжил выводить элементы коллекции на экран: Sergey,
Ivan, Igor. И лишь в самом конце программы на экране вывелась уже измененная коллекция -
[Stas, Oleg, Sergey, Ivan, Elena]
Почему только в самом конце данной программы можно увидеть измененную коллекцию, а не на этапе работы итератора?
Перед тем как, началась итерация по элементам коллекции (на момент создания итератора), вот на этой строке кода:

        Iterator<String> iterator = list.iterator();

итератору передалось состояние коллекции, т.е. итератору передалась коллекция с содержимым
[Stas, Oleg, Sergey, Ivan, Igor]. Итератор запоминает содержимое коллекции и уже по этому содержимому "пробегается".
Что происходит в параллельных потоках итератору не важно. Поток thread2, который использует инструкции runnable2,
изменяет коллекцию, а именно удаляет элемент коллекции на индексе 4 ((list.remove(4);)). После этого изменения
происходит создание новой копии коллекции, но уже с изменениями (удален элемент на 4-ом индексе), т.е. коллекция на
данном этапе выполнения программы имеет вид [Stas, Oleg, Sergey, Ivan]. Далее по инструкции поток thread2 добавляет
новый элемент в коллекцию (list.add("Elena");). И после этой операции происходит создание новой копии коллекции,
но уже с изменениями (добавлен новый элемент "Elena" в коллекцию). И только после того, как итератор закончил всю
свою работу, старая копия коллекции уже никому не нужна и программа начинает работать с самой новой копией коллекции.
В конце программы, на строке кода System.out.println(list);) выводится новая копия коллекции -
[Stas, Oleg, Sergey, Ivan, Elena]. Таким образом, процесс создания копий времязатратный, т.к. каждый раз создается
копия при действиях удаления и добавления. А если коллекция CopyOnWriteArrayList будет содержать много элементов,
то для создания копий понадобится какое-то время. Поэтому, эту коллекцию следует использовать тогда, когда не особо
много операций по изменению элементов, но много операций чтения.

Есть еще класс CopyOnWriteArraySet, который работает по похожему сценарию.
*/

public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        list.add("Stas");
        list.add("Oleg");
        list.add("Sergey");
        list.add("Ivan");
        list.add("Igor");

        System.out.println(list);

        Runnable runnable1 = () -> {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100); // сон 100 миллисекунд
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(iterator.next()); // вывод всех элементов коллекции на экран
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(200); // сон 200 миллисекунд
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.remove(4); // удаление элемента с индексом 4
            list.add("Elena"); // добавление нового элемента
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

        System.out.println(list);
    }
}