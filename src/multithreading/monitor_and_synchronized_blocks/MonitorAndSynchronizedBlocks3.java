package multithreading.monitor_and_synchronized_blocks;

/*
Можно синхронизировать работу не только одного метода, а можно синхронизировать работу нескольких
методов. К примеру, есть потоки Т1, Т2 и Т3. Есть метод м1 (внутри метода какой-то код, неважно
какой) и есть метод m2 с определенным кодом (неважно какой код). Если есть необходимость, чтобы
в программе работали три потока или какое-то n-ое количество потоков и если один из потоков
выполняет метод м1 и в тот же момент времени метод м2 не должен выполняться, тогда можно
синхронизировать методы м1 и м2 по одному объекту. Создается какой-то объект, пусть называется
для примера object1 и устанавливаем, что метод m1 синхронизирован по object1 (это будет сделано
с помощью с synchronized блоков) и так же устанавливаем, что метод m2 синхронизирован по object1.
Что происходит?
Допустим, поток Т1 успел захватить монитор object1 и выполняет метод m1, т.е. монитор у объекта
object1 захвачен (статус "Занято"). Если поток Т2 захочет выполнить метод м2, он будет стоять и
ждать до тех пор, пока монитор у объекта object1 не освободится. Потому, что м2 метод синхронизирован
по объекту object1 так же как и метод м1. Когда Т1 закончит свою работу с методом м1, монитор у объекта
object1 освободится и Т2 может войти в тело метода м2 и выполнить код. Когда поток Т2 заходит в тело
метода м2, то естественно монитор объекта object1 снова становится занятым. Вот так работает синхронизация
по одному объекту для нескольких методов.

Задача пример:
Нам могут звонить на мобильный телефон используя мобильную связь, по Skype могут звонить на мобильный
телефон и по программе WhatsApp. Договоримся, что у нас будут три разных потока ответственные за
звонки с разных каналов. И придумаем правило, что если мы уже разговариваем, используя любой из этих
каналов и нам приходит звонок из другого канала, то этот звонок будет ждать до тех пор, пока не
закончится текущий. Т.е. если мы разговариваем по мобильному телефону используя Skype и нам звонят
используя мобильную сеть, то мы не сможем принять входящий звонок по мобильной сети до тех пор,
пока у нас активный Skype звонок.
Запустим код:
public class MonitorAndSynchronizedBlocks3 {

    void mobileCall() {
        System.out.println("Mobile call starts");
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Mobile call ends");
    }

    void skypeCall() {
        System.out.println("Skype call starts");
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Skype call ends");
    }

    void whatsappCall() {
        System.out.println("WhatsApp call starts");
        try {
            Thread.sleep(7_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("WhatsApp call ends");
    }

    public static void main(String[] args) {
        Thread threadMobile = new Thread(new RunnableImplementationMobile());
        Thread threadSkype = new Thread(new RunnableImplementationSkype());
        Thread threadWhatsApp = new Thread(new RunnableImplementationWhatsApp());

        threadMobile.start();
        threadSkype.start();
        threadWhatsApp.start();
    }
}

class RunnableImplementationMobile implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().mobileCall();
    }
}

class RunnableImplementationSkype implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().skypeCall();
    }
}

class RunnableImplementationWhatsApp implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().whatsappCall();
    }
}
Тут пока, что никакая синхронизация не использовалась.
Вывод:
Mobile call starts
Skype call starts
WhatsApp call starts
(три звонка начались одновременно)
(тут задержка 3 сек)
Mobile call ends
(тут задержка +2 сек)
Skype call ends
(тут задержка +2  сек)
WhatsApp call ends

Это не то что нужно, поэтому будут использовано ключевое слово synchronized, а именно synchronized методы.
Запустим получившийся код:
public class MonitorAndSynchronizedBlocks3 {

    synchronized void mobileCall() {
        System.out.println("Mobile call starts");
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Mobile call ends");
    }

    synchronized void skypeCall() {
        System.out.println("Skype call starts");
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Skype call ends");
    }

    synchronized void whatsappCall() {
        System.out.println("WhatsApp call starts");
        try {
            Thread.sleep(7_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("WhatsApp call ends");
    }

    public static void main(String[] args) {
        Thread threadMobile = new Thread(new RunnableImplementationMobile());
        Thread threadSkype = new Thread(new RunnableImplementationSkype());
        Thread threadWhatsApp = new Thread(new RunnableImplementationWhatsApp());

        threadMobile.start();
        threadSkype.start();
        threadWhatsApp.start();
    }
}

class RunnableImplementationMobile implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().mobileCall();
    }
}

class RunnableImplementationSkype implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().skypeCall();
    }
}

class RunnableImplementationWhatsApp implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().whatsappCall();
    }
}
Вывод:
Mobile call starts
Skype call starts
WhatsApp call starts
(три звонка начались одновременно)
(тут задержка 3 сек)
Mobile call ends
(тут задержка +2 сек)
Skype call ends
(тут задержка +2  сек)
WhatsApp call ends

И снова не удовлетворяет условию задачи output. Снова три звонка начались одновременно и закончились в
зависимости от их длительности (3, 5, 7 секунд). Почему так происходит?
Когда используется synchronized в сигнатуре метода, а метод, в данном случае, не статичный, тогда
by default используется синхронизация на объекте this. Выведем на экран this для каждого метода (для
этого добавим в начало каждого метода строку: System.out.println(this);)
Вывод:
multithreading.monitorandsynchronizedblocks.MonitorAndSynchronizedBlocks3@52523f14
Mobile call starts
multithreading.monitorandsynchronizedblocks.MonitorAndSynchronizedBlocks3@2c06e991
Skype call starts
multithreading.monitorandsynchronizedblocks.MonitorAndSynchronizedBlocks3@39443a5d
WhatsApp call starts
Mobile call ends
Skype call ends
WhatsApp call ends

Идет синхронизация по трем разным объектам. Когда mobileCall метод запускается, идет синхронизация по
этому объекту: MonitorAndSynchronizedBlocks3@52523f14, т.е. монитор этого объекта занят. Потом начинается
skypeCall и монитор этого объекта: MonitorAndSynchronizedBlocks3@2c06e991, потом начинается whatsappCall и
занимается монитор этого объекта: MonitorAndSynchronizedBlocks3@39443a5d.
Это абсолютно не то, что по условию задания. Если необходимо добиться того результата, что описано в задании
выше, то необходимо добиться, чтобы синхронизация шла по одному объекту. А пока что объекты синхронизируются
по трем разным объектам. Почему так происходит?
Потому, что в сигнатуре метода указано ключевое synchronized и by default в методах используется монитор на
объекте this. Поэтому нужно использовать в данном примере синхронизированные блоки и синхронизацию делать
по одному объекту. Можно использовать абсолютно любой объект. Например, можно создать класс Car, и использовать
объект этого класса для синхронизации всех методов. Будет использован монитор одного и того же объекта для
синхронизации.
Код:
public class MonitorAndSynchronizedBlocks3 {
    final static Car car = new Car();

    void mobileCall() {
        synchronized (car) {
            System.out.println("Mobile call starts");
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Mobile call ends");
        }
    }

    void skypeCall() {
        synchronized (car) {
            System.out.println("Skype call starts");
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Skype call ends");
        }
    }

    void whatsappCall() {
        synchronized (car) {
            System.out.println("WhatsApp call starts");
            try {
                Thread.sleep(7_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("WhatsApp call ends");
        }
    }

    public static void main(String[] args) {
        Thread threadMobile = new Thread(new RunnableImplementationMobile());
        Thread threadSkype = new Thread(new RunnableImplementationSkype());
        Thread threadWhatsApp = new Thread(new RunnableImplementationWhatsApp());

        threadMobile.start();
        threadSkype.start();
        threadWhatsApp.start();
    }
}

class RunnableImplementationMobile implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().mobileCall();
    }
}

class RunnableImplementationSkype implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().skypeCall();
    }
}

class RunnableImplementationWhatsApp implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().whatsappCall();
    }
}

class Car {}

Вывод:
Mobile call starts
(тут задержка 3 сек)
Mobile call ends
WhatsApp call starts
(тут задержка 5 сек)
WhatsApp call ends
Skype call starts
(тут задержка 7 сек)
Skype call ends

Очередность, конечно же может меняться.

Если нужен просто какой-то объект, на мониторе которого необходимо сделать синхронизацию, как в этом примере,
т.е. на самом же деле код никак от машины не зависит, то не стоит использовать выдуманный класс (в данном
случае был использован выдуманный класс Car). Лучше вместо этого написать:
        final static Object lock = new Object();
и синхронизироваться по нему, чтобы не использовать какой-то другой класс.

Пример блока:

        static final Object lock = new Object();
        public void abc() {
            method body
            synchronized (lock) {
                block body
            }
        }

Создается финальный статический объект с именем lock и внутри какого-то метода, необязательно синхронизировать
все statements метода, выбирается какой-то блок и синхронизация происходит по объекту lock, т.е. будет
использоваться монитор объекта lock.

ВАЖНЫЙ НЮАНС!
Невозможно синхронизировать конструкторы, JVM гарантирует, что конструктор может обрабатываться в одно и то же
время только одним потоком.
 */

public class MonitorAndSynchronizedBlocks3 {
    final static Object lock = new Object();

    void mobileCall() {
        synchronized (lock) {
            System.out.println("Mobile call starts");
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Mobile call ends");
        }
    }

    void skypeCall() {
        synchronized (lock) {
            System.out.println("Skype call starts");
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Skype call ends");
        }
    }

    void whatsappCall() {
        synchronized (lock) {
            System.out.println("WhatsApp call starts");
            try {
                Thread.sleep(7_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("WhatsApp call ends");
        }
    }

    public static void main(String[] args) {
        Thread threadMobile = new Thread(new RunnableImplementationMobile());
        Thread threadSkype = new Thread(new RunnableImplementationSkype());
        Thread threadWhatsApp = new Thread(new RunnableImplementationWhatsApp());

        threadMobile.start();
        threadSkype.start();
        threadWhatsApp.start();
    }
}

class RunnableImplementationMobile implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().mobileCall();
    }
}

class RunnableImplementationSkype implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().skypeCall();
    }
}

class RunnableImplementationWhatsApp implements Runnable {
    @Override
    public void run() {
        new MonitorAndSynchronizedBlocks3().whatsappCall();
    }
}