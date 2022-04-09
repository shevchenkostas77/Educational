package stream;

import java.util.stream.Stream;

/*
Метод distinct (intermediate method).
Возвращает stream уникальных элементов, а проверяет их с помощью метода equals&
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamDistinct {
    public static void main(String[] args) {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 1, 2, 3);
        stream.distinct().forEach(element -> System.out.print(element + " "));
//        Вывод:
//        1 2 3 4 5
    }
}