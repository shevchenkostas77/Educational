package regex;

/*
Для того, чтобы вывести на экран какое-то сообщение часто используют метод print или метод println.
Но есть еще один метод и он называется printf. С помощью этого метода можно выводить на экран данные в нужном формате.
Рассмотрим метод printf, который принимает два параметра (String и varargs) - pritnf(String, Object...).
Varargs - аргументы переменной длины: фича, которая появилась еще в JDK5 позволяют нам создавать методы с
произвольным количеством аргументов.
Создадим вспомогательный класс с названием Employee:

        class Employee {
            int id;
            String name;
            String surname;
            int salary;
            double bonusPct;

            public Employee(int id, String name, String surname, int salary, double bonusPct) {
                this.id = id;
                this.name = name;
                this.surname = surname;
                this.salary = salary;
                this.bonusPct = bonusPct;
            }
        }

Вот такой просто класс. В классе PrintfAndFormatMethods создадим метод employeeInfo, который ничего не возвращает, а
просто выводит информацию на экран при помощи метода printf:

        static void employeeInfo(Employee emp) {
            System.out.printf("", emp.id, emp.name, emp.surname, emp.salary*(1+emp.bonusPct));
        }

Первым параметром в методе printf (где стоят пустые двойные кавычки - "") задается формат вывода информации на экран.
Шаблон спецификаторов формата выглядит следующим образом:

        "%[flags][width][.precision]datatype_specifier"

Обязательных полей здесь два: знак процента - "%" и datatype_specifier. Важно соблюдать очередность, как в шаблоне выше,
т.е. сначала идет знак процента, затем указывается нужный флаг, нужная ширина, необходимая точность, спецификатор типа
данных.

flags - флаг представляет собой один символ, который означает какую-то характеристику, например, выравнивание.
        Основные флаги:
        "-" - выравнивание по левому карю;
        "0" - добавление нулей перед числом. !!!Работает, когда работа происходит с числами, а не со строками;
        "," - разделитель разрядов в числах. !!!Работает, когда работа происходит с числами, а не со строками;

width - означает минимальное количество символов, которое будет определено для текста. Если input, окажется короче, чем
        указаная ширина, допустим, слова из 5-ти букв, а указана ширина 3-и, то это слово все равно будет состоять их
        5-ти символов и выделится ширина в 5-ть символов, а не в 3 символа.

.precision - это точность. При помощи .precision можно округлять дробные числа. !!!Работает, когда работа происходит с
числами, а не со строками.

datatype_specifier - спецификатор типа данных, необходимо указать с каким типом данных будет происходить работа:
                     "b" - boolean;
                     "c" - character;
                     "s" - String;
                     "d" - decimal, целое число;
                     "f" - дробное десятичное число;

Зададим формат для работы с id работника - "%03d", где d - работа будет происходить с целыми числами, 3 - выделяется
три символа для id, 0 - означает, что если id работника равен единице, т.е. одному символу, то перед единицей будет
стоять два нуля (свободное место будет заполнено нулями, было то выделено 3 символа для id):

        static void employeeInfo(Employee emp) {
            System.out.printf("%03d", emp.id, emp.name, emp.surname, emp.salary*(1+emp.bonusPct));
        }

Затем, допустим, необходимо между значенями (между id и именем работника), чтобы было несколько пробелов или Tab:

        static void employeeInfo(Employee emp) {
            System.out.printf("%03d \t ", emp.id, emp.name, emp.surname, emp.salary*(1+emp.bonusPct));
        }

Символ Tab ("\t") не имеет отношения к printf, т.е. это не специальный символ метода printf.
Далее зададим формат для имени работника:

        static void employeeInfo(Employee emp) {
            System.out.printf("%03d \t %-12s", emp.id, emp.name, emp.surname, emp.salary*(1+emp.bonusPct));
        }

После символа "\t" снова пишется знак процента - %, знак "-" (флаг), означающий, что будет выравнивание по левому караю,
число "12", означающее минимальное количество символов, которое будет определено для текса и "s" - тип данных String.
Такой же формат и для фамилии:

        static void employeeInfo(Employee emp) {
            System.out.printf("%03d  \t %-12s \t %-12s", emp.id, emp.name, emp.surname, emp.salary*(1+emp.bonusPct));
        }

Ну, и наконец, зададим формат для зарплаты с бонусной частью работника:

        static void employeeInfo(Employee emp) {
            System.out.printf("%03d  \t %-12s \t %-12s \t %,.1f \n"
                    , emp.id, emp.name, emp.surname, emp.salary*(1+emp.bonusPct));
        }

".1" (.presition) - округление до одного символа после запятой;
"," (флаг) - разделитель разрядов в числах;
"f" (datatype_specifier) - тип данных float;

\n" - символ новой строки, не специальный символ метода pritnf.
Метод employeeInfo готов, теперь создадим трех работников, для примера будет достаточно, и передадим каждого работника
в метод employeeInfo.

Код:

public class PrintfAndFormatMethods {

    static void employeeInfo(Employee emp) {
        System.out.printf("%03d \t %-12s \t %-12s \t %,.1f \n"
                , emp.id, emp.name, emp.surname, emp.salary*(1+emp.bonusPct));
    }

    public static void main(String[] args) {
        employeeInfo(new Employee(1, "Stas", "Shevchenko", 12346, 0.16));
        employeeInfo(new Employee(15, "Ivan", "Petrov", 6542, 0.08));
        employeeInfo(new Employee(123, "Mariya", "Sidorova", 8542, 0.12));
    }
}

class Employee {
    int id;
    String name;
    String surname;
    int salary;
    double bonusPct;

    public Employee(int id, String name, String surname, int salary, double bonusPct) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.bonusPct = bonusPct;
    }
}

Запуск программы. Вывод на экран:
001      Stas            Shevchenko      14,321.4
015      Ivan            Petrov          7,065.4
123      Mariya          Sidorova        9,567.0

Получился вот такой output. Варианты вывода зарплаты сотрудника:
%03d     %-12s           %-12s           %,.1f          %.1f        %f
001      Stas            Shevchenko      14,321.4       14321.4     14321.360000
015      Ivan            Petrov          7,065.4        7065.4      7065.360000
123      Mariya          Sidorova        9,567.0        9567.0      9567.040000

Метод printf может выбрасывать исключение, например, когда в формате задано дробное число, а передается строка -
IllegalFormatConversionException.

Такие флаги как: "-" - выравнивание по левому краю, "0" - добавление нулей перед числом, они работают только тогда,
когда задается ширина.

В параметре формата можно писать и обычные слова:

        static void employeeInfo(Employee emp) {
            System.out.printf("%03d \t Hello %-12s \t %-12s \t %,.1f \n"
                    , emp.id, emp.name, emp.surname, emp.salary*(1 + emp.bonusPct));
        }

Запуск программы. Вывод на экран:
001      Hello Stas              Shevchenko      14,321.4
015      Hello Ivan              Petrov          7,065.4
123      Hello Mariya            Sidorova        9,567.0

%03d     Hello %-12s             %-12s           %,.1f

Т.е. можно писать не только специальные символы, а и любой текст.

Метод format.
Если необходимо, чтобы текс не просто выводился в необходимом формате на экран, но и хранился в таком же виде, нужно
использовать метод format. Метод format принадлежит классу String.

        String newString = String.format("%03d \t Hello %-12s \t %-12s \t %,.1f \n"
                , 1, "Stas", "Shevchenko", 12346 * (1 + 0.16));
        System.out.println(newString);

Запуск программы. Вывод на экран:
001      Hello Stas              Shevchenko      14,321.4
*/

public class PrintfAndFormatMethods {

    static void employeeInfo(Employee emp) {
        System.out.printf("%03d \t Hello %-12s \t %-12s \t %,.1f \n"
                , emp.id, emp.name, emp.surname, emp.salary*(1 + emp.bonusPct));
    }

    public static void main(String[] args) {
        employeeInfo(new Employee(1, "Stas", "Shevchenko", 12346, 0.16));
        employeeInfo(new Employee(15, "Ivan", "Petrov", 6542, 0.08));
        employeeInfo(new Employee(123, "Mariya", "Sidorova", 8542, 0.12));

        String newString = String.format("%03d \t Hello %-12s \t %-12s \t %,.1f \n"
                , 1, "Stas", "Shevchenko", 12346 * (1 + 0.16));
        System.out.println(newString);
    }
}

class Employee {
    int id;
    String name;
    String surname;
    int salary;
    double bonusPct;

    public Employee(int id, String name, String surname, int salary, double bonusPct) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.bonusPct = bonusPct;
    }
}