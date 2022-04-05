package stream;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Метод forEach.
Это совершенно не тот метод, который используется напрямую, вызвав его из коллекции.
Метод forEach возвращает void (т.е. ничего не возвращает), таким образом после него нельзя создать, например,
List при помощи метода collect. List и тому подобное возмодно создать при помощи метода collect из stream-a.
Метод forEach помогает пройтись по всем элементам и, например, показывать (выводить на экран) эти элементы.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamForEach {
    public static void main(String[] agrs) {
        int[] array = {5, 9, 3, 8, 1};
        /*
        Чтобы получить из массива stream, необходимо вызвать статический метод stream из класса Arrays и передать
        в параметр методу сам массив.
        */
        Arrays.stream(array).forEach(el -> {
            el *= 2;
            System.out.print(el + " ");
        });
        /*
        Нет необходимости после метода forEach преобразовывать stream, который получился из массива в массив.
        Можно сразу запустить.
        */
        // Вывод:
        // 10 18 6 16 2
        System.out.println();

        // Такую запись можно сократить:
        Arrays.stream(array).forEach(el -> System.out.println(el));
        Arrays.stream(array).forEach(System.out::println);
        /*
        System.out::println - называется метод референс, т.е. передается ссылка на метод println().
        Этот метод находится в System.out и Java понимает, что параметром для метода println будет
        каждый элемент stream-a (Arrays.stream(array)). Java ищет метод println в System.out и в
        параметр подставляет каждый элемент и выводит на экран.
        */
        // Вывод:
        // 5
        // 9
        // 3
        // 8
        // 1
        /*
        Разницы в логике нет между этим вариантном написания:
        Arrays.stream(array).forEach(el -> System.out.println(el));
        и этим:
        Arrays.stream(array).forEach(System.out::println);
        Т.е. до двух двоеточий указывается класс, после двух двоеточий метод.

        Еще пример по синтаксису двух двоеточий (метод референс):
        Есть класс и у него есть статический метод myMethod.
        class Utils {
            public static void myMethod(int a) {
                a += 5;
                System.out.println("The new element value = " + a);
            }
        }

        Arrays.stream(array).forEach(Utils::myMethod);
        Выше строкой создается stream из массива и каждый элемент потока помещается в метод myMethod, в качестве
        параметра, который находится в классе Utils. Java понимает, что в классе Utils есть myMethod, в качестве
        параметра он принимает int a и в данном случае поток содержит элементы типа int, поэтому такая запись
        возможна (метод референс - ссылка на метод).
        Это просто короткое написание lambda выражения:
        Arrays.stream(array).forEach(el -> Utils.myMethod(el));
        */

        /*
        Необязательно создавать stream из готовой коллекции или из готового массива, можно создать его "с нуля".
        */
        Student st1 = new Student("Ivan", 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Mariya", 'm', 23, 3, 7.4);

        Stream<Student> myStream = Stream.of(st1, st2, st3, st4, st5);
        // myStream - это уже stream, поэтому не нужно вызывать метод stream на нем.
        System.out.println(myStream.filter(el ->
                el.getAge() > 22 && el.getAverageGrade() < 7.2).collect(Collectors.toList()));
//        Вывод:
//        [Student {name = 'Nikolay', sex = m, age = 28, course = 2, average grade = 0.0},
//            Student {name = 'Petr', sex = m, age = 35, course = 4, average grade = 0.0},
//            Student {name = 'Mariya', sex = m, age = 23, course = 3, average grade = 0.0}]

    }
}