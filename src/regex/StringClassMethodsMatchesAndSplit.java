package regex;

import java.util.Arrays;

/*
Два метода matches и split работают с регулярными выражениями, но их главное отличие от методов, которые работают с
Pattern и Matcher классами это performance (с англ. производительность).

        public boolean matches(String regex)
        public String[] split(String regex)

Метод matches возвращает boolean (true или false), он сверяет текст, т.е. объект типа String, на котором вызывается
метод matches, с регулярным выражением, которое задается в параметре метода.
Метод split делит текст, т.е. объект типа String, на котором вызывается метод split, на несколько частей, по какому
принципу делит, указывается в параметре метода split, и в итоге получается несколько частей, которые помещаются в
массив типа String, который и будет возвращаться методом.

Код:

public class StringClassMethodsMatchesAndSplit {
    public static void main(String[] args) {
        String s2 = "chuck@gmail.com";
        boolean result = s2.matches("\\w+@\\w+\\.(com|ru)");
        System.out.println(result);
    }
}

Запуск программы. Вывод на экран:
true

Т.е. электронный адрес "chuck@gmail.com" соответствует регулярному выражению "\\w+@\\w+\\.(com|ru)". Метод matches
необходимо использовать тогда, когда необходимо подтвердить или опровергнуть ОДНО соответствие.
Если добавить в переменную s2 еще один электронный адрес, то соответствия не будет.

Код:

public class StringClassMethodsMatchesAndSplit {
    public static void main(String[] args) {
        String s2 = "chuck@gmail.com vivanov@mail.ru";
        boolean result = s2.matches("\\w+@\\w+\\.(com|ru)");
        System.out.println(result);
    }
}

Запуск программы. Вывод на экран:
false

Задание-пример.
Необходимо поделить текст используя метод split и в качестве разделителя использовать пробел.

Код:

public class StringClassMethodsMatchesAndSplit {
    public static void main(String[] args) {
        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

        String[] array = s.split(" ");
        System.out.println(Arrays.toString(array));
    }
}

Запуск программы. Вывод на экран:
[Ivanov, Vasiliy,, Russia,, Moscow,, Lenin, street,, 51,, Flat, 48,, email:, vivanov@mail.ru,, Postcode:, AA99,, Phone,
 number:, +123456789;, Petrova, Mariya,, Ukraine,, Kiyev,, Lomonosov, street,, 33,, Flat, 18,, email:, masha@yandex.ru,,
 Postcode:, UKR54,, Phone, number:, +987654321;Chuck, Nirris,, USA,, Hollywood,, All, stars, street,, 87,, Flat, 21,,
 email:, chuck@gmail.com,, Postcode:, USA23,, Phone, number:, +136478952;]

 */

public class StringClassMethodsMatchesAndSplit {
    public static void main(String[] args) {
        String s
                = "Ivanov Vasiliy, Russia, Moscow, Lenin street, 51, Flat 48," +
                " email: vivanov@mail.ru, Postcode: AA99, Phone number: +123456789;" +
                " Petrova Mariya, Ukraine, Kiyev, Lomonosov street, 33, Flat 18," +
                " email: masha@yandex.ru, Postcode: UKR54, Phone number: +987654321;" +
                "Chuck Nirris, USA, Hollywood, All stars street, 87, Flat 21," +
                " email: chuck@gmail.com, Postcode: USA23, Phone number: +136478952;";

        String[] array = s.split(" ");
        System.out.println(Arrays.toString(array));
    }
}