package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Метод filter.
Из названия понятно, что этот метод фильтрует данные из определенного набора, например, коллекции List.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamFilter {
    public static void main(String[] args) {
        Student st1 = new Student("Ivan" , 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Mariya", 'm', 23, 3, 7.4);

        List<Student> students = new ArrayList<>();

        students.add(st1);
        students.add(st2);
        students.add(st3);
        students.add(st4);
        students.add(st5);

        System.out.println("Old list of students = " + students);
        // Вывод:
        // Old list of students = [Student {name = 'Ivan', sex = m, age = 22, course = 3, average grade = 0.0},
        //     Student {name = 'Nikolay', sex = m, age = 28, course = 2, average grade = 0.0},
        //     Student {name = 'Elena', sex = f, age = 19, course = 1, average grade = 0.0},
        //     Student {name = 'Petr', sex = m, age = 35, course = 4, average grade = 0.0},
        //     Student {name = 'Mariya', sex = m, age = 23, course = 3, average grade = 0.0}]

        /*
        Как работает метод filter.
        К примеру, необходимо отфильтровать студентов и оставить только тех, возраст которых, больше 22 лет и
        средняя оценка меньше 7.2.
        Из коллекции students при помощи метода stream() получается поток, далее вызывается метод filter, который в
        параметре принимает Predicate, т.е. вместо параметра необходимо написать Predicate (как будет выглядеть
        метод, а метод должен возвращать true или false).
        */

        students = students.stream().filter(el
                        -> el.getAge() > 22 && el.getAverageGrade() < 7.2)
                .collect(Collectors.toList());

        /*
        Метод filter тоже возвращает stream, т.е. получился stream из 5 студентов, а метод filter вернет стрим с теми
        студентами, которые удовлетворят условию (пройдут этот фильтр). Если в итоге необходимо stream преобразовать
        в коллекцию, к примеру, в List, то необходимо использовать метод collect с параметром Collectors.toList()
        (import java.util.stream.Collectors;).
        */

        System.out.println("List of students after filtering = " + students);
        // List of students after filtering = [Student {name = 'Nikolay', sex = m, age = 28, course = 2, average grade = 0.0},
        //     Student {name = 'Petr', sex = m, age = 35, course = 4, average grade = 0.0},
        //     Student {name = 'Mariya', sex = m, age = 23, course = 3, average grade = 0.0}]

    }
}

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
        this.averageGrade = averageGrade;
    }

    public String toString() {
        return "Student {" +
                "name = '" + getName() + '\'' +
                ", sex = " + getSex() +
                ", age = " + getAge() +
                ", course = " + getCourse() +
                ", average grade = " + getAverageGrade() +
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