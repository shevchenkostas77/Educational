package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/*
Predicate.
Интерфейс Predicate находится в пакете java.util.function.
В этом пакете находятся готовые функциональные интерфейсы для разных случаев.
Благодаря этому пакету, нет необходимости, в большинстве случаев, создавать
свои интерфейсы. Используя функциональные интерфейсы из пакета java.util.function
код будет более читабельным.

Интерфейс Predicate<T> - является параметризованным.

Выглядит функциональный интерфейс Predicate следующим образом:

@FunctionalInterface // аннотацией помечено, что он функциональный интерфейс;
    public interface Predicate<T> {
        boolean test(T t); // в нем один абстрактный метод (другие методы тоже есть,
                           // но они не абстрактны;
        }

    Абстрактный метод называется test. Он принимает в параметр тип "Т", т.е.
    какой-то класс и результатом работы метода должен быть возврат значения
    типа boolean. Чтобы Predicate работал с нужным типом необходимо в операторе
    diamond указать необходимый класс (вместо "Т" подставить нужный класс), т.е.
    параметризовать под определенный тип.
*/

public class MyPredicate {
    public static void main(String[] args) {

        Student st1 = new Student("Ivan", 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7.0);
        Student st5 = new Student("Mariya", 'f', 23, 3, 9.1);

        ArrayList<Student> students = new ArrayList<>();

        students.add(st1);
        students.add(st2);
        students.add(st3);
        students.add(st4);
        students.add(st5);

        StudentsInfo info = new StudentsInfo();
        /*
        void testStudents(ArrayList<Student> al, Predicate<Student> pr) {
            for (Student s : al) {
                if (pr.test(s)) {
                    System.out.println(s);
                }
            }
        }
        Метод testStudents вторым параметром ожидает увидеть Predicate.
        При вызове метода testStudents во втором параметре, как бы происходит override
        единственного абстрактного метода test функционального интерфейса Predicate.
        Этот метод test принимает в параметр объект класса Student (в строке заголовка метода
        void testStudents(ArrayList<Student> al, Predicate<Student> pr) вторым
        параметром указан Predicate, который будет работать с объектами типа Student)
        и возвращает значение типа boolean.
        */
        info.testStudents(students, (Student s) -> {return s.age<30;});
        // Вывод:
        // Student {name = 'Ivan', sex = m, age = 22, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = m, age = 28, course = 2, averageGrade = 6.4}
        // Student {name = 'Elena', sex = f, age = 19, course = 1, averageGrade = 8.9}
        // Student {name = 'Mariya', sex = f, age = 23, course = 3, averageGrade = 9.1}

        System.out.println("-------------------------------------------------");

        /*
        Еще интерфейс Predicate<T> используется методом removeIf.
        Метод removeIf удаляет из коллекции элемент по какому-то критерию.
        Метод removeIf в параметре содержит Predicate, поэтому указывается
        lambda выражение, которое принимает один параметр и возвращает boolean.
        Т.е. необходимо написать выражение, которое тестирует каждый элемент
        коллекции.
        */
        students.removeIf((Student element) -> {return element.age < 30;});
        //для разового использования лучше этот метод

        // или

        // Predicate<Student> pr = (Student element) -> {return element.age < 30;};
        // students.removeIf(pr);

        System.out.println(students);
        // Вывод:
        // [Student {name = 'Petr', sex = m, age = 35, course = 4, averageGrade = 7.0}]
        /*
        Каждый элемент коллекции students прошел через метод test (абстрактный метод)
        функционального интерфейса Predicate, а переопределение этого метода было
        в параметре метода removeIf - (Student element) -> {return element.age < 30;}
        */

        students.add(st1);
        students.add(st2);
        students.add(st3);
        students.add(st5);

        System.out.println("-------------------------------------------------");

        Predicate<Student> p1 = (Student student) -> {return student.averageGrade > 7.5;};
        Predicate<Student> p2 = (Student student) -> {return student.sex == 'm';};
        /*
        Default метод "and" функционального интерфейса Predicate позволяет объединить две
        проверки в одну.
        */
        info.testStudents(students, p1.and(p2));
        // Вывод:
        // Student {name = 'Ivan', sex = m, age = 22, course = 3, averageGrade = 8.3}

        System.out.println("-------------------------------------------------");

        /*
        Default метод "or" функционального интерфейса Predicate позволяет отфильтровать
        элементы, которые удовлетворяют хоты бы одному из условий фильтрации (проходит
        хотя бы одну проверку или фильтра p1, или фильтра p2)
        */
        info.testStudents(students, p1.or(p2));
        // Вывод:
        // Student {name = 'Petr', sex = m, age = 35, course = 4, averageGrade = 7.0}
        // Student {name = 'Ivan', sex = m, age = 22, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = m, age = 28, course = 2, averageGrade = 6.4}
        // Student {name = 'Elena', sex = f, age = 19, course = 1, averageGrade = 8.9}
        // Student {name = 'Mariya', sex = f, age = 23, course = 3, averageGrade = 9.1}

        System.out.println("-------------------------------------------------");

        /*
        Default метод "negate" (с англ. "отрицать") функционального интерфейса Predicate
        отрицает принципы проверки.
        Выглядит метод "negate" следующим образом:

            default Predicate<T> negate() {
                return (t) -> !test(t);
            }

        Predicate<Student> p1 = (Student student) -> {return student.averageGrade > 7.5;};
        т.е. при помощи данного метода true будет для студентов, у которых средний балл не
        больше 7.5, а меньше 7.5.
        */
        info.testStudents(students, p1.negate());
        // Вывод:
        // Student {name = 'Petr', sex = m, age = 35, course = 4, averageGrade = 7.0}
        // Student {name = 'Nikolay', sex = m, age = 28, course = 2, averageGrade = 6.4}
    }
}

class StudentsInfo {

    /*
    В метод testStudents во втором параметре указывается не созданный вручную
    функциональный интерфейс или класс имплементирующий функциональный интерфейс,
    а функциональный интерфейс Predicate. Тем самым нет лишне написанного кода.
    */
    void testStudents(ArrayList<Student> al, Predicate<Student> pr) {
        for (Student s : al) {
            if (pr.test(s)) {
                System.out.println(s);
            }
        }
    }
}