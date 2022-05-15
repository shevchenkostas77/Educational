package working_with_files_IO_and_NIO;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


/*
Очень часто программисты забывают, забывали и будут забывать закрывать ресурсы, которые они используют. Особенно, если
используется сразу несколько ресурсов. Чтобы не забывать это делать, а так же, чтобы вообще не закрывать ресурсы, т.е.
не прописывать команду resource.close(); был придуман такой механизм, который называется try with resources.
Как пишется Try with resource?
После ключевого слова try открываются круглые скобки, в эти круглые скобки записываются ресурсы, которые после
срабатывания кода будут автоматически закрыты:

        try (all resources) {
            SOME CODE;
        } catch (Exception e) {
            SOME CODE;
        } finally {
            SOME CODE;
        }

После компиляции кода Java компилятор внутренне переведет try with resource в обычный try и finally блок. Естественно,
если используются catch блоки, то Java компилятор их трогать не будет, они останутся на своем законном месте. Когда
пишется несколько ресурсов в Try with resources (в круглых скобках), то statements отделяются друг от друга символом
точка с запятой - ";":

        try (first resource;
             second resource;
             ...;
             n resource;) {

             SOME CODE;

        } catch (Exception e) {
            SOME CODE;
        } finally {
            SOME CODE;
        }

Блок try может использоваться как с catch блоком, как с finally блоком, а так же и с catch и с finally блоком
одновременно:
    1. try-catch;
    2. try-finally;
    3. try-catch-finally;

Из-за того, что try with resources подразумевает, что компилятор внутренне все же создаст finally блок, то catch блок
можно не писать, конечно же, если не нужно, чтобы код обрабатывал исключения. Т.е. может быть код, где есть только один
блок Try with resources:

        method() throw Exception { <- указать, что метод может выбросить исключение
            try (all resources) {
                SOME CODE;
            }

ВАЖНО!!!
Ресурс, который используется в Try with resources должен имплементировать интерфейс AutoCloseable!
Например, ресурс FileReader наследует класс InputStreamReader, InputStreamReader наследует абстрактный класс Reader,
Reader имплементирует интерфейсы Readable и Closeable, Closeable происходит от интерфейса AutoCloseable.
 */

public class TryWithResources {
    public static void main(String[] args) {
        String rubai = "Много лет размышлял я над жизнью земной.\n" +
                "Непонятного нет для меня под луной.\n" +
                "Мне известно, что мне ничего не известно!\n" +
                "Вот последняя правда, открытая мной.\n";
        try (FileWriter writer = new FileWriter("rubai.txt", true);
             FileReader reader = new FileReader("rubai.txt")) {
            // Запись в файл
            for (int i = 0; i < rubai.length(); i++) {
                writer.write(rubai.charAt(i));
            }
            writer.close(); // тут метод close для того, чтобы после записи в файл можно было прочитать из файла

            // чтение из файла
            int character;
            while((character = reader.read()) != -1) {
                System.out.print((char)character);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}