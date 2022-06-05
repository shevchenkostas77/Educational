package working_with_files_IO_and_NIO;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

/*
Задание-пример.
Необходимо скопировать папку "Х" со всем ее содержимым в папку CopyHere. На рабочем столе создана папка "Х". Папка "Х"
содержит внутри себя еще три папки: "Y1", "Y2", "Z". Папка "Y1" содержит папку "О" (она пуста) и три файла "test1.txt",
"test2.txt", "test3.txt". Папка "Y2" содержит файл "test4.txt", папка "Z" пуста.

Для копирования не пустых папок нужно "прогуляться" по дереву, а для этого нужно создать класс, который будет
имплементировать интерфейс FileVisitor или же наследовать класс SimpleFileVisitor. В данном примере будет создан класс,
который наследуется от класса SimpleFileVisitor потому, что из 4-х методов интерфейса FileVisitor будут
использованы всего два: preVisitDirectory (для того, чтобы копировать саму папку) и visitFile (для того, чтобы
копировать сам файл).

Создадим класс MyFileVisitorForCopy наследуемый от класса SimpleFileVisitor (нужен импорт класса -
import java.nio.file.SimpleFileVisitor;). Класс SimpleFileVisitor работает с Generics, будем работать с интерфейсом
Path (нужен импорт import java.nio.file.Path;):

        public class SimpleFileVisitor<T> implements FileVisitor<T> {

            protected SimpleFileVisitor() {
                    }

            @Override
            public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException {
                Objects.requireNonNull(dir);
                Objects.requireNonNull(attrs);
                return FileVisitResult.CONTINUE;
            }


            @Override
            public FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException {
                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);
                return FileVisitResult.CONTINUE;
            }


            @Override
            public FileVisitResult visitFileFailed(T file, IOException exc) throws IOException {
                Objects.requireNonNull(file);
                throw exc;
             }

            @Override
            public FileVisitResult postVisitDirectory(T dir, IOException exc) throws IOException {
                Objects.requireNonNull(dir);
                if (exc != null)
                    throw exc;
                return FileVisitResult.CONTINUE;
            }
        }

        class MyFileVisitorForCopy extends SimpleFileVisitor<Path> {
        }

Как выглядит сам класс SimpleFileVisitor:

            public class SimpleFileVisitor<T> implements FileVisitor<T> {

            protected SimpleFileVisitor() {
                    }

            @Override
            public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException {
                Objects.requireNonNull(dir);
                Objects.requireNonNull(attrs);
                return FileVisitResult.CONTINUE;
            }


            @Override
            public FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException {
                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);
                return FileVisitResult.CONTINUE;
            }


            @Override
            public FileVisitResult visitFileFailed(T file, IOException exc) throws IOException {
                Objects.requireNonNull(file);
                throw exc;
             }

            @Override
            public FileVisitResult postVisitDirectory(T dir, IOException exc) throws IOException {
                Objects.requireNonNull(dir);
                if (exc != null)
                    throw exc;
                return FileVisitResult.CONTINUE;
            }
        }

Для того, чтобы производить процесс копирования, необходимо знать откуда будет копироваться файл или папка и куда будет
скопировано. Создадим два поля:

        Path source; // откуда будет идти копирование
        Path destination; // куда будет копироваться

и эти два поля передадим в конструктор MyFileVisitorForCopy:

        public MyFileVisitorForCopy(Path source, Path destination) {
        this.source = source;
        this.destination = destination;
    }

Далее переопределим два необходимых для работы метода: preVisitDirectory и visitFile.

Начнем с preVisitDirectory.
В методе preVisitDirectory первым делом нужно создать путь newDestination, который будет указывать куда нужно
копировать конкретную папку:

        Path newDestination = destination.resolve(source.relativize(dir));

Далее нужно прописать код копирования самой папки:

        Files.copy(dir, newDestination, StandardCopyOption.REPLACE_EXISTING);

В итоге в теле метода 3 строки кода:

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Path newDestination = destination.resolve(source.relativize(dir));
            Files.copy(dir, newDestination, StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
        }

Как это все работает?

Содержимое папки "Х":

Folder "Х" ("/Users/shevchenkostas77/Desktop/X")
    |
    --- Folder "Y1" ---
    |                 |
    |                 --- Folder "O"
    |                 --- File "test1.txt"
    |                 --- File "test2.txt"
    |                 --- File "test3.txt"
    --- Folder "Y2"---
    |                |
    |                --- Файл "test4.txt"
    --- Folder "Z"

source = /Users/shevchenkostas77/Desktop/X
destination = /Users/shevchenkostas77/Desktop/CopyHere"

Когда будет создаваться объект класса MyFileVisitorForCopy необходимо указать откуда копировать (source) и куда
копировать (destination).
Нужно прописать путь, чтобы скопировалась сначала папка "Y1", потом папка "О" и тд.
Самое главное понять как работает данный код:

        Path newDestination = destination.resolve(source.relativize(dir));

Сначала сработает то, что в скобках (в параметре метода resolve). Метод relativize возвращает относительный путь dir
относительно абсолютного пути source. Рассмотрим на примере папки "Y1". Когда сработает метод preVisitDirectory для
папки "Y1", то Path newDestination будет таким:

        /Users/shevchenkostas77/Desktop/CopyHere/.resolve(/Users/shevchenkostas77/Desktop/X
                .relativize(/Users/shevchenkostas77/Desktop/X/Y))

Метод relativize возвращает относительный путь из двух абсолютных путей, т.е. позволяет вычислить «разницу путей»: один
путь относительно другого. Т.е. на выходе в скобках метода resolve - source.relativize(dir) будет "Y".
Метод resolve объединяет два пути в один, т.е. строит новый абсолютный путь из абсолютного и относительного. Т.е.
объединит два пути /Users/shevchenkostas77/Desktop/CopyHere/ + Y , что в итоге newDestination будет иметь путь:

                Path newDestination = "/Users/shevchenkostas77/Desktop/CopyHere/Y"

Так по антологии со всеми остальными папками.
Такая же аналогичная работа будет происходить и в методе visitFile.

Код:

public class CopyFileTree {
    public static void main(String[] args) {
        Path source = Path.of("/Users/shevchenkostas77/Desktop/X");
        Path destination = Path.of("/Users/shevchenkostas77/Desktop/CopyHere");
        try {
            Files.walkFileTree(source, new MyFileVisitorForCopy(source, destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}

class MyFileVisitorForCopy extends SimpleFileVisitor<Path> {
    Path source; // откуда будет идти копирование
    Path destination; // куда будет копироваться

    public MyFileVisitorForCopy(Path source, Path destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path newDestination = destination.resolve(source.relativize(dir));
        Files.copy(dir, newDestination, StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path newDestination = destination.resolve(source.relativize(file));
        Files.copy(file, newDestination);
        return FileVisitResult.CONTINUE;
    }
}

Запуск программы. Вывод на экран:
Done!


 */

public class CopyFileTree {
    public static void main(String[] args) {
        Path source = Path.of("/Users/shevchenkostas77/Desktop/X");
        Path destination = Path.of("/Users/shevchenkostas77/Desktop/CopyHere");
        try {
            Files.walkFileTree(source, new MyFileVisitorForCopy(source, destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}

class MyFileVisitorForCopy extends SimpleFileVisitor<Path> {
    Path source; // откуда будет идти копирование
    Path destination; // куда будет копироваться

    public MyFileVisitorForCopy(Path source, Path destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path newDestination = destination.resolve(source.relativize(dir));
        Files.copy(dir, newDestination, StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path newDestination = destination.resolve(source.relativize(file));
        Files.copy(file, newDestination);
        return FileVisitResult.CONTINUE;
    }
}