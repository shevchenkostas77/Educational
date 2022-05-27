package working_with_files_IO_and_NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;

/*
Создается файл "forChannelBufferExample2.txt" в корне проекта и в файл записывается текст "abcdefghij".
В try with resources создаются два ресурса:

        RandomAccessFile file = new RandomAccessFile("forChannelBufferExample2.txt", "r");

объект типа RandomAccessFile (в конструкторе указывается путь и mode - "r") и канал

        FileChannel channel = file.getChannel();

RandomAccessFile выбрасывает исключение - FileNotFoundException (нужен импорт import java.io.FileNotFoundException;) и
IOException (тоже нужен импорт import java.io.IOException;). В теле try with resources создает буфер, емкостью 5 байт:

        ByteBuffer buffer = ByteBuffer.allocate(5);

Далее будет один раз читаться информация из файла и записываться в буфер, буфер всего содержит 5 байт и будет заполнен
символами a, b, c, d, e:

        channel.read(buffer);

После заполнения буфера будет вызван метод flip, чтобы можно было прочитать информацию из буфера:

         buffer.flip();

И в конце будет выводится на экран информация о каждом байте (о 3-х байтах):

         System.out.println((char)buffer.get());
         System.out.println((char)buffer.get());
         System.out.println((char)buffer.get());

Код:

public class ChannelBufferExample2 {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample2.txt", "r");
             FileChannel channel = file.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(5);
            channel.read(buffer);
            buffer.flip();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
a
b
c

Если вызвать на буфере метод rewind, то этот метод поместит Position на нулевую ячейку:

ДО вызова метода rewind
BUFFER WRITE MODE                               BUFFER READ MODE

              |__a__| 0                                         |__a__| 0
              |__b__| 1                                         |__b__| 1
              |__c__| 2                                         |__c__| 2
              |__d__| 3                           Position ---> |__d__| 3
Limit   --->  |__e__| 4  <---  Capacity           Limit   --->  |__f__| 4  <---  Capacity
Position --->
(в режиме READ было прочитано первые три байта и Position остановился на ячейке 3)

ПОСЛЕ вызова метода rewind
BUFFER WRITE MODE                               BUFFER READ MODE

              |__a__| 0                           Position ---> |__a__| 0
              |__b__| 1                                         |__b__| 1
              |__c__| 2                                         |__c__| 2
              |__d__| 3                                         |__d__| 3
Limit   --->  |__e__| 4  <---  Capacity           Limit   --->  |__f__| 4  <---  Capacity
Position --->
Это позволит снова прочитать байты в буфере, которые находятся на позициях 0, 1, 2.

Код:

public class ChannelBufferExample2 {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample2.txt", "r");
             FileChannel channel = file.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(5);
            channel.read(buffer);
            buffer.flip();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());

            buffer.rewind();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
a
b
c
a
b
c

Снова били прочтены символы 'a', 'b', 'c' и остались не прочтенные из буфера символы 'd' и 'e'. Метод compact он
некоторыми своими способностями схож с методом clear, т.е. вызывается метод или clear или compact.
Из предыдущего примера было прочтено первые три байта - 'a', 'b', 'c' и Position остановился на ячейки 3:

method flip --->                                <--- method clear
BUFFER WRITE MODE                               BUFFER READ MODE

              |__a__| 0                                         |__a__| 0
              |__b__| 1                                         |__b__| 1
              |__c__| 2                                         |__c__| 2
              |__d__| 3                           Position ---> |__d__| 3
Limit   --->  |__e__| 4  <---  Capacity           Limit   --->  |__e__| 4  <---  Capacity
Position --->

Когда буфер заполнен информацией вызывается метод flip, который переводит буфер из режима записи в режим чтения и
меняет Position на нулевую ячейку. Далее информация читается из буфера и, когда информация уже прочитана из буфера
вызывается метод clear, который переводит буфер из режима чтения в режим записи и меняет Position на нулевую ячейку,
чтобы при записи новой информации в буфер начинать с нулевой ячейки, т.е. перезаписывать все элементы. Таким образом,
элементы, которые не были прочитаны из примера выше, а это - 'd', 'e', если вызвать метод clear, то эти элементы уже
невозможно будет прочесть.
Если не нужно, чтобы не прочитанные элементы были затерты и утеряны, а были прочитаны позже, но перед этим необходимо
что-то дописать в буфер, то в этом поможет метод compact. Метод compact копирует непрочитанные байты в начало буфера.
После вызова метода compact:

   BUFFER WRITE MODE                               BUFFER READ MODE

              |__d__| 0                                         |__a__| 0
              |__e__| 1                                         |__b__| 1
Position ---> |_____| 2                                         |__c__| 2
              |_____| 3                           Position ---> |__d__| 3
Limit   --->  |_____| 4  <---  Capacity           Limit   --->  |__e__| 4  <---  Capacity

непрочитанные элементы копируются в начало буфера и остается, в данном примере, 3 байта для записи информации. Position
в режиме записи указывает на ячейку 2, а не на 0 (как было бы при вызове метода clear). Т.е. метод compact НЕ будет
перезаписывать не прочитанную информацию из буфера.
Вызывается на буфере метод compact:

        buffer.compact();

Далее читается информация из файла и заполняется остаток буфера (остаток потому, что были скопированы не прочтенные
байты в начало буфера):

        channel.read(buffer);

Вызывается метод flip, который переводит буфер из режима чтения в режим записи и меняет Position на нулевую ячейку:

        buffer.flip();

и с помощью цикла while выводятся все элементы из буфера на экран:

        while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }

Код:

public class ChannelBufferExample2 {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample2.txt", "r");
             FileChannel channel = file.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(5);
            channel.read(buffer);
            buffer.flip();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());

            buffer.rewind();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());

            System.out.println("------compact------");
            buffer.compact();
            channel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
a
b
c
a
b
c
------compact------
d
e
f
g
h

Методы mark и reset (эти методы работают всегда вместе).
Добавим еще 3 символа в файл 'k', 'l', 'm', прочтем их из файла в буфер и выведем на экран.
            buffer.clear(); // вызывается метод clear, чтобы записать новые байты
            channel.read(buffer); // читаются из файла и записываются в буфер новые байты
            buffer.flip(); // буфер переводится из режима записи в режим чтения и Position переходит на ячейку 0
            System.out.println((char)buffer.get()); // выводится первый новый элемент - i

   BUFFER WRITE MODE                               BUFFER READ MODE

              |__i__| 0                                         |__i__| 0
              |__j__| 1                           Position ---> |__j__| 1
              |__k__| 2                                         |__k__| 2
              |__l__| 3                                         |__l__| 3
Limit   --->  |__m__| 4  <---  Capacity           Limit   --->  |__m__| 4  <---  Capacity
Position --->

Поставим здесь отметку, на символе 'j', чтобы потом могли вернуться к этой позиции. Для этого нужно использовать метод
mark:

        buffer.mark();

Отметка поставлена и после этого дважды выведем на экран байты:

        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());

   BUFFER WRITE MODE                               BUFFER READ MODE

              |__i__| 0                                         |__i__| 0
              |__j__| 1                    (было) Position ---> |__j__| 1  <---  Mark
              |__k__| 2                                         |__k__| 2
              |__l__| 3                   (стало) Position ---> |__l__| 3
Limit   --->  |__m__| 4  <---  Capacity           Limit   --->  |__m__| 4  <---  Capacity
Position --->

Можно вернуться к позиции, где была буква j потому, что эта позиция была помечена (Mark). Для этого стоит вызвать метод
reset:

        buffer.reset();

После того, как вызывается метод reset Position меняется с ячейки 3 на ячейку 1 в режиме чтения, которая была помечена
(Mark):

   BUFFER WRITE MODE                               BUFFER READ MODE

              |__i__| 0                                         |__i__| 0
              |__j__| 1                   (стало) Position ---> |__j__| 1  <---  Mark
              |__k__| 2                                         |__k__| 2
              |__l__| 3                    (было) Position ---> |__l__| 3
Limit   --->  |__m__| 4  <---  Capacity           Limit   --->  |__m__| 4  <---  Capacity
Position --->

и с помощью цикла while можно вывести оставшиеся символы на экран.

Код:

public class ChannelBufferExample2 {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample2.txt", "r");
             FileChannel channel = file.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(5);
            channel.read(buffer);
            buffer.flip();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());

            buffer.rewind();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());

            System.out.println("------compact------");
            buffer.compact();
            channel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }

            System.out.println("------mark&reset------");
            buffer.clear();
            channel.read(buffer);
            buffer.flip();
            System.out.println((char)buffer.get());
            buffer.mark();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            buffer.reset();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
a
b
c
a
b
c
------compact------
d
e
f
g
h
------mark&reset------
i
j
k
j
k
l
m

 */
public class ChannelBufferExample2 {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample2.txt", "r");
             FileChannel channel = file.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(5);
            channel.read(buffer);
            buffer.flip();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());

            buffer.rewind();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());

            System.out.println("------compact------");
            buffer.compact();
            channel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }

            System.out.println("------mark&reset------");
            buffer.clear();
            channel.read(buffer);
            buffer.flip();
            System.out.println((char)buffer.get());
            buffer.mark();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            buffer.reset();
            System.out.println((char)buffer.get());
            System.out.println((char)buffer.get());
            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}