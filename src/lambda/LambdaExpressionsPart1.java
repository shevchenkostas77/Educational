package lambda;

import java.util.ArrayList;

/*
Lambda expressions.
*/

public class LambdaExpressionsPart1 {
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
        /*
        И теперь необходимо отфильтровать студентов по разным показателям. К примеру,
        вывести на экран студентов, которые старше 20 лет или студентов, у которых
        средний бал ниже 8-ми и тд.
        */

        /*
        Вариан №1.
        Создать три метода фильтрации в классе StudentInfo.
        Такой вариант подходит, если часто использовать эти методы без каких либо
        изменений. Но если понадобится какая-то новая фильтрация, то новый метод
        необходимо будет записать в класс StudentInfo, т.е. создать в классе новый
        метод. Это не всегда удобно, особенно, если нет доступа к классу StudentInfo,
        а новая фильтрация необходима.
        Рассмотрим вариант №2, который представлен ниже.
        */
        StudentInfo info = new StudentInfo();

        info.printStudentsOverGrade(students, 8);
        // Вывод:
        // Students with an average grade above 8.0:
        // Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3}
        // Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9}
        // Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1}

        info.printStudentsUnderAge(students, 30);
        // Вывод:
        // Students under the age of 30:
        // Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = m, course = 2, averageGrade = 6.4}
        // Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9}
        // Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1}

        info.printStudentsMixCondition(students, 20, 9.5, 'f');
        // Вывод:
        // Students older than 20 with an average grade below 9.5 and gender - f:
        // Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1}

        System.out.println("--------------------------------------------------");

        /*
        Вариант №2.
        Можно создать интерфейс, к примеру, StudentChecks, который будет содержать
        в себе всего лишь один метод. Return type метода check будет boolean и
        принимает в параметрах студента:
        boolean check(Student s);
        Тогда в классе StudentInfo (с его помощью будет происходить фильтрация)
        достаточно написать один метод, название его будет checksStudents.
        */
        info.checksStudents(students, new checkOverGrade());
        // Вывод:
        // Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3}
        // Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9}
        // Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1}

        System.out.println("--------------------------------------------------");

        /*
        Вариант №3.
        Отфильтруем студентов по примеру метода printStudentsUnderAge из
        варианта №1. В данном примере используется анонимный класс.
        Если необходимо воспользоваться фильтрацией всего один раз, то можно
        не создавая класс, который имплементирует какой-то интерфейс, создать
        "на ходу" объект анонимного класса.
        */
        info.checksStudents(students, new StudentChecks() {
            @Override
            public boolean check(Student s) {
                return s.age < 30;
            }
        });
        // Вывод:
        // Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = m, course = 2, averageGrade = 6.4}
        // Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9}
        // Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1}

        System.out.println("--------------------------------------------------");

        /*
        Вариант №4.
        Lambda expressions.
        Отфильтруем студентов по примеру метода printStudentsUnderAge из
        варианта №1 и варианта №3.
        Одной строкой:
        (Student s) -> {return s.age < 30;}
        заменяется множество написанных строк из других примеров. Тем и хороши
        lambda выражения.
        */
        info.checksStudents(students, (Student s) -> {return s.age < 30;});
        /*
        До примера с lambda выражениями был лучший пример с анонимным классом.
        В lambda выражении, сравнивая с анонимным классом, не нужно прописывать
        создание объекта анонимного класса, не нужно писать аннотацию, не нужно
        писать название метода, тип возвращаемого его значения, модификатор
        доступа метода. Вместо всего этого лишь указывается, как должен выглядеть
        метод, в данном случае, метод check:
        1. Параметр у этого метода Student s;
        2. Ставится знак "->" (такой синтаксис у lambda expressions);
        3. В фигурных скобках указывается тело метода - return s.age < 30;
        Это lambda выражение можно сократить, это один из вариантов написания.

        Вместо того чтобы создавать какой-то класс, который имплементирует
        интерфейс StudentChecks, потом override его метод check, после чего
        создавать объект этого класса и вставлять его в параметр метода checksStudents,
        lambda выражение заменяет все эти шаги, ненужные шаги.
        Конечно же вот такое
        (Student s) -> {return s.age < 30;}
        lambda выражение
        должно соответствовать методу boolean check(Student s) интерфейса StudentChecks.
        Соответствие должно быть в том, что в lambda выражение передается объект
        типа Student, естественно в параметре lambda выражения можно написать не
        букву 's', а к примеру, 'p': (Student p) -> {return p.age < 30;}.
        Далее Java проверяет на соответствие return type.

        Выражение с использованием анонимного класса

        info.checksStudents(students, new StudentChecks() {
            @Override
            public boolean check(Student s) {
                return s.age < 30;
            }
        });

        и выражение с использованием lambda выражения

        info.checksStudents(students, (Student s) -> {return s.age < 30;});

        вернут одинаковый результат.
        */
        // Вывод:
        // Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = m, course = 2, averageGrade = 6.4}
        // Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9}
        // Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1}

