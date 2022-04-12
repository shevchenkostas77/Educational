package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/*
mapToInt - intermediate method.
Данный метод возвращает не просто stream после себя, а IntStream, т.е. stream,
который содержит значения int.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamMapToInt {
    public static void main(String[] args) {
        Student st1 = new Student("Ivan", 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Marya", 'f', 23, 3, 7.4);

        List<Student> students = new ArrayList<>();

        students.add(st1);
        students.add(st2);
        students.add(st3);
        students.add(st4);
        students.add(st5);

        int[] arrayOfCourses = students.stream()
                .mapToInt(element -> element.getCourse())
                .toArray();
        System.out.println("Array of courses = " + Arrays.toString(arrayOfCourses));
        // Вывод:
        // Array of courses = [3, 2, 1, 4, 3]

        List<Integer> listOfCourses = students.stream()
                .mapToInt(element -> element.getCourse())
                .boxed()
                .collect(Collectors.toList());
        System.out.println("List of courses = " + listOfCourses);
        // Вывод:
        // List of courses = [3, 2, 1, 4, 3]
        /*
        Метод mapToInt ожидает в параметре ToIntFunction, а возвращает IntStream.
        Если необходимо присвоить результат переменной List<Integer> необходимо
        перед методом collect указать метод boxed.

        Следующий код не сработает:

            List<Integer> listOfCourses = students.stream()
                    .mapToInt(element -> element.getCourse())
                    .collect(Collectors.toList());

        Хотя метод mapToInt возвращает после себя не поток студентов, а поток их курсов,
        т.е. поток int-a, нельзя назначить переменной List<Integer> результат работы
        такого stream-a. Необходимо воспользоваться методом boxed. который конвертирует
        значение int в значение Integer. В даном случае курсы студентов это int,
        а необходимо конвертировать их в Integer. Метод boxed возвращает stream.
        Если необходимо назначить результат работы такого stream-a массиву, то
        метод boxed тут уже не нужен и можно при помощи метода toArray преобразовать stream
        в массив, как это в примере выше.
        */

        /*
        Если необходимо поработать с double, то можно воспользоваться похожим методом
        mapToDouble. К примеру:
        */
        List<Double> listOfAverageGrade = students.stream()
                .mapToDouble(element -> element.getAverageGrade())
                .boxed()
                .collect(Collectors.toList());
        System.out.println("List of average grade = " + listOfAverageGrade);
        // Вывод:
        // List of average grade = [8.3, 6.4, 8.9, 7.0, 7.4]

        /*
        У IntStream есть несколько методов, которые работают с числами:
        1. метод sum;
        2. метод average;
        3. метод min;
        4. метод max;
        */

        int sum = students.stream()
                .mapToInt(element -> element.getCourse())
                .sum();
        System.out.println("Method sum = " + sum);
        // Вывод:
        // Method sum = 13
        /*
        Метод sum суммирует все курсы студентов из списка students. Этот метод
        возвращает int.
        */

        double average = students.stream()
                .mapToDouble(element -> element.getAverageGrade())
                .average()
                .getAsDouble();
        System.out.println("Method average = " + average);
        // Вывод:
        // Method average = 7.6
        /*
        Метод average возвращает OptionalDouble, поэтому с помощью метода getAsDouble можно
        получить результат работы и присвоить его переменной типа double - среднее
        арифметическое значение баллов всех студентов.
        */

        int min = students.stream()
                .mapToInt(element -> element.getCourse())
                .min()
                .getAsInt();
        System.out.println("Method min = " + min);
        // Вывод:
        // Method min = 1
        /*
        Метод min возвращает OptionalInt, поэтому с помощью метода getAsInt
        можно получить значение и присвоить это значение переменной типа int.
        У метода min нет метода get, зато есть метод getAsInt потому, что это
        не просто Optional, а OptionalInt, который оборачивает не любое какое-то
        значение, а конкретно значение Integer.
        */

        int max = students.stream()
                .mapToInt(element -> element.getCourse())
                .max()
                .getAsInt();
        System.out.println("Method max = " + max);
        // Вывод:
        // Method max = 4
        /*
        Метод max возвращает OptionalInt, поэтому с помощью метода getAsInt
        можно получить значение и присвоить это значение переменной типа int.
        У метода max нет метода get, зато есть метод getAsInt потому, что это
        не просто Optional, а OptionalInt, который оборачивает не любое какое-то
        значение, а конкретно значение Integer.
        */
    }
}

//class Student {
//    private String name;
//    private char sex;
//    private int age;
//    private int course;
//    private double averageGrade;
//
//    public Student(String name, char sex, int age, int course, double averageGrade) {
//        this.name = name;
//        this.sex = sex;
//        this.age = age;
//        this.course = course;
//        this.averageGrade = averageGrade;
//    }
//
//    @Override
//    public String toString() {
//        return "Student {" +
//                "name = '" + name + '\'' +
//                ", sex = '" + sex + '\'' +
//                ", age = " + age +
//                ", course = " + course +
//                ", averageGrade = " + averageGrade +
//                '}';
//
//    }
//
//    public String getName() {return name;}
//    public void setName(String name) {this.name = name;}
//
//    public char getSex() {return sex;}
//    public void setSex(char sex) {this.sex = sex;}
//
//    public int getAge() {return age;}
//    public void setAge(int age) {this.age = age;}
//
//    public int getCourse() {return course;}
//    public void setCourse(int course) {this.course = course;}
//
//    public double getAverageGrade() {return averageGrade;}
//    public void setAverageGrade(double averageGrade) {this.averageGrade = averageGrade;}
//}