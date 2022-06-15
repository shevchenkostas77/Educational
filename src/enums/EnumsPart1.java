package enums;

/*
Если нужно как-то ограничить пользователя в выборе какой-то информации из определенного списка, например, есть какой-то
метод, который принимает в параметр только допустимые значения - дни недели, то для этого нужен специальный класс -
enumeration (с англ. перечисление), коротко - Enum (появился с выходом Java 1.5).

enum - это способ ограничения определенного рода информации конкретным списком возможных вариантов.

Как создается enum?
Объявление перечисления происходит с помощью оператора enum, после которого идет название перечисления. Затем идет
список элементов перечисления через запятую:

        enum WeekDays {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY; // в конце лучше поставить точку с запятой
        }

Код:

public class Main {
    public static void main(String[] args) {
        Today todayMonday = new Today(WeekDays.MONDAY); // обязательно сначала пишется тип - WeekDays, затем элемент
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

            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY; // в конце лучше поставить точку с запятой

        }

class Today {

    WeekDays weekDay; // тип переменной enum

    public Today(WeekDays weekDay) {
        this.weekDay = weekDay;
    }

    void daysInfo () {

        В методе используется перечисление элементов без типа enum-a, тут даже нельзя указывать это, что элементы
        относятся к типу enum-a - WeekDays (case WeekDays.MONDAY:), компилятор не даст это сделать. Но, а как тогда
        Java понимает к чему относятся элементы перечисления?
        Java видит, что в switch помешена переменная с названием weekDay, а тип этой переменной - WeekDays

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
    }
}

Запуск программы. Вывод на экран:
Gotta go to work!
Gotta go to work!
Gotta go to work!
Gotta go to work!
Gotta go to work!
You can relax :)
You can relax :)

Теперь в конструктор класса Today никакой, к примеру, "privet" уже не написать, никакое число или же WeekDays.PRIVET и
тд. Таким образом enum делает код type safe.

В данном примере enum определен, как какой-то класс. Его можно создать в отдельном файле или же внутри класса, т.е. он
будет nested enum (с англ. вложенное перечисление). НО, например, внутри метода, как локальные классы создаются, enum
нельзя указать.
*/

public class EnumsPart1 {
    public static void main(String[] args) {
        Today todayMonday = new Today(WeekDays.MONDAY); // обязательно сначала пишется тип - WeekDays, затем элемент
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

//enum WeekDays {
//
//    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY; // в конце лучше поставить точку с запятой
//
//}

//class Today {
//
//    WeekDays weekDay; // тип переменной enum
//
//    public Today(WeekDays weekDay) {
//        this.weekDay = weekDay;
//    }
//
//    void daysInfo () {
//        /*
//        В методе используется перечисление элементов без типа enum-a, тут даже нельзя указывать это, что элементы относятся
//        к типу enum-a - WeekDays (case WeekDays.MONDAY:), компилятор не даст это сделать. Но, а как тогда Java понгимает к
//        чему относятся элементы перечисления?
//        Java видит, что в switch помешена переменная с названием weekDay, а тип этой переменной - WeekDays
//
//        */
//        switch(weekDay) {
//            case MONDAY:
//            case TUESDAY:
//            case WEDNESDAY:
//            case THURSDAY:
//            case FRIDAY:
//                System.out.println("Gotta go to work!");
//                break;
//            case SATURDAY:
//            case SUNDAY:
//                System.out.println("You can relax :) ");
//                break;
//        }
//    }
//}