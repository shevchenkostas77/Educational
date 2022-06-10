package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
REGular EXpressions (с англ. регулярные выражения), по другому еще называют REGEX из-за первый букв.
Для чего же нужны регулярные выражения?
Регулярные выражения необходимы для создания шаблонов, с помощью которых производят такие операции, как поиск,
сравнение, замена.

Создадим строку:

        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

Возникает вопрос: Зачем нужны регулярные выражения, если те же операции, например, операция поиска возможна при помощи
методов класса String?

Когда происходит работа с поиском используя методы класса String, которые ищут что-то, это что-то является КОНКРЕТНОЙ
ПОДСТРОКОЙ. Т.е. если нужно найти в переменной s слово "Lomonosov" при помощи методов класса String, то это без проблем
удастся. Но если нужно найти все, к примеру, электронные адреса из данной строки s, а конкретные электронные адреса
не знаете (чтобы можно было найти через методы класса String), то нужно использовать регулярные выражения. Но должно
быть понимание, как выглядит шаблон того, что ищете, к примеру, тех же электронных адресов, т.е. в начале это какое-то
имя, потом идет символ "собака" - '@', далее должно быть указано доменное имя, к примеру, mail, google или что-то
другое, после обязательное наличие точки и в конце "ru", "com" и другие возможные варианты (например, vivanov@mail.ru).
Т.е. зная шаблон того, что нужно найти с помощью регулярных выражений можно осуществить необходимый поиск. Вот для чего
нужны регулярные выражения. Таким образом, работа с регулярными выражениями порой является намного более мощной
"штукой", чем работа с конкретными значениями String.

Регулярные выражения - это совокупность символов (каких-то char-ов), некоторые из которых являются специальными -
метасимволами, т.е. обладают каким-то функционалом.

Чтобы создать шаблон регулярного выражения необходимо использовать класс Pattern (import java.util.regex.Pattern;).
Pattern с англ. шаблон. Создается шаблон следующим образом:

        Pattern pattern1 = Pattern.compile("ABCD");

При создании шаблона не используется конструктор, т.к. конструктор класса Pattern имеет access modifier - private. При
создании шаблона используется метод compile, который возвращает объект типа Pattern. В метод compile передается сам
шаблон регулярного выражения, в данном случае оно очень простое - ABCD. Написание регулярного выражения - это написание
правила, которому должны соответствовать искомые значения. Т.е. искомое значение "ABCD" будет искаться в какой-то
строке, к примеру, в следующей строке:

        String s1 = "ABCD ABCE ABCFABCGABCH";

Не трудно догадаться, что в этом примере всего одно совпадение, в самам начале "->ABCD<- ABCE ABCFABCGABCH".

Шаблон с искомым значением есть, строке в которой нужно найти искомое значение есть, теперь нужно как-то попросить
программу выполнить поиск. Для этого нужно воспользоваться классм Matcher (import java.util.regex.Matcher;):

        Matcher matcher = pattern1.matcher(s1);

Matcher ищет соответствие. Метод matcher создает обьект типа Matcher, ищущий в тексте "ABCD ABCE ABCFABCGABCH"
соответствие шаблону, в данном случае шаблону pattern1. Т.е. этот метод возвращает логическое значение true, если
последовательность символов совпадает с шаблоном, а иначе - логическое значение false. Следует, однако, иметь в виду,
что с шаблоном должна совпадать вся последовательность символов, а не только ее часть (т.е. подпоследовательность).

Далее создадим цикл while с методами find и group:

        while (matcher.find()) {
            System.out.println(matcher.group());
        }

В цикле на объекте matcher вызывается метод find, который возвращает  логическое значение true, если какое-то совпадение
было найдено, а если было найдено несколько совпадений, то этот метод несколько раз вернет логическое значение true,
после чего, когда совпадения закончатся он будет возвращать false. При каждом вызове метода find() сравнение начинается
с того места, где было завершено предыдущее сравнение. Поэтому этот метод можно использовать в цикле while и при помощи
метода group на экран выводить совпадения.

Классы Pattern и Matcher. Как создаются:

import java.util.regex.Matcher;
import java.util.regex.Pattern;

String myString = "Hello REGEX world";

Pattern myPattern = Pattern.compile("REGEX");
Matcher myMatcher = myPattern.matcher(myString);

Код:

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "ABCD ABCE ABCFABCGABCH";

        Pattern pattern1 = Pattern.compile("ABCD");

        Matcher matcher = pattern1.matcher(s1);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
ABCD

Найдено одно совпадение.

Вопрос: Зачем нужны классы Pattern и Matcher, если у класса String есть некоторые методы, которые так и так работаю с
регулярными выражениями. Если отвечать одним словом, то ответ это performance (с англ. производительность) кода.
Работая с классами Pattern и Matcher производительность регулярных выражений будет в разы выше, чем, если работать
просто с методами класса String, которые поддерживают регулярные выражения.

Часто используемые символы в RegEx:

