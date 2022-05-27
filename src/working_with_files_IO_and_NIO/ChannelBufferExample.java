package working_with_files_IO_and_NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
Channel - это интерфейс в пакете java.nio.channels - важный компонент в Java New IO (Java NIO - Java New Input Output).
Buffer - это публичный абстрактный класс представляет собой контейнер с фиксированной емкостью (capacity) для хранения
примитивных данных. Находится этот класс в пакете java.nio. Т.е. Buffer - это блок памяти, в который можно записывать
информацию и читать информацию из этого блока памяти. Используя Buffer возможно читать информацию из него, а затем
возвратиться к уже прочитанной информации и снова ее просматривать, т.е. "гулять" по буферу туда-сюда, чего невозможно
сделать в Stream (поток) в Java IO.
Java NIO был впервые представлен с Java 1.4 в качестве альтернативы традиционному Java IO с целью повышения
производительности программ. С помощью пакета java.nio, c помощи Channel-ов и Buffe-ов можно работать не только с
файлами, но и, например, с сетью тоже.

Что такое буферы и каналы?

*1 read:

                       ________________                   ________________
program       *4          Buffer               *3           Channel                *2      file
            <------    ________________      <------      ________________      <------


*5 write:

                       ________________                   ________________
program       *6           Buffer              *7            Channel              *8       file
            ------>    ________________      ------>      ________________      ------>


Есть программа - program и есть файл - file. Для работы с файлами будут использоваться Channel-ы и Buffer-ы.

*1 Когда необходимо ПРОЧИТАТЬ что-то из файла, *2 Channel связывается с файлом, читает из него определенную информацию,
*3 записывает информацию в Buffer и *4 затем программа читает информацию из Buffer.

*5 Когда необходимо что-то ЗАПИСАТЬ в файл, то *6 сначала запись информации происходит из программы в Buffer, далее
*7 Channel читает информацию из Buffer-a и *8 записывает в файл.

Взаимосвязь между Channel и Buffer аналогична взаимосвязи между миской и ложкой. Ложку можно использовать как небольшой
контейнер для извлечения, к примеру, сахара из миски, а так же как небольшой контейнер для добавления сахара в миску
извне. Таким образом, ложка действует как Buffer, а миска - как Channel.
Да, при использовании стримов можно было обернуть в Buffered Stream (поток) в Java IO, но все же самую важную роль в
этой концепции играет сам стрим, а не его буферизация.

Концепция Channel (канал) в Java NIO аналогична концепции Stream (поток) в Java IO, но с некоторыми отличиями:
1. Можно как читать, так и записывать на Channel. В то время как в Stream для чтения информации нужен InputStream, для
записи нужен OutputStream. Т.е. стримы всегда работают в одном направлении: или чтение или запись, Channel работают в
оба направления, т.е. можно записывать в файл информацию из Buffer и можно читать информацию из файла в Buffer.

Чтение файла: Channel читает информацию из файла и записывает в Buffer.
Запись в файл: Channel читает информацию из Buffer и записывает ее в файл. Естественно, эту информацию необходимо
записать в Buffer.

2. Channel может читать и записывать асинхронно.

3. Channel и Buffer имеют связь друг с другом - Channel всегда работает с Buffer. Как было ранее упомянуто, Buffer -
это блок памяти, в который можно записывать информацию и читать информацию из этого блока памяти. Используя Buffer
возможно читать информацию из него, а затем возвратиться к уже прочитанной информации и снова ее просматривать, т.е.
"гулять" по буферу туда-сюда, чего невозможно сделать в Stream (поток) в Java IO. Просто нужно манипулировать с Buffer
для обработки данных, считанных с Channel, и манипулировать с Buffer для записи данных в Channel.

Есть разные классы, которые реализуют интерфейс Channel:
DatagramChannel, SocketChannel, ServerSocketChannel, FileChannel и тд. В этом разделе я рассматриваю FileChannel.

