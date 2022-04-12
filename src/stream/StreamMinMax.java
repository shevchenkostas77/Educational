package stream;

import java.util.ArrayList;
import java.util.List;

/*
Методы min и max - terminal methods.
Методы min и max возвращают объекты типа Optional.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamMinMax {
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

        Student min = students.stream()
                .min((student1, student2) -> student1.getAge() - student2.getAge()).get();
        /*
        Метод min ожидает, в данном случае, что в его параметре будет описан Comparator, т.к.
        Java не понимает by default как сравнивать объекты типа Student.
        Чтобы присвоить результат работы переменной необходимо воспользоваться
        методом get.
        */
        System.out.println(min);
        // Вывод:
        // Student {name = 'Elena', sex = 'f', age = 19, course = 1, averageGrade = 8.9}

        // Таким же образом работает и метод max.
        Student max = students.stream()
                .max((student1, student2) -> student1.getAge() - student2.getAge()).get();
        /*
        Процедура вся та же самая, необходимо Java объяснить с помощью Comparator-a какой
        объект типа Student считается максимальным.
        */
        System.out.println(max);
        // Вывод:
        // Student {name = 'Petr', sex = 'm', age = 35, course = 4, averageGrade = 7.0}
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