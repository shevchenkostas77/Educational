package regex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/*
Задание-пример.
Необходимо найти слова в этом выражении:

        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Ivanov
Vasiliy
Russia
Moscow
Lenin
street
51
Flat
48
email
vivanov
mail
ru
Postcode
AA99
Phone
number
123456789
Petrova
Mariya
Ukraine
Kiyev
Lomonosov
street
33
Flat
18
email
masha
yandex
ru
Postcode
UKR54
Phone
number
987654321
Chuck
Nirris
USA
Hollywood
All
stars
street
87
Flat
21
email
chuck
gmail
com
Postcode
USA23
Phone
number
136478952

Вывелись все слова на экран. Естественно, запятых, двоеточий, пробелов и других каких-то символов нет потому, что все
эти символы не входят в диапазоны "A-Za-z0-9_"

Задание-пример.
Необходимо найти все номера домов и все номера квартир. Представим, что все номера домов и квартир состоят из двух
цифр.

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

        Pattern pattern = Pattern.compile("\\d{2}");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
51
48
99
12
34
56
78
33
18
54
98
76
54
32
87
21
23
13
64
78
95

Вывелись все цифры из выражения. Java нашла соответствия, которые не подходят под условие задачи. Нужно чтобы
выводилось:
51
48
33
18
87
21
Для этого нужно изменить шаблон "\\d{2}", был написан не совсем верно. Необходимо показать, что из двух цифр состоит
число, которое необходимо найти, а не просто эти две цифры являются частью чего-то другого ("AA99" тут две цифры
являются большего выражения или "+123456789;" тут две цифры являются частью большого числа). Нужно показать, что все
число состоит из двух цифр. Для подобных заданий есть метасимвол: \b - Соответствует границе слова или числа. И
необходимый для этого задания шаблон будет выглядеть следующем образом: "\\b\\d{2}\\b".
В этом шаблоне "говорится": "\\начало границы числа\\две цифры\\конец границы числа", т.е. искомое совпадение это число
состоящее из двух цифр, слева и справа от него больше нет ни чисел, ни букв.

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

        Pattern pattern = Pattern.compile("\\b\\d{2}\\b");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
51
48
33
18
87
21

На экран был выведен нужный результат.

Задание-пример.
Необходимо найти все номера телефонов из выражения.

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

        Pattern pattern = Pattern.compile("\\+\\d{9}");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
+123456789
+987654321
+136478952

Если в выражении поставить просто знак плюс, т.е. "+\\d{9}", то компилятор будет выдавать ошитбку, т.к. Java не понимает,
что это за знак плюс в начале выражения потому, что для RegEx знак плюс означает повторение одного или большего количества
раз. Чтобы из этого "суперсимвола" сделать "обычный" необходимо использовать backslach symbol - "\". Если поставить один
символ backslash-а, то компилятор будет выдвать ошибку потому, что backslach symbol в RegEx тоже имеет дургое значений.
В RegEx ГДЕ СТОИТ backslach symbol НУЖНО СТАВИТЬ ЕЩЕ ОДИН backslach symbol.

В Intellij Idea есть возможность проверить что соответствует регулянному выражению без запуска приложения.
Для этого необходимо поставить курсор на шаблон RegEx и слева появиться лампочка, навжав на нее появляется выпадающее
меню, нужно выбрать "Check RegEx" (с англ. Проверить регулярное выражение). Появится окно с двумя полями RegEx и Sample,
где RegEx это шаблон регулярного выражения, а Sample это проверяемое выражение.

Задание-пример.
Необходимо найти все электронные адреса из выражения.
1. Сначала идет n-ое количество символов (букв, цифр, underscore symbol) - "\\w+"
2. После идет символ собачка "@" - "\\w+@"
3. После символа собачки идет опять какое-то количество символов (букв, цифр, underscore symbol) - "\\w+@\\w+"
4. Далее идет обязательный символ точка, если в шаблоне просто поставить точку, то в регулярных выражениях это будет
   означать, соответствие одному любому символу. ИСКЛЮЧЕНИЕ: символ новой строки, а это не то, что нужно. Поэтому
   необходимо убрать "суперсилу" этой точки, т.е. поставить два backslash symbol перед ней - "\\w+@\\w+\\." Теперь это
   просто точка.
4. После точки идет или "com" или "ru", в данном примере. Можно снова написать "\\w+", но для разнообразия - (ru|com).
5. В итоге необходимый шаблон будет выглядеть так: "\\w+@\\w+\\.(com|ru)"

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

        Pattern pattern = Pattern.compile("\\w+@\\w+\\.(com|ru)");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
vivanov@mail.ru
masha@yandex.ru
chuck@gmail.com


5. \s - Соответствует пробельному символу;
Будем искать в выражении "poka     abc        Stas    dom kino abstrakcio" совпадение со следующим шаблоном:
"\\w\\s+\\w"
Шаблон "\\w\\s+\\w" читается как:
\\какой-то символ(буква или цифра или "_")\\пробельный символ, его какое-либо количесвто повторений, один или
больше\\какой-то символ(буква или цифра или "_").

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "poka     abc        Stas    dom kino abstrakcio";

        Pattern pattern = Pattern.compile("\\w\\s+\\w");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 3 a     a
Position: 11 c        S
Position: 23 s    d
Position: 30 m k
Position: 35 o a

Т.е. вывелись пробельные символы и то, что их ограняет. Пробельный символ это тоже метасимвол, \\s соответсвутет
диапазону [\t\n\r\f], где
\t - tab;
\n - начало новой строки;
\r - символ возврата каретки, означающий новую строку ;
\f - символ означающий окончание страницы;
\s означает набор этих [\t\n\r\f] символов.


6. \S - Соответствует НЕ пробельному символу;
Означает любой знак препинания, любая цифра, любая буква


5. выражение{m, n} - Соответствует количеству повторений от "m" до "n";

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "abcd abce3 abcfa78abcg6abchch";

        Pattern pattern = Pattern.compile("\\D{2,6}");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 abcd a
Position: 6 bce
Position: 10  abcfa
Position: 18 abcg
Position: 23 abchch

\\D - это метасимвол, означающий ЛЮБОЙ символ, но НЕ цифра. Когда пишется в фигурных скобках одна цифра, это означает
конкретное количесвто повторений, когда пишется через запятую две цифры, это означает количество повторений ЛЮБОГО
символа, т.е. не цифры, должно быть ОТ 2-х ДО 6-ти. Пробел тоже являеться символом.

Если попытаться найти по шаблону "AB{2,3}" соответствие в выражении "ABCABABDA", то количесвто повторений будет до 2-х
до 3-х будет относится только к символу 'B'. Если необходимо, чтобы количество повторений от 2-х до 3-х относилось к
выражению "AB", то это выражение берется в круглые скобки - "(AB){2,3}".

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "ABCABABDA";

        Pattern pattern = Pattern.compile("(AB){2,3}");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 3 ABAB


6. выражение{n,} - Соответствует n или большему количеству повторений;
Если после запрятой в фигурных скобках ничего не указывать - "\\D{2,6}", то нет указаний максимального количества любых
символов, НЕ цифр - "\\D{2,}". Миникальное количество любых символов - 2, максимум - сколько угодно. Прерывать
соответствия могут только цифры.

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "abcd abce3 abcfa78abcg6abchchassdcxefxv4fdfe!!!!!!!!esf3rfdf";

        Pattern pattern = Pattern.compile("\\D{2,6}");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 abcd abce
Position: 10  abcfa
Position: 18 abcg
Position: 23 abchchassdcxefxv
Position: 40 fdfe!!!!!!!!esf
Position: 56 rfdf

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "DABCDABABDABABABABD";

        Pattern pattern = Pattern.compile("D(AB){2,}"); // ищется минимум "DABAB"
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 4 DABAB
Position: 9 DABABABAB

1. выражение? - Соответствует 0 или 1 повторению;

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "DABCDABABDA";

        Pattern pattern = Pattern.compile("D(AB)?");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 DAB
Position: 4 DAB
Position: 9 D

Шаблон "D(AB)?" "говорит", что поиск будет происходить сначала по символу 'D', за которым может идти "AB" всего
лишь один раз, а может и не идти.


2. выражение* - Соответствует 0 или большему количеству повторений;

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "DABCDABABDA";

        Pattern pattern = Pattern.compile("D(AB)*");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 DAB
Position: 4 DABAB
Position: 9 D

Шаблон "D(AB)*" "говорит", что поиск будет происходить сначала по символу 'D', за которым может идти ноль или
большее количесвто повторений выраженя "AB".

7. \A - Соответствует выражению в начале String-a;

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "abcd abce3 abcfabcgabch";

        Pattern pattern = Pattern.compile("\\Aabcd");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 0 abcd

"abcd" это начало String-a. Можно оставить только букву 'a' - "\\Aa", т.к. 'a' это тоже начало String-a, в данном
случае. Если написать "\\Aabce", то соответствий найдено не будет, т.к. "abce" не начало String-a.


8. \Z - Соответствует выражению в конце String-a;

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "abcd abce3 abcfabcgabch";

        Pattern pattern = Pattern.compile("bch\\Z");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 20 bch

"bch" соответствует окончанию выражения "abcd abce3 abcfabcgabch".

Еще одно задание-пример.
Необходимо найти на соответствие первая буква из диапазона "a-d" вторая из "e-h" или же цифра из диапазона "3-8".

Код:

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "abcd abcd4 afc4ced7";

        Pattern pattern = Pattern.compile("[a-d][e-h3-8]");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}

Запуск программы. Вывод на экран:
Position: 8 d4
Position: 11 af
Position: 13 c4
Position: 15 ce
Position: 17 d7

Шаблон: \\b\\d{2}
Строка: 123456

Начинается работа:

1) КУРСОР123456 - проверяется, стоит ли курсор на границе слова/числа - ДА.
Идут ли далее две цифры - ДА. Выводим совпадение - 12

2) 12КУРСОР3456 - проверяется, стоит ли курсор на границе слова/числа - НЕТ (слева от него цифра).

3) 123КУРСОР456 - проверяется, стоит ли курсор на границе слова/числа - НЕТ (слева от него цифра).

Далее процесс повторяется по аналогии 3-му шагу.
*/

public class RegExPart3 {
    public static void main(String[] args) {

        String s = "ab333cd  18 ab7cd4 1 10 dfgaf 3 jdbask 7 afc4ced7dd10";

        Pattern pattern = Pattern.compile("\\b([1-9]|10)\\b");
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()) {
            System.out.println("Position: " + matcher.start() + " " + matcher.group());
        }
    }
}