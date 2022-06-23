package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/*
Метод map.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/


public class StreamMap {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("privet");
        list.add("kak dela?");
        list.add("OK");
        list.add("poka");
        list.add("gooooood");

        /*
        Необходимо заменить объекты типа String (слова) из коллекции list,
        на их длину.

        Способ №1.
        При помощи стандартного for loop-a.

        for (int i = 0; i < list.size(); i++) {
            list.set(i, String.valueOf(list.get(i).length()));

            list.get(i).length() - эта запись возвращает int, поэтому из этого itn-a необходимо
                                   получить String, для этого необходимо использовать метод valueOf.

        }
        System.out.println(list);
        Вывод:
        6, 9, 2, 4, 8
        Вместо слов список содержит длины этих же слов.
        */

        /*
        Способ №2.
        С помощью коллекции можно создать stream.

        List<Integer> list2 = list.stream().map(element -> element.length())
                .collect(Collectors.toList());

        (Правая часть) Пишется имя переменной коллекции, вызывается метод stream(), этот метод stream возвращает Stream.
        Stream можно получить или создать несколькими способами, самый распространенный это создание его
        из коллекции путем вызова метода stream. На выходе поток, этот поток абсолютно не имеет отношения к
        многопоточности и к стримам, с помощью которых происходит или чтение, или запись с файла.
        Т.е. методы stream работают не напрямую с массивами или коллекциями, а перед этим происходит
        преобразование коллекций или массивов в поток. Таким образом после метода stream поток содержит
        следующие элементы:
         - "privet"
         - "kak dela?"
         - "OK"
         - "poka"
         - "gooooood"
         Из коллекции получился Stream.
         Вызывается метод map.
         Метод map берет по очереди каждый элемент из Stream и сопоставляет ему элемент, который из него получается
         после применения над ним тех действий, которые описаны внутри метода map, с помощью lambda выражения:

         element -> element.length().
         1. element это каждый элемент уже Stream ("privet", "kak dela?", "OK",
         "poka", "gooooood");
         2. element.length() - получаем длину каждого элемента;

         На выходе метода map, а map возвращает тоже Stream, получается уже другой Stream, вместо элемента
         "privet" - 6, "kak dela?" - 9, "OK" - 2, "poka" - 4, "gooooood" - 8. Т.е. на выходе получается
         6, 9, 2, 4, 8. Это работа метода map. Метода map принимает интерфейс Function, поэтому можно в методе
         map написать lambda выражение, у которого на input - String, output - int.

         Метод map возвращает Stream, поэтому этот результат нельзя просто присвоить новому или уже имеющемуся List-у.
         К примеру, List<Integer> list2 = list.stream().mep(element -> element.length());
         Это значение list.stream().mep(element -> element.length()); нельзя присвоить List<Integer> list2, который
         содержит Integer (Integer, т.к. элементы превратились в их длины, в Integer-ы с помощью метода map),
         потому что нельзя присвоить List-у поток. Нужно Stream преобразовать в List и для этого нужно использовать
         метод collect, в параметре указать Collectors.toList(). Т.е. поток, который получился способом
         list.stream().mep(element -> element.length()) преобразуется в List.
         */
        List<Integer> list2 = list.stream().map(element -> element.length())
                .collect(Collectors.toList());
        System.out.println("list2 = " + list2);
        // Вывод:
        // list2 = [6, 9, 2, 4, 8]
        /*
        Тут уже никакой for не используется, т.к. в методе map поочередно каждый элемент будет обработан и преобразован
        в длину этого элемента.
        */

        /*
        ВАЖНО!
        Все методы stream не меняют саму коллекцию или массив на котором они были вызваны, т.е. коллекция list как была
        с набором элементов "privet", "kak dela?", "OK", "poka", "gooooood", так и останется.
        */
        System.out.println("list = " + list);
        // Вывод:
        // list = [privet, kak dela?, OK, poka, gooooood]

        // Применение метода map для массива:

        int[] array = {5, 9, 3, 8, 1};
        array = Arrays.stream(array).map(element
                -> {
            if(element % 3 == 0) {
                element /= 3;
            }
            return element;
        }).toArray();

        /*
        Метод stream - статический метод класса Arrays, в параметре метода указывается массив из которого нужно
        получить stream. После можно на этом stream вызвать метод map и проделать необходимые операции с элементами.
        К примеру, если элемент нацело делится на 3, то элемент будет разделен на 3, иначе никаких действий с элементом
        происходить не будет.

        element
                -> {
            if(element % 3 == 0) {
                element /= 3;
            }
            return element;
        }

        Это обычное Lambda выражение, тут используется больше одного statement-a, поэтому пишется в фигурных скобках.
        Из-за того, что map использует в своем параметре интерфейс Function, то необходимо возвращать элемент.

        После этой работы:

        Arrays.stream(array).map(element
                -> {
            if(element % 3 == 0) {
                element /= 3;
            }
            return element;
        })

        поток уже не 5, 9, 3, 8, 1, а -> 5, 3, 1, 8, 1.
        Для преобразования потока в массив необходимо вызвать метод toArray и присвоить это получившиеся значение
        новой переменной или же прежней.
        */
        System.out.println("Array = " + Arrays.toString(array));
        // Вывод:
        // Array = [5, 3, 1, 8, 1]

        // Применение метода map для коллекции Set:

        Set<String> set = new TreeSet<>();
        // TreeSet by default is sorted

        set.add("privet");
        set.add("kak dela?");
        set.add("OK");
        set.add("poka");
        set.add("gooooood");
        System.out.println("Set = " + set);
        // Вывод:
        // Set = [OK, gooooood, kak dela?, poka, privet]

        Set<Integer> setAfterMap = set.stream().map(e -> e.length()).collect(Collectors.toSet());
        /*
        После этой работы set.stream().map(e -> e.length()) получился на выходе поток с результатом 6, 9, 2, 4, 8.
        */
        System.out.println("setAfterMap = " + setAfterMap);
        // Вывод:
        // setAfterMap = [2, 4, 6, 8, 9]
        /*
        На выходе поток с результатом 6, 9, 2, 4, 8, а так как в итоге поток был преобразован в коллекцию TreeSet, то
        произошла сортировка от меньшего элемента к большему 2, 4, 6, 8, 9 (коллекция TreeSet содержит в себе сортированные
        значения).
        Этот же поток можно преобразовать в List и тд.
        */
        List<Integer> listAfterMap = set.stream().map(el -> el.length()).collect(Collectors.toList());
        System.out.println("listAfterMap = " + listAfterMap);
        // Вывод:
        // listAfterMap = [2, 8, 9, 4, 6]
        /*
        И так как поток был преобразован в List, то никакой сортировки не будет, в отличие от TreeSet.
        Но почему получился результат 2, 8, 9, 4, 6?
        Когда значения были добавлены в коллекцию set, TreeSet их отсортировал [OK, gooooood, kak dela?, poka, privet] и
        далее метод map работал с отсортированным Stream.
        */
    }
}