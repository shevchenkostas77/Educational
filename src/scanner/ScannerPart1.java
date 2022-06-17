package scanner;

import java.util.Scanner;

/*
Если необходимо ввести что-то в консоли, т.е. передать какую-то информацию программе через консоль, и программа должна
считать то, что было введено, так сказать сканировать, например, какое число пользователь ввел, то необходимо для
этого использовать объект класса Scanner.
Когда создается объект типа Scanner, то, обычно, в параметр конструктора вводится объект System.in:

        Scanner scanner = new Scanner(System.in);

При создании объекта типа Scanner нужно импортировать класс - import java.util.Scanner;
Что такое "in"?
Это InputStream, т.е. поток:

        public static final InputStream = null;

Например, нужно прочитать какое-то число из консоли и вывести его на экран, такой вот простой пример.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your number: ");
        int i = scanner.nextInt();
        System.out.println("Entered number: " + i);
    }
}

Запуск программы. Вывод на экран:
Enter your number: 3
Entered number: 3

Теперь, попробуем ввести два числа в консоли. Эти два числа нужно разделить, первое на второе и вывести на экран
частное и остаток.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter two numbers");
        System.out.print("Enter the first number: ");
        int x = scanner.nextInt();
        System.out.print("Enter the srcond number: ");
        int y = scanner.nextInt();
        System.out.println("Quotient of numbers: " + x / y);
        System.out.println("Result of division: " + x % y);
    }
}

Запуск программы. Вывод на экран:
Enter two numbers
Enter the first number: 19
Enter the srcond number: 5
Quotient of numbers: 3
Result of division: 4

В следующем примере попробуем ввести дробное число.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a fractional number: ");
        double d = scanner.nextDouble();
        System.out.println("Entered number: " + d);
    }
}

Запуск программы. Вывод на экран:
Enter a fractional number: 3.1415
Entered number: 3.1415

В этом примере я хотел показать, что сущестувует множество много специальных методов: nextBoolean, nextFloat, nextLong
и тд., которые соответствуют примитивным типам данных.

Теперь попробуем ввести в консоль какую-то фразу. Считывание информации из консоли будет уже не в переменную типа int,
а в String (для этого можно использовать метод nextLine).

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter some words: ");
        String s = scanner.nextLine();
        System.out.println("You wrote: " + s);
    }
}

Запуск программы. Вывод на экран:
Enter some words: Hello friend
You wrote: Hello friend

Теперь попробуем написать две строки.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write two lines");
        System.out.print("Write the firs line: ");
        String s1 = scanner.nextLine();
        System.out.print("Wtite the second line: ");
        String s2 = scanner.nextLine();
        System.out.println("You wrote on the first line: " + s1);
        System.out.println("You wrote on the second line: " + s2);
    }
}

Запуск программы. Вывод на экран:
Write two lines
Write the firs line: Hello Stas
Wtite the second line: Hello my friend!
You wrote on the first line: РHello Stas
You wrote on the second line: Hello my friend!

Рассмотрим, что делает метод next.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write a few words: ");
        String s = scanner.next();
        System.out.println("You wrote on the first line: " + s1);
    }
}

Запуск программы. Вывод на экран:
Write a few words: what a nice day!
You wrote on the first line: what

В консоли была введена фраза "what a nice day!", а в переменную s передалась не вся строка, а только одно слово -
"what". Т.к. в этом примере использовался метод next, а не nextLine, то значение было передано до первого пробела.

Теперь поработаем не с консолью. У Scanner много конструкторов. Можно сразу передать какой-то объект типа String.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner("Hello my friend!");
        System.out.println(scanner.next()); // при помощи метода next извлекаем информацию из объекта типа Scanner
    }
}

Запуск программы. Вывод на экран:
Hello

Если строка была полностью прочитана и больше читать уже нечего, а в этот момент вызвать метод, к примеру, nextLine,
то выбросится исключение - NoSuchElementException: No line found.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner("Hello my friend!\nHow are you doing?" +
                "\nWhat's new in life?");
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
    }
}

Запуск программы. Вывод на экран:
Hello my friend!
How are you doing?
What's new in life?
Exception in thread "main" java.util.NoSuchElementException: No line found
        at java.base/java.util.Scanner.nextLine(Scanner.java:1651)
        at Main.main(Main.java:172)

Чтобы не напороться на такое исключение используют цикл while.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner("Hello my friend!\nHow are you doing?" +
                "\nWhat's new in life?");
        while(scanner.hasNextLine()) { // пока есть строки, которые можно прочитать (т.е. забегает чуть вперед метод)
            System.out.println(scanner.nextLine());
        }
    }
}

Запуск программы. Вывод на экран:
Hello my friend!
How are you doing?
What's new in life?

Таких методов как: hasNextLine(), hasNext(), hasNextInt() и тд. много (методов для проверки). Кстати, метода nextChar нет.
А как тогда, например, прочитать из строки только третий символ?
Можно использовать два метода: next и charAt.

Код:

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner("Hello my friend!\nHow are you doing?" +
                "\nWhat's new in life?");
        System.out.println("The third letter of the first word of the first line: " +
                scanner.next().charAt(3));
    }
}

Запуск программы. Вывод на экран:
The third letter of the first word of the first line

*/

public class ScannerPart1 {
    public static void main(String[] agrs) {
        Scanner scanner = new Scanner("Hello my friend!\nHow are you doing?" +
                "\nWhat's new in life?");
        System.out.println("The third letter of the first word of the first line: " +
                scanner.next().charAt(3));
    }
}