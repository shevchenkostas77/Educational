package stream;

import java.util.stream.Stream;

/*
Метод count (terminal method).
Считает количество элементов в stream и возвращает не int, а long.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamCount {
    public static void main(String[] args) {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 1, 2, 3);
        System.out.println(stream.count());
//        Вывод:
//        10

//        Попытка вывести уникальное количество элементов:
//        System.out.println(stream.distinct().count());
//        Вывод:
//        Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
        /*
        ВАЖНОЕ ПРАВИЛО!
        Stream после обработки НЕЛЬЗЯ переиспользовать иначе будет выброшен exception!
        Т.е. на строке 14 stream уже был использован, чтобы найти его количество элементов. Поэтому стока 19 лишняя, т.к.
        невозможно переиспользовать заново stream.
        */
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6, 7, 1, 2, 3);
        System.out.println(stream2.distinct().count());
//        Вывод:
//        7
    }
}