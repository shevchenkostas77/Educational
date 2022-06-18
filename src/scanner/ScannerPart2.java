package scanner;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/*
Если необходимо ввести что-то в консоли, т.е. передать какую-то информацию программе через консоль, и программа должна
Оказывается, в параметр конструктора Scanner можно передавать файл! (конечно же если нужно ПРОЧИТАТЬ этот файл)

Задание-пример.
Требуется вывести УНИКАЛЬНЫЕ слова (дубликаты не нужны) в отсортированном по алфавиту порядке.
Для этого задания будет использован TreeSet, ведь он содержит только уникальные значения и содержит их упорядоченно.

Код:

public class ScannerPart2 {
    public static void main(String[] args) {
        Scanner scanner = null;
        Set<String> set = new TreeSet<>();
        try {
            scanner = new Scanner(new FileReader(
                new File("Romeo and Juliet by William Shakespeare.txt")));
            scanner.useDelimiter("\\W"); // delimiter с англ. разделитель, принимает в параметре RegEx
            while(scanner.hasNext()) {
                String word = scanner.next();
                set.add(word);
            }
            for(String word : set) {
                System.out.print(word + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // объект типа Scanner нужно закрывать
        }
    }
}

Запуск программы. Вывод на экран:
 A And Do From In Is The Two Verona What Where Which Whole alike ancient attend blood both break bury but children
 civil continuance could cross d death dignity ears end fair fatal fearful foes forth grudge hands here hours
 households if in lay life loins love lovers makes mark mend misadventured miss mutiny new nought now of our
 overthrows pair parents passage patient piteous rage remove s scene shall stage star strife strive take the their
 these to toil traffic two unclean we where which with you

Если бы в примере не использовали метод useDelimiter, то в output-e были бы вместе со словами знаки препинания.
*/

public class ScannerPart2 {
    public static void main(String[] args) {
        Scanner scanner = null;
        Set<String> set = new TreeSet<>();
        try {
            scanner = new Scanner(new FileReader(
                    new File("Romeo and Juliet by William Shakespeare.txt")));
            scanner.useDelimiter("\\W"); // delimiter с англ. разделитель, принимает в параметре RegEx
            while(scanner.hasNext()) {
                String word = scanner.next();
                set.add(word);
            }
            for(String word : set) {
                System.out.print(word + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // объект типа Scanner нужно закрывать или использовать try-with-resources
        }
    }
}