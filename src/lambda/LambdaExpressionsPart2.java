package lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LambdaExpressionsPart2 {
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

        StudentInfo info = new StudentInfo();

        info.checksStudents(students, (Student s) -> {return s.age < 30;});
        /*
        Самый короткий вариант написания lambda выражения (в данном случае):

        Полный вариант написания lambda выражения:
        info.checksStudents(students, (Student s) -> {return s.age < 30;});

        Короткий вариант написания lambda выражения:
        info.checksStudents(students, s -> s.age < 30);

        Без типа, без скобок указывается название переменной, в данном случае, "s",
        без фигурных скобок, без слова "return" и без символа ";" указывается тело
        lambda выражения.
        */
        System.out.println("--------------------------------------------------");
        info.checksStudents(students, s -> s.age < 30);
        /*
        Метод checksStudents во втором параметре ждет объект типа StudentChecks, а
        StudentChecks - это функциональный интерфейс, который имеет один абстрактный метод
        и Java понимает, что переменная "s" может быть только типом Student потому,
        что в параметре метода check функционального интерфейса StudentChecks тип Student.
        Java так же понимает, что абстрактный метод check функционального интерфейса
        StudentChecks после его переопределения должен возвращать boolean значение,
        т.е. слово "return" в теле lambda выражения можно уже не писать.

        В lambda выражении оператор стрелка "->" разделяет параметры метода и тело метода.

        В lambda выражении справа от оператора стрелка находится тело метода, которое было
        бы у метода соответствующего класса, имплементировавшего функциональный интерфейс с
        единственным методом.

        Можно использовать смешанный вариант написания lambda выражения:
        слева от оператора стрелка писать короткий вариант, справа - полный. Или наоборот.
        Если использовать полный вариант написания для части lambda выражения справа от стрелки,
        то необходимо использовать слово return и знак ";".

        info.checksStudents(students, s -> {return s.age < 30;});

        Левая часть lambda выражения может быть написана в краткой форме, если метод функционального
        интерфейса принимает только 1 параметр.*
        Даже если метод функционального интерфейса принимает 1 параметр, но в lambda выражении
        пишется параметр используя его тип данных, тогда необходимо писать левую часть
        lambda выражения в скобках.**

        * если бы функциональный интерфейс был, к примеру таким:
        interface StudentChecks {
            boolean check(Student s, int i);
        }
        то написание lambda выражение могло бы быть следующим:
        info.checksStudents(students, (s, i) -> {return s.age < 30 && i == 5;});
        info.checksStudents(students, (Student s, int i) -> s.age < 30 && i == 5);

        ** info.checksStudents(students, (Student s) -> s.age < 30);

        Если в правой части lambda выражения пишется более одного statement-a,
        то необходимо использовать его полный вариант написания.

        info.checksStudents(students, s -> {System.out.println("Hello"); return s.age < 30;});

        Если метод функционально интерфейса не принимает параметры, то в левой части lambda выражения
        указываются пустые скобки "()".

        info.checksStudents(students, () -> true);
        или
        info.checksStudents(students, () -> {return true;});
        */

        // Lambda выражение, в данном случае, можно вынести за пределы метода checksStudents:
        StudentChecks sc = (Student s) -> {return s.age < 30;};
        info.checksStudents(students, sc);
        /*
        Переменная "sc" типа StudentChecks является lambda выражением.
        Разница с написанием lambda выражения в параметре метода checksStudents в том, что в
        параметре метода checksStudents lambda выражение используется всего один раз, т.е. только в
        этом методе (в данном случае), а lambda выражение, которое назначено переменной "sc" в дальнейшем
        можно использовать сколько угодно раз.

        Переменные, которые определены в секции объявления переменных (левая часть от оператора стрелка)
        в lambda выражении их область видимости это все lambda выражение. Дальше они перестают быть видными.

        info.checksStudents(students, s -> {return s.age < 30;});
        System.out.println(s); // компилятор не понимает, что это за переменная "s";
        Переменная "s" видна только внутри тела lambda выражения - {return s.age < 30;}

        Если переменная, к примеру, c названием "variable" объявления была до lambda выражения и в
        lambda выражении попытаться использовать переменную с таким же название - произойдет
        ошибка компиляции:

        int variable = 10;
        info.checksStudents(students, (Student variable) -> {return variable.age < 30;});
        Variable 'variable' is already defined in the scope

        Переменная Student "variable" в lambda выражении не перекрывает видимость int variable.
        Но если будет необходимость использовать переменную int variable в lambda выражении, то
        такая возможность есть (ЕСЛИ переменная int variable не была изменена после ее инициализации
        или же переменная  объявлена как final):
        */
        System.out.println("--------------------------------------------------");
        int variable;
        variable = 20;
        // variable = 30; // Ошибка компиляции: Variable used in lambda expression should be final or effectively final
        info.checksStudents(students, (Student s) -> {return s.age > variable;});
        // variable = 30; // Ошибка компиляции: Variable used in lambda expression should be final or effectively final

        // Вывод:
        // Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3}
        // Student {name = 'Nikolay', sex = m, course = 2, averageGrade = 6.4}
        // Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9}
        // Student {name = 'Petr', sex = m, course = 4, averageGrade = 7.0}
        // Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1}

        /*
        Если объявить в теле lambda выражения переменную, то она будет видна только в теле lambda выражения,
        и за пределами она будет недоступна.

        Следующая запись будет НЕ верна:
        method( (int x, int y) -> {int x = 5 return 10;} );
        В качестве параметров передается "х" и "у" и внутри тела lambda выражения создается переменная
        с таким же названием, как и в параметре метода - "х". Java не поймет, какая переменная за что отвечает.

        Следующая запись будет верна:
        method( (int x, int y) -> {x = 5 return 10;} );
        В качестве параметров передается "х" и "у" и внутри тела lambda выражения переменной "х"
        задается другое значение, в данном случае, значение равное 5.

        Следующая запись будет верна:
        method( (int x, int y) -> {int x2 = 5 return 10;} );
        В качестве параметров передается "х" и "у" и внутри тела lambda выражения создается
        новая переменная "х2" и присваивается ей значение равное 5.

        Lambda выражения работают с интерфейсом, в котором есть только 1 абстрактный метод.
        Такие интерфейсы называются функциональными интерфейсами, т.е. интерфейсами, пригодными
        для функционального программирования.
        */

        /*
        Еще один пример с сортировкой студентов.
        Допустим необходимо отсортировать всех студентов по определенному правилу, которые находятся
        в ArrayList students. В классе Student (класс находится в файле LambdaExpressionsPart1) не
        имплементирован интерфейс Comparable. Поэтому для сортировки будет использован Comparator.
        Когда создавался класс, который имплементирует интерфейс Comparator, то переопределяется всего
        лишь один метод compare, т.е. это единственный абстрактный метод интерфейса Comparator, т.е. он
        является функциональным интерфейсом. Поэтому, в качестве второго параметра в методе sort, класса
        Collections использовать lambda выражение, а не создавать класс, который имплементирует интерфейс
        Comparator и не создавать объект Comparator-а.
        */
        // С использованием анонимного класса:
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.course - s2.course; // сортировка по курсу
                // Если курс у студента s1 меньше, чем у студента s2, то студент s1 будет считаться
                // меньше студента s2.
            }
        });
        System.out.println("--------------------------------------------------");
        System.out.println(students);
        // Вывод:
        // [Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9},
        //        Student {name = 'Nikolay', sex = m, course = 2, averageGrade = 6.4},
        //        Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3},
        //        Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1},
        //        Student {name = 'Petr', sex = m, course = 4, averageGrade = 7.0}]

        // С использованием lambda выражения:
        Collections.sort(students, (Student student1, Student student2) -> {
            return student1.course - student2.course;
        });
        System.out.println("--------------------------------------------------");
        System.out.println(students);
        // Вывод:
        // [Student {name = 'Elena', sex = f, course = 1, averageGrade = 8.9},
        //        Student {name = 'Nikolay', sex = m, course = 2, averageGrade = 6.4},
        //        Student {name = 'Ivan', sex = m, course = 3, averageGrade = 8.3},
        //        Student {name = 'Mariya', sex = f, course = 3, averageGrade = 9.1},
        //        Student {name = 'Petr', sex = m, course = 4, averageGrade = 7.0}]

        // Output одинаков, что у lambda выражения, что у примера с использованием анонимного класса.
    }
}