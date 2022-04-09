package stream;

import java.util.stream.Stream;
import java.util.Arrays;
/*
Метод concat.
Выполняет конкатенацию. Этот метод статический.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamConcat {
    public static void main(String[] args) {
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5, 1, 2, 3);
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> stream3 = Stream.of(6, 7, 8, 9, 10);
        Stream<Integer> stream4 = Stream.concat(stream2, stream3);
        stream4.forEach(element -> System.out.print(element + " "));
        System.out.println();
//        Вывод:
//        1 2 3 4 5 6 7 8 9 10
        /*
        На выходе получился stream, который объединил stream1 и stream2. Метод concat статический и возвращает stream.
        Этот метод не может быть использован в цепочке. К примеру:

        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7};

        Чтобы получить из массива stream, необходимо у класса Arrays (необходимо импортировать import java.util.Arrays;)
        вызвать статический метод stream и в его парамер передать массив из которого необходимо получить поток.

        int result = Arrays.stream(array)
                .filter(element -> element % 2 == 0)
                .concat                             <- даже не найдется накой метод
                .map(element -> element / 2)
                .reduce((accumulator, element) -> accumulator + element).getAsInt();
        System.out.println(result);
        При помощи метода getAsInt (после работы метода reduce поток превратился в объект типа Optional) результат
        работы передается переменной типа int и выводится на экран.

        Метод concat не является terminal методом ведь он возвращает stream, но и после него нельзя вызвать другой
        intermediate метод.
         */
    }
}