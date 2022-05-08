package multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/*
Exchanger (с англ. обменник) - это синхронизатор, позволяющий обмениваться данными между двумя потоками, обеспечивает
то, что оба потока получат информацию друг от друга одновременно. Для обмена используется метод exchange.

            Thread ------->      |               |       <------- Thread
              T1            <------- Exchanger ------->             T2
                                 |               |

Допустим, есть потоки Т1, Т2 и есть Exchanger. Если поток Т1 вызвал метод exchange и он хочет обменяться информацией,
то эту информацию второй поток Т2 сразу не увидит. Поток Т1 вызвав метод exchange блокируется до тех пор, пока поток Т2
тоже не будет готов поделится информацией. Только, когда поток Т2 тоже посылает необходимую информацию, происходит
обмен информацией (естественно, поток Т1 выходит из блокировки). Стоит отметить, что эти оба потока получают информацию
обмененную в одно и то же время. Информация, которой они обмениваются должна быть одного типа данных.

Задание-пример.
Игра "Камень, ножницы, бумага".
Два друга играют в эту игру. Если друг, к примеру по имени Иван, первым покажет выбранный какой-то объект, то второй
друг, к примеру по имени Петр, его не увидит до тех пор, пока сам не покажет свой объект. Обмен информацией происходит
только тогда, когда оба потока вызывают метод exchange и информацией они будут обмениваться в один момент времени.
 */

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<Action> exchanger = new Exchanger<>();

        List<Action> friend1Action = new ArrayList<>();
        friend1Action.add(Action.NOJNICI);
        friend1Action.add(Action.BUMAGA);
        friend1Action.add(Action.NOJNICI);

        List<Action> friend2Action = new ArrayList<>();
        friend2Action.add(Action.BUMAGA);
        friend2Action.add(Action.KAMEN);
        friend2Action.add(Action.KAMEN);

        new BestFriend("Ivan", friend1Action, exchanger);
        new BestFriend("Petr", friend2Action, exchanger);
    }
}

enum Action {
    KAMEN,
    NOJNICI,
    BUMAGA;
}

class BestFriend extends Thread {
    private String name;
    private List<Action> myActions; // Игроки сыграют в игру, к примеру, три раза. Поэтому нужна переменная типа List
    private Exchanger<Action> exchanger; // Exchanger использует Generics

    public BestFriend(String name, List<Action> myActions, Exchanger<Action> exchanger) {
        this.name = name;
        this.myActions = myActions;
        this.exchanger = exchanger;
        this.start(); // при создании потока происходит сразу его запуск
    }

    private void whoWins(Action myAction, Action friendAction) {
        // тут прописана логик только победителя (для примера)
        if ((myAction == Action.KAMEN && friendAction == Action.NOJNICI)
                || (myAction == Action.NOJNICI && friendAction == Action.BUMAGA)
                || (myAction == Action.BUMAGA && friendAction == Action.KAMEN)) {
            System.out.println(name + " WINS!");
        }
    }

    public void run() {
        Action reply; // Ответ друга будет храниться в этой переменной (reply с англ. ответ)
        /*
        Метод exchange возвращает информацию полученную от второго потока. Т.е. в параметр метода exchange передается
        информация от первого потока, а в output-е этого метода возвращается информацию от второго потока
         */
        for (Action action : myActions) {
            try {
                reply = exchanger.exchange(action); // метод exchange выбрасывает исключение InterruptedException
                /*
                При помощи Exchanger-a и метода exchange (в параметр метода задается action одного потока) в output-e
                будет ответ. Если второй поток (друг) не готов показать один из вариантов (камень, ножницы, бумага), то
                первый поток будет тут заблокирован до тех пор, пока второй поток (второй друг) не вызовет метод
                exchange со своим action-ом, первый поток будет все это время в блокировке. А когда второй поток
                вызовет метод exchange, то первый поток получит значение второго в output-e метода exchange.
                 */
                whoWins(action, reply);
                sleep(2_000); // сон 2 сек после каждой игры
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}