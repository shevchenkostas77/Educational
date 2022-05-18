package working_with_files_IO_and_NIO;



import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
Для работы с бинарными файлами созданы специаьльные классы - FileInputStream и FileOutputStream.

Задание-пример.
Необходимо попробывать скопировать картинку используя стриимы предназначенные для работы с читабельным для человека
текстом. Т.е. необходимо использовать BufferedReader и BufferedWriter для работы с картинкой.

Код:

public class FileInputStreamAndFileOutputStream {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("java_pic.jpeg"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("copy_java_pic.jpeg"));) {

                 int character;
                 while ((character = reader.read()) != -1) {
                     writer.write(character);
                 }

                 System.out.println("Done!");

             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
    }
}

Запуск программы. Содержимое файла "copy_java_pic.jpeg":
При попытке открыть файл copy_java_pic.jpeg возникают проблемы - Image not loaded. Проблемы возникают потому, что картинка
не была нормально скопирована. Т.е. невозможно использовать стримы предназначенные для текстовых файлов при работе с
бинарными файлами.

FileInputStream и FileOutputStream используются для работы с бинарм=ными файлами.

Как создается FileInputStream (читает из файла):

        FileInputStream inputStream = new FileInputStyream("nameFile");

Как создается FileOutputStream (записывает в файл):

        FileOutputStream outputStream = new FileOutputStream("nameFile");

FileInputStream и FileOutputStream необходимо импортировать:
import java.io.FileInputStream; <- выбрасывает FileNotFoundException
import java.io.FileOutputStream;

Теперь необходимо попробывать скопировать картинку используя стриимы предназначенные для работы с бинарными файлами.

Код:

public class FileInputStreamAndFileOutputStream {
    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("java_pic.jpeg");
             FileOutputStream outputStream = new FileOutputStream("copy_java_pic.jpeg"); ) {

                 int i;
                 while ((i = inputStream.read()) != -1) {
                     outputStream.write(i);
                 }

                 System.out.println("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Содержимое файла "copy_java_pic.jpeg":
Картинка была успешно скопирована в корень проэкта.

В FileInputStream и в FileOutputStream тоже можно использовать буферизацию. Это достигается с помощью классов
BufferedInputStream и BufferedOutputStream. Суть работы буферизации такая же, как и для классов BufferedReader и
BufferedWriter.

Во избежание неприятных сюрпризов, лучше всегда закрыть записывающий поток и только после этого начинать чтение.
*/

public class FileInputStreamAndFileOutputStream {
    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("java_pic.jpeg");
             FileOutputStream outputStream = new FileOutputStream("copy_java_pic.jpeg"); ) {

            int i;
            while ((i = inputStream.read()) != -1) {
                outputStream.write(i);
            }

            System.out.println("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}