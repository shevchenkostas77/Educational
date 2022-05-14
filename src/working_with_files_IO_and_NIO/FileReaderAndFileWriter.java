package working_with_files_IO_and_NIO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/*
Character streams and Byte streams (это не имеет никакого отношения к интерфейсу Stream, где в методах используются
lambda выражения).

Stream (поток) для работы с файлами - это упорядоченная последовательность данных. Когда необходимо записать какую-то
информацию в файл или же прочитать информацию из файла в этом будут помогать streams. Определенные stream будут
записывать информацию, определенные stream будут читать информацию.

Если попробывать визуализировать этот процесс, то он будет, приблизительно, выглядеть так:

            ________write________

            ->  ->  ->  ->  ->  ->
            _____________________
program                              file
            ________read_________

            <-  <-  <-  <-  <-  <-
            _____________________

Есть программа (program) и есть файл (file). Между программой и файлом "протекает" поток. Когда происходит запись
информации из программы в файл, то при помощи stream передается информация в файл, а когда читается программой
информация из файла, естественно, нужен будет отдельный поток, который поможет передать эту информацию.

В Java потоки разделяются на:
1. потоки, которые работают с какими-то символами (буквы, цифры и тд.);
2. потоки, которые работают с байтами;

Файлы разделяют на:
1. читабельные для человека - text files (текстовые файлы). Т.е. те файлы, которые можно открыть и прочитать (обычный
   текс), например, файлы Word, PDF файлы и тд.
2. нечитабельные для человека - binary files (бинарные файлы). Т.е. те файлы, открыв которые, человек не сможет
   прочитать, например, если человек откроет в бинарном режиме, какую-то картинку или видео. Зато эти файлы с
   легкостью читают программы, которые должны уметь открывать фотографии или воспроизводить видео;

В пакете java.io (IO - Input Output) существуют классы для работы с обоими типами файлов (читабельные / нечитабельные
для человека). При работе с текстовыми и бинарными файлами необходимо использовать разные типы потоков. Т.е., ничего
не получится, если попытаться с помощью стрима, который подходит для текстовых файлов прочитать бинарный.

Стримы, которые используются для работы с текстовыми файлами - FileReader (чтение файлов) и FileWriter (запись файлов).


Задание-пример.
Необходимо записать текст (рубаи) в файл.
Для того, что бы записать текс в файл необходимо создать объект FileWriter (нужно импортировать класс -
import java.io.FileWriter;).
Метод содания объекта FileWriter:

        FileWriter writer = new FileWriter("nameFile.txt");

Код:

public class FileReaderAndFileWriter {
    public static void main(String[] args) {
        String rubai = "Много лет размышлял я над жизнью земной.\n" +
                "Непонятного нет для меня под луной.\n" +
                "Мне известно, что мне ничего не известно!\n" +
                "Вот последняя правда, открытая мной.\n";
        FileWriter writer = null;
        try {
            writer = new FileWriter("text1.txt"); // в параметр передается относительный путь в двойных кавычках
            for (int i = 0; i < rubai.length(); i++) {
                writer.write(rubai.charAt(i)); // будет происходить посимвольная передача текста (побуквенная)
            }
            System.out.println("Done!"); // как текст полностью будет записан в файл, "Done!" выведется в консоль
        } catch (IOException e) { // FileWriter выбрасывает исключение (нужно import java.io.IOException;)
            e.printStackTrace();
        } finally {
            try {
                writer.close(); // метод close выбрасывает исключение - IOException
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

Исключение IOException - это суперкласс для исключений, которые выбрасываются при работе с файлами.
Метод write принимает в свой параметр int, а не char (метод charAt возвращает char):

        writer.write(rubai.charAt(i));

Тут будет происходить автоматический casting до int.

Обязательно после создания стрима FileWriter и проведения всех необходимых операций, стрим нужно закрыть с помощью
метода close (обычно его закрывают в блоке finally). Метод close выбрасывает исключение - IOException. Если переменную
writer указать в блоке try:

        try {
            FileWriter writer = new FileWriter("text1.txt"); //          <---- ВОТ ТУТ!
            for (int i = 0; i < rubai.length(); i++) {
                writer.write(rubai.charAt(i));
            }
            System.out.println("Done!");
        }

то эту переменную в блоке finally не будет видно!!!

Код:
public class FileReaderAndFileWriter {
    public static void main(String[] args) {
        String rubai = "Много лет размышлял я над жизнью земной.\n" +
                "Непонятного нет для меня под луной.\n" +
                "Мне известно, что мне ничего не известно!\n" +
                "Вот последняя правда, открытая мной.\n";
        FileWriter writer = null;
        try {
            writer = new FileWriter("text1.txt"); // в параметр передается относительный путь в двойных кавычках.
            for (int i = 0; i < rubai.length(); i++) {
                writer.write(rubai.charAt(i)); // будет происходить посимвольная передача текста (побуквенная)
            }
            System.out.println("Done!"); // как текст полностью будет записан в файл, "Done!" выведется в консоль
        } catch (IOException e) { // FileWriter выбрасывает исключение (нужно import java.io.IOException;)
            e.printStackTrace();
        } finally {
            try {
                writer.close(); // метод close выбрасывает исключение - IOException
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

Запуск программы. Вывод:
Done!

Содержимое файла "text1.txt":
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

Если не закрыть стрим, то

Запуск программы. Вывод:
Done!

Содержимое файла:
ПУСТО

Заранее файл можно не создавать, к примеру, на рабочем столе или в какой-либо папке, в этом случае файл будет создан
автоматически после запуска программы. Если не указывать абсолютный путь (путь, по которому можно найти файл в файловой
системе операционной системы) в параметре, при создании объекта FileWriter, а лишь указать название файла (относительный
путь), т.е. использовать этот метод создания:

        writer = new FileWriter("text1.txt");

тогда файл создасться в корне проэкта.

Абсолютный путь к файлу (Windows): C:\Documents\Java\text1.txt
Относительный путь к файлу(Windows): text1.txt

Можно в параметр метода write, передать объект типа String, т.е. текс целиком:

public class FileReaderAndFileWriter {
    public static void main(String[] args) {
        String rubai = "Много лет размышлял я над жизнью земной.\n" +
                "Непонятного нет для меня под луной.\n" +
                "Мне известно, что мне ничего не известно!\n" +
                "Вот последняя правда, открытая мной.\n";
        FileWriter writer = null;
        try {
            writer = new FileWriter("text1.txt");
            writer.write(rubai);   <--- в параметр метода write передается переменная rubai типа String
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

Запуск программы. Вывод:
Done!

Содержимое файла "text1.txt":
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

В данном примере удобнее в параметр методы write передавать объект типа String, но "под капотом" будет идти та же
посимвольная работа. Т.е. объект типа String будет разбиваться на отдельные символы. Все это не очень эффективно.


Задание-пример.
Необходимо дополнить файл text1 словом privet. Т.е. в файл уже записан Рубаи и нужно добавить к этому тексту одно
слово.

public class FileReaderAndFileWriter {
    public static void main(String[] args) {
        String s = "privet"; // Объект типа String, т.е. слово privet
        FileWriter writer = null;
        try {
            writer = new FileWriter("text1.txt");
            writer.write(s);
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

Запуск программы. Вывод:
Done!

Содержимое файла "text1.txt" (ДО запуска программы):
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

Содержимое файла "text1.txt" (ПОСЛЕ запуска программы):
privet

В данном случае произошла полная перезапись файла, т.е. ранее записанный текс рубай был стерт и вместо него появилось
одно слово privet. Для того, чтобы слово privet добавилось после четверостишия, для этого нужно в конструкторе
класса FileEriter, вторым параметром указать слово true (второй параметр - append, т.е. как бы задается вопрос
"присоеденить?"):

        writer = new FileWriter("text1.txt", true);

Запуск программы. Вывод:
Done!

Содержимое файла "text1.txt" (ДО запуска программы):
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.

Содержимое файла "text1.txt" (ПОСЛЕ запуска программы):
Много лет размышлял я над жизнью земной.
Непонятного нет для меня под луной.
Мне известно, что мне ничего не известно!
Вот последняя правда, открытая мной.
privet

Теперь все работает как нужно.

Конструктор класса FileWriter может принимать не только объекты типа String:

        writer = new FileWriter("text1.txt"); "text1.txt" - задано String имя файла и его расширение

но и сам объект класса File.

public class FileReaderAndFileWriter {
    public static void main(String[] args) {
        String rubai = "Много лет размышлял я над жизнью земной.\n" +
                "Непонятного нет для меня под луной.\n" +
                "Мне известно, что мне ничего не известно!\n" +
                "Вот последняя правда, открытая мной.\n";
        String s = "privet";
        FileWriter writer = null;
        try {
            writer = new FileWriter("text1.txt", true); // в параметр передается относительный путь в двойных кавычках.
            writer.write(s);
            System.out.println("Done!"); // как текст полность будет записан в файл, "Done!" выведется в консоль
        } catch (IOException e) { // FileWriter выбрасывает исключение (нужно import java.io.IOException;)
            e.printStackTrace();
        } finally {
            try {
                writer.close(); // метод close выбрасывает исключение - IOException
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

Задание-пример.
Нужно прочитать и вывексти на экран все, что есть в каком-то текстовом файле.

Для того, чтобы прочитать текс из файла необходимо создать объект FilaReader (нужно импортировать класс -
import java.io.FileReader;).
Метод содания объекта FileReader:

        FileReader writer = new FileReader("nameFile.txt");

FileReader выбрасывает исключение FileNotFoundException.
Стрим FileReader читает посимвольно c помощью метода read. Метод read возвращает int, это нужно для того, чтобы можно
было понять, когда наступает конец файла. Когда наступает конец файла, метод read будет возвращать "-1". Т.е. происходит
чтение каждого символа из файла с помощью метода read, если насупает конец файла, метод это понимает и возвращает "-1".

Код:
public class FileReaderAndFileWriter {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            reader = new FileReader("test1.txt"); // в параметр передается относительный путь в двойных кавычках
            int character;
            while ((character = reader.read()) != -1) {
                System.out.println((char)character); // character это int поэтому тут нужен casting
            }
            System.out.println(); // строка разграничитель
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close(); // ОБЯЗАТЕЛЬНО нужно закрывать ресурс
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
*/

public class FileReaderAndFileWriter {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            reader = new FileReader("test1.txt"); // в параметр передается относительный путь в двойных кавычках
            int character;
            while ((character = reader.read()) != -1) {
                System.out.println((char)character); // character это int поэтому тут нужен casting
            }
            System.out.println(); // строка разграничитель
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close(); // ОБЯЗАТЕЛЬНО нужно закрывать ресурс
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}