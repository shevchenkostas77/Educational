package stream;

import java.util.stream.Stream;

/*
Метод peek (intermediate method) (с англ. подглядывать).
Данный метод так же, как и forEach принимает в параметр consumer-a (consumer c англ. потребитель).
НО в отличие от forEach данный метод возвращает не void, а stream. Поэтому он является intermediate метод. Этот метод
необходим, чтобы посмотреть, как проходит поэтапно метод chaining.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamPeek {
    public static void main(String[] args) {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 1, 2, 3);
        System.out.println(stream.distinct().peek(System.out::println).count());
//        Вывод:
//        1
//        2
//        3
//        4
//        5
//        6
//        7
//        7
        /*
        На выходе после метода distinct получился стрим уникальных элементов и при помощи метода peek на экран был
        выведен промежуточный результат работы цепочки и в конце метод count вернул количество оставшихся уникальных
        элементов. Т.е. при помощи метода peek, не прерывая работу цепочки, можно посмотреть промежуточный результат
        после того или иного метода.
        */
    }
}