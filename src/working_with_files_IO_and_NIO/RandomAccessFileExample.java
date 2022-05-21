package working_with_files_IO_and_NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*

Иногда нужно записать какую-то информацию, например, в середину файла или в его конец. Или прочитать небольшой отрывок из
конца большого файла. Читать для этого весь файл - не очень эффективно. Поэтому был создан класс RandomAccessFile, с его
помощью можно писать в любое место файла, читать из него, а также читать и писать одновременно.

С чем можно сравнить класс RandomAccessFile?
Представим открытый Word-документ, в нем всегда используется курсор. Когда что-то пользователь печатает, текст добавляется
в том месте, где стоит курсор. Пользователь может перемещать курсор в любую часть текста и печатать там текст. С чтением в
RandomAccessFile то же самое, чтение происходит в том месте, где стоит курсор. При чтении и записи курсор, естественно,
автоматически сдвигается.

Задание-пример.
Необходимо в корне проекта создать текстовый файл "testRandomAccessFile.txt" со стихотворением:
Had I the heavens' embroidered cloths,
Enwrought with golden and silver light,
The blue and the dim and the dark cloths
Of night and light and the half-light,
I would spread the cloths under your feet:
But I, being poor, have only my dreams;
I have spread my dreams under your feet;
Tread softly because you tread on my dreams.

Как создается RandomAccessFile:

        RandomAccessFile file = new RandomAccessFile("fileName", "rw");

второй параметр в конструкторе - string mode, т.е. режим работы с файлом:
1. "r" - read (только чтение файла);
2. "w" - write (только запись в файл);
3. "rw" - read / write (чтение и запись);

Конструктор выбрасывает исключение - FileNotFoundException (нужен импорт import java.io.FileNotFoundException;).

Код:

public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();

    //         Метод read прочитает один байт, который находится на позиции, где стоит курсор (pointer)
    //         В начале pointer стоит в самом начале текста (перед самым первым байтом):
    // вот тут --->    |Had I the heavens' embroidered cloths,
    //                 ...
    //                 Tread softly because you tread on my dreams.
    //         и эта запись file.read(); вернет заглавную букву "H". Метод read возвращает int, поэтому, чтобы
    //         вывести этот символ необходимо использовать кастинг


            System.out.println((char)a);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
H

Если еще раз продублировать код a = file.read(); и вывести на экран, то выведется буква "a":

Код:

public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();
            System.out.println((char)a);

            a = file.read();
            System.out.println((char)a);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
H
a

Метод read читает по одному байту и перемещает курсор. Таким образом по мере чтения pointer двигаться (по мере записи
pointer тоже двигается). Отчет байтов идет с нуля, т.е. "H" - нулевой байт, "a" - первый байт, "d" - второй байт,
пробел - это третий байт и тд.
Можно читать не по символьно, а прочитать срезу строку:

Код:

public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();
            System.out.println((char)a);

            String s = file.readLine();
            System.out.println(s);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
H
ad I the heavens' embroidered cloths,

Теперь рассмотрим особенности класса RandomAccessFile.
Допустим, необходимо прочитать строку начиная с 101-ого байта. Нужно переместить pointer на позицию 101. Этого можно
достичь при помощи метода seek (с англ. искать).

Код:

public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();
            System.out.println((char)a);

            String s = file.readLine();
            System.out.println(s);

            file.seek(101);
            s = file.readLine();
            System.out.println(s);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
H
ad I the heavens' embroidered cloths,
 and the dark cloths

При помощи метода seek можно устанавливать pointer куда необходимо.
Есть еще метод getFilePointer, он позволяет узнать на какой позиции в данный момент времени находится pointer.

Код:

public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();
            System.out.println((char)a);

            String s = file.readLine();
            System.out.println(s);

            file.seek(101);
            s = file.readLine();
            System.out.println(s);

            long positionPointer = file.getFilePointer(); // метод getFilePointer возвращает long
            System.out.println("Pointer position = " + positionPointer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
H
ad I the heavens' embroidered cloths,
 and the dark cloths
Pointer position = 123

Что произойдет, если переместить курсор на нулевую позицию и попытаться записать в этот файл слово "privet"?

public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();
            System.out.println((char)a);

            String s = file.readLine();
            System.out.println(s);

            file.seek(101);
            s = file.readLine();
            System.out.println(s);

            long positionPointer = file.getFilePointer(); // метод getFilePointer возвращает long
            System.out.println("Pointer position = " + positionPointer);

            file.seek(0);
            file.writeBytes("privet");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
H
ad I the heavens' embroidered cloths,
 and the dark cloths
Pointer position = 123

Содержимое файла "testRandomAccessFile.txt":
privetthe heavens' embroidered cloths,
Enwrought with golden and silver light,
The blue and the dim and the dark cloths
Of night and light and the half-light,
I would spread the cloths under your feet:
But I, being poor, have only my dreams;
I have spread my dreams under your feet;
Tread softly because you tread on my dreams.

Первые шесть байт были заменены на слово "privet", т.е. байты были не добавлены, а записаны поверх старых. Добавлять новую
информацию, не затирая старую, можно только в конец файла. Вернем стихотворение в прежнее состояние и добавим в конец
стиха имя автора.

Код:
public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();
            System.out.println((char)a);

            String s = file.readLine();
            System.out.println(s);

            file.seek(101);
            s = file.readLine();
            System.out.println(s);

            long positionPointer = file.getFilePointer(); // метод getFilePointer возвращает long
            System.out.println("Pointer position = " + positionPointer);

            // file.seek(0);
            // file.writeBytes("privet");

            file.seek(file.length()); // позиция начинается с нуля
            file.writeBytes("\n\t\t\t\t\tWilliam Butler Yeats");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
H
ad I the heavens' embroidered cloths,
 and the dark cloths
Pointer position = 123

Содержимое файла "testRandomAccessFile.txt":
Had I the heavens' embroidered cloths,
Enwrought with golden and silver light,
The blue and the dim and the dark cloths
Of night and light and the half-light,
I would spread the cloths under your feet:
But I, being poor, have only my dreams;
I have spread my dreams under your feet;
Tread softly because you tread on my dreams.
					William Butler Yeats

Класс RandomAccessFile позволяет читать информацию из любого места файла и записывать информацию в любое место файла.

Необходимо учитывать кодировку, чтобы всё читалось корректно (текст на русском языке):

        RandomAccessFile file
                    = new RandomAccessFile("nameFile.txt", "rw");
        String s = file.readLine();
        String utf8String = new String(s.getBytes("ISO-8859-1"),"UTF-8");
        System.out.println(utf8String);
*/

public class RandomAccessFileExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("testRandomAccessFile.txt", "rw")) {

            int a = file.read();
            /*
            Метод read прочитает один байт, который находится на позиции, где стоит курсор (pointer)
            В начале pointer стоит в самом начале текста (перед самым первым байтом):
    вот тут --->    |Had I the heavens' embroidered cloths,
                    ...
                    Tread softly because you tread on my dreams.
            и эта запись file.read(); вернет заглавную букву "H". Метод read возвращает int, поэтому, чтобы
            вывести этот символ необходимо использовать кастинг
            */
            System.out.println((char)a);

            String s = file.readLine();
            System.out.println(s);

            file.seek(101);
            s = file.readLine();
            System.out.println(s);

            long positionPointer = file.getFilePointer(); // метод getFilePointer возвращает long
            System.out.println("Pointer position = " + positionPointer);

            // file.seek(0);
            // file.writeBytes("privet");

            file.seek(file.length()); // позиция начинается с нуля
            file.writeBytes("\n\t\t\t\t\tWilliam Butler Yeats");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
