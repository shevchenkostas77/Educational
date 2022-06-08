package regex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/*
Часто используемые символы в RegEx:

7. [^d-j] - данный символ "^", стоящий в начале этих скобок, означает отрицание. Соответствует одной из букв НЕ из
диапазона d - j;
8. . - Соответствует одному любому символу. ИСКЛЮЧЕНИЕ: символ новой строки;
9. ^выражение - Соответствует выражению в начале строки;
10. выраженгие$ - Соответствует выражению в конце строки;


8. . - Соответствует одному любому символу. ИСКЛЮЧЕНИЕ: символ новой строки;

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";
        Pattern pattern = Pattern.compile("abc.");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 abcd
Position: 5 abce
Position: 10 abc5
Position: 14 abcg
Position: 19 abch

Рассматиривая данный пример, после "abc" может идти все, что угодно: и буква и цифра, НО кроме символа новой строки.


9. ^выражение - Соответствует выражению в начале строки;

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";
        Pattern pattern = Pattern.compile("^abc");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 abc

Т.е. тут идет проверка начинается ли строка, в данном случае строка "abcd abce abc5abcg6abch", с букв "abc".


10. выраженгие$ - Соответствует выражению в конце строки;

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";
        Pattern pattern = Pattern.compile("abc5abcg6abch$");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 10 abc5abcg6abch

Т.е. тут идет проверка заканчивается ли строка, в данном случае строка "abcd abce abc5abcg6abch", на "abc5abcg6abch".
Можно в примере уменьшить количество букув в шаблоне, т.е. с "abc5abcg6abch" на, к примеру, "abcg6abch" или на
"abch", т.к. все это конец строки, в данном примере. Но если написать что-то другое, к примеру, "abc", а буквы "h"
не включить в шаблон, то соответствия найдено не будут  .

Как найти в этом тексте "abcd abce abc5abcg6abch" любую цифру?
Можно написать, что ищется любая цифра из диапазона 0-9.

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 13 5
Position: 18 6

Все сработало, нашлась и 5-ка и 6-ка, НО для того, чтобы это "[0-9]" не писать, можно использовать МЕТАсимволы.

Часто используемые МЕТАсимволы в REGEX:
1. \d - Соответствует одной цифре;
2. \D - Соответствует одной НЕ цифре;
3. \w - Соответствует одной букве, цифре или "_";
4. \W - Соответствует одному символу, который НЕ буква, НЕ цифра и НЕ "_";
5. \s - Соответствует пробельному символу;
6. \S - Соответствует НЕ пробельному символу;
7. \A - Соответствует выражению в начале String-a;
8. \Z - Соответствует выражению в конце String-a;
9. \b - Соответствует границе слова или числа;
10. \B - Соответствует границе НЕ слова и НЕ числа;


1. \d - Соответствует одной цифре;

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";
        Pattern pattern = Pattern.compile("\\d"); // тоже самое, что и [0-9]
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 13 5
Position: 18 6

Т.е. это "\\d" означает соответствие любой цифры от 0 до 9. Почему в примере написано два backslash?
1. Обязательно нужно использовать backslash, а не slash, компилятор не воспримет это как ошибку, НО он будет искать в
выражении именно "/d" и тут появляется логическая ошибка, т.к. ищется любая цифра из диапазона от 0 до 9, а
на самом деле просим Java найти "/d";
2. "\\" - это простоте экранирование;


2. \D - Соответствует одной НЕ цифре;

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";
        Pattern pattern = Pattern.compile("\\D");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 a
Position: 1 b
Position: 2 c
Position: 3 d
Position: 4
Position: 5 a
Position: 6 b
Position: 7 c
Position: 8 e
Position: 9
Position: 10 a
Position: 11 b
Position: 12 c
Position: 14 a
Position: 15 b
Position: 16 c
Position: 17 g
Position: 19 a
Position: 20 b
Position: 21 c
Position: 22 h

Т.е. \D - любой символ, кроме цифр.


3. \w - Соответствует одной букве, цифре или "_";

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";
        Pattern pattern = Pattern.compile("\\w"); // тоже самое, что и "[A-Za-z0-9_]"
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 a
Position: 1 b
Position: 2 c
Position: 3 d
Position: 5 a
Position: 6 b
Position: 7 c
Position: 8 e
Position: 10 a
Position: 11 b
Position: 12 c
Position: 13 5
Position: 14 a
Position: 15 b
Position: 16 c
Position: 17 g
Position: 18 6
Position: 19 a
Position: 20 b
Position: 21 c
Position: 22 h

Т.е. соответствие ТОЛЬКО любой букве, любой цифре, символу underscore "_". Никакие другие символы сюда не входят.

4. \W - Соответствует одному символу, который НЕ буква, НЕ цифра и НЕ "_";

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd!?abce====abc +++5abcg6abch";

        Pattern pattern = Pattern.compile("\\W"); // A-Za-z0-9_
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 4 !
Position: 5 ?
Position: 10 =
Position: 11 =
Position: 12 =
Position: 13 =
Position: 17
Position: 18 +
Position: 19 +
Position: 20 +

Выражение "\W" обозначает соответстиве на любой из символов не из диапазонов "A-Za-z0-9_".

Часто используемые символы в RegEx, обозначающие количество повторений:
1. выражение? - Соответствует 0 или 1 повторению;
2. выражение* - Соответствует 0 или большему количеству повторений;
3. выражение+ - Соответствует 1 или большему количеству повторений;
4. выражение{n} - Соответствует количеству повторений "n";
5. выражение{m, n} - Соответствует количеству повторений от "m" до "n";
6. выражение{n,} - Соответствует n или большему количеству повторений;


3. выражение+ - Соответствует 1 или большему количеству повторений;

Код:

public class RegExPart2 {
    public static void main(String[] args) {
        String s = "abcd abce abc5abcg6abch";

        Pattern pattern = Pattern.compile("\\w+"); // A-Za-z0-9_
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 abcd
Position: 5 abce
Position: 10 abc5abcg6abch

Добавив в конце выражения "\w" (любого выражения) знак плюс "+", то поиск будет происходить на соответстиве по
одному или более символам входящие в диапазон A-Z и в диапазон a-z и в диапазон 0-9 и underscore symbol.


4. выражение{n} - Соответствует количеству повторений "n";

Код:

public class Main {
    public static void main(String[] args) {
        String s = "poka abc Stas dom kinos";

        Pattern pattern = Pattern.compile("\\w{4}"); // A-Za-z0-9_
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 poka
Position: 9 Stas
Position: 18 kino

Выражение "\\w{4}" говорит, что поиск на соответствие должен осуществляться в диапазнах "A-Za-z0-9_" и количество
идущих друг за другом символов должно быть равно 4-м.
*/

public class Main {
    public static void main(String[] args) {
        String s = "poka abc Stas dom kino";

        Pattern pattern = Pattern.compile("\\w{4}"); // A-Za-z0-9_
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}



//public class RegExPart2 {
//    public static void main(String[] args) {
//        String s = "abcd abce abc5abcg6abch";
//        Pattern pattern = Pattern.compile("\\w");
//        Matcher matcher = pattern.matcher(s);
//
//        while (matcher.find()) {
//            System.out.println("Position: " + matcher.start() + " " + matcher.group());
//        }
//    }
//}