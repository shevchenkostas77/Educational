package working_with_files_IO_and_NIO;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/*
Из официальной документации:
API Note: Рекомендуется получать Path с помощью методов Path.of, а не с помощью
          методов get, определенных в классе Paths, поскольку этот класс может
          быть объявлен устаревшим в будущем выпуске.

Метод copy.
Копирует файлы или директории (директории должны быть пустые). В параметрах метода copy первым параметром указывается
путь к файлу или директории (директории должны быть пустые при копировании), которые нужно скопировать, вторым
параметром указывается путь куда будет скопировано.
НО конечный элемент пути, куда будет скопирован файл не должен заканчиваться какой-то папкой, а должен заканчиваться на
имени нового файла с расширением файла.
Метод copy выбрасывает исключение - IOException (нужен импорт java.io.IOException;).

Задание-пример.
Нужно скопировать файл "testFileForPathAndFiles.txt" с таким же именем в папку "testDirectoryForPathAndFiles".

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {
        Path filePath = Path.of("testFileForPathAndFiles.txt");
        Path directoryPath = Path.of("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");
        try {
            Files.copy(filePath, directoryPath.resolve(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Файл был успешно скопирован.


        Files.copy(filePath, directoryPath.resolve(filePath));


В параметрах метода copy первым параметром был указан путь к файлу, который будет скопирован, вторым параметром при
помощи метода resolve был создан путь с именем нового файла, куда и был скопирован этот файл.
Если попытаться запустить еще раз код из примера выше, а в папке "testDirectoryForPathAndFiles" есть уже файл
"testFileForPathAndFiles.txt", то выброситься исключение - FileAlreadyExistsException. Т.е. такой файл уже есть в этой
папке. Если нужно перезаписать имеющийся файл, то нужно использовать дополнительный параметр метода copy:

        Files.copy(filePaht, directoryPath.resolve(filePaht), StandardCopyOption.REPLACE_EXISTING);

replace existing с англ. заменить существующие. Нужен импорт класса StandardCopyOption -
import java.nio.file.StandardCopyOption;

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {
        Path filePath = Path.of("testFileForPathAndFiles.txt");
        Path directoryPath = Path.of("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        try {
            Files.copy(filePath, directoryPath.resolve(filePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Никакое исключение не выбрасывается и файл "testFileForPathAndFiles.txt" был перезаписан.

Задание-пример.
Необходимо папку "directoryВ" скопировать в папку "testDirectoryForPathAndFiles".

В параметрах метода copy первым параметром указывается путь к директории, которую нужно скопировать, вторым параметром
указывается куда будет скопирована папка (последним элементом пути, куда должна быть скопирована папка, должно быть
название папки, название папки можно задать другое).

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {
        Path directoryBPath = Path.of("/Users/shevchenkostas77/Desktop/В");
        Path directoryPath = Path.of("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        try {
            Files.copy(directoryBPath, directoryPath.resolve("B"));
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Теперь в папке "testDirectoryForPathAndFiles" есть папка "В".

Задание-пример.
Удалил копию папку "В" из папки "testDirectoryForPathAndFiles". Теперь в папке "testDirectoryForPathAndFiles"
нет папки "В". В папку "В" добавил текстовый файл "forTest.txt". Нужно снова попробывать скопировать папку "В",
но уже с файлом в папку "testDirectoryForPathAndFiles".

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {
        Path directoryBPath = Path.of("/Users/shevchenkostas77/Desktop/В");
        Path directoryPath = Path.of("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        try {
            Files.copy(directoryBPath, directoryPath.resolve("B"));
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Вроде бы все скопировалось, в папке "testDirectoryForPathAndFiles" снова появилась папка "В", но она пустая.
Нельзя с помощью метода copy просто взять и скопировать папку с каким-то содержанием в другую папку. Т.е.
сама папка то скопируется, но ее содержимое не будет скопировано. Если нужно скопировать и содержимое папки
тоже, то необходимо сначала скопировать папку саму, а потом ее содержимое, т.е. все это сделать вручную.

Метод move.
Метод move - перемещает и переименовывает файл. Он очень похож на метод copy, просто в copy копируемый файл
оставался на прежнем месте и появлялась скопированная версия, а в move откуда копируется файл, оттуда будет файл
удален и появится в том месте, куда он копируется, т.е. куда его перемещают.
Метод move выбрасывает исключение - IOException (нужен импорт java.io.IOException;).

Задание-пример.
Папка "testDirectoryForPathAndFiles" была очищена от предыдущих файлов и директорий. Нужно переместить файл
"testFileForPathAndFiles.txt" в директорию "testDirectoryForPathAndFiles".

        Files.move(filePath, directoryPath.resolve("testFileForPathAndFiles.txt"));

В параметрах метода move первым параметром задается путь к файлу, который будет перемещен, вторым параметром
указывается путь куда будет перемещен файл (последним элементом пути, куда будет перемещен файл, должно быть имя
файла, имя файла может быть любым).

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {
        Path filePath = Path.of("testFileForPathAndFiles.txt");
        Path directoryPath = Path.of("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        try {
            Files.move(filePath, directoryPath.resolve("testFileForPathAndFiles.txt"));
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Файл был перемещен в папку. Если попытаться запустить еще раз код, то Exception - NotSuchFileException.

При помощи метода move можно не только перемещать файлы и директории из одного места в другое, но их можно и
переименовывать. В классе нет метода rename, поэтому можно переименовываать файлы и директории при помощи метода
move. Для этого необходимо как бы переместить файл, который нужно переименовать, в ту же папку, но переместить
уже с другим именем.

        Files.move(Paths.of("renamingTest.txt"), Path.of("newRenamingTest.txt"));

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {

        try {
            Files.move(Path.of("renamingTest.txt"), Path.of("newRenamingTest.txt"));
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Файл был успешно переименован.

Метод delete.
Удаляет файлы и папки.

Задание-пример.
Необходимо удалить файл "newRenamingTest.txt".

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {

        try {
            Files.delete(Path.of("newRenamingTest.txt"));
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Если запустить код еще раз, то выброситься исключение, т.к. файла "newRenamingTest.txt" уже нет.

Теперь нужно попробывать удалить не пустую директорию. Создал папку "directoryToDelete" с файлом
"fileToDelete.txt".

Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {

        Path pathToDeleteDirectory = Path.of("/Users/shevchenkostas77/Desktop/directoryToDelete");

        try {
            Files.delete(pathToDeleteDirectory);
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы.
Выбросилось исключение - DirectoryNotEmptyException. Это означает, что не возможн удалить папку, если она не
пуста. Для того, чтобы удалить не пустую папку, нужно сначала удалить все файлы из нее, это похоже, например, с
копированием папки с содержимым.

С помозью класса Files можно записывать информацию в файл и читать ее из нее.

Запись в файл с помощью метода write класса Files.
Код:

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {

        Path filePath = Path.of("fileForWRTest.txt");
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dialog = "-Hello\n-Hello\n-How are you doing?\n-Everything is fine!\n" +
                 "-And how are you?\n-Thank you! Everything is fine!";

        try {
            Files.write(filePath, dialog.getBytes());
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Содержимое файла "fileForWRTest.txt":
-Hello
-Hello
-How are you doing?
-Everything is fine!
-And how are you?
-Thank you! Everything is fine!

Чтение из файла с помощью метода read класса Files.

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {

        Path filePath = Path.of("fileForWRTest.txt");

        try {
            List<String> list = Files.readAllLines(filePath);
            for (String s : list) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 */

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {

        Path filePath = Path.of("fileForWRTest.txt");

        try {
            List<String> list = Files.readAllLines(filePath);
            for (String s : list) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}