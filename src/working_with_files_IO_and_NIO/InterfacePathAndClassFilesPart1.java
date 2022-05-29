package working_with_files_IO_and_NIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


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
        Path directory = Paths.get("/Users/MisterX/Desktop/MyFolder"); // Путь к директории

Используя класс Paths (буква s на конце) и его метод get (с англ. получить), в параметре метода указывается адрес файла
или директории к которым необходимо будет обратиться, метод get возвращает путь (объект класса имплементирующий
интерфейс Path).
Невозможно создать объект типа Path с помощью кода вида new Path().

Файл или директория не обязаны существовать, чтобы мог существовать валидный объект типа Path. Может файл или директория
будут созданы позже... Объект типа Path не привязан к конкретному файлу на диске: он просто хранит некий путь на диске
и все.

Path нужно импортировать - import java.nio.file.Path;
Paths нужно импортировать - import java.nio.file.Paths;

Метод getFileName.
Возвращает имя файла или директории, которые расположены на данном пути, т.е.  возвращает одно имя файла или
директории — то, что идет после последнего разделителя.

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
/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles (абсолютный путь к папке testDirectoryForPathAndFiles)
/Users/shevchenkostas77/Desktop/ --> testDirectoryForPathAndFiles <-- (последний элемент)

Метод getParent.
Возвращает родителя файла или директории, т.е. возвращает путь, который указывает на родительскую директорию для
текущего пути. Независимо от того, был этот путь директорией или файлом.

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
Возвращает корневую директорию от куда берет начало файл или директория.

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

У filePath корневая директория - null, т.к. путь указан относительный, а у directoryPath - жесткий диск (в ОС Windows
возможный вариант вывода: C:\ (диск С)).

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
Преобразует путь в абсолютный.

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

Объединив методы toAbsolutePath и getParent можно узнать родителя файла или директории, если был указан относительный
путь при создании объекта типа Path (как в данном случае с filePath).

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
Данный метод объединяет два пути в один, т.е. строит новый абсолютный путь из абсолютного и относительного.

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
Возвращает относительный путь из двух абсолютных путей, т.е. позволяет вычислить «разницу путей»: один путь
относительно другого.
Для наглядности примера, необходимо создать еще один путь с абсолютным путем, к примеру:

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

Класс Files.
Класс Files это utility класс, который содержит много очень полезных статических методов для работы с файлами и
директориями (нужен импорт import java.nio.file.Files). Все методы этого класса работают с объектами типа Path.

Метод exists.
Проверяет существование файла / директории. Возвращает true или false.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("does the file exist?: " + Files.exists(filePath));
        System.out.println("does the folder exist?: " + Files.exists(directoryPath));
    }
}

Запуск программы. Вывод на экран:
does the file exist?: false
does the folder exist?: true

Методы createFile, createDirectory и createDirectories.
Все три метода выбрасывают исключения IOException, нужен импорт exception - import java.io.IOException;
createFile - создает новый файл;
createDirectory - создает новую директорию;
createDirectories - создает директорию и все нужные поддиректории, если их не существует;

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");
        Path directoriesPath = Paths.get("/Users/shevchenkostas77/Desktop/folderM/folderN/folderN");

        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
                System.out.println("File has been created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
                System.out.println("Directory has been created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Files.exists(directoriesPath)) {
            try {
                Files.createDirectories(directoriesPath);
                System.out.println("All directories has been created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

Запуск программы. Вывод на экран:
File has been created
All directories has been created

На рабочем столе создалась папка folderM с подпапкой folderN и в папке folderN создалась подпапка folderO.

Методы isReadable, isWriteable и isExecutable.
isReadable - возвращает true или false в зависимости от того, если ли доступ к чтению файла;
isWritable - возвращает true или false в зависимости от того, если ли доступ к записи в файл;
isExecutable - возвращает true или false в зависимости от того, если ли доступ к выполнению файла, т.е. запуску файла;

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Paths.get("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        System.out.println("Have read access to the file? Answer: " + Files.isReadable(filePath));
        System.out.println("Have write access to the file? Answer: " + Files.isWritable(filePath));
        System.out.println("Have access to run the file? Answer: " + Files.isExecutable(filePath));
    }
}

Запуск программы. Вывод на экран:
Have read access to the file? Answer: true
Have write access to the file? Answer: true
Have access to run the file? Answer: true

Метод isSameFile.
Метод проверяет ссылаются ли оба пути указанные в параметрах метода на один и тот же файл. Метод isSameFile выбрасывает
исключение - IOException.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt"); // относительный путь к файлу
        Path filePathCopy = Paths.get("/Users/shevchenkostas77/IdeaProjects/" +
                "blackBeltJava/testFileForPathAndFiles.txt"); // абсолютный путь к файлу
        try {
            System.out.println("Do two paths point to the same file? Answer: " +
                    Files.isSameFile(filePath, filePathCopy));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Do two paths point to the same file? Answer: true

Метод size.
Возвращает размер файла (long). Метод size выбрасывает исключение - IOException.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        try {
            System.out.println("What is the file size? Answer: " + Files.size(filePath) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
What is the file size? Answer: 14 bytes

Размер файла "testFileForPathAndFiles.txt" - 14 байт, т.к. файл содержит фразу "Hello World!!!".

Метод getAttribute.
Метод возвращает информацию о каком-то атрибуте. В параметрах метода первым параметром указывается путь к файлу или к
директории, вторым параметром указывается в виде String атрибут, например creationTime или size (название атрибута
ВАЖНО писать без ошибок!).
Метод getAttribute выбрасывает исключение - IOException.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("testFileForPathAndFiles.txt");
        Path directoryPath = Path.of("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        try {
            System.out.println("When was the file created? Answer: " +
                    Files.getAttribute(filePath, "creationTime"));
            System.out.println("When was the directory created? Answer: " +
                    Files.getAttribute(directoryPath, "creationTime"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
When was the file created? Answer: 2022-05-29T11:55:32Z
When was the directory created? Answer: 2022-05-28T14:47:37Z

Если необходимо рассмотреть все базовые атрибуты, то можно воспользоваться методом readAttributes. В параметрах метода
первым параметром указывается путь к файлу или директории (чьи атрибуты будем читать), вторым параметром можно указать
название атрибутов или же в двойных кавычках указать знак "Звездочка" - "*". Метод readAttributes возвращает объект
тима Map, где ключом служит объект типа String, а значением - Object.
Метод readAttributes выбрасывает исключение - IOException.

Код:

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Path.of("testFileForPathAndFiles.txt");

        try {
            Map<String, Object> attributes = Files.readAttributes(filePath, "*");
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
lastAccessTime:2022-05-29T16:12:54Z
lastModifiedTime:2022-05-29T16:12:54Z
size:14
creationTime:2022-05-29T11:55:32Z
isSymbolicLink:false
isRegularFile:true
fileKey:(dev=1000003,ino=24276540)
isOther:false
isDirectory:false

 */

public class InterfacePathAndClassFilesPart1 {
    public static void main(String[] args) {
        Path filePath = Path.of("testFileForPathAndFiles.txt");

        try {
            Map<String, Object> attributes = Files.readAttributes(filePath, "*");
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}