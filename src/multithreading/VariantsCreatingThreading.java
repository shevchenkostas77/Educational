package multithreading;

/*
Есть класс, который называется Thread (с англ. поток). Чтобы создать новый поток можно наследовать этот
класс. В методе run (необходимо переопределить (override) этот метод) закладываться вся логика, которую
должен выполнять новый поток. К примеру, класс MyThreadExtendsThread_1 будет наследовать (extends) класс
Thread и в методе run будет заложена логика вывода чисел на экран от 1 до 1_000. Второй класс
MyThreadExtendsThread_2 тоже наследует класс Thread и в его методе run заложена логика вывода чисел на
экран от 1_000 до 1 (в перевернутом порядке).

        class MyThreadExtendsThread_1 extends Thread {
            @Override
            public void run() {
                for (int i = 1; i <= 1_000; i++) {
                    System.out.println(i);
                }
            }
        }

        class MyThreadExtendsThread_2 extends Thread {
            @Override
            public void run() {
                for (int i = 1_000; i >= 1; i--) {
                    System.out.println(i);
                }
            }
        }

В методе main создаются два объекта MyThreadExtendsThread_1 и MyThreadExtendsThread_2.

        MyThreadExtendsThread_1 myThread1 = new MyThreadExtendsThread_1();
        MyThreadExtendsThread_2 myThread2 = new MyThreadExtendsThread_2();

Запускать поток необходимо при помощи метода start.

        myThread1.start();
        myThread2.start();

После запуска программы поток myThread1 и поток myThread2 срабатывают
параллельно, т.е. в одно и то же время. Между двумя этими потоками нет никакой синхронизации, они работают
независимо друг от друга и какой из них начнет выполнять свою работу первым или закончит выполнять работу
первым - ЗНАТЬ ЭТОГО НЕЛЬЗЯ! Этот момент программист не в состоянии контролировать. Если запустить эту
программу несколько раз, то output будет совершенно разным. На момент написания этого текста в первом
output-e сначала выполнялся второй поток myThread2 до 803, потом стал выводить первый поток myThread1.
Запуская второй раз программу снова начал выполнять свою работу второй поток myThread2, дошел до числа
729 и стал выводиться первый поток myThread1. Каждый раз запуская данную программу будет всегда выводится
новый разный результат. К примеру, даже вот такой:
                1
                1000
                999
                998
                2
                997
                3
                996
                4
                ...
                1000
Какой запуститься первым, какой вторым, в данном случае, вызвав их так подряд:

        myThread1.start();
        myThread2.start();

невозможно узнать. Они не синхронизованы. Если бы была синхронизация между потоками, т.е. они бы зависели
как-то друг от друга, то результат был бы другим. К примеру, работы могла бы быть: выводилось бы одно число
из первого потока, потом бы выводилось число из второго потока и тд. Это если бы была синхронизация, то
возможно, так и работало.
Но в данном случае потоки не синхронизированные и работают в беспорядочном режиме. Они работают одновременно
и тот поток, который захватывает консоль, тот и будет выводить свои числа. На самом деле, в этом примере,
работают не два потока, а три. Потому, что поток main, который начинает свою работу в main методе он создается
автоматически. Запустился поток main, внутри которого были отпачкованы или отсоединены другие потоки, а
именно потоки myThread1 и myThread2.

                поток main
                    |
            работает поток main
                    |
                    |
                    отпочковался первый поток
                    ---->
                    |                               первый и второй потоки работают параллельно
                    отпочковался второй поток
                    ---->
                    |
                    |
                поток main продолжает свою работу
                    |
                поток main заканчивает свою работу,
                но к этому времени эти два потока могут еще не закончить свою работу и тогда
                программа не останавливается несмотря на то, что поток main завершил свою работу.
                Программа ждет завершения работы всех созданных потоков.
          _______________________

Если не переопределять (override) метод run, к примеру, не сделать этого в классах MyThreadExtendsThread_1,
MyThreadExtendsThread_2 и запустить этот код, то ничего не произойдет. Метод run в классе Thread ничего
не делает, он пустой:

        @Override
        public void run() {
            if (target != null) {
                target.run();
            }
        }

Так же стоит обратить внимание на то, что в классах MyThreadExtendsThread_1 и MyThreadExtendsThread_2
переопределяется метод run, а запускаются потоки при помощи метода start. Метод run вызывается автоматически
JVM после вызова метода start.

Можно запустить два потока MyThread1. Для этого создаются два объекта MyThread1 и на обоих вызывается
метод start.

        MyThreadExtendsThread_1 myThread1_1 = new MyThread1();
        MyThreadExtendsThread_1 myThread1_2 = new MyThread1();
        myThread1_1.start();
        myThread1_2.start();

При запуске программы два потока будут выполнять параллельно одну и туже работу, т.е. выводить числа
от 1 до 1_000. На момент написания текста, после запуска программы в консоль было выведены числа сначала
от 1 до 70, потом начал работу другой поток от 1 до 128 и тд.

Первый вариант создания потоков:

Создается поток наследуя класс Thread, переопределяется метод run, в теле метода run пишется код, который
необходимо, чтобы проделывал поток:

        class MyThread extends Thread {
            @Override
            public void run() {
                код
            }
        }

Запуск.
Создается объект класса, который наследует класс Thread и в котором переопределен метод run, а далее
вызывается на этом объекте метод start для запуска потока:

        new MyThread.start();

Что делать, если класс уже имеет родителя (в Java не допускается множественное наследование)? К примеру,
класс MyThreadImplementsRunnable_1 уже наследовался (extends) от кого-то. Тогда необходимо использовать
второй тип создания потока - использование интерфейса Runnable (множественная имплементация в Java возможна).
Интерфейс Runnable имеет всего один метод run и является функциональным интерфейсом:

        @FunctionalInterface
        public interface Runnable {
            public abstract void run();
        }

Класс имплементирует функциональный интерфейс Runnable, переопределяется метод run. В теле метода run
необходимо указать, что необходимо делать потоку.

        class MyRunnable_1 implements Runnable {
            @Override
            public void run() {
                for (int i = 1; i <= 1_000; i++) {
                    System.out.println(i);
                }
            }
        }

        class MyRunnable_2 implements Runnable {
            @Override
            public void run() {
                for (int i = 1_000; i >= 1; i--) {
                    System.out.println(i);
                }
            }
        }

Как запускаются потоки в этом случае?
Для начала необходимо создать объект типа Thread:

        Thread thread1 = new Thread();
        Thread thread2 = new Thread();

и в параметр конструктора Thread необходимо вставить объект класса, который имплементирует функциональный
интерфейс Runnable:

        Thread thread1 = new Thread(new MyRunnable_1());
        Thread thread2 = new Thread(new MyRunnable_2());

Класс имплементирующий Runnable  - это ещё не поток. Поток получается, вставив объект данного класса в параметр
конструктора класса Thread.
Запускаются эти потоки таким же образом, через вызов метода start:

        thread1.start():
        thread2.start();

Принципиально ничего не меняется. В первом варианте идет наследование от класса Thread, создается
объект этого класса и запускается при помощи метода start. Во втором варианте создается Thread используя
сам класс Thread и в параметр конструктора передается объект, который имплементирует функциональный
интерфейс Runnable.
Output будет абсолютно таким же как и в первом варианте, таким же не предсказуемым. Выводился первый поток
до 106, потом второй поток перехватил консоль от 1_000 до 896 и тд., а работают эти потоки, конечно же,
параллельно.

Как связаны класс Thread и функциональный интерфейс Runnable?
Класс Thread имплементирует функциональный интерфейс Runnable, поэтому у него тоже есть метод run.

        public class Thread implements Runnable {
        ...
        }

Вне зависимости от того, какой способ будет использован, обязательно нужно переопределять метод run и
писать в нем то, что необходимо выполнить потоку.

Можно объявить, что класс VariantsCreatingThreading наследует (extends) класс Thread. Тогда он тоже
может быть использован как поток.

        public class VariantsCreatingThreading extends Thread {
        ...
        }

Естественно, что в этом классе необходимо переопределить метод run.
В методе main создается объект класса VariantsCreatingThreading. Теперь нужен еще один поток, который
будет выводить числа от 1_000 до 1. И это можно сделать в самом методе main, т.к. сам метод main это
тоже поток.
Вот этот поток отпачковуется от main потока и работает обособленно:

        VariantsCreatingThreading thread3 = new VariantsCreatingThreading();
        thread3.start();

а в методе main можно указать само тело кода, который был бы в переопределенном методе run другого класса:

        for (int i = 1_000; i >= 1; i--) {
            System.out.println(i);
        }

Output будет абсолютно таким же как и во всех вариантах выше, таким же не предсказуемым. Два потока
не синхронизированны и выполняются параллельно. Тут уже, в отличии от предыдущих примеров, выполняется
всего два потока, а не три. Можно встретить такой output, что сначала полностью консоль захватил main
поток, он вывел все числа от 1_000 до 1, потом консоль освободилась и ее захватил второй поток. Если
запустить несколько раз, то можно увидеть, что output будет меняться. Таким же образом можно сократить
код в примере с использованием функционального интерфейса Runnable:

        public class VariantsCreatingThreading implements Runnable {
             @Override
             public void run() {
                 for (int i = 0; i <= 1_000; i++) {
                     System.out.println(i);
                 }
             }

Еще один пример.
Допустим, необходимо "по-быстрому" создать поток и запустить его не используя класс, который
имплементирует функциональный интерфейс Runnable. Тогда можно использовать анонимный класс:

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 1_000; i++) {
                    System.out.println(i);
                }
            }
        }).start();

Или же еще быстрее, можно создать используя lambda выражение:

        new Thread(() -> {
            for (int i = 0; i <= 1_000; i++) {
                System.out.println(i);
            }
        }).start();

Для запуска потока необходимо использовать метод start, иначе поток не будет запущен!

Из за того, что в Java отсутствует множественное наследование, чаще используют второй тип создания
потока.
 */

