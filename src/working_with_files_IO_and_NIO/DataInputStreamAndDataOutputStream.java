package working_with_files_IO_and_NIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
Классы DataInputStream и DataOutputStream позволяют записывать в файл и читать из него примитивные типы данных.

Как создается DataInputStream (читает из файла):

        DataInputStream inputStream = new DataInputStream(new FileInputStream("nameFile.bin"));
        В конструктор помещается объект класса FileInputStream(выбрасывает исключение FileNotFoundException)

Как создается DataOutputStream (записывает в файл):

        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("nameFile.bin"));
        В конструктор помещается объект класса FileOutputStream(выбрасывает исключение FileNotFoundException)

Задание-пример.
Необходимо записать в файл примитивные типы данных, а после их прочитать и вывести в консоль.

Код:

public class DataInputStreamAndDataOutputStream {
    public static void main(String[] args) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("my_test.bin"));
             DataInputStream inputStream = new DataInputStream(new FileInputStream("my_test.bin")); ) {

                 outputStream.writeBoolean(true);
                 outputStream.writeByte(5);
                 outputStream.writeShort(120);
                 outputStream.writeInt(500);
                 outputStream.writeLong(1_000_000L); // L - показатель того, что это long
                 outputStream.writeFloat(3.14f);
                 outputStream.writeDouble(123.456);

                 System.out.println(inputStream.readBoolean());
                 System.out.println(inputStream.readByte());
                 System.out.println(inputStream.readShort());
                 System.out.println(inputStream.readInt());
                 System.out.println(inputStream.readLong());
                 System.out.println(inputStream.readFloat());
                 System.out.println(inputStream.readDouble());

             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
    }
}

Запуск программы. Вывод в консоль:

true
5
120
500
1000000
3.14
123.456

Если попытаться открыть файл my_test.bin, то увидим символы:

т.к. файл в нечитабельном для человека формате, в бинарном формате (.bin)

Во избежании неприятных сюрпризов, лучше всегда закрыть записывающий поток и только после этого начинать чтение.

Как записать в текстовый файл примитивные типы данных, что бы можно было посмотреть результат?

Можно создать BufferedWriter и записывать в файл типа txt таким образом:

int i = 5;
double d=3.14;
boolean b = true;
writer.write(String.valueOf(i));
writer.write(String.valueOf(d));
writer.write(String.valueOf(b));

*/

public class DataInputStreamAndDataOutputStream {
    public static void main(String[] args) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("my_test.bin"));
             DataInputStream inputStream = new DataInputStream(new FileInputStream("my_test.bin")); ) {

            outputStream.writeBoolean(true);
            outputStream.writeByte(5);
            outputStream.writeShort(120);
            outputStream.writeInt(500);
            outputStream.writeLong(1_000_000L); // L - показатель того, что это long
            outputStream.writeFloat(3.14f);
            outputStream.writeDouble(123.456);

            System.out.println(inputStream.readBoolean());
            System.out.println(inputStream.readByte());
            System.out.println(inputStream.readShort());
            System.out.println(inputStream.readInt());
            System.out.println(inputStream.readLong());
            System.out.println(inputStream.readFloat());
            System.out.println(inputStream.readDouble());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}