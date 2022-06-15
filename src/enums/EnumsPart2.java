package enums;

import java.util.Arrays;

/*
Изменим немного enum WeekDays, добавим в него новые элементы. В enum могут содержаться переменные, методы, конструкторы.
Для каждого дня пусть будет свое настроение:

        enum WeekDays {

            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY,
            SUNDAY; // в конце лучше поставить точку с запятой

            private String mood;

            private WeekDays(String mood) {
                this.mood = mood;
            }
        }

После того, как был создан конструктор, компилятор подчеркнул красным все элементы (MONDAY, ..., SUNDAY;) enum-a
потому, что этот конструктор:

        rivate WeekDays(String mood) {
                this.mood = mood;
            }

вызывается для каждого элемента enum-a, т.е. каждому элементу enum-a в скобках нужно передать String mood:

        MONDAY(String mood),
        TUESDAY(String mood),
        WEDNESDAY(String mood),
        THURSDAY(String mood),
        FRIDAY(String mood),
        SATURDAY(String mood),
        SUNDAY(String mood);

т.е.:

        enum WeekDays {

            MONDAY("So-so"),
            TUESDAY("Good"),
            WEDNESDAY("Good"),
            THURSDAY("Good"),
            FRIDAY("Good"),
            SATURDAY("Nice"),
            SUNDAY("Nice"); // в конце лучше поставить точку с запятой

            private String mood;
            private WeekDays(String mood) {
                this.mood = mood;
            }
        }

Конструктор внутри enum-a может быть только private, даже, если это не указывать, то он будет все равно private. Если
написать public, то компилятор начнет возмущаться и не даст этого сделать. Этот конструктор срабатывает internally
(с англ. внутри), внутренне, когда элементам enum-a дается значение в скобках. Конструктор enum-a "вручную" вызвать
не получится, по этой простой причине и по причине того, что он всегда private enum не создается с помощью ключевого
слова "new":

        WeekDays w = WeekDays.FRIDAY;

Тут никакого слова "new" нет и быть не может. Конструкторов может быть много, например, можно добавить конструктор без
параметров:

        enum WeekDays {

            MONDAY("So-so"),
            TUESDAY("Good"),
            WEDNESDAY("Good"),
            THURSDAY("Good"),
            FRIDAY,
            SATURDAY("Nice"),
            SUNDAY("Nice"); // в конце лучше поставить точку с запятой

            private String mood;

            private WeekDays(String mood) {
                this.mood = mood;
            }

            private WeekDays() {
            }
        }

и тогда, допустим, для FRIDAY можно ничего не задавать.
Добавим в enum метод, например, метод get, который будет возвращать настроение. Кстати, методы можно сделать public:

        enum WeekDays {

            MONDAY("So-so"),
            TUESDAY("Good"),
            WEDNESDAY("Good"),
            THURSDAY("Good"),
            FRIDAY("Good"),
            SATURDAY("Nice"),
            SUNDAY("Nice"); // в конце лучше поставить точку с запятой

            private String mood;

            private WeekDays(String mood) {
                this.mood = mood;
            }

            public String getMood() {
                return mood;
            }
        }

Как написано выше, enum может содержать элементы, переменные, конструкторы и методы.
В методе daysInfo можно воспользоваться методом getMood:

        public class EnumsPart2 {
        public static void main(String[] args) {
            Today todayMonday = new Today(WeekDays.MONDAY);
            todayMonday.daysInfo();

            Today todayTuesday = new Today(WeekDays.TUESDAY);
            todayTuesday.daysInfo();

            Today todayWednesday = new Today(WeekDays.WEDNESDAY);
            todayWednesday.daysInfo();

            Today todayThursday = new Today(WeekDays.THURSDAY);
            todayThursday.daysInfo();

            Today todayFriday = new Today(WeekDays.FRIDAY);
            todayFriday.daysInfo();

            Today todaySuturday = new Today(WeekDays.SATURDAY);
            todaySuturday.daysInfo();

            Today todaySunday = new Today(WeekDays.SUNDAY);
            todaySunday.daysInfo();

        }
}

enum WeekDays {

            MONDAY("So-so"),
            TUESDAY("Good"),
            WEDNESDAY("Good"),
            THURSDAY("Good"),
            FRIDAY("Good"),
            SATURDAY("Nice"),
            SUNDAY("Nice");

            private String mood;

            private WeekDays(String mood) {
                this.mood = mood;
            }

            public String getMood() {
                return mood;
            }
        }

class Today {

    WeekDays weekDay;

    public Today(WeekDays weekDay) {
        this.weekDay = weekDay;
    }

    void daysInfo () {

        switch(weekDay) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                System.out.println("Gotta go to work!");
                break;
            case SATURDAY:
            case SUNDAY:
                System.out.println("You can relax :) ");
                break;
        }

        System.out.println("Mood on this day: " + weekDay.getMood());
    }
}

Запуск программы. Вывод на экран:
Gotta go to work!
Mood on this day: So-so
Gotta go to work!
Mood on this day: Good
Gotta go to work!
Mood on this day: Good
Gotta go to work!
Mood on this day: Good
Gotta go to work!
Mood on this day: Good
You can relax :)
Mood on this day: Nice
You can relax :)
Mood on this day: Nice

В методе main можно вывести на экран значение переменной weekDay любого из объектов, например, todayMonday (сработает
метод toString enum-a):

        Today todayMonday = new Today(WeekDays.MONDAY);
        todayMonday.daysInfo();
        System.out.println(todayMonday.weekDay);

Запуск программы. Вывод на экран:
Gotta go to work!
Mood on this day: So-so
MONDAY


Еще раз информация для закрепления и немного новой:
 - Конструкторы в enum имеют access modifier - private и не нуждается во внешнем вызове;
 - enum является дочерним классом для java.lang.Enum
   Вообще, после такого написания:

   enum WeekDays {

            MONDAY("So-so"),
            TUESDAY("Good"),
            WEDNESDAY("Good"),
            THURSDAY("Good"),
            FRIDAY("Good"),
            SATURDAY("Nice"),
            SUNDAY("Nice");

            private String mood;

            private WeekDays(String mood) {
                this.mood = mood;
            }

            public String getMood() {
                return mood;
            }
        }

   internally Java переводит enum в обычный класс;

 - элементы одного и того же enum можно сравнивать при помощи оператора "двойное равно":

   WeekDays w1 = WeekDays.FRIDAY;
   WeekDays w2 = WeekDays.FRIDAY;
   WeekDays w3 = WeekDays.MONDAY;
   System.out.println("w1 equals w2? " + (w1 == w2));
   System.out.println("w1 equals w3? " + (w1 == w3));

   Запуск программы. Вывод на экран:
   w1 equals w2? true
   w1 equals w3? false

   Создадим еще одни enum - WeekDays2:
   enum WeekDays2 {

           MONDAY,
           TUESDAY,
           WEDNESDAY,
           THURSDAY,
           FRIDAY,
           SATURDAY,
           SUNDAY;

        }

   и попытаемся сделать следующее сравнение:

   System.out.println(WeekDays.FRIDAY == WeekDays2.FRIDAY);

   Компилятор не позволит этого сделать потому, что разные типы - WeekDays и WeekDays2. Сравнить можно при помощи метода
   equals:

   System.out.println("WeekDays.FRIDAY equals WeekDays2.FRIDAY? " + WeekDays.FRIDAY.equals(WeekDays2.FRIDAY));

   Запуск программы. Вывод на экран:
   WeekDays.FRIDAY equals WeekDays2.FRIDAY? false

   Метод equals вернул false потому, что хоть и сами константы (FRIDAY) одинаковы, но они разных типов;

 - Часто используемые методы: valueOf и values.
   Метод valueOf позволет получать элементы enum из какого-то String значения. Например,

   WeekDays w4 = WeekDays.valueOf("MONDAY");
   System.out.println("w4 = " + w4);

   Запуск программы. Вывод на экран:
   w4 = MONDAY

   Если наспиать в параметре метода что-то не то, к примеру, слово MONDAy, то выбросится исключение -
   IllegalArgumentException, которое "скажет", что такой константы не существует в enum типа WeekDays. Т.е. метод
   valueOf позволяет создать enum из какого-то String значения, но это String значение должно быть полностью
   верно написанным.

   Метод values возвращает массив констант, которые принадлежат enum-у:

   WeekDays[] array = WeekDays.values();
   System.out.println("array = " + Arrays.toString(array));

   Запуск программы. Вывод на экран:
   array = [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY]

*/

