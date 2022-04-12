package stream;

import java.util.ArrayList;
import java.util.List;

/*
Limit - intermediate method.
Возвращает stream. Этот метод ограничивает количество элементов в stream.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamLimit {
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

        students.stream() // тут stream содержит 5 элементов;
                .filter(element -> element.getAge() > 20) // тут stream уже содержит 4 элемента;
                .limit(2) // после метода limit stream будет содержать всего 2-х студентов;
                .forEach(System.out::println); // При помощи метода reference выводится результат;
        /*
        System.out::println - называется метод референс, т.е. передается ссылка на метод println().
        Этот метод находится в System.out и Java понимает, что параметром для метода println будет
        каждый элемент stream-a. Java ищет метод println в System.out и в параметр подставляет
        каждый элемент и выводит на экран.
        */
        // Вывод:
        // Student {name = 'Ivan', sex = 'm', age = 22, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = 'm', age = 28, course = 2, averageGrade = 6.4}
        /*
        Метод limit сделал свою работу и в итоге, вместо 4-х студентов, на экран было выведено
        2 сдутента.
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