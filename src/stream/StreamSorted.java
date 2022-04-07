package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



/*
Метод sorted.
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamSorted {
    public static void main(String[] args) {
        int[] array = new int[]{3, 8, 1, 5, 9, 12, 4, 21, 81, 7, 18};
	    /*
	    Задание-пример использования метода sorted.
	    Необходимо отсортировать массив.
	    Из класса Arrays вызывается метод stream и в параметр этого метода передается массив, который
	    необходимо отсортировать. Получается stream и вызывается метод sorted. В параметр метода sorted
	    можно ничего не передавать, т.к. Java понимает как сортировать числа. Методом toArray переводится
	    поток в массив.
	    */
        array = Arrays.stream(array).sorted().toArray();
        // Меняется ссылка у переменной array типа int[] на отсортированный массив.
        System.out.println(Arrays.toString(array));
        //Вывод:
        //[1, 3, 4, 5, 7, 8, 9, 12, 18, 21, 81]

	   /*
	   Задание-пример использования метода sorted.
	   Необходимо отсортировать список студентов.
	   */
        Student st1 = new Student("Ivan", 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Mariya", 'f', 23, 3, 7.4);

        List<Student> listOfStudents = new ArrayList<>();

        listOfStudents.add(st1);
        listOfStudents.add(st2);
        listOfStudents.add(st3);
        listOfStudents.add(st4);
        listOfStudents.add(st5);

        listOfStudents = listOfStudents.stream().sorted((student1, student2) ->
                        student1.getName().compareTo(student2.getName()))
                .collect(Collectors.toList());
        System.out.println(listOfStudents);
        //Вывод:
        //[Student {name = 'Elena', sex = 'f', age = 19, course = 1, averageGrade = 8.9},
        // 	   Student {name = 'Ivan', sex = 'm', age = 22, course = 3, averageGrade = 8.3},
        // 	   Student {name = 'Mariya', sex = 'f', age = 23, course = 3, averageGrade = 7.4},
        // 	   Student {name = 'Nikolay', sex = 'm', age = 28, course = 2, averageGrade = 6.4},
        // 	   Student {name = 'Petr', sex = 'm', age = 35, course = 4, averageGrade = 7.0}]

	   /*
	   На объекте listOfStudents вызывается метод stream и получается поток. После вызывается метод sorted и
	   при помощи метода collect c параметром Collectors.toList() поток переводится в объект типа List (обязательно
	   необходимо импортировать класс Collectors для работы с ним - import java.util.stream.Collectors;).
	   Если в методе sorted не указать в параметрах, по каким критериям необходимо сортировать, в данном случае, список
	   студентов, то выскочит исключение:

	   Exception in thread "main" java.lang.ClassCastException: class Student cannot be cast to class java.lang.Comparable

	   потому, что Java не находит Comparable и не знает, как сортировать студентов, т.е. вот такая запись не сработает:

	   listOfStudents.stream().sorted().collect(Collectors.toList());

	   Поэтому, в данном случае, в параметр метода sorted необходимо вставить Comparator:

	   (student1, student2) -> student1.getName().compareTo(student2.getName())

	   сортировка по именам. Сортировка может быть по любым значениям, к примеру:

	   students = students.stream().sorted((student1, student2) ->
	            {
                    int result = student1.getName().compareTo(student2.getName());
                    if (result == 0) {
                        result = student1.getAge() - student2.getAge();
                    }
                    return result;
                }
        ).collect(Collectors.toList());

	   Метод sorted возвращает stream.
	   */

	   /*
	   В примере с массивом int-ов Java знает как сортировать, поэтому нет необходимости в параметре метода sorted указывать
	   Comparator. В примере же со студентами, в параметре метода sorted необходимо указать, как необходимо (по каким критериям)
	   Java сортировать объекты в списке.
	   */

	   /*
	   В чем разница сортировки через stream и через классы Collections / Arrays?
	   1. Сортировки на классах меняет сам массив, а сортировка на stream возвращает новый массив, не изменяя при этом начальный;
	   2. Если предпочтителен функциональный стиль программирование - выбор stream;
	   */

    }
}

/*
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
                "name = '" + name + '\'' +
                ", sex = '" + sex + '\'' +
                ", age = " + age +
                ", course = " + course +
                ", averageGrade = " + averageGrade +
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

    public void setAverageGreade(double averageGrade) {
        this.averageGrade = averageGrade;
    }
}
*/