package working_with_files_IO_and_NIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

Файл был успешно скопирован. В параметрах метода copy первым параметром был указан путь к файлу, который будет
скопирован, вторым параметром при помощи метода resolve был создан путь с именем нового файла, куда и был скопирован
этот файл.
Если попытаться запустить еще раз код из примера выше, а в папке "testDirectoryForPathAndFiles" есть уже файл
"testFileForPathAndFiles.txt", то выброситься исключение - FileAlreadyExistsException. Т.е. такой файл уже есть в этой
папке. Если нужно перезаписать имеющийся файл, то нужно использовать дополнительный параметр метода copy


 */

public class InterfacePathAndClassFilesPart2 {
    public static void main(String[] args) {
        Path filePath = Path.of("testFileForPathAndFiles.txt");
        Path directoryPath = Path.of("/Users/shevchenkostas77/Desktop/testDirectoryForPathAndFiles");

        try {
            Files.copy(filePath, directoryPath.resolve(filePath));
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
