package lambda;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/*
Function.
Интерфейс Function находится в пакете java.util.function.
Функциональный интерфейс Function используют тогда, когда на вход поступает
один тип данных, а на выходе какой-то другой (указываются необходимые для работы типы).
Выглядит функциональный интерфейс Function следующим образом:

    @FunctionalInterface // помечен аннотацией, что он функциональный;
    public interface Function<T, R> {
        R apply(T t); // в нем один абстрактный метод (другие методы тоже есть,
                      // но они не абстрактны);
    }

    1. Параметре "Т" - это входной параметр (в данном случае, вместо "Т" объект
       типа Student);
    2. Параметре "R" - return type (в данном случае, вместо "R" объект типа Double);
*/

public class MyFunction {
    public static void main(String[] args) {

        Student st1 = new Student("Ivan", 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7.0);
        Student st5 = new Student("Mariya", 'f', 23, 3, 7.4);

        ArrayList<Student> students = new ArrayList<>();

        students.add(st1);
        students.add(st2);
        students.add(st3);
        students.add(st4);
        students.add(st5);
    /*
    Необходимо найти среднее арифметическое для всех студентов по разным показателям. Например,
    среднее арифметическое значение курсов студентов. Можно найти среднее арифметическое значение
    возраста студентов, средних баллов студентов и тд. Т.е. необходимо работать с любым показателем
    объекта, в данном случае, объекта типа Student.
    На input необходимо передать объект типа Student, а в output, к примеру, объект типа Double.
     */

        System.out.println("Average course = " + averageSomething(students, (Student s) -> {
            return (double) s.course;
        }));
        /*
        Вызывается метод averageSomething, передается в первый параметр список студентов,
        во втором параметре с помощью lambda выражения показывается как работает метод
        apply. Метод apply принимает в параметры объект типа Student и возвращает double.
        */
        // Вывод:
        // Average course = 2.6

        System.out.println("Average grade of all students = " + averageSomething(students, (Student s) -> {
            return s.averageGrade;
        }));
        // Вывод:
        // Average grade of all students = 7.6

        System.out.println("Average age = " + averageSomething(students, (Student s) -> {
            return (double) s.age;
        }));
        // Вывод:
        //  Average age = 25.4
    }


    public static double averageSomething(List<Student> list, Function<Student, Double> function) {
        double result = 0;
        for (Student student : list) {
            result += function.apply(student);
            /*
            У функционального интерфейса Function есть единственны абстрактный метод apply,
            который принимает в параметры свои на input объект типа Student и возвращает
            объект типа Double. Как получиться результат в виде объекта Double тут не
            прописывается, а будет указано в lambda выражении при вызове метода averageSomething
            в его параметрах.
            Тут прописано, что к переменной result прибавляется Double, который вернет функциональный
            интерфейс. Переменная function вызывает метод apply, который будет перезаписан. В параметр
            метода apply передается объект типа Student.
             */
        }
        result /= list.size();
        return result;
    }
}