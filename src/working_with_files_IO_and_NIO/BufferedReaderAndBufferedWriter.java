package working_with_files_IO_and_NIO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/*
Что такое буферизация?
Буферизация это процесс загрузки части файла происходящий во время потоковой передач, например, музыки или видео до их
воспроизведения. Эта форма буферизации позволяет смотреть или слушать медиафайлы почти мгновенно, загружая только часть
файла, затем воспроизводя его, пока оставшаяся часть продолжает загружаться. Например, когда открывается какое-то видео
на YouTube можно увидеть как идет буферизация и по мере того, как буферизация продвигается можно просматривать видео.
Использование буферизации в стримах позволяет достичь большей эффективности при чтении файла или записи в него.

Как создается BufferedWriter:

        BufferedWriter writer = new BufferedWriter(new FileWriter("nameFile.txt");

Как создается BufferedReader:

        BufferedReader reader = new BufferedReader(new FileReader("nameFile.txt");

При создании BufferedWriter и BufferedReader используются объекты FileWriter и FileReader соответственно. Т.е.
BufferedWriter и BufferedReader это обертки, они оборачивают объекты FileWriter и FileReader и добавляют
функциональность буферизации.

Есть программа (program) и есть файл (file). В файле содержится слово "privet".
Для начала будет рассмотрен пример с BufferedReader. Если использовать только FileReader, а он использует посимвольное
чтение, то для прочтения каждого символа он будет обращаться к файлу, что довольно-таки ресурсозатратная работа.
Т.е. происходит обращение к файлу, читается один символ из файла, допустим, первый символ - "p", с этим символом можно
делать что угодно, допустим, вывести на экран. Затем FileReader снова обращается к файлу, читает следующий один символ -
"r" и этот символ выводится на экран. Процесс снова повторяется, FileReader обращается к файлу, читает следующий один
символ - "i" и этот символ так же выводится на экран. Т.е. для каждого следующего символа процесс будет повторяться
снова и снова. С BufferedReader все немного по-другому. Есть какой-то буфер, с определенным количеством места для
символов. Происходит обращение к файлу, читается информация из файла (читаются символы) и заполняется этой информацией
буфер на сколько это возможно. Допустим, все слово "privet" поместилось в буфер. Т.е. всего один раз (в зависимости от
размера буфера), необходимо обратиться к файлу, не пять раз, если использовать просто объект FileReader для чтения
каждого символа слова "privet", а всего один. Да, запись в буфер происходит посимвольно, но потом уже работа происходит
с самим буфером, а не с файлом. Читается из буфера информация и выводятся те же символы на экран. Разница в том, что
обращение к файлу - это ресурсоемкая операция, требующая определенного времени. При использовании FileReader происходит
n-ое количество раз обращения к файлу, а при использовании BufferedReader, если содержимое файла не большое, как в
примере со словом "privet", то запросто можно обратиться всего один раз. В любом случае количество раз обращений к файлу
с использованием BufferedReader будет гораздо меньше, чем при помощи FileReader. В количестве обращений к файлу и
заключается разница. И это сильно увеличивает эффективность работы программы.

Теперь будет рассмотрен пример с BufferedWriter.
FileWriter каждый раз, при вызове метода write будет обращаться к файлу и записывать в него всего лишь один символ. Все
тот же пример со словом "privet". Происходит обращения к файлу и записывается один символ - "p". Для записи следующего
символа снова происходит обращение к файлу и записывается символ - "r", потом, еще раз, происходит обращение к файлу и
записывается символ - "i" и тд. Когда используется BufferedWriter, то в том случае, когда используется метод write
много раз, сначала все символы будут записываться в буфер, потом один или несколько раз (в зависимости от размера
буфера) будет идти обращение к файлу, а далее будет идти посимвольная запись в файл из буфера. Таким образом,
опять-таки, разница заключается в том, что значительно уменьшается количество раз обращений к файлу, что делает код
более эффективным.

BufferedReader и BufferedWriter нужно импортировать:
import java.io.BufferedReader;
import java.io.BufferedWriter;

Задание-пример.
Необходимо все содержимое какого-то файла скопировать при помощи BufferedReader и записать в новый файл с помощью
BufferedWriter.

Код:

public class BufferedReaderAndBufferedWriter {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("rubai.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("copyRubai.txt"))) {

            int character;
            while ((character = reader.read()) != -1) {
                writer.write((char)character);
            }
            System.out.println("Done!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы.
Содержимое файла "rubai.txt":
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

Содержимое файла "copyRubai.txt":
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

Все сработало как нужно.

У BufferedReader есть метод readLine, он читает сразу строку.

Код:

public class BufferedReaderAndBufferedWriter {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("rubai.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("copyRubai.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n"); // после записи каждой строки происходит переход на новую строку
            }

            System.out.println("Done!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы.
Содержимое файла "rubai.txt":
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

Содержимое файла "copyRubai.txt":
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

Все сработало как нужно.

 */

public class BufferedReaderAndBufferedWriter {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("rubai.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("copyRubai.txt"))) {

//            int character;
//            while ((character = reader.read()) != -1) {
//                writer.write((char)character);
//            }

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n"); // после записи каждой строки происходит переход на новую строку
            }

            System.out.println("Done!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}