1. abc - Соответствует последовательно идущим abc;
2. [abc] - Соответствует или a, или b, или c;
3. [d-j] - Соответствует одной из букв из диапазона d - j;
4. [3-8] - Соответствует одной из цифр из диапазона 3 - 8;
5. [B-Fd-j3-8] - Соответствует одной из букв из обоих диапазонов B-F и d-j или одной из цифр из диапазона 3 - 8;
6. a|b - Соответствует либо букве a, либо букве b;


1. abc - Соответствует последовательно идущим abc.

Код:

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "ABCD ABCE ABCFABCGABCH";
        Pattern pattern1 = Pattern.compile("ABC");

        Matcher matcher = pattern1.matcher(s1);
        while (matcher.find()) {
        // Чтобы узнать на какой позиции найдено соответствие необходимо воспользоваться методом start
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 ABC
Position: 5 ABC
Position: 10 ABC
Position: 14 ABC
Position: 18 ABC


2. [abc] - Соответствует или a, или b, или c.

Код:

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "OPABMNCD";
        Pattern pattern1 = Pattern.compile("[ABC]");

        Matcher matcher = pattern1.matcher(s1);
        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 2 A
Position: 3 B
Position: 6 C

Когда указывается в квадратных скобках какие-либо символы, в данном случае "[ABC]", означает, что шаблон с искомым
значением это или A, или B, или С.


3. [d-j] - Соответствует одной из букв из диапазона d - j

Код:

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "ABDOP";
        Pattern pattern1 = Pattern.compile("AB[C-F]OP");

        Matcher matcher = pattern1.matcher(s1);
        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 ABDOP

"AB[C-F]OP" это выражение "говорит", что шаблон искомого значения начинается с букв "AB", потом должна быть одна любая
буква из диапазона "C-F" (буква "D" находится в этом диапазоне) и заканчивается шаблон искомого значения двумя буквами
"OP". Если в s1 поставить, допустим, букву I, то никакого соответствия найдено не будет, т.к. внутри этого текста
"AIBDOP" шаблона "AB[C-F]OP" нет. Или же, если вместо буквы "D" в тексте "AIBDOP" поместить букву не из диапазона
"C-F", а допустим, букву "М", то тоже соответствия никакого найдено не будет. Или же, допустим, поставить вместо буквы
"D" любую цифру - "AIB7OP", то тут так же соответствия с шаблоном отсутствует и ничего найдено не будет.


4. [3-8] - Соответствует одной из цифр из диапазона 3 - 8.
В квадратных скобках можно написать не только буквы, но и цифры тоже. Т.е. диапазон ([]) работает
с цифрами тоже.


5. [B-Fd-j3-8] - Соответствует одной из букв из обоих диапазонов B-F и d-j или одной из цифр из диапазона 3 - 8.
Можно миксовать диапазоны. Т.е. что-то одно из B-F или d-j или 3-8. Все вот это выражение [B-Fd-j3-8] соответствует
одной из букв из диапазонов B-F, d-j или одной из цифр из диапазона 3-8. Между диапазонами никаки запятых или каких либо
других разделителей нет - [B-Fd-j3-8].

Код:

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "abcd abce abc5abcg6abch";
        Pattern pattern1 = Pattern.compile("abc[e-g4-7]");

        Matcher matcher = pattern1.matcher(s1);
        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 5 abce
Position: 10 abc5
Position: 14 abcg

Т.е. в шаблоне "abc[e-g4-7]" было указано, что нужно искать сначала на соответствие с abc, далее должна быть одна буква
из диапазона e-g ИЛИ должна быть одна цифра из диапазона 4 - 7. Если внутри квадратных скобок, в самом начале квадратных
скобок, поставить символ "^", этот символ означет "отрицание", т.е. такой шаблон "abc[^e-g4-7]" будет читаться сначала
идут три символа abc, потом идет один символ не входящий не в один из диапазонов: e-g и 4-7.

Код:

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "abcd abce abc5abcg6abch";
        Pattern pattern1 = Pattern.compile("abc[^e-g4-7]");

        Matcher matcher = pattern1.matcher(s1);
        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 abcd
Position: 19 abch


6. a|b - Соответствует либо букве a, либо букве b

Код:

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "abcd abce abc5abcg6abch";
        Pattern pattern1 = Pattern.compile("abc(e|5)");

        Matcher matcher = pattern1.matcher(s1);
        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 5 abce
Position: 10 abc5

В данном примере поиск сначала идет по символам abc, а далее поиск будет по 4-ому символу по символу 'e' или '5'
*/

// public class RegExPart1 {
//     public static void main(String[] args) {
//         String s
//                 = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
//                 " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
//                 " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
//                 " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
//                 "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
//                 " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";
//     }
// }

public class RegExPart1 {
    public static void main(String[] args) {

        String s1 = "abcd abce abc5abcg6abch";
        Pattern pattern1 = Pattern.compile("abc(e|5)");

        Matcher matcher = pattern1.matcher(s1);
        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}