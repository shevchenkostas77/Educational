package multithreading;

import java.util.concurrent.atomic.AtomicInteger;

/*
Код:
        public class AtomicIntegerExample {
            static int counter = 0;

            public static void increment() {
                counter++;
            }

            public static void main(String[] args) {
                Thread thread1 = new Thread(new MyRunnableImplementation18());
                Thread thread2 = new Thread(new MyRunnableImplementation18());

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

                System.out.println(counter);

            }
        }

        class MyRunnableImplementation18 implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 1_000; i++) {
                    AtomicIntegerExample.increment();
                }
            }
        }
Если не синхронизирован метод increment, тогда не всегда будет правильный результат. Тут происходит Data race. Поэтому
нужно написать ключевое слово synchronized в методе increment:

        public synchronized static void increment() {
            counter++;
        }

И теперь будет все работать как нужно, т.е. всегда будет результат 2_000.
На самом деле synchronized довольно таки "тяжелая штука". Каждый раз идет блокировка монитора, после выполнения всех
действий одного из потоков идет разблокировка монитора и тд. А приходится применять ключевое слово synchronized потому,
что counter++; - это неатомарная операция, т.е. не неделимая. С начало читается значение, затем увеличивается на один,
потом записывается в память. Java предлагает для таких случаев пользоваться классом AtomicInteger. В этом классе все
операции происходят атомарно.
AtomicInteger - это класс, который предоставляет возможность работать с целочисленными значениями int, используя
атомарные операции.

Помимо класса AtomicInteger есть еще классы: AtomicLong, AtomicBoolean, AtomicIntegerArray ...
Все они работают по схожему принципу, их операции атомарны.

 */

public class AtomicIntegerExample {
    static AtomicInteger counter = new AtomicInteger(0); // можно передать initial value (by default = 0)

    public static void increment() {
        // Увеличивать переменную counter теперь нужно через специальный метод - incrementAndGet
        // counter.incrementAndGet();

        // Если нужно сначала получить значение, а потом увеличить на единицу - метод getAndIncrement
        // counter.getAndIncrement();

        // Если нужно увеличить значение не на единицу, а на какое-то определенное число - метод addAndGet
        // counter.addAndGet(5);

        // Если нужно сначала получить значение, а потом увеличить на какое-то определенное число - метод getAndAdd
        // counter.getAndAdd(5);

        // Если нужно уменьшить значение не на единицу, а на какое-то определенное число - метод addAndGet( - value )
        counter.addAndGet(-5);

        // Если нужно уменьшить переменную на единицу - метод decrementAndGet
        // counter.decrementAndGet();

        // Если нужно сначала получить значение, а потом уменьшить на единицу - метод
        // counter.getAndDecrement();
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyRunnableImplementation18());
        Thread thread2 = new Thread(new MyRunnableImplementation18());

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

        System.out.println(counter);

    }
}

class MyRunnableImplementation18 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10_000; i++) {
            AtomicIntegerExample.increment();
        }
    }
}