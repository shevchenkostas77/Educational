package stream;

import java.util.ArrayList;
import java.util.List;

/*
FindFirst (англ. найти первым)  - terminal method.
Возвращает первый элемент stream (не первый по какому-то соответствию, а просто
первый элемент stream).
Метод findFirs возвращает объект типа Optional.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamFindFirst {
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

        Student result = students.stream()
                .map(element -> {element.setName(element.getName().toUpperCase()); return element;})
                .filter(element -> element.getSex() == 'f')
                .sorted((student1, student2) -> student1.getAge() - student2.getAge())
                .findFirst().get();
                /*
                т.к. метод findFirs возвращает объект типа Optional необходимо воспользоваться
                методом get, если, к примеру, необходимо результат присвоить переменной, как в
                данном случае. Метод isPresent тут не используется, т.к. точно один элемент
                присутствует в результате.
                */

        System.out.println(result);
        // Вывод:
        // Student {name = 'ELENA', sex = 'f', age = 19, course = 1, averageGrade = 8.9}
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