//public class VariantsCreatingThreading extends Thread {
//     @Override
//     public void run() {
//         for (int i = 0; i <= 1_000; i++) {
//             System.out.println(i);
//         }
//     }

public class VariantsCreatingThreading {
    public static void main(String[] args) {

//        Первый тип создания потоков:
//        MyThreadExtendsThread_1 myThread1 = new MyThread1();
//        MyThreadExtendsThread_2 myThread2 = new MyThread2();
//        myThread1.start();
//        myThread2.start();

//        MyThreadExtendsThread_1 myThread1_1 = new MyThread1();
//        MyThreadExtendsThread_1 myThread1_2 = new MyThread1();
//        myThread1_1.start();
//        myThread1_2.start();

//        Второй тип создания потоков:
//        Thread thread1 = new Thread(new MyRunnable_1());
//        Thread thread2 = new Thread(new MyRunnable_2());
//        thread1.start();
//        thread2.start();

//        Пример с использованием main потока и наследования:
//        VariantsCreatingThreading thread3 = new VariantsCreatingThreading();
//        thread3.start();
//
//        for (int i = 1_000; i >= 1; i--) {
//            System.out.println(i);
//        }

//        Пример с использованием main потока и имплементации:
//        Thread thread4 = new Thread(new VariantsCreatingThreading());
//        thread4.start();

//        for (int i = 1_000; i >= 1; i--) {
//            System.out.println(i);
//        }

//        Пример с использованием анонимного класса:
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 1_000; i++) {
                    System.out.println(i);
                }
            }
        }).start();

//        Пример с использованием lambda выражения:
        new Thread(() -> {
            for (int i = 0; i <= 1_000; i++) {
                System.out.println(i);
            }
        }).start();
    }
}

class MyThreadExtendsThread_1 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 1_000; i++) {
            System.out.println(i);
        }
    }
}

class MyThreadExtendsThread_2 extends Thread {
    @Override
    public void run() {
        for (int i = 1_000; i >= 1; i--) {
            System.out.println(i);
        }
    }
}

class MyRunnable_1 implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 1_000; i++) {
            System.out.println(i);
        }
    }
}

class MyRunnable_2 implements Runnable {
    @Override
    public void run() {
        for (int i = 1_000; i >= 1; i--) {
            System.out.println(i);
        }
    }
}