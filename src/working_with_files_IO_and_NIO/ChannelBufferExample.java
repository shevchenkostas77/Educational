package working_with_files_IO_and_NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
Пакет "java.nio" переводится, как "new input output". С помощью пакета java.nio, c помощи классов реализующих интерфейсы
Channel и Buffer можно работать не только с файлами, но и, например, с сетью тоже.
Есть программа (program) и есть файл (file). И есть объекты классов реализующие интерфейс  Channel и Buffer. Когда
необходимо прочитать что-то из файла, объект класса реализующий интерфейс Channel связывается с файлом, читает из него
определенную информацию, записывает информацию в объект класса реализующий интерфейс Buffer и
затем программа читает информацию из Buffer. Когда необходимо что-то записать в файл, то сначала запись программы
происходит из программы в Buffer, далее Channel читает эту информацию из Buffer-a и записывает в файл. Есть разные
классы, которые реализуют интерфейс Channel: DatagramChannel, SocketChannel,
ServerSocketChannel, FileChannel и тд. В этом разделе будет рассмотрен FileChannel. Buffer тоже бывают разными:
ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer, ShortBuffer. В своих примерах буду работать с
ByteBuffer.
Интерфейс Channel немного похож на стрим, но есть огромная разница:
1. Стримы всегда работают в одном направлении: или чтение или запись, Channel работают в оба направления, т.е. можно
записывать в файл информацию из Buffer и можно читать информацию из файла в Buffer. Так же Channel всегда работает с
Buffer. Он либо читает информацию из файла в Buffer либо записывает информацию из Buffer в файл. Что такое Buffer?
Buffer - это блок памяти, в который можно записывать информацию и читать информацию из нее. Используя Buffer можно
читать информацию из него затем возвращаться к уже прочитанной информации и снова ее просматривать, т.е. "гулять" по
буферу туда-сюда, чего не возможно сделать в стримах.

Buffer - это блок памяти, в который можно записывать информацию, а также читать ее.
В отличие от стримов Channel может как читать файл, так и записывать в него.
Чтение файла: Channel читает информацию из файла и записывает в Buffer.
Запись в файл: Channel читает информацию из Buffer и записывает ее в файл.

В примере будет использован файл со стихотворением William Butler Yeats (forChannelBufferExample.txt). Необходимо
прочитать этот файл и вывести его содержимое на экран.
Для того, чтобы одним Channel можно было и писать и читать из файла можно использовать класс RandomAccessFile.


 */
public class ChannelBufferExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("forChannelBufferExample.txt", "rw");
             FileChannel channel = file.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(25); // создан буфер с вместимостью 25 байт
            /*
            Если попытатся после его полного заполнения добавить в буфер еще хотя бы один байт, то будет выброшего
            исключение сигнализирующее о том, что буфер уже полный и места для новых байт нет. Т.е. buffer может быть
            заполнен максимум на 25 байт.
             */

            // Кусочки стиха можно записывать в StringBuilder и в конце вывести весь стих на экран
            StringBuilder stih = new StringBuilder();


            // Теперь начинаем читать с помощью channel-a информацию из файла и записываем ее buffer.
            int byteRead = channel.read(buffer); // первый раз читается информация из файла в буфер
            // этот метод возвращает количество прочитанных байт
            /*
            Естественно, за один раз весь текст стиха не уместиться в буфер размером в 25 байт, поэтому необходимо
            поместить это в цикл и читать до тех пор, пока есть что читать
             */
            while (byteRead > 0) { // когда byteRead будет равен нулю, то читать уже нечего
                System.out.println("Read: " + byteRead); // каждый раз выводится на экран кол-во прочитанных байт

                buffer.flip();
                /*
                Метод flip (с англ. кувырок или сальто) можно считать, что этот метод выполняет сальто назад *
                 */
                while (buffer.hasRemaining()) { // пока в буфере есть что читать
                    stih.append((char) buffer.get());
                }

                buffer.clear();
                byteRead = channel.read(buffer);
            }

            System.out.println(stih);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
