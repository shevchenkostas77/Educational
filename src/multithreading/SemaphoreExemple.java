package multithreading;

import java.util.concurrent.Semaphore;

/*
Low level (с англ. низкий уровень) concurrency  - это когда самостоятельно необходимо прописывать lock, необходимо
самостоятельно прописывать синхронизацию и тд.

Higt level (с англ. высокий уровень) concurrency - не нужно самостоятельно прописывать lock, синхронизацию и тд., а
можно пользоваться готовыми решениями, которые, в свою очередь, используют low level concurrency механизмы. Такие готовые
решения называют synchronizes (с англ. синхронизаторы).

Semaphore - это синхронизатор, позволяющий ограничить доступ к какому-то ресурсу. В конструктор Semaphore нужно передать
количество потоков, которым Semaphore будет разрешать одновременно использовать этот ресурс.

        Т1  ------------>

        Т2

        Т3  ------------>    Resource

        Т4

        Т5  ------------>
                            new Semaphore(3);
                            счетчик: 3 -> 2 -> 1 -> 0 -> 1 -> 0

Есть какой-то ресурс. Создается Semaphore и в скобках указывается сколько потоков, может одновременно использовать
этот ресурс - 3 (в данном случае). Цифра 3 - это счетчик. Semaphore будет использовать этот счетчик, чтобы отслеживать,
что не более трех потоков одновременно использует ресурс. Допустим, есть потоки: Т1, Т2, Т3, Т4, Т5. Количество разрешений
равно трем. Счетчик Semaphore на данном этапе программы равен трем. Когда поток Т1 хочет получить доступ к ресурсу, Semaphore
"смотрит" на свой счетчик: 3 больше 0? Да. Тогда Semaphore дает разрешение потоку Т1 использовать ресурс и счетчик Semaphore
становится равным двум. Поток Т3 тоже хочет подключиться, он хочет использовать для своих целей ресурс. Semaphore смотрит чему
равен текущий счетчик, а счетчик на данном этапе равен двум. 2 больше 0? Да и Semaphore дает разрешение потоку Т3 использовать
ресурс. После этого он меняет свой счетчик на единицу. Далее, к примеру, поток Т5 хочет использовать ресурс, Semaphore смотрит
есть ли еще разрешения. Да, дает разрешение потоку Т5 на использование ресурса и на этом этапе работы программы счетчик
Semaphore становится быть равен нулю. Теперь, если поток Т2 или поток Т4 захотят использовать ресурс, Semaphore не разрешит им
это сделать. Потоки Т2 и Т4 залочаться (lock) до тех пор, пока поток Т1 или какой-то другой поток не завершит использование
ресурса и счетчик Semaphore из нуля не превратится в единицу (когда, к примеру, поток Т1 закончит использование ресурса, то
счетчик увеличится). Какому потоку повезет больше, потоку Т2 или потоку Т4, тот и подключится к ресурсу. К примеру, потоку Т2
повезло больше и он получает доступ к ресурсу, счетчик снова становится равным быть нолю. Поток Т4 по-прежнему ждет и он будет
ждать до тех пор, пока счетчик не увеличится с нуля, хотя бы, на единицу. Вот в чем заключается логика Semaphore.

Если Semaphore изначально разрешает только одному потоку использовать ресурс, т.е. в конструкторе Semaphore указывается единица,
тогда использование Semaphore будет такое же, как и использование lock-a.

Задание-пример.
Вернемся в прошлое, в прошлое, когда еще не было мобильных телефонов и люди, которые находились вне дома, могли позвонить
какому-то используя телефонные будки. Допустим, есть две телефонные будки и к ним подошли пять человек желающие позвонить.
Естественно, в одно и то же время каждой будкой может пользоваться только один человек. В этом примере можно использовать
Semaphore поставив ограничения на двух людей, которые одновременно могут звонить, ведь будок всего две.
*/

public class SemaphoreExemple {
    public static void main(String[] args) {
        Semaphore callBox = new Semaphore(2); // телефонных будок всего 2, одновременно могут звонить два человека

        new Person("Stas", callBox);
        new Person("Oleg", callBox);
        new Person("Elena", callBox);
        new Person("Victor", callBox);
        new Person("Marina", callBox);

    }
}

class Person extends Thread { // каждый человек будет потоком
    String name;
    private Semaphore callBox;

    public Person(String name, Semaphore callBox) {
        this.name = name;
        this.callBox = callBox;
        this.start(); // при создании объекта поток запускается

        /*
        Запуск программы. Вывод:

        Oleg ждет...
        Stas ждет...
        Victor ждет...
        Marina ждет...
        Elena ждет...
        Stas пользуется телефоном
        Victor пользуется телефоном
        Stas завершил(а) звонок
        Victor завершил(а) звонок
        Marina пользуется телефоном
        Oleg пользуется телефоном
        Marina завершил(а) звонок
        Oleg завершил(а) звонок
        Elena пользуется телефоном
        Elena завершил(а) звонок
        */
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " ждет... ");
            callBox.acquire();
            System.out.println(name + " пользуется телефоном");
            sleep(2_000); // каждый человек пользуется телефоном 2 секунды
            System.out.println(name + " завершил(а) звонок");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            callBox.release(); // обязательно нужно вызвать метод release на Semaphore
            /*
            Метод release говорит о том, что после выполнения задания освобождается разрешение Semaphore, тем самым увеличивается
            счетчик у Semaphore на единицу. Этот метод release нужно использовать в finally блоке, ведь если выбросится Exception
            и работа потока прервется, то необходимо освободить доступ к ресурсу.
            */
        }
    }
}

/*
Метод acquire предназначен для попытки получить разрешение от Semaphore. Метод acquire заблокирует поток пока ресурс
не будет доступен для него. После доступности поток получает разрешение использовать этот общий ресурс и counter
Semaphore уменьшается на единицу.
*/