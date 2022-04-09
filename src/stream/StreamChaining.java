package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/*
Работа метод chaining в stream (chaining с англ. цепочка).
Метод chaining - использование методов друг за другом в цепочке.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamChaining {
    public static void main(String[] args) {

        int[] array = new int[] {3, 8, 1, 5, 9, 12, 4, 21, 81, 7, 18};
        /*
        Задание-пример использования chaining.
        Необходимо отфильтровать массив так, чтобы в массиве остались нечетные числа, после необходимо поделить на 3
        только те числа, которые делятся без остатка и найти сумму оставшихся изменённых чисел.
        */
        int result = Arrays.stream(array).filter(element -> element % 2 == 1) // 3, 1, 5, 9, 21, 81, 7 (возвращает stream)
                .map(element ->
                {
                    if (element % 3 == 0) {
                        element /= 3;
                    }
                    return element;
                })                                                            // 1, 1, 5, 3, 7, 27, 7 (возвращает stream)
                .reduce((accumulator, element) -> accumulator + element)      // 51 (возвращает объект типа Optional)
                .getAsInt();
        System.out.println("result = " + result);
        // Вывод:
        // result = 51
        /*
        Чтобы создать stream из массива, необходимо импортировать класс Arrays, который находится в пакете java.util.
        Вызвать статический метод stream и в параметре метода указать массив, из которого необходимо получить поток.
        Сначала происходит фильтрация, проверяется очередной элемент: при делении на 2 дает 1? Если да, то он проходит
        дальше. Далее с помощью метода map происходит деление только тех чисел, которые делятся на 3 из уже
        отфильтрованного потока. Далее с помощью метода reduce находится сумма. При помощи метода getAsInt (после работы
        метода reduce поток превратился в объект типа Optional) результат работы передается переменной типа int и
        выводится на экран.
        */

        /*
        Задание-пример использования chaining.
        Для списка студентов нужно сделать следующее:
        1. Имена заглавными буквами;
        2. Отфильтровать по полу;
        3. Отсортировать по возрасту;
        4. Результат вывести на экран;
        Если все это делать без stream-a будет намного больше строк кода.
        */

        Student st1 = new Student("Ivan", 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Mariya", 'f', 23, 3, 7.4);

        List<Student> listOfStudents = new ArrayList<>();

        listOfStudents.add(st1);
        listOfStudents.add(st2);
        listOfStudents.add(st3);
        listOfStudents.add(st4);
        listOfStudents.add(st5);

        listOfStudents.stream()
                .map(element ->
                {
                    element.setName(element.getName().toUpperCase());
                    return element;
                })
                .filter(element -> element.getSex() == 'f')
                .sorted((student1, student2) -> student1.getAge() - student2.getAge())
                .forEach(System.out::println);
        // Вовод:
        // Student {name = 'ELENA', sex = 'f', age = 19, course = 1, averageGrade = 8.9}
        // Student {name = 'MARIYA', sex = 'f', age = 23, course = 3, averageGrade = 7.4}
        /*
        1. При помощи метода map все буквы в именах студентов становятся заглавными;
        2. При помощи метода filter происходит фильтрация по полу;
        3. При помощи метода sorted происходит сортировка студентов по возрасту;
        4. При помощи метода forEach (в параметрах метода указывается метод референс) выводится результат на экран;
        */

        /*
        Как происходит работа chaining в stream.
        Есть какой-то source (например, коллекции, массив). Из этого source создается stream (поток) и уже элементы
        этого stream-а используются методами, которые называются называются "intermediate methods"(lazy methods)
        (intermediate methods с англ. промежуточные методы, lazy с англ. ленивый). После обработки intermediate методами
        потока, элементы потока в конечном итоге поступают на вход к "terminal method" (с англ. терминальный), т.е. к
        конечному методу. Terminal метод так же называется "eager" (с англ. действующий сразу, нетерпеливый).
        Intermediate методы обрабатывают поступающие элементы потока и возвращают поток (к промежуточным методам
        приходит stream и после обработки этими методами получается тоже stream), например:

            listOfStudents.stream()
                        .map(element ->
                        {
                            element.setName(element.getName().toUpperCase());
                            return element;
                        })
                        .filter(element -> element.getSex() == 'f')
                        .sorted((student1, student2) -> student1.getAge() - student2.getAge())
                        .forEach(System.out::println);

        В самом начале этого примера, после вызова на коллекции listOfStudents метода stream из списка студентов
        получился stream и после обработки методом map на выходе получается тоже stream. Далее, при помощи метода
        filter, происходит фильтрация и снова на выходе получается stream (поток), далее поток был отсортирован и на
        выходе опять же получился stream. Поэтому intermediate методы (промежуточные операторы) обрабатывают stream
        (поток) и возвращают stream (поток). Промежуточных операторов в цепочке (метод chaining) может быть больше
        одного, НО они не будут исполнены, пока не будет вызвана terminal операция (terminal метод).
        Intermediate methods (промежуточные методы) не работают до тех пор, пока не будет вызван terminal methods
        (терминальный метод), т.е. конечный метод.
        В примере  выше конечным методом является метод forEach. Если бы его не было, то не один их предшествующих
        методов (sorted, filter, map) не сработал бы.

        Почему intermediate методы называют "lazy"?
        Lazy-операциями называются операции, которые не сработают, пока что-то не произойдет. А в последнем примере
        intermediate методы не сработают, пока не будет вызван terminal метод.

        Terminal методы обрабатывают элементы stream-а и завершают работу stream-a. По этой причине terminal оператор
        стоит в конце всей цепочки и уже после него intermediate метод не сработает, да и не получится вызвать
        intermediate методы. Не получиться вызвать потому, что как любой terminal метод не возвращает stream, в отличии
        от, к примеру, как в данном случае, методов map, filter, sorted. К примеру, terminal метод forEach ничего
        не возвращает, а точнее возвращает void. Тут напрашивается вопрос: так как же после terminal метода forEach
        возможно вызвать, к примеру, метод map, если нет потока? Ответ: никак.
        Terminal операторы это те, которые возвращают либо void, либо какой-то результат отличный от stream, поэтому
        после них метод chaining заканчивается. Eager операции (eager методы) срабатывают стразу после вызова.
        В этом примере:

            int result = Arrays.stream(array).filter(element -> element % 2 == 1) // 3, 1, 5, 9, 21, 81, 7 (возвращает stream)
                .map(element ->
                {
                    if (element % 3 == 0) {
                        element /= 3;
                    }
                    return element;
                })                                                            // 1, 1, 5, 3, 7, 27, 7 (возвращает stream)
                .reduce((accumulator, element) -> accumulator + element)      // 51 (возвращает объект типа Optional)
                .getAsInt();

        intermediate методы map и filter сработали только потому, что есть метод reduce. Метод reduce после себя
        не возвращает поток, а возвращает какое-то одно значение и он является terminal методом. После метода reduce
        никакой intermediate метод вызвать не получится.
        */

        /*
        В итоге:
        До дет пор, пока не будет написан terminal метод не один intermediate метод не будет обработан.
        Ниже доказывающий это пример.
        */

        Stream<Integer> myStream1 = Stream.of(1, 2, 3, 4, 5, 1, 2, 3);
        // Тут уже метод stream вызывать не нужно, т.к. работа идет с самим потоком, а не с коллекцией;
        myStream1.filter(element ->
        {
            System.out.println("Method \"filter\" is working!");
            return element % 2 == 0;
        });
        /*
        Необходимо отфильтровать поток myStream1. Результат не выводится на экран после работы метода filter, а это и
        не нужно, в данном примере, т.к. необходимо понять, сработал ли метод filter без вызова terminal метода, а
        подтверждением этого будет выводиться для каждого элемента фраза "Method "filter" is working!", т.е 8 раз должна
        появиться на экране эта фраза. Но они не появятся, т.к. метод filter не сработает и он не сработает до тех пор,
        пока не будет использован терминальный метод.
        */

        /*
        А вот если вызвать, к примеру, терминальный метод collect, то все сработает. Без терминального оператора метод
        filter не сработает, поэтому он и ему подобные методы называются lazy. А метод collect - eager, он сразу
        срабатывает, как только его вызывают.
        */
        Stream<Integer> myStream2 = Stream.of(1, 2, 3, 4, 5, 1, 2, 3);
        myStream2.filter(element ->
        {
            System.out.println("Method \"filter\" is working!");
            return element % 2 == 0;
        }).collect(Collectors.toList());
        // Вывод:
        // Method "filter" is working!
        // Method "filter" is working!
        // Method "filter" is working!
        // Method "filter" is working!
        // Method "filter" is working!
        // Method "filter" is working!
        // Method "filter" is working!
        // Method "filter" is working!

        // ArrayList - лежит в основе возвращаемого через .collect(Collectors.toList());
    }
}
/*
class Student {
    private String name;
    private char sex;
    private int age;
    private int course;
    private double averageGrade;

    public Student(String name, char sex, int age, int course, double averageGrade) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.course = course;
        this.averageGrade= averageGrade;
    }

    @Override
    public String toString() {
        return "Student {" +
                "name = '" + name + '\'' +
                ", sex = '" + sex + '\'' +
                ", age = " + age +
                ", course = " + course +
                ", averageGrade = " + averageGrade +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }
}
*/