Буфер может быть в режиме записи (BUFFER WRITE MODE) и в режиме чтения (BUFFER READ MODE). Буфер - это блок в памяти, в
который возможно что-то записать и который потом можно использовать для чтения. Буфер имеет 3 очень важных понятия:
1. Capacity
2. Position означает позицию на которую идет запись в буфер. После метода flip Position обнуляется,
3. Limit помогает при чтении установить ячейку, до которой мы будем читать наши байты.
Понятия Position и Limit зависит от того, происходит ли запись в буфер сейчас в буфер или происходит чтение из буфера.
Capacity всегда означает одно и тоже - вместимость. Представим, что файл с которым происходит работа, содержит 23 байта.
Был создан буфер вместимостью 10 байт (ByteBuffer buffer = ByteBuffer.allocate(10);). Что такое Capacity? Буфер, как
уже ранее упоминалось, блок памяти, который имеет определенный размер. Этот определенный размер так же называют
capacity. Если речь идет о ByteBuffer, то возможно записать в buffer количество байтов не превышающее его вместимость,
т.е. не превышающее его capacity. В данном случае capacity равен 10. Рассмотрим WRITE MODE.
Буфер содержит 10 мест для записи байтов. Когда что-то записывается в буфер, то это запись делается на определенную
позицию - Position. Изначально позиция равна нулю. Когда что-то записывается в буфер, в данном случае, записываются
байты, то позиция меняется. Теперь позиция в буфере указывает на 1. При следующей записи, байт уже записывается на
позицию 1 и теперь позиция в буфере указывает на 2. При еще следующей записи запись будет на позиции 2 и тд. Т.е.
постоянно будет происходить запись в следующую ячейку. Максимальная позиция, в данной случае, это позиция 9
(capacity - 1, потому, что нумерация ячейки идет с нуля). Когда происходит чтение информации из буфера, то чтение
происходит с определенной ячейки буфера. Допустим, было записано 10 байт в буфер. Что это означает? Что Position
находится за пределами области пямяти, которая была выделена для буфера, т.е. на позиции 10, куда больше ничего
записать уже нельзя иначе выбросится исключение. Теперь мы хотим читать из буфера. Мы уже записали и записывать дальше
некуда, пора читать из буфера. И что мы должны начинать читать информацию из буфера с позиции 10? Мы же ничего не
сможем прочесть. Это нас не устраивает, поэтому я и вызываю метод flip. Метод flip меняет режим буфера, из режима
записи в режим чтения. Когда мы вызываем метод flip, позиция меняется с 10 из WRITING MODE в нулевую позицию для READING
MODE. Т.е. вызвав метод flip мы меняем позицию с какой-то, например, с 10-ой на нулевую позицию. И теперь мы можем
начинать читать нащ буфер начиная с позиции 0. Вот что делает метод flip. Мы читаем байт на нулевой позиции, затем с
первой позиции и тд. пока не дойдем до позиции 9, когда мы читаем байт с 9 позиции, после чего мы вызываем дугой метод
не метод flip, а метод clear. Что происходит? Когда вызывается метод clear, то на самом деле данные в буфере не
очищаются, мы просто ставим позицию на нулевой элемент в режиме WRITING MODE и теперь, когда мы записываем какую-то
информацию в буфер, то старый байт затирается и мы начинаем записывать в эту ячейку новый байт. Переходим к следующей
позиции, перезаписываем новый байт и тд. Т.е. метод clear меняет позицию с какой-либо на нулевую, т.е. он не удаляет
информацию из буфера, но при записи новой информации в буфер старая информация просто затирается, мы записываем новые
новые байты поверх старых. Если так случилось, что мы были на позиции, допустим, 4-ой в режиме READING MODE и вызвали
метод clear, позиция в буфере WRITE MODE перевелась на нулевую и мы начинаем записывать новую информацию. Тогда,
оставшиеся непрочитанные байты для нас навсегда потеряны, потому что поверх них будет записана новая информация.
Что такое Limit?
В режиме записи Limit то же самое, что и capacity, т.е. он означает максимальное количество байтов, которое мы можем
записать в буфер. Когда мы делаем flip, сальто назад, и позиция в режиме чтения из буфера переходит на нулевую тут
Limit означает максимальное количество байтов, которое мы можем прочитать. И этот Limit равен позиции Position в
WRITING MODE до перехода буфера в READING MODE. Это помогает при чтении, сколько байт мы можем прочитать. Благодаря
Limit мы понимаем, что дальше Limit читать не нужно, т.к. буфер был заполнен до Limit-a.


 */