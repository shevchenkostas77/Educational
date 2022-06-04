package working_with_files_IO_and_NIO;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/*
Простое использование метода delete из класса Files удаляет определенные файлы и пустые папки. Невозможно просто
используя метод delete удалять папки с содержимым. НО благодаря методу walkFileTree класса Files можно обойти содержимое
папки, которую необходимо удалить, удалить сначала все ее содержимое и после этого удалить и саму папку.

Задание-пример.
Необходимо удалить папку и ее содержимое. На рабочем столе создана папка "Х". Папка "Х" содержит внутри себя еще три
папки: "Y1", "Y2", "Z". Папка "Y1" содержит папку "О" (она пуста) и три файла "test1.txt", "test2.txt", "test3.txt".
Папка "Y2" содержит файл "test4.txt", папка "Z" пуста.

Создадим класс с названием MyFileVisitorForDelete, который будет наследоваться от класса SimpleFileVisitor. Для удаления
файлов и папок нам понадобится два метода, а именно: visitFile и postVisitDirectory. Поэтому класс
MyFileVisitorForDelete будет наследоваться от класса SimpleFileVisitor, а не имплементировать интерфейс FileVisitor и
переопределять все его 4-е метода.
Переопределим метод visitFile:

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            System.out.println("File \"" + file.getFileName() + "\" deleted");
            return FileVisitResult.CONTINUE;
        }

При помощи метода walkFileTree пробегаемся по каждому файлу и при помощи метода delete класса Files удаляем файлы.
Точно так же и в методе postVisitDirectory, только в отношении уже пустых папок:

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            System.out.println("Directory \"" + dir.getFileName() + "\" deleted");
            return FileVisitResult.CONTINUE;
        }

Код:

public class DeleteFileTree {
    public static void main(String[] args) {
        Path path = Path.of("/Users/shevchenkostas77/Desktop/X");
        try {
            Files.walkFileTree(path, new MyFileVisitorForDelete());
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyFileVisitorForDelete extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        System.out.println("File \"" + file.getFileName() + "\" deleted");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        System.out.println("Directory \"" + dir.getFileName() + "\" deleted");
        return FileVisitResult.CONTINUE;
    }
}

Запуск программы. Вывод на экран:
Directory "O" deleted
File "test1.rtf" deleted
File "test2.txt" deleted
File "test3.txt" deleted
Directory "Y1" deleted
File "test4.txt" deleted
Directory "Y2" deleted
Directory "Z" deleted
Directory "X" deleted
Done!
 */

public class DeleteFileTree {
    public static void main(String[] args) {
        Path path = Path.of("/Users/shevchenkostas77/Desktop/X");
        try {
            Files.walkFileTree(path, new MyFileVisitorForDelete());
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyFileVisitorForDelete extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        System.out.println("File \"" + file.getFileName() + "\" deleted");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        System.out.println("Directory \"" + dir.getFileName() + "\" deleted");
        return FileVisitResult.CONTINUE;
    }
}