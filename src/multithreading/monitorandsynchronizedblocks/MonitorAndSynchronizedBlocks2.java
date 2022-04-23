package multithreading.monitorandsynchronizedblocks;

public class MonitorAndSynchronizedBlocks2 {
    volatile static int counter = 0;

    public static void increment() {
        synchronized (MonitorAndSynchronizedBlocks2.class) {
            counter++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        R2 r2 = new R2();
        Thread thread1 = new Thread(r2);
        Thread thread2 = new Thread(r2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter);
    }
}

class R2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100_000; i++) {
            MonitorAndSynchronizedBlocks2.increment();
        }
    }
}

/*
Здесь используется синхронизированный метод increment и этот метод static. By default в
MonitorAndSynchronizedBlocks в методе doWork1 используется монитор на объекте this. В данном
случае монитор на объекте this уже использоваться не может, потому что метод increment
static. В этом случае, когда используется ключевое слово synchronized в описании статичного
метода, используется монитор всего класса, класса MonitorAndSynchronizedBlocks2. Если
перевести код метода increment в synchronized блок, то это будет выглядеть следующим образом:
        ДО:
        public static synchronized void increment() {counter++;}

        ПОСЛЕ:
        public static void increment() {
            synchronized (MonitorAndSynchronizedBlocks2.class) {
                counter++;
            }
        }
Пишется ключевое слово synchronized и уже синхронизация происходит не используя монитор объекта
какого-то, потому, что если указать ключевое слово this или любой другой объект, Java не позволит
так сделать потому, что метод ведь статический, this нельзя использовать внутри статического метода,
необходимо синхронизироваться на самом классе: MonitorAndSynchronizedBlocks2.class
Понятное дело, если необходимо синхронизировать весь метод increment, то лучше использовать
синхронизацию всего метода, но если бы метод increment был довольно такие велик и необходимо часть
его кода синхронизировать, а часть не синхронизировать, тогда необходимо использовать синхронизированные
блоки.
СИНХРОНИЗАЦИЯ ИДЕТ ПО МОНИТОРУ, А МОНИТОР ЭТО НЕОТЕМЛЕМАЯ ЧАСТЬ КАЖДОГО ОБЪЕКТА И КАЖДОГО КЛАССА В JAVA!
 */