public class EnumsPart2 {
    public static void main(String[] args) {
        Today todayMonday = new Today(WeekDays.MONDAY);
        todayMonday.daysInfo();
        System.out.println(todayMonday.weekDay);

        Today todayTuesday = new Today(WeekDays.TUESDAY);
        todayTuesday.daysInfo();
        System.out.println(todayTuesday.weekDay);

        Today todayWednesday = new Today(WeekDays.WEDNESDAY);
        todayWednesday.daysInfo();
        System.out.println(todayWednesday.weekDay);

        Today todayThursday = new Today(WeekDays.THURSDAY);
        todayThursday.daysInfo();
        System.out.println(todayThursday.weekDay);

        Today todayFriday = new Today(WeekDays.FRIDAY);
        todayFriday.daysInfo();
        System.out.println(todayFriday.weekDay);

        Today todaySuturday = new Today(WeekDays.SATURDAY);
        todaySuturday.daysInfo();
        System.out.println(todaySuturday.weekDay);

        Today todaySunday = new Today(WeekDays.SUNDAY);
        todaySunday.daysInfo();
        System.out.println(todaySunday.weekDay);

        WeekDays w1 = WeekDays.FRIDAY;
        WeekDays w2 = WeekDays.FRIDAY;
        WeekDays w3 = WeekDays.MONDAY;
        System.out.println("w1 equals w2? " + (w1 == w2));
        System.out.println("w1 equals w3? " + (w1 == w3));

        // System.out.println(WeekDays.FRIDAY == WeekDays2.FRIDAY);
        System.out.println("WeekDays.FRIDAY equals WeekDays2.FRIDAY? " + WeekDays.FRIDAY.equals(WeekDays2.FRIDAY));

        WeekDays w4 = WeekDays.valueOf("MONDAY");
        System.out.println("w4 = " + w4);

        WeekDays[] array = WeekDays.values();
        System.out.println("array = " + Arrays.toString(array));
    }
}

enum WeekDays {

    MONDAY("So-so"),
    TUESDAY("Good"),
    WEDNESDAY("Good"),
    THURSDAY("Good"),
    FRIDAY("Good"),
    SATURDAY("Nice"),
    SUNDAY("Nice");

    private String mood;

    private WeekDays(String mood) {
        this.mood = mood;
    }

    public String getMood() {
        return mood;
    }
}

enum WeekDays2 {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

}

class Today {

    WeekDays weekDay;

    public Today(WeekDays weekDay) {
        this.weekDay = weekDay;
    }

    void daysInfo () {

        switch(weekDay) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                System.out.println("Gotta go to work!");
                break;
            case SATURDAY:
            case SUNDAY:
                System.out.println("You can relax :) ");
                break;
        }

        System.out.println("Mood on this day: " + weekDay.getMood());
    }
}