        /*
        Таким образом, начиная в Java с версии 8 дается возможность при программировании
        использовать функциональный стиль программирования, тогда как Java является
        обьктно-ориентированным языком программирования. Можно воспринимать
        lambda выражения как анонимные методы, т.е. у этого метода есть параметр,
        есть тело, но у этого метода нет имени.
        В строке

        info.checksStudents(students, (Student s) -> {return s.age < 30;});

        в параметр листа метода записывается метод, т.е. в качестве параметра
        используется метод. Когда используется метод checksStudents, Java
        ожидает, что во втором параметре будет объект типа StudentChecks и когда
        Java видит вот такое lambda выражение

        (Student s) -> {return s.age < 30;}

        она понимает, что происходит override единственно возможного метода
        интерфейса StudentChecks, т.е. метод check.

        Интерфейс

        interface StudentChecks {
            boolean check(Student s);
        }

        называется функциональным.

        Функциональный интерфейс - это тот интерфейс, который содержит всего один
        абстрактный метод. Статических или дефолтных может быть сколько угодно, но
        только один абстрактный метод. Именно благодаря тому, что в функциональном
        интерфейсе находится один абстрактный метод Java понимает, что именно он
        в lambda выражении и прописан.
        Если прописать еще какой-то абстрактный метод даже с иным названием,
        иным возвращаемым типом, инным листом параметров, то Java все равно
        не поймет с чем сравнивать lambds выражение
        (Student s) -> {return s.age < 30;}.
        */
    }
}

interface StudentChecks {
    boolean check(Student s);
}

class StudentInfo {

    // Относится к варинату №1
    void printStudentsOverGrade(ArrayList<Student> al, double grade) {
        System.out.println("Students with an average grade above " + grade + ':');
        for(Student s : al) {
            if (s.averageGrade > grade) {
                System.out.println(s);
            }
        }
    }

    void printStudentsUnderAge(ArrayList<Student> al, int age) {
        System.out.println("Students under the age of " + age + ':');
        for(Student s : al) {
            if (s.age < age) {
                System.out.println(s);
            }
        }

    }

    void printStudentsMixCondition(ArrayList<Student> al, int age, double grade, char sex) {
        System.out.println("Students older than " + age + " with an average grade below " +
                grade + " and gender - " + sex + ':');
        for(Student s : al) {
            if (s.age > age && s.averageGrade < grade && s.sex == sex) {
                System.out.println(s);
            }
        }
    }

    // Относится к вариант №2
    /*
    Метод checksStudents принимает в параметрах ArrayList студентов и обьект
    класса, который будет имплементировать интерфейс StudentChecks, а у интерфейса
    StudentChecks есть метод check, который возвращает boolean. А что делает
    метод check, об этом знает только класс, который имплементирует этот интерфейс.
    Ниже создается класс checkOverGrade, который будет имплементировать интерфейс
    StudentChecks и будет выполнять фильтрацию, подобную в примере №1 (printStudentsOverGrade).

    Но у этого подхода есть "минусы". В классе checkOverGrade, который имплементирует
    интерфейс StudentChecks, в переопределенном методе check необходимо вручную
    строго указывать, в данном случае, больше какой оценке необходимо проверять,
    если нужно будет добавить другую проверку, к примеру, добавить другую
    проверочную оценку, то необходимо создать еще один класс, подобный
    checkOverGrade. Но "плюс" этого подхода, что не нужно менять внутренности метода
    checksStudents.

    Заменить и упростить задачу поможет анонимный класс (Вариант №3).
    */
    void checksStudents(ArrayList<Student> al, StudentChecks sc) {
        for(Student s : al) {
            if(sc.check(s)) { // вызывается метод check объекта sc типа StudentChecks;
                System.out.println(s);
            }
        }
    }
}

class checkOverGrade implements StudentChecks {
    @Override
    public boolean check(Student s) {
        return s.averageGrade > 8;  // строго прописываем каких студентов будем искать;
    }
}

class Student {
    String name;
    char sex;
    int age;
    int course;
    double averageGrade;

    public Student(String name, char sex, int age, int course, double averageGrade) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.course = course;
        this.averageGrade = averageGrade;
    }

    public String toString() {
        return "Student {" +
                "name = '" + name + '\'' +
                ", sex = " + sex +
                ", course = " + course +
                ", averageGrade = " + averageGrade +
                '}';
    }
}

