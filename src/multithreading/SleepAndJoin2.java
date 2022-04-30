package multithreading;

public class SleepAndJoin2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Method main begins");
        Thread thread = new Thread(new Worker());
        thread.start();
        thread.join();
        System.out.println("Method main is done");
//        Вывод:
//        Method main begins
//        Work begins
//        (тут задержка 2.5 сек)
//        Work is done
//        Method main is done
        /*
        Начинает работу метод main, срабатывает первая строка кода метода main и выводится на экран
        Method main begins. После создается поток thread и запускается. Этой командой:

                thread.join();

        поток main понимает, что на этой строке кода должен остановится и продолжить свое выполнение
        после того, как поток thread закончит свою работу.
         */

        /*
        У метода join есть второй вариант использования - с параметром. Этот параметр принимает миллисекунды.
        Если вызвать метод join с параметром, тогда поток, В котором был вызван этот метод, будет ждать пока
        другой поток НА котором вызван метод join с параметром не завершит свою работу или будет ждать время
        указанное в параметре метода join и после продолжит свою работы даже, если поток НА котором вызван
        метод join не закончил свою работу. Т.е., что из этих событий произойдет первым, то и подстегнет поток
        В котором вызван этот метод продолжить свою работу.

                    public static void main(String[] args) throws InterruptedException {
                        System.out.println("Method main begins");
                        Thread thread = new Thread(new Worker());
                        thread.start();
                        thread.join(1_000);
                        System.out.println("Method main is done");
                    }
         Вывод:
         Method main begins
         Work begins
         (тут задержка 1 сек)
         Method main is done
         (тут задержка на оставшиеся время выполнения потока thread)
         Work is done
         */
    }
}

class Worker implements Runnable {
    @Override
    public void run() {
        System.out.println("Work begins");
        try {
            Thread.sleep(2_500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Work is done ");
    }
}