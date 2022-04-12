package stream;

import java.util.ArrayList;
import java.util.List;

/*
Skip (с англ. пропускать) - intermediate method.
Этот метод ограничивает количество элементов в stream так же как метод limit,
НО в отличие от метода limit (оставляет первые n элементов из stream) пропускает первые
n элементы stream.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamSkip {
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

        System.out.println("All students on the \"students\" list are over 20 years old:");
        students.stream()
                .filter(element -> element.getAge() > 20)
                .forEach(System.out::println);
        // Вывод:
        // All students on the "students" list are over 20 years old:
        // Student {name = 'Ivan', sex = 'm', age = 22, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = 'm', age = 28, course = 2, averageGrade = 6.4}
        // Student {name = 'Petr', sex = 'm', age = 35, course = 4, averageGrade = 7.0}
        // Student {name = 'Marya', sex = 'f', age = 23, course = 3, averageGrade = 7.4}

        System.out.println("First two students on the \"students\" list are over 20 years old:");
        students.stream()
                .filter(element -> element.getAge() > 20)
                .limit(2)
                .forEach(System.out::println);
        // Вывод:
        // First two students on the "students" list are over 20 years old:
        // Student {name = 'Ivan', sex = 'm', age = 22, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = 'm', age = 28, course = 2, averageGrade = 6.4}

        System.out.println("Last two students ont the \"students\" list are over 20 years old:");
        students.stream()
                .filter(element -> element.getAge() > 20)
                .skip(2)
                .forEach(System.out::println);
        // Вывод:
        // Last two students ont the "students" list are over 20 years old:
        // Student {name = 'Petr', sex = 'm', age = 35, course = 4, averageGrade = 7.0}
        // Student {name = 'Marya', sex = 'f', age = 23, course = 3, averageGrade = 7.4}

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