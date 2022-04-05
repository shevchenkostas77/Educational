package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Метод reduce (с англ. уменьшить).
Когда применяется метод reduce для stream, то на выходе получается один элемент, т.е. уменьшается
n-ое количество элементов в stream до одного. При помощи метода reduce очень удобно выполнять агрегированные
функции: суммирование, умножение и тд.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class streamReduce {
    public static void main(String[] args) {
        /*
        Задание-пример использование метода reduce.
        Необходимо найти произведение всех элементов коллекции.
         */
        List<Integer> list = new ArrayList<>();

        list.add(5);
        list.add(8);
        list.add(2);
        list.add(4);
        list.add(3);

//        Вариант №1 использования метода reduce.
        Optional<Integer> result = list.stream().reduce((accumulator, element) ->
                accumulator * element);
        System.out.println(result);
//        Вывод:
//        Optional[960]

        /*
        После того как на коллекции list вызывается метод stream, появляется stream, который содержит
        следующие элементы: 5, 8, 2, 4, 3.
        Работа происходит с двумя параметрами: accumulator и element.
        Переменная accumulator это то, что что-то накапливает. Здесь переменная accumulator это что-то
        на подобии накопительной переменной int sum в for loop-е, к примеру, когда ищется сумма всех
        элементов массива (проходясь по каждому элементу массива в for loop-e прибавляется значение
        элемента к переменной sum). Т.е. аккумулируется весь результат в одну переменную. Таким же образом
        работает параметр accumulator. В этом варианте метода reduce, когда он принимает одно lambda выражение,
        а в lambda выражении два параметра (accumulator и element) переменной accumulator сразу присваивается
        первое значение потока (5). Переменной element присваивается второе значение потока (8). Затем происходит
        умножение accumulator на element, т.е. 5 умножается на 8 и передача этого произведения происходит
        accumulator-y и переменная accumulator становится 40 (он будет собирать все эти значения, в данном случае это
        произведения). Следующий элемент потока (2) назначается переменной element и снова происходит умножение:
        40 умножается на 2 = 80. Element присваивается следующее значение 4, 80 * 4 = 320. Значение 320 присевается
        accumulator. Следующее значение потока это 3 и присваивается element, происходит та же операция по умножению
        320 * 3 = 960. На этом метод reduce заканчивает свою работу и возвращает значение 960.
        accumulator = 5,  40,  80,  320,  960.
        element =     8,   2,   4,    3.
        Если необходимо назначить это произведение типа int, необходимо воспользоваться методом get.
        Т.к. метод reduce возвращает объект типа Optional.
        Объект типа Optional делает wrap, т.е. оборачивает собой, в данном случае, объект Integer-a, который
        получился в данном случае. Объект типа Optional является контейнером для конкретного значения.
        Optional может содержать null и not-null значения.
         */
        int res = list.stream().reduce((accumulator, element) ->
                accumulator * element).get();
        System.out.println(res);
//        Вывод:
//        960

        List<Integer> listHasNoElements = new ArrayList<>();
//        int listHasNoElementsResult = listHasNoElements.stream().reduce((accumulator, element) ->
//                accumulator * element).get();
//        System.out.println(listHasNoElementsResult);
//        Вывод:
//        Exception in thread "main" java.util.NoSuchElementException: No value present
        Optional<Integer> listHasNoElementsResult2 = listHasNoElements.stream().reduce((accumulator, element) ->
                accumulator * element);
        System.out.println(listHasNoElementsResult2);
//        Вывод:
//        Optional.empty
        /*
        Если попытаться вывести результат работы стрима вызванного на коллекции listHasNoElement, которая
        не содержит никаких элементов, то выбросится исключение NoSuchElementException.
        Чтобы подобного исключения не возникало необходимо обезопаситься, в конце выражения не вызвать
        метод get, а присвоить результат выполнения работы метода reduce переменной типа Optional и
        воспользоваться методом isPresent (присутствует значение?):
         */
        Optional<Integer> listHasNoElementsResult3 = listHasNoElements.stream().reduce((accumulator, element) ->
                accumulator * element);
        if (listHasNoElementsResult3.isPresent()) {
            System.out.println(listHasNoElementsResult3.get());
        } else {
            System.out.println("No value present");
        }
//        Вывод:
//        No value present
        /*
        Присутствует результат произведения? Вот такой вопрос задает метод isPresent. Если значение
        null, как в данном случае, то выводится предложение "No value present", если же метод
        reduce возвращает not-null значение, то при помощи метода get получается это значение и выводится
        на экран.
         */

//      Вариант №2 использования метода reduce.
        /*
        Можно добавить еще один параметр в метод reduce, который будет являться аккумулятором.
        */
        int result2 = list.stream().reduce(1, (accumulator, element) ->
                accumulator * element);
        System.out.println(result2);
//        Вывод:
//        960
        int result2_1 = listHasNoElements.stream().reduce(1, (accumulator, element) ->
                accumulator * element);
        System.out.println(result2_1);
//        Вывод:
//        1
        /*
        Когда добавляется еще один параметр на первое место, он будет являться первичным значением
        (начальным значением) переменной accumulator.
        Переменной accumulator присваивается значение равное 1, и теперь с первого элемента stream-a
        передаются значения переменной element.
        accumulator = 1,  5,  40,  80,  320,  960.
        element =     5,  8,   2,   4,    3.
        Когда используется этот вариант метода reduce, то null значение уже точно не будет получено потому, что,
        как минимум, указано значение аккумулятора = 1. Поэтому здесь нет необходимости в конце выражения
        использовать метод get. Метод reduce возвращает объект типа Integer.
         */

        /*
        Задание-пример использование метода reduce.
        Необходимо конкатенировать все элементы объекта типа List, который содержит объекты типа String.
         */
        List<String> list2 = new ArrayList<>();
        list2.add("privet");
        list2.add("kak dela?");
        list2.add("OK");
        list2.add("poka");
        list2.add("gooooood");

        String list2Result = list2.stream().reduce((accumulator, element) ->
                accumulator + " " + element).get();
//        Null значение тут не вернется, поэтому вызывается метод get в конце
        System.out.println(list2Result);
//        Вывод:
//        privet kak dela? OK poka gooooood

        /*
        Если использовать вариант №1, где используются в методе reduce в lambda выражении два параметра,
        то желательно обезопасить себя и воспользоваться методом isPresent (присутствует результат после метода reduce?).
        Если использовать вариант №2, где аккумулятору задается изначальное значение, то метод get в конце
        нет смысла использовать, да и Java не даст воспользоваться этим методом, т.к. результат будет, как минимум,
        это начальное значение аккумулятора.
        */
    }
}