Buffer тоже бывают разными:
ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer, ShortBuffer. В этом разделе я рассматриваю
ByteBuffer.

Задание-пример.
В примере будет использован файл со стихотворением William Butler Yeats (forChannelBufferExample.txt). Необходимо
прочитать этот файл и вывести его содержимое на экран.

Для того чтобы одним Channel можно было и писать, и читать из файла, можно использовать класс RandomAccessFile (нужно
импортировать класс - import java.io.RandomAccessFile; и конструктор класса RandomAccessFile выбрасывает исключение -
FileNotFoundException и для этого исключения тоже нужен импорт - import java.io.FileNotFoundException;):

    RandomAccessFile file = new RandomAccessFile("forChannelBufferExample.txt", "rw");

второй параметр в конструкторе - string mode, т.е. режим работы с файлом:
1. "r" - read (только чтение файла);
2. "w" - write (только запись в файл);
3. "rw" - read / write (чтение и запись);

Из самого file получаем канал для связи с этим файлом:

    FileChannel channel = file.getChannel();

channel - это тоже ресурс, поэтому можно поместить в блок try with resources.

Теперь можно перейти к чтению из файла. Для начала нужно создать буфер, буфер будет заведомо меньшего размера, чем
размер самого стиха, чтобы понять принцип работы с буфером:

        ByteBuffer buffer = ByteBuffer.allocate(25);

Этой записью создается буфер с вместимостью в 25 байт. Если попытаться после полного заполнения буфера добавить в буфер
еще, хотя бы, 1 байт, то будет выброшено исключение, сигнализирующее о том, что буфер уже полный и места для новых байт
нет. Т.е. данный буфер может быть заполнен, максимум, на 25 байт.

Кусочки стиха будут записываться в переменную типа StringBuilder и в конце стих будет выведен на экран:

        StringBuilder verse = new StringBuilder();

Далее начинается чтение с помощью channel-a информации из файла и записывается в buffer:

        int byteRead = channel.read(buffer);

