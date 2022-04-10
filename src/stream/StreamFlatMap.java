package stream;

import java.util.ArrayList;
import java.util.List;

/*
Метод flatMap (intermediate method).
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamFlatMap {
    public static void main(String[] args) {
        Student st1 = new Student("Ivan" , 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);

        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Mariya", 'm', 23, 3, 7.4);

        Faculty facultyEconomics = new Faculty("Economics");
        Faculty facultyAppliedMathematics = new Faculty("Applied mathematics");

        facultyEconomics.addStudentInFaculty(st1);
        facultyEconomics.addStudentInFaculty(st2);
        facultyEconomics.addStudentInFaculty(st3);

        facultyAppliedMathematics.addStudentInFaculty(st4);
        facultyAppliedMathematics.addStudentInFaculty(st5);

        List<Faculty> listOfFaculty = new ArrayList<>();
        listOfFaculty.add(facultyEconomics);
        listOfFaculty.add(facultyAppliedMathematics);
        /*
        Есть лист факультетов listOfFaculty, который содержит два факультета: Economics и Applied Mathematics, а сами
        факультеты содержат List-ы своих студентов. Метод flatMat используется тогда, когда необходимо поработать на
        прямую не с самими элементами коллекции, а с элементами элементов коллекции. Например, в данном случае необходимо
        вывести имена всех студентов из всех факультетов.

        Student st1 = new Student("Ivan" , 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);

        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Mariya", 'm', 23, 3, 7.4);
        */
        listOfFaculty.stream()
                .flatMap(faculty -> faculty.getListOfStudents().stream())
                .forEach(element -> System.out.println(element.getName()));
//                Вывод:
//                Ivan
//                Nikolay
//                Elena
//                Petr
//                Mariya
                /*
                Из коллекции listOfFaculty создается stream:

                    listOfFaculty.stream()

                и далее вызывается метод flatMap. В параметрах указывается элемент faculty и при работе метода flatMap
                вместо faculty будут по очереди подставляться элементы из stream, который получился из listOfFaculty.
                Сначала это будет facultyEconomics, на этом элементе будет вызван метод getListOfStudents(),
                который возвращает List студентов и создается еще один stream из этого List-a студентов. После
                завершения работы terminal метода forEach со списком студентов из facultyEconomics, т.е. вывод всех имен
                студентов на экран, все то же самое будет со следующим элементом stream, который получился из
                listOfFaculty - facultyAppliedMathematics.

                Выглядит это примерно так:

                1. listOfFaculty.stream()
                            .flatMap(facultyEconomics -> facultyEconomics.getListOfStudents().stream())
                            .forEach(
                            st1 -> System.out.println(st1.getName())
                            st2 -> System.out.println(st2.getName())
                            st3 -> System.out.println(st3.getName()
                            );
                   Вывод:
                   Ivan
                   Nikolay
                   Elena

                2. listOfFaculty.stream()
                            .flatMap(facultyAppliedMathematics -> facultyAppliedMathematics.getListOfStudents().stream())
                            .forEach(
                            st4 -> System.out.println(st1.getName())
                            st5 -> System.out.println(st2.getName())
                            );
                   Вывод:
                   Ivan
                   Nikolay
                   Elena
                   Petr
                   Mariya
                */
    }
}

class Faculty {
    private String facultyName;
    private List<Student> listOfStudents;

    public Faculty(String facultyName) {
        this.facultyName = facultyName;
        listOfStudents = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.getFacultyName() + " faculty: " +
                listOfStudents;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void addStudentInFaculty(Student st) {
        listOfStudents.add(st);
    }
}