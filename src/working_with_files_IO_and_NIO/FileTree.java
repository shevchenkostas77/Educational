package working_with_files_IO_and_NIO;

import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;

/*
Метод класса Files walkFileTree.
Бывают ситуаций, что нужно совершить обход по дереву фалов или, как еще говорят, "прогуляться" по дереву файлов. Такие
ситуации возникают, когда, например, ищется какой-то файл или же какая-то папка, или же, когда необходимо удалить папку,
которая содержит файлы и/или другие папки, тогда необходимо "прогуляться" по дереву, сначала удалить все внутренние
файлы и папки, и только после этого можно удалить саму папку тоже. Еще пример, когда нужно скопировать папку с каким-то
содержимым, то нужно, так же, "прогуляться" по дереву файлов и скопировать все ее содержимое вместе с ней. Класс Files
предоставляет для решения таких задач метод walkFileTree.

Метод

        Files.walkFileTree(Path start, FileVisitor visitor);

используется для обхода дерева файлов. Этот метод принимает объект типа Path (первый параметр), т.е. путь, откуда
начнется "прогулка" по дереву файлов. Вторым параметром этого метода должен быть объект класса имплементирующий
интерфейс FileVisitor. FileVisitor - это интерфейс, имплементируя который, будет описана вся логика обхода дерева
файлов (т.е. что нужно делать программе, когда работа происходит с тем или иным файлом, или директорией).
Нужен импорт FileVisitor - import java.nio.file.FileVisitor;

Логика обхода дерева файлов заключается в классе, имплементирующем интерфейс FileVisitor.
Интерфейс FileVisitor содержит 4 метода:
1. preVisitDirectory - срабатывает перед обращением к элементам папки. Т.е. в этом методе описывается логика того, что
   необходимо сделать при входе в какую-то директорию;
2. visitFile - срабатывает при обращении к файлу. Например, в этом методе можно описать логику того, что при обращении
   к какому-то файлу его нужно скопировать;
3. postVisitDirectory - срабатывает после обращения ко всем элементам папки. В этом методе описывается логика того, что
   необходимо делать по завершению работа со всеми элементами папки;
4. visitFileFailed - срабатывает, когда файл по каким-то причинам недоступен. Например, когда нет прав на работу;

Как уже понятно из вышенаписанного, чтобы работать с walkFileTree методом необходимо создать класс, который будет
имплементировать интерфейс FileVisitor. НО, что делать, если не нужно переопределить все 4-е метода интерфейса?
Допустим, что для работы нужен всего только один метод visitFile. Разработчики Java позаботились об этом моменте, и
создали класс SimpleFileVisitor, который имплементирует интерфейс FileVisitor и в этом классе есть базовая
имплементация всех 4-х методов. Это означает, что можно создать класс, который будет наследоваться от класса
SimpleFileVisitor и переопределить необходимый(е) для работы метод(ы).

Задание-пример.
Необходимо вывести на экран информацию о всех файлах и директориях внутри какой-то одной папки. К примеру, это будет
папка с названием "Х". Итак, на рабочем столе создана папка "Х". Папка "Х" содержит внутри себя еще три папки: "Y1",
"Y2", "Z". Папка "Y1" содержит папку "О" (она пуста) и три файла "test1.txt", "test2.txt", "test3.txt". Папка "Y2"
содержит файл "test4.txt", папка "Z" пуста. В этом задании нужно создать класс ,который будет имплементировать интерфейс
FileVisitor и нужно переопределить его все 4-е метода.

Создадим класс MyFileVisitor имплементирующий интерфейс FileVisitor. FileVisitor работает с Generics, будем работать с
интерфейсом Path (нужен импорт import java.nio.file.Path;):

        public interface FileVisitor<T> {
            FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException;
            FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException;
            FileVisitResult visitFileFailed(T file, IOException exc) throws IOException;
            FileVisitResult postVisitDirectory(T dir, IOException exc)throws IOException;
        }


        class MyFileVisitor implements FileVisitor<Path> {
        }

В этом классе будет переопределено 4-е метода (preVisitDirectory, visitFile, postVisitDirectory, visitFileFailed).
Первый метод - preVisitDirectory.
В этом методе будет описана самая простенькая логика вывода на экран информация о том, что осуществляется вход в ту или
иную директорию.

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("Enter to directory " + dir);
        }

dir в System.out.println берется из параметра метода preVisitorDirectory, т.е. метод работает с директорией и будет
выводиться информация на экран, что происходит вход в ту или иную директорию. Метод возвращает FileVisitResult
(нужен импорт - import java.nio.file.FileVisitResult;).

Что такое FileVisitResult?

FileVisitResult это enum класс и он содержит 4 значения:

        public enum FileVisitResult {
            CONTINUE,
            TERMINATE,
            SKIP_SUBTREE,
            SKIP_SIBLINGS;
            }
Когда произошел вход в первую директорию, перед тем как произошла работа со всем содержимым данной директории,
вызывается метод preVisitDirectory. После вызова этого метода и срабатывания кода, который в теле этого метода, мы
должны сказать программе, что ей делать дальше и у нас есть 4 варианта, что мы можем делать дальше.
Значения FileVisitResult:
1. CONTINUE - означает, что нужно продолжить обход по файлам;
2. TERMINATE - означает, что нужно немедленно прекратить обход по файлам. Например, ищем какой-то файл, нашли искомый
файл и дальше гулять по файловому дереву не будем, смысла нет, мы уже нашли наш файл;
3. SKIP_SUBTREE - означает, что в данную директорию заходить не нужно;
4. SKIP_SIBLINGS - означает, что в данной директории продолжать обход по файлам не нужно. Sibling с англ. брат или
сестра, в данном примере sibling для папки "Y1" это папки  "Y2" и "Z". Или, еще пример, sibling для файла "test2.txt"
папка "О", "test1.txt" и "test3.txt"; Когда можно использовать SKIP_SIBLINGS? Например, когда зашли внутрь директории,
к примеру, "Y1", изучили директорию "О", "прогулялись по ней", и файл "test1.txt". После изучения файла "test1.txt"
возвращаем SKIP_SIBLINGS, это означает, что больше в директории "Y1" ничего исследовать не нужно, файлы "test2.txt" и
"test3.txt" можно пропустить и двигаться дальше.
После того, как мы выводим информацию о нашей директории, естественно, мы хотим продолжить гуляение по нашему файловому
дереву и мы возвращаем в методе return FileVisitResult.CONTINUE; Чтобы мы продолжали иследовать наши файлы и директории:

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("Enter to directory " + dir);
            return FileVisitResult.CONTINUE;
        }

Когда исследуется какой-то файл (метод visitFile) будет выводится информация о имени дальше и зададим в теле метода
инструкцию о продолжении работы:

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println("File name " + file.getFileName());
            return FileVisitResult.CONTINUE;
        }

В методе postVisitDirectory будет выводиться информация о том, что мы выходим из директории и продолжаем работу:

        @Override
        public FileVisitResult postVizitResult(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("Exit from directory " + dir);
            return FileVisitResult.CONTINUE;
        }

В методе visitFileFailed если буде какая-то ошибка при изучении файла можно выводить на экран сообение
"Error while visiting file" и давайте, если какая-то такая ошибка вылазит, т.е. у нас нет доступа на файл, давайте
заканчивать гуляние по дереву
 */

public class FileTree {
    public static void main(String[] args) {
        Path path = Path.of("/Users/shevchenkostas77/Desktop/X");
        try {
            Files.walkFileTree(path, new MyFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyFileVisitor implements FileVisitor<Path> {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Enter to directory " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("File name " + file.getFileName());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("Exit from directory " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error while visiting file " + file.getFileName());
        return FileVisitResult.TERMINATE;
    }

}