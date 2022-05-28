package working_with_files_IO_and_NIO;

import java.nio.file.Path;
import java.nio.file.Paths;


/*
Path (с англ. путь)
В Java версии 7 разработчики решили изменить работу с файлами и директориями. Это произошло из-за того, что у класса
File, который считается устаревшим, и использовать его не рекомендуется (конечно, можно еще встретить его в коде,
параметрах методов или конструкторах классов), был ряд недостатков. Например, в нем не было метода copy, который
позволил бы скопировать файл из одного места в другое, а это очень нужная операция. На самом деле недостатков было
много. И вместо единого класса File появились интерфейс Path и класс Files.

Почему интерфейс Path, а не класс Path?
Так сделано для того, чтобы можно было под каждую операционную (и файловую) систему писать свой класс-наследник Path.
У Windows свои стандарты написания пути файлов, у Linux — свои. А ведь в мире еще много операционных систем, и у
каждой — свои стандарты.

Поэтому везде в методах для работы с файлами указан интерфейс Path, а реально работа идет с его классами-наследниками:
WindowsPath, UnixPath и тд.

Объект типа Path представляет собой путь к файлу или директории.
Как создается объект типа Path (на самом деле, в моем случае, это будет объект класса-наследника — UnixPath, т.к.
использую MacOS):

        Path path = Paths.get("file.txt"); // Путь к файлу
        Path directory = Paths.get("/Users/MisterX/Desktop/"); // Путь к директории

Используя класс Paths (буква s на конце) и его метод get (с англ. получить), в параметре метода указывается адрес файла
или директории к которым необходимо будет обратиться, метод get возвращает путь (объект класса-наследника интерфейса
Path).
Невозможно создать объект типа Path с помощью кода вида new Path().

Файл или директория не обязаны существовать, чтобы мог существовать валидный объект типа Path. Может вы только хотите
создать файл... Объект типа Path — это как продвинутая версия типа String — он не привязан к конкретному файлу на диске:
он просто хранит некий путь на диске и все.

Path нужно импортировать - import java.nio.file.Path;
Paths нужно импортировать - import java.nio.file.Paths;

Метод getFileName.
Возвращает имя файла или директории, которые расположены на данном пути.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("filePath: " + filePath.getFileName());
        System.out.println("directoryPath: " + directoryPath.getFileName());
    }
}

Запуск программы. Вывод на экран:
filePath: testFileForPathAndFiles.txt
directoryPath: testDirectoryForPathAndFiles

Имя в директории является последним элементом в абсолютном пути
/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles (абсолютный путь к директории testDirectoryForPathAndFiles)
/Users/shevchenkostas77/Desktop/ --> testDirectoryForPathAndFiles <-- (последний элемент)

Метод getParent.
Возвращает родителя файла или директории.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("filePath: " + filePath.getParent());
        System.out.println("directoryPath: " + directoryPath.getParent());
    }
}

Запуск программы. Вывод на экран:
filePath: null
directoryPath: /Users/shevchenkostas77/Desktop

У filePath родителя нет, т.к. путь указан относительный (указано только название файла), а у directoryPath - Desktop и
выводится абсолютный путь к Desktop-у.

Метод getRoot.
Возвращает "корень" от куда берет начало файл или директория.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("filePath: " + filePath.getRoot());
        System.out.println("directoryPath: " + directoryPath.getRoot());
    }
}

Запуск программы. Вывод на экран:
filePath: null
directoryPath: /

У filePath root - null, т.к. путь указан относительный, а у directoryPath - жесткий диск (на ОС Windows возможный
вариант вывода: C:\ (диск С))

Метод isAbsolute.
Возвращает true или false, в зависимости указан абсолютный путь или нет.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("filePath: " + filePath.isAbsolute());
        System.out.println("directoryPath: " + directoryPath.isAbsolute());
    }
}

Запуск программы. Вывод на экран:
filePath: false
directoryPath: true

Метод toAbsolutePath.
Возвращает абсолютный адрес.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("filePath: " + filePath.toAbsolutePath());
        System.out.println("directoryPath: " + directoryPath.toAbsolutePath());
    }
}

Запуск программы. Вывод на экран:
filePath: /Users/shevchenkostas77/IdeaProjects/blackBeltJava/testFileForPathAndFiles.txt
directoryPath: /Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles

Объединив методы toAbsolutePath и getParent можно узнать Path родителя файла или директории, если был указан
относительный адрес при создании объекта Path.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("filePath: " + filePath.toAbsolutePath().getParent());
        System.out.println("directoryPath: " + directoryPath.toAbsolutePath().getParent());
    }
}

Запуск программы. Вывод на экран:
filePath: /Users/shevchenkostas77/IdeaProjects/blackBeltJava
directoryPath: /Users/shevchenkostas77/Desktop

Метод resolve.
Данный метод объединяет два пути в один.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("Concatenation directoryPath and filePath: " + directoryPath.resolve(filePath));
    }
}

Запуск программы. Вывод на экран:
Concatenation directoryPath and filePath:
                        /Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles/testFileForPathAndFiles.txt

Вышло так, что как будь-то файл testFileForPathAndFiles находится в папке testDirectoryForPathAndFiles, но речь идет
о путях, а не самих файлах и директориях.

Метод relativize.
Возвращает относительный путь.
Для наглядности примера, необходимо создать еще один путь с абсолютным адресом, к примеру:

        Path anotherPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles" +
                "/newFolderM/newFolderN/newFileTest.txt");

Теперь необходимо найти относительный путь абсолютного пути:
/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles/newFolderM/newFolderN/newFileTest.txt
относительно, например, пути: /Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles.
Т.е. нужно найти относительный путь anotherPath относительно directoryPath.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        Path anotherPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles" +
                "/newFolderM/newFolderN/newFileTest.txt");

        System.out.println(directoryPath.relativize(anotherPath));
    }
}

Запуск программы. Вывод на экран:
newFolderM/newFolderN/newFileTest.txt

Т.е. вывелся на экран относительный путь: /newFolderM/newFolderN/newFileTest.txt
абсолютного пути: /Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles/newFolderM/newFolderN/newFileTest.txt

Класс Files это utility класс, который содержит много очень полезных статических методов для работы с файлами и
директориями.
 */

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        Path anotherPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles" +
                "/newFolderM/newFolderN/newFileTest.txt");

        System.out.println(directoryPath.relativize(anotherPath));
    }
}
