package regex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/*
Метод replaceAll.
Есть строка String s1 = "Privet,      moy  drug!  Kak idet izuchenie       Java        ?";
Метод replaceAll принимает regular expression. При помощи данного метода в строке s1 необходимо заменить все
повторяющиеся пробелы на один. Для этого в параметрах метода replaceAll в первом параметре необходимо указать, что будет
заменены все повторяющиеся пробелы, т.е. от 2-х и более - " {2,}", на один пробел, который указывается во втором
параметре метода - " ".

Код:

public class ReplaceAllAndGroupMethods {
    public static void main(String[] args) {
        String s1 = "Privet,      moy  drug!  Kak idet izuchenie       Java        ?";
        System.out.println(s1);
        s1 = s1.replaceAll(" {2,}", " ");
        System.out.println(s1);
    }
}

Запуск программы. Вывод на экран:
Privet,      moy  drug!  Kak idet izuchenie       Java        ?
Privet, moy drug! Kak idet izuchenie Java ?

Еще один вариант replaceAll.
Необходимо заменить слова начинающиеся на "i", после может идти сколько угодно символов, - "\\bi\\w+" на, к примеру,
четыре четверки - "4444".

Код:

public class ReplaceAllAndGroupMethods {
    public static void main(String[] args) {
        String s1 = "Privet,      moy  drug!  Kak idet izuchenie       Java        ?";
        System.out.println(s1);
        s1 = s1.replaceAll("\\bi\\w+", "4444");
        System.out.println(s1);
    }
}

Запуск программы. Вывод на экран:
Privet,      moy  drug!  Kak idet izuchenie       Java        ?
Privet,      moy  drug!  Kak 4444 4444       Java        ?

Если использовать в данном примере метод replaceFirst, то замениться только первое слово - "idet" на четыре четверки.

Код:

public class ReplaceAllAndGroupMethods {
    public static void main(String[] args) {
        String s1 = "Privet,      moy  drug!  Kak idet izuchenie       Java        ?";
        System.out.println(s1);
        s1 = s1.replaceFirst("\\bi\\w+", "4444");
        System.out.println(s1);
    }
}

Запуск программы. Вывод на экран:
Privet,      moy  drug!  Kak idet izuchenie       Java        ?
Privet,      moy  drug!  Kak 4444 izuchenie       Java        ?

Пример с использованием метода replaceAll класса Matcher.
Есть строка:

        String s1 = "12345678912345670325987;" +
                "98765432165498750921654;" +
                "85274196345612381122333";

Строка состоит из трех равных по количеству цифр чисел разделенных точкой с запятой. Это информация о банковских картах:
12345678912345670325987 -> номер банковской карты: 1234 5678 9123 4567, срок действия карты: 03/25, CVV: 987.
Необходимо поменять формат текста и сделать в удобочитаемом виде, например: 03/25 1234 5678 9123 4567 (987).
Чтобы преобразовать число необходимо разбить это число, состоящее из 23 цифр, на подгруппы:
1. сначала это четыре числа по четыре цифры - (\\d{4})(\\d{4})(\\d{4})(\\d{4})
2. потом две цифры месяца - (\\d{2})
3. потом две цифры года - (\\d{2})
4. и три цифры CVV кода - (\\d{3})
В итоге, получится шаблон:

        Pattern pattern = Pattern.compile("(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{2})(\\d{2})(\\d{3})");

Далее необходимо воспользоваться методом matcher, который найде необходимые совпадения, т.е. разобьет число на подгруппы
по определенному шаблону:

        Matcher matcher = pattern.matcher(s1);

После необходимо воспользоваться методом replaceAll класса Matcher, который поможет отформатировать число из 23 цифр
используя группы, на которые было поделено число в необходимый формат:

        (\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{2})(\\d{2})(\\d{3})
Группы №:  1       2       3       4       5       6       7
Группы обозначаются выражения взятые в круглые скобки.

Чтобы указать нужную последовательность групп в методе replaceAll, необходимо написать сначала символ "$" и номер нужной
группы:

        String newS1 = matcher.replaceAll("$5/$6 $1 $2 $3 $4 ($7)");

Код:

public class ReplaceAllAndGroupMethods {
    public static void main(String[] args) {
        String s1 = "12345678912345670325987;" +
                "98765432165498750921654;" +
                "85274196345612381122333";
        Pattern pattern = Pattern.compile("(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{2})(\\d{2})(\\d{3})");
        Matcher matcher = pattern.matcher(s1);

        String newS1 = matcher.replaceAll("$5/$6 $1 $2 $3 $4 ($7)");
        System.out.println(newS1);
    }
}

Запуск программы. Вывод на экран:
03/25 1234 5678 9123 4567 (987);09/21 9876 5432 1654 9875 (654);11/22 8527 4196 3456 1238 (333)

Если написать следующий код:
public class ReplaceAllAndGroupMethods {
    public static void main(String[] args) {
        String s1 = "12345678912345670325987;" +
                "98765432165498750921654;" +
                "85274196345612381122333";
        Pattern pattern = Pattern.compile("(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{2})(\\d{2})(\\d{3})");
        Matcher matcher = pattern.matcher(s1);

        while(matcher.find()){
            System.out.println(matcher.group()); <--- ВНИМАНИЕ СЮДА!
        }
    }
}

возникает вопрос: почему выводится информация на экран при помощи метода с названием "group"? Какое-то не логичное
название у метода. Почему получаем информацию при помощи метода group, а не get, например?
Когда метод group используется без аргумента, он возвращает полное соответствие. Давайте напишем аргумент, а в аргумент
можно поставить номер группы, которую хотим увидеть, например, группу 7-мь:

Код:

public class ReplaceAllAndGroupMethods {
    public static void main(String[] args) {
        String s1 = "12345678912345670325987;" +
                "98765432165498750921654;" +
                "85274196345612381122333";
        Pattern pattern = Pattern.compile("(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{2})(\\d{2})(\\d{3})");
        Matcher matcher = pattern.matcher(s1);

        while(matcher.find()){
            System.out.println(matcher.group(7));
        }
    }
}

Запуск программы. Вывод на экран:
987
654
333

Вывелись только CVV коды банковских карт (последние три цифры числа).
По умолчанию, т.е. без указания аргумента, метод group выводит нулевую группу, т.е. полное соответствие.
 */

public class ReplaceAllAndGroupMethods {
    public static void main(String[] args) {
        String s1 = "12345678912345670325987;" +
                "98765432165498750921654;" +
                "85274196345612381122333";
        Pattern pattern = Pattern.compile("(\\d{4})(\\d{4})(\\d{4})(\\d{4})(\\d{2})(\\d{2})(\\d{3})");
        Matcher matcher = pattern.matcher(s1);

        while(matcher.find()){
            System.out.println(matcher.group(7));
        }
    }
}