Метод read ВОЗВРАЩАЕТ КОЛИЧЕСТВО прочитанных байт и запоминает в переменную типа int - byteRead. Т.е. читается
информация с помощью channel из файла и сразу записывается в буфер
( channel.read(куда записываем прочитанную информацию?) ), а КОЛИЧЕСТВО прочитанных байт запоминается в переменной
byteRead. Естественно, за один раз (когда этот код channel.read(buffer); сработает один раз) весь текст стиха не
уместиться в буфер размером в 25 байт, поэтому нужно использовать цикл while до тех про, пока есть что читать из файла:

        while (byteRead > 0) {

Пока переменная byteRead, в которой запоминается КОЛИЧЕСТВО прочтенных байт, будет больше нуля - это будет означать,
что из файла есть что читать.
В цикле будет выводиться каждый раз количество прочитанных байтов (для наглядности работы буфера):

        System.out.println("How many bytes were read from " +
                        "the file and written to the buffer?: " + byteRead +
                        " bytes;");

На буфере в цикле нужно вызвать метод flip. Что это за метод такой и для чего он вообще нужен?
Метод flip (с англ. кувырок или сальто) можно считать, что этот метод выполняет сальто НАЗАД.

Схематически-условное изображение буфера.
Пусть файл содержит 23 байта.
Буфер будет на 10 байт:
ByteBuffer buffer = ByteBuffer.allocate(10); // allocate с англ. выделять

BUFFER WRITE MODE                               BUFFER READ MODE

Position ---> |_____| 0                           Position ---> |_____| 0
              |_____| 1                                         |_____| 1
              |_____| 2                                         |_____| 2
              |_____| 3                                         |_____| 3
              |_____| 4                                         |_____| 4
              |_____| 5                                         |_____| 5
              |_____| 6                                         |_____| 6
              |_____| 7                                         |_____| 7
              |_____| 8                                         |_____| 8
Limit   --->  |_____| 9  <---  Capacity           Limit   --->  |_____| 9  <---  Capacity


Буфер может быть в режиме записи - BUFFER WRITE MODE и в режиме чтения - BUFFER READ MODE.
Буфер, как ранее упоминалось, — это блок в памяти, в который можно что-то записать и потом можно использовать для
чтения. Буфер имеет 3 очень важных понятия:
1. Capacity;
2. Position (означает позицию на которую идет запись в буфер. После вызова метода flip - position обнуляется);
3. Limit (помогает при чтении установить ячейку, до которой необходимо читать);

Понятия Position и Limit зависят от того, происходит запись в буфер или происходит чтение из буфера. Capacity означает
всегда одно и тоже - вместимость буфера, определенный его размер. Если речь идет о ByteBuffer, то возможно записать в
буфер количество байтов не превышающее его вместимость, т.е. не превышающее его capacity. В данном случае capacity
равен 10 байт. Представим, что файл, с которым происходит работа, содержит 23 байта и был создан буфер вместимостью
всего 10 байт.

Для начала рассмотрим WRITE MODE.
Буфер содержит 10 мест для записи байтов, будем считать, что сверху отчет начинается от 0 до 9. Capacity буфера - 10
элементов. Когда что-то записывается в буфер, то это запись делается на определенной позицию - Position. Изначально
позиция равна нулю. Когда что-то записывается в буфер, в данном случае, записываются байты, то позиция меняется.
Записали какой-то байт:

BUFFER WRITE MODE                               BUFFER READ MODE

              |__X__| 0  <--- записали байт       Position ---> |_____| 0
Position ---> |_____| 1                                         |_____| 1
              |_____| 2                                         |_____| 2
              |_____| 3                                         |_____| 3
              |_____| 4                                         |_____| 4
              |_____| 5                                         |_____| 5
              |_____| 6                                         |_____| 6
              |_____| 7                                         |_____| 7
              |_____| 8                                         |_____| 8
Limit   --->  |_____| 9  <---  Capacity           Limit   --->  |_____| 9  <---  Capacity

Теперь позиция в буфере указывает на 1. При следующей записи, байт уже записывается на позицию 1, записался и теперь
позиция в буфере указывает на 2.

BUFFER WRITE MODE                               BUFFER READ MODE

              |__X__| 0  <--- записали байт       Position ---> |_____| 0
              |__X__| 1  <--- записали еще байт                 |_____| 1
Position ---> |_____| 2                                         |_____| 2
              |_____| 3                                         |_____| 3
              |_____| 4                                         |_____| 4
              |_____| 5                                         |_____| 5
              |_____| 6                                         |_____| 6
              |_____| 7                                         |_____| 7
              |_____| 8                                         |_____| 8
Limit   --->  |_____| 9  <---  Capacity           Limit   --->  |_____| 9  <---  Capacity

При еще следующей записи запись будет на позиции 2 и тд. Т.е. постоянно будет происходить запись в следующую ячейку.
Максимальная позиция, в данной случае, это позиция 9 ((capacity - 1), потому, что нумерация ячейки идет с нуля).

Теперь рассмотрим READ MODE.
Когда происходит чтение информации из буфера, то чтение происходит точно так же, как и запись, с определенной ячейки.
Допустим, было записано 10 байт в буфер.

BUFFER WRITE MODE                               BUFFER READ MODE

              |__X__| 0                           Position ---> |_____| 0
              |__X__| 1                                         |_____| 1
              |__X__| 2                                         |_____| 2
              |__X__| 3                                         |_____| 3
              |__X__| 4                                         |_____| 4
              |__X__| 5                                         |_____| 5
              |__X__| 6                                         |_____| 6
              |__X__| 7                                         |_____| 7
              |__X__| 8                                         |_____| 8
Limit   --->  |__X__| 9  <---  Capacity           Limit   --->  |_____| 9  <---  Capacity
Position --->         10

После заполнения буфера Position находится за пределами области памяти, которая была выделена для буфера, т.е. на
позиции 10, куда больше ничего записать уже нельзя, иначе выбросится исключение. Теперь нужно читать из буфера. НО, а
как быть, если Position находится на позиции 10? В этом поможет метод flip. Метод flip меняет режим буфера, т.е.
переводит буфер из режима записи в режим чтения и Position меняется с 10 позиции в позицию 0:

BUFFER WRITE MODE                               BUFFER READ MODE

              |__X__| 0                           Position ---> |__X__| 0
              |__X__| 1                                         |__X__| 1
              |__X__| 2                                         |__X__| 2
              |__X__| 3                                         |__X__| 3
              |__X__| 4                                         |__X__| 4
              |__X__| 5                                         |__X__| 5
              |__X__| 6                                         |__X__| 6
              |__X__| 7                                         |__X__| 7
              |__X__| 8                                         |__X__| 8
Limit   --->  |__X__| 9  <---  Capacity           Limit   --->  |__X__| 9  <---  Capacity
Position --->         10

Чтение начинается с позиции 0, затем с позиции 1, затем с позиции 2 и тд. до позиции, когда чтение происходит с
позиции 9. После чего вызывается другой метод - метод clear. Когда вызывается метод clear (с англ. чистый), то на самом
деле данные в буфере не очищаются, т.е. метод clear не удаляет информацию из буфера, а просто Position возвращается на
нулевой элемент и меняет режим READ на режим WRITING. Когда происходит очередная запись информацию в буфер, то в каждой
ячейке старый байт затирается и записывается в эту ячейку новый байт, т.е. происходит перезапись информации в каждой
ячейке буфера. Метод clear не удаляет информацию из буфера, но при записи новой старая информация затирается, новые
байты записываются поверх старых.

Допустим, так случилось, что Position был на 4 ячейке в режиме READING, а в этот момент был вызван метод clear. Что
произойдёт?

BUFFER WRITE MODE                               BUFFER READ MODE

              |__X__| 0                                         |__X__| 0
              |__X__| 1                                         |__X__| 1
              |__X__| 2                                         |__X__| 2
              |__X__| 3                                         |__X__| 3
              |__X__| 4                            Position --->|__X__| 4
              |__X__| 5                                         |__X__| 5  <---  не прочитанная информация
              |__X__| 6                                         |__X__| 6  <---  не прочитанная информация
              |__X__| 7                                         |__X__| 7  <---  не прочитанная информация
              |__X__| 8                                         |__X__| 8  <---  не прочитанная информация
Limit   --->  |__X__| 9  <---  Capacity           Limit   --->  |__X__| 9  <---  Capacity
Position --->

Переключается режим с READ на WRITE, Position возвращается на нулевой элемент, происходит запись новой информации, а
оставшиеся непрочитанные байты будут потеряны, т.е. перезатерты, потому что поверх них будет записана новая информация:

BUFFER WRITE MODE                               BUFFER READ MODE

Position ---> |__O__| 0                                         |__X__| 0
              |__0__| 1                                         |__X__| 1
              |__0__| 2                                         |__X__| 2
              |__0__| 3                                         |__X__| 3
              |__0__| 4                            Position --->|__X__| 4
              |__0__| 5  <---  перезаписалась инф.              |__X__| 5  <---  не прочитанная информация
              |__0__| 6  <---  перезаписалась инф.              |__X__| 6  <---  не прочитанная информация
              |__0__| 7  <---  перезаписалась инф.              |__X__| 7  <---  не прочитанная информация
Position ---> |__O__| 8  <---  перезаписалась инф.              |__X__| 8  <---  не прочитанная информация
Limit   --->  |__X__| 9  <---  Capacity           Limit   --->  |__X__| 9  <---  Capacity

Что такое Limit?
В режиме записи Limit то же самое, что и capacity, т.е. он означает максимальное количество байт, которое можно
записать в буфер. Когда вызывается метод flip и Position в режиме READ переходит на 0, то Limit означает максимальное
количество байт, которое возможно прочитать. В этом примере файл содержит 23 байта, емкость буфера 10 байт.
Записались первые 10 байтов в буфер. Вызывается метод flip и из буфера читаются все 10 байт. В режиме READ
устанавливается Limit на позиции 9, тем самым сообщая, что можно прочитать весь буфер.

BUFFER WRITE MODE                               BUFFER READ MODE

              |__X__| 0                           Position ---> |__X__| 0
              |__X__| 1                                         |__X__| 1
              |__X__| 2                                         |__X__| 2
              |__X__| 3                                         |__X__| 3
              |__X__| 4                                         |__X__| 4
              |__X__| 5                                         |__X__| 5
              |__X__| 6                                         |__X__| 6
              |__X__| 7                                         |__X__| 7
              |__X__| 8                                         |__X__| 8
Limit   --->  |__X__| 9  <---  Capacity           Limit   --->  |__X__| 9  <---  Capacity
Position --->

Прочитались все 10 байт и был вызван метод clear. В режиме WRITE записываются следующие 10 байт. После записи снова
Position в режиме WRITE установилась на позиции 9. Следовательно, когда вызывается метод flip, то Limit будет равен
позиции 9 в режиме READ. Прочитались обновленные байты и после прочтения был вызван метод clear, тем самым была
установлена позиция в режиме WRITE на 0 ячейку. Два раза по 10 байт уже было записано, осталось записать 3 байта из
23-х. Записываются оставшиеся 3 байта в буфер:

BUFFER WRITE MODE                               BUFFER READ MODE

              |__V__| 0                           Position ---> |__X__| 0
              |__V__| 1                                         |__X__| 1
Position ---> |__V__| 2                                         |__X__| 2
              |__X__| 3                                         |__X__| 3
              |__X__| 4                                         |__X__| 4
              |__X__| 5                                         |__X__| 5
              |__X__| 6                                         |__X__| 6
              |__X__| 7                                         |__X__| 7
              |__X__| 8                                         |__X__| 8
Limit   --->  |__X__| 9  <---  Capacity           Limit   --->  |__X__| 9  <---  Capacity

Тут конечная позиция на ячейке 2 и когда вызывается метод flip, то в режиме READ меняется Position на ячейку 0, но
Limit тут ссылается на ту ячейку, на которой остановился Position в режиме при WRITE вызове метода flip. Т.е. тут
Limit ссылается на ячейку с номером 2:

BUFFER WRITE MODE                               BUFFER READ MODE

              |__V__| 0                           Position ---> |__V__| 0
              |__V__| 1                                         |__V__| 1
Position ---> |__V__| 2                           Limit   --->  |__V__| 2
              |__X__| 3                                         |__X__| 3
              |__X__| 4                                         |__X__| 4
              |__X__| 5                                         |__X__| 5
              |__X__| 6                                         |__X__| 6
              |__X__| 7                                         |__X__| 7
              |__X__| 8                                         |__X__| 8
Limit   --->  |__X__| 9  <---  Capacity                         |__X__| 9  <---  Capacity

Это помогает при чтении, определить сколько байт мы можем прочитать. Читается байт из ячейки 0, затем из ячейки 1,
затем из ячейки 2 и достигается Limit. Благодаря Limit есть понимаение, что дальше Limit читать не нужно, т.к. буфер
был заполнен до Limit-a, до ячейки 2 включительно.

Код:

public class ChannelBufferExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample.txt", "rw");
             FileChannel channel = file.getChannel();) { // создается канал

            ByteBuffer buffer = ByteBuffer.allocate(25); // создан буфер с вместимостью 25 байт

            StringBuilder verse = new StringBuilder(); // Кусочки стиха будут записаны в StringBuilder

            // Читается информация из файла с помощью channel-a и записывается в буфер
            int byteRead = channel.read(buffer); // первый раз читается информация из файла в буфер
            // метод read возвращает количество прочитанных байт
            // Естественно, за один раз весь текст стиха не уместиться в буфер размером в 25 байт, поэтому необходимо
            // поместить это в цикл и читать до тех пор, пока есть что читать
            while (byteRead > 0) { // когда byteRead будет равен нулю, то читать уже нечего
                System.out.println("How many bytes were read from " +
                        "the file and written to the buffer?: " + byteRead +
                        " bytes;"); // каждый раз выводится на экран кол-во прочитанных байт

                buffer.flip(); // Flip с англ. кувырок или сальто

                while (buffer.hasRemaining()) { // hasRemaining - пока в буфере есть что читать,

                    verse.append((char) buffer.get()); // метод get читает по 1 байту.
                    // позиция каждый раз смещается и приближается к концу буфера
                }
                buffer.clear();
                byteRead = channel.read(buffer);
            }

            System.out.println(verse); // стихотворение выводится на экран

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы.Вывод на экран:
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 25 bytes;
How many bytes were read from the file and written to the buffer?: 3 bytes;
Had I the heavens' embroidered cloths,
Enwrought with golden and silver light,
The blue and the dim and the dark cloths
Of night and light and the half-light,
I would spread the cloths under your feet:
But I, being poor, have only my dreams;
I have spread my dreams under your feet;
Tread softly because you tread on my dreams.
					William Butler Yeats

Задание-пример.
Необходимо дополнить файл "forChannelBufferExample.txt" цитатой Альберта Эйнштейна "There are only two ways to live your
life. One is as though nothing is a miracle. The other is as though everything is a miracle." используя Channel и
Buffer.
Цитата будет помещена в переменную text типа String:

        String text = "\nThere are only two ways to live your life." +
                    " One is as though nothing is a miracle. The other is as " +
                    "though everything is a miracle.";

Для записи информации в файл будет создан новый буфер, а Channel будет прежним. Это будет доказетельство того, что
Channel можно использовать как для записи в файл, так и для чтения из него. Буфер будет создан таким размером, чтобы
цитата в него сразу вместилась целиком. Такой буфер можно создать двумя способами:

        1-ый способ:
        ByteBuffer bufferForWrite = ByteBuffer.allocate(text.getBytes().length);

Т.е. 1-ым способом цитата переводится в массив байтов при помощи метода getBytes и вычисляется длина массива (length).
Буфер создался конкретно под эту цитату. При помощи метода put цитата будет добавлена в буфер:

        bufferForWrite.put(text.getBytes());

Чтобы прочитать из буфера необходимо вызвать метод flip:

        bufferForWrite.flip();

Чтобы Channel прочитал информацию из буфера и после записал в файл:

        channel.write(bufferForWrite);

Код:

public class ChannelBufferExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample.txt", "rw");
             FileChannel channel = file.getChannel();) {

            ByteBuffer buffer = ByteBuffer.allocate(25);
            StringBuilder verse = new StringBuilder();

            int byteRead = channel.read(buffer);
            while (byteRead > 0) {
                System.out.println("How many bytes were read from " +
                        "the file and written to the buffer?: " + byteRead +
                        " bytes;");

                buffer.flip();

                while (buffer.hasRemaining()) {
                    verse.append((char) buffer.get());
                }
                buffer.clear();
                byteRead = channel.read(buffer);
            }
            System.out.println(verse);

            // Цитата Альберта Эйнштейна
            String text = "\nThere are only two ways to live your life." +
                    " One is as though nothing is a miracle. \nThe other is as " +
                    "though everything is a miracle.";

            ByteBuffer bufferForWrite = ByteBuffer.allocate(text.getBytes().length);
            bufferForWrite.put(text.getBytes());
            bufferForWrite.flip();
            channel.write(bufferForWrite);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Содержимое файла "forChannelBufferExample.txt":
Had I the heavens' embroidered cloths,
Enwrought with golden and silver light,
The blue and the dim and the dark cloths
Of night and light and the half-light,
I would spread the cloths under your feet:
But I, being poor, have only my dreams;
I have spread my dreams under your feet;
Tread softly because you tread on my dreams.
					William Butler Yeats
There are only two ways to live your life. One is as though nothing is a miracle.
The other is as though everything is a miracle.

2-ой способ создания буфера.
Создается буфер:

         ByteBuffer bufferForWrite2 = ByteBuffer.wrap(text.getBytes());

И после информация записывается из буфера в файл:

        channel.write(bufferForWrite2);

Этот второй способ более эллегантый. Метод wrap сразу записывает информацию из тектса в буфер, т.е. как в 1-ом способе
не нужно указывать размер буфера ( ByteBuffer bufferForWrite = ByteBuffer.allocate(text.getBytes().length); ), не нужно
записывать в буфер информацию ( bufferForWrite.put(text.getBytes()); ) и более того, не нужно вызыватьметод flip
( bufferForWrite.flip(); ) чтобы position изменить на 0. Все эти вещи делает метод wrap. Метод wrap и записывает
информацию сразу в буфер из текста переведенного в массив байтов, соответствено этому размеру он создает необходимой
вместительности (capacity) буфер и сам делате flip. После чего остается записать, с помошью канала, информацию
из этого буфера.

Код:

public class ChannelBufferExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample.txt", "rw");
             FileChannel channel = file.getChannel();) {

            ByteBuffer buffer = ByteBuffer.allocate(25);
            StringBuilder verse = new StringBuilder();

            int byteRead = channel.read(buffer);
            while (byteRead > 0) {
                System.out.println("How many bytes were read from " +
                        "the file and written to the buffer?: " + byteRead +
                        " bytes;");

                buffer.flip();

                while (buffer.hasRemaining()) {
                    verse.append((char) buffer.get());
                }
                buffer.clear();
                byteRead = channel.read(buffer);
            }
            System.out.println(verse);

            // Цитата Альберта Эйнштейна
            String text = "\nThere are only two ways to live your life." +
                    " One is as though nothing is a miracle. \nThe other is as " +
                    "though everything is a miracle.";

//            ByteBuffer bufferForWrite = ByteBuffer.allocate(text.getBytes().length);
//            bufferForWrite.put(text.getBytes());
//            bufferForWrite.flip();
//            channel.write(bufferForWrite);

            ByteBuffer bufferForWrite2 = ByteBuffer.wrap(text.getBytes());
            channel.write(bufferForWrite2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Содержимое файла "forChannelBufferExample.txt":
Had I the heavens' embroidered cloths,
Enwrought with golden and silver light,
The blue and the dim and the dark cloths
Of night and light and the half-light,
I would spread the cloths under your feet:
But I, being poor, have only my dreams;
I have spread my dreams under your feet;
Tread softly because you tread on my dreams.
					William Butler Yeats
There are only two ways to live your life. One is as though nothing is a miracle.
The other is as though everything is a miracle.
 */

public class ChannelBufferExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample.txt", "rw");
             FileChannel channel = file.getChannel();) {

            ByteBuffer buffer = ByteBuffer.allocate(25);
            StringBuilder verse = new StringBuilder();

            int byteRead = channel.read(buffer);
            while (byteRead > 0) {
                System.out.println("How many bytes were read from " +
                        "the file and written to the buffer?: " + byteRead +
                        " bytes;");

                buffer.flip();

                while (buffer.hasRemaining()) {
                    verse.append((char) buffer.get());
                }
                buffer.clear();
                byteRead = channel.read(buffer);
            }
            System.out.println(verse);

            // Цитата Альберта Эйнштейна
            String text = "\nThere are only two ways to live your life." +
                    " One is as though nothing is a miracle. \nThe other is as " +
                    "though everything is a miracle.";

            ByteBuffer bufferForWrite = ByteBuffer.allocate(text.getBytes().length);
            bufferForWrite.put(text.getBytes());
            bufferForWrite.flip();
//            channel.write(bufferForWrite);

            ByteBuffer bufferForWrite2 = ByteBuffer.wrap(text.getBytes());
            channel.write(bufferForWrite2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}