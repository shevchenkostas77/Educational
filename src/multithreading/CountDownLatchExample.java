package multithreading;

import java.util.concurrent.CountDownLatch;

/*
CountDownLatch (с англ. замок с обратным отсчетом) - это синхронизатор. Данный синхронизатор позволяет нескольким
потокам ждать до тех пор, пока не закончится обратный отсчет, т.е. не настанет ноль. Другими словами, CountDownLatch
предоставляет возможность любому количеству потоков ожидать до тех пор, пока не завершится определенное количество
операций. После окончания этих операций потоки будут отпущены, чтобы продолжить свою деятельность. В конструктор
CountDownLatch ОБЯЗАТЕЛЬНО передается количество операций, которое должно быть выполнено, чтобы замок отпустил
заблокированные потоки. Все синхронизаторы находятся в пакете java.util.concurrent;

Есть обратный счетчик CountDownLatch и допустим, он в данный момент времени равен 5. Есть потоки: Т1, Т2, Т3.
Потоки начинают свою работу, и они хотят продолжить работу на каком-то участке кода, но им сообщается, что сделать
они этого пока не могут. Не смогут продолжить свою работу до тех пор, пока счетчик CountDownLatch не будет равным
нулю. Что означает этот счетчик? Например, работу каких-то операций. Сработала какая-то одна операция и счетчик стал
равен четырем, потом сработала еще одна операция, счётчик уменьшился на единицу и стал равен трем, и т.д. Когда счетчик
станет равным нулю, то потоки Т1, Т2 и Т3 продолжают свою работу и если какой-то новый поток, к примеру, Т4 должен
работать, то проверяется счетчик чему равен, а равен он нулю (в данный момент времени по примеру). Поэтому поток Т4
ничего не ждет и может продолжить свою работу в полной мере.

Задание-пример.
Представим ситуацию, которая может произойти утром в день "Черной пятницы", когда во многих магазинах бывают огромные
скидки. К примеру, пятеро друзей пошли за покупками с утра пораньше, стали в очередь перед магазином одни из первых. Но
ребята не могут приступить к покупкам, пока не выполнится определенные операции.
В данном примере будут следующие операции:
    1. Работники магазина должны прийти на работу;
    2. Все в магазине должно быть разложено по полкам, аккуратно убрано, включен свет в магазине;
    3. Магазин должен открыться;
Т.е. есть три действия, которых покупатели должны дождаться, чтобы начать делать покупки. Это все можно реализовать
с помощью CountDownLatch.

*/

public class CountDownLatchExample {

    static CountDownLatch countDownLatch = new CountDownLatch(3); // в конструктор передается количество операций

    private static void marketStaffOnPlace() {
        try {
            Thread.sleep(2_000); // имитация прихода сотрудников на работу
            System.out.println("Market staff came to work");
            countDownLatch.countDown(); // уменьшение счетчика на единицу после выполнения одной операции
            System.out.println("countDownLatch = " + countDownLatch.getCount()); // getCount - текущее значение счетчика
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void everythingIsReady() {
        try {
            Thread.sleep(3_000); // имитация подготовки помещения к открытию
            System.out.println("Everything is ready, so let's open the market");
            countDownLatch.countDown(); // уменьшение счетчика на единицу после выполнения одной операции
            System.out.println("countDownLatch = " + countDownLatch.getCount()); // getCount - текущее значение счетчика
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void openMarket() {
        try {
            Thread.sleep(4_000); // имитация открытия магазина
            System.out.println("The marked is opened");
            countDownLatch.countDown(); // уменьшение счетчика на единицу после выполнения одной операции
            System.out.println("countDownLatch = " + countDownLatch.getCount()); // getCount - текущее значение счетчика
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Friend("Stas", countDownLatch);
        new Friend("Oleg", countDownLatch);
        new Friend("Elena", countDownLatch);
        new Friend("Victor", countDownLatch);
        new Friend("Marina", countDownLatch);

        marketStaffOnPlace();
        everythingIsReady();
        openMarket();
        /*
        Каждый из этих действий:
                marketStaffOnPlace();
                everythingIsReady();
                openMarket();
        уменьшит счетчик countDownLatch на единицу

        Запуск программы. Вывод:

        Market staff came to work
        countDownLatch = 2
        Everything is ready, so let's open the market
        countDownLatch = 1
        The marked is opened
        countDownLatch = 0
        Victor приступил(а) к закупкам
        Elena приступил(а) к закупкам
        Stas приступил(а) к закупкам
        Marina приступил(а) к закупкам
        Oleg приступил(а) к закупкам
        */
    }
}

class Friend extends Thread {
    String name;
    private CountDownLatch countDownLatch;

    public Friend(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
        this.start();
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            /*
            Если счетчик CountDownLatch больше нуля, то поток будет заблокирован до тех пор, пока счетчик не станет
            равен нулю. Если же счётчик уже нуль, тогда поток беспрепятственно будет выполнять свою работу. Метод
            await выбрасывает Exception - InterruptedException.
            */
            System.out.println(name + " приступил(а) к закупкам");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
