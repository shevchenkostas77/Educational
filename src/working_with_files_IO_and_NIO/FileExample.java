package working_with_files_IO_and_NIO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/*
Класс File позволяет управлять информацией о файлах и директориях. Класс File не работает напрямую с потоками, его задачей
является управление информацией о файлах и каталогах (каталоги еще называют директории или папки). На уровне операционной
системы файлы и директории отличаются, но в Java они описываются одним классом File.

Самый часто используемый конструктор для создания файлов такой:

        File file = new File("nameFile");

Абсолютный путь к файлу (Windows): C:\Documents\Java\file.txt
Относительный путь к файлу(Windows): file.txt

Абсолютный путь к файлу (Mac): /Documents/Java/file.txt
Относительный путь к файлу(Mac): file.txt
В зависимости от операционной системы путь к файлу может содержать backslash ('\') или же forward slash ('/').

При создании объектов класса File можно указать путь к несуществующему файлу или несуществующей папке. При этом никакое
исключение не будет вызвано.

Метод getAbsolutePath.
Возвращает абсолютный путь к файлу или директории.

Код:

class FileExample {
    public static void main(String[] agrs) {

        File file = new File("file.txt");
        File folder = new File("C:\\Users\\user\\Desktop\\A");

        System.out.println("Absolute path to file file.txt: " + file.getAbsolutePath());
        System.out.println("Absolute path to folder A: " + folder.getAbsolutePath());
    }
}

Запуск программы. Вывод на экран:
Absolute path to file file.txt: /home/file.txt
Absolute path to folder A: /home/A

Метод isAbsolute.
Возвращает true или false, в зависимости указан абсолютный путь или нет.

Код:

class FileExample {
    public static void main(String[] agrs) {

        File file = new File("file.txt");
        File folder = new File("/home/A");

        System.out.println("Absolute path to file file.txt: " + file.isAbsolute());
        System.out.println("Absolute path to folder A?: " + folder.isAbsolute());
    }
}

Запуск программы. Вывод на экран:
Absolute path to file file.txt: false
Absolute path to folder A?: true

Метод isDirectory.
Проверяет является ли директорией указанный путь. Возвращает true или false.

Код:

class FileExample {
    public static void main(String[] agrs) {
        File file = new File("file.txt");
        File folder = new File("/home/A");

        System.out.println("is the file a directory?: " + file.isDirectory());
        System.out.println("folder is a directory?: " + folder.isDirectory());
    }
}

Запуск программы. Вывод на экран:
is the file a directory?: false
folder is a directory?: true

Метод exists.
Проверяет существование файла / директории. Возвращает true или false.

Код:

class FileExample {
    public static void main(String[] agrs) {
        File file = new File("file.txt");
        File folder = new File("/home/A");

        System.out.println("does the file exist?: " + file.exists());
        System.out.println("does the folder exist?: " + folder.exists());
    }
}

Запуск программы. Вывод на экран:
does the file exist?: true
does the folder exist?: true

Методы createNewFile и mkdir.
Метод createNewFile - создает новый файл и после создания возвращает true (если успешно создался) и false (если по какой-то
причине файл не был создан). Выбрасывает исключение - IOException (нужен импорт - import java.io.IOException;).

Метод mkdir - расшифруется, как "make directory", с англ. создать директорию. Возвращает true (если успешно создалась
директория) и false (если по какой-то причине директория не была создана).

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {
        File createNewFile = new File("newFile.txt");
        File createNewDirectory = new File("/home/newDirectory");

        System.out.println("new file created?: " + createNewFile.createNewFile());
        System.out.println("new folder created?: " + createNewDirectory.mkdir());
        System.out.println("new folder created?: " + createNewDirectory.mkdir()); // пересоздание существующего файла
    }
}

Запуск программы. Вывод на экран:
new file created?: true
new folder created?: true
new folder created?: false      <--- такой файл уже существует

Если попытаться пересоздать уже существующий файл или директорию, то вернется false, т.к. создать файл или директорию
не удалось.

Метод length.
Возвращает информацию в байтах о размере файла или директории. Напишем в файле "file.txt" слово "privet"

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");
        File folder = new File("/home/A");

        System.out.println("what is the file size?: " + file.length());
        System.out.println("what is the folder size?: " + folder.length());

    }
}

Запуск программы. Вывод на экран:
what is the file size?: 6    <--- 6 байт
what is the folder size?: 0

Если нужно узнать размер директории, т.е. сумму размеров файлов, содержащихся в директории, то нужно написать код
самостоятельно. Класс File не предоставляет такой функциональности. Необходимо заходить в папку, читать размеры файлов
этой папки и складывать эти размеры.

Метод delete.
При помощи этого метода можно удалить файл или директорию. Возвращает true (если успешно удалил) и false (если по какой-то
причине удаление не произошло).

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");
        File folder = new File("/home/A");

        System.out.println("file was deleted?: " + file.delete());
        System.out.println("folder was deleted?: " + folder.delete());

    }
}

Запуск программы. Вывод на экран:
file2 was deleted?: true
folder2 was deleted?: true

Директорию можно удалить использую такое написание - folder.delete();, только в том случае, если эта директория пуста. Если
директория содержит файлы, то удалить ее так просто невозможно. Сначала необходимо из папки удалить всю информацию, т.е. все
файлы, все другие подпапки и только после этого возможно удалить саму папку.

Метод listFiles.
При помощи этого метода моно проверить содержимое директории. Метод listFiles возвращает массив типа File.

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");
        File folder = new File("/home/A");

        File[] files = folder.listFiles();
        System.out.println("Folder content: " + Arrays.toString(files));
    }
}

Запуск программы. Вывод на экран:
Folder content: [/home/A/test.txt, /home/A/test2.txt, /home/A/В]

Метод isHidden.
Проверяет скрыт ли файл / директория. Возвращает true (если скрыто) и false (если не скрыто).

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");
        File folder = new File("/home/A");

        System.out.println("file hidden?: " + file.isHidden());
        System.out.println("folder hidden?: " + folder.isHidden());

    }
}

Запуск программы. Вывод на экран:
file hidden?: false
folder hidden?: false

Метод canRead.
Возвращает true или false в зависимости от того, если ли доступ к чтению файла.

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");

        System.out.println("have read access to the file?: " + file.canRead());
    }
}

Запуск программы. Вывод на экран:
have read access to the file?: true

Метод canWrite.
Возвращает true или false в зависимости от того, если ли доступ к записи в файл.

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");

        System.out.println("have write access to the file?: " + file.canWrite());
    }
}

Запуск программы. Вывод на экран:
have write access to the file?: true

Метод canExecute.
Возвращает true или false в зависимости от того, если ли доступ к выполнению файла, т.е. запуску файла.

Код:

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");

        System.out.println("have access to run the file?: " + file.canExecute());
    }
}

Запуск программы. Вывод на экран:
have access to run the file?: true
*/

class FileExample {
    public static void main(String[] agrs) throws IOException {

        File file = new File("file.txt");

        System.out.println("have access to run the file?: " + file.canExecute());
    }
}