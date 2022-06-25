package comparation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
Интерфейс Comparable.
Из названия уже понятно, что этот интерфейс, как и интерфейс Comparator, необходимы для сравнения. Не просто сравнения
двух объектов, что они равны или нет, а эти интерфейсы помогают узнавать какой объект больше, какой меньше, чтобы была
возможность сортировать эти объекты, например, в какой-то коллекции или в каком-то массиве.

Создадим ArrayList и поместим туда несколько элементов и посмотрим, как проходит сортировка:

public class MyComparable {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Stas");
        list.add("add");
        list.add("Mariya");
    }
}

Можно воспользоваться методом sort класса Collections для сортировки списка:

public class MyComparable {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Stas");
        list.add("Ivan");
        list.add("Mariya");
        System.out.println("Before sorting: " + list);

        Collections.sort(list);
        System.out.println("After sorting: " + list);
    }
}

Запуск программы. Вывод на экран:
Before sorting: [Stas, Ivan, Mariya]
After sorting: [Ivan, Mariya, Stas]

Сортировка произошла по естественному порядку (natural order), т.е. сортировка произошла в лексикографическом порядке.
При сортировку никаких неприятных сюрпризов не произошло и тут естественный порядок сортировки очевиден. Так же
очевиден естественный порядок сортировки при работе, например, с Integer. Если бы в list были записаны Integer
значения, то отсортировать их можно было точно так же, при полоши метода sort класса Collections.

Теперь поработаем с объектами классов, которые самостоятельно создадим.
Создадим класс Employee:

class Employee {

    int id;
    String name;
    String surname;
    int salary;

    Employee(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = " + this.id +
                ", name = '" + this.name + '\'' +
                ", surname = '" + this.surname + '\'' +
                ", salary = " + this.salary +
                "}";
    }
}

Теперь создадим в main ArrayList, который будет содержать работников и посмотрим, как сработает сортировка:

public class MyComparable {
    public static void main(String[] args) {
        List <Employee> list = new ArrayList<>();

        Employee emp1 = new Employee(10, "Petr", "Ivanov", 1000);
        Employee emp2 = new Employee(20, "Ivan", "Sidorov", 200);
        Employee emp3 = new Employee(40, "Ivan", "Petrov", 3000);

        list.add(emp1);
        list.add(emp2);
        list.add(emp3);
        System.out.println("Before sorting: \n" + list + "\n");
    }
}

Запуск программы. Вывод на экран:
Before sorting:
[Employee {id = 10, name = 'Petr', surname = 'Ivanov', salary = 1000},
    Employee {id = 20, name = 'Ivan', surname = 'Sidorov', salary = 200},
    Employee {id = 40, name = 'Ivan', surname = 'Petrov', salary = 3000}]

Никаких неприятных сюрпризов, в каком порядке были добавлены элементы в ArrayList, в таком же и остались. Теперь
сделаем сортировку. Когда был написан следующий код:

        Collections.sort(list);

компилятор начал "ругаться". Компилятор ругается на то, что Java не понимает каким образом делать сортировку у
объектов класса Employee. Как производить сортировку объектов класса String, Integer Java понимает, а с объектами
класса Employee - нет. Например. объект emp1 больше, чем emp2? А может emp1 меньше, чем emp2? Или же emp1 равен emp2?
Т.е. по каким критериям emp1 сравнивать с emp2 Java не понимает. Эти критерии необходимо самостоятельно задать и эти
критерии могут быть любыми. Допустим, будем сравнивать объекты класса Employee по id. Для того, чтобы применить этот
метод сравнения этих объектов, можно использовать интерфейс Comparable (с англ. возможный для сравнения).
Для этого класс Employee должен имплементировать интерфейс Comparable<Employee>. У этого интерфейса всего лишь один
метод - compareTo. С помощью этого метода и будет происходить сравнение объектов класса Employee:

    @Override
    public int compareTo(Employee anotherEmp) {
    }

Какова логика этого метода?
Нужно обратить внимание на то, что метод должен возвращать int. Что это за int? Мы сравниваем текущий объект с объектом
в параметре метода compareTo и должны возвратить какое-то число. Так вот, если текущий объект (this) больше объекта в
параметре, то вернуться методом должно положительное число, если меньше, то отрицательное число, а если они равны, то
должен вернуться ноль. Можно встретить разные написания кода в compareTo методе. Классический способ это когда:
"1" возвращаемое значение, если текущий объект (this) больше объекта, с которым идет сравнение;
"-1" возвращаемое значение, если текущий объект (this) меньше объекта, с которым идет сравнение;
"0" возвращаемое значение, если оба объекта равны;
Поэтому можно написать следующим образом:

    @Override
    public int compareTo(Employee anotherEmp) {
        if (this.id == anotherEmp.id) {
            return 0;
        } else if (this.id < anotherEmp.id) {
            return -1;
        } else {
            return 1;
        }
    }

Класс Employee cимплементировал интерфейс Comparable и теперь объекты класса Employee могут быть сравнены между собой
по полю "id". Теперь никаких "возмущений" со стороны компилятора нет, т.к. Java понимает как теперь сортировать.

Код:

public class MyComparable {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();

        Employee emp1 = new Employee(10, "Petr", "Ivanov", 1000);
        Employee emp2 = new Employee(20, "Ivan", "Sidorov", 200);
        Employee emp3 = new Employee(40, "Ivan", "Petrov", 3000);

        list.add(emp1);
        list.add(emp2);
        list.add(emp3);
        System.out.println("Before sorting: \n" + list + "\n");

        Collections.sort(list);
        System.out.println("After sorting: \n" + list);
    }
}

class Employee implements Comparable<Employee> {
    int id;
    String name;
    String surname;
    int salary;

    Employee(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = " + this.id +
                ", name = '" + this.name + '\'' +
                ", surname = '" + this.surname + '\'' +
                ", salary = " + this.salary +
                "}";
    }

    @Override
    public int compareTo(Employee anotherEmp) {
        if (this.id == anotherEmp.id) {
            return 0;
        } else if (this.id < anotherEmp.id) {
            return -1;
        } else {
            return 1;
        }
    }
}

Запуск программы. Вывод на экран:
Before sorting:
[Employee {id = 10, name = 'Petr', surname = 'Ivanov', salary = 1000},
    Employee {id = 20, name = 'Ivan', surname = 'Sidorov', salary = 200},
    Employee {id = 40, name = 'Ivan', surname = 'Petrov', salary = 3000}]

After sorting:
[Employee {id = 10, name = 'Petr', surname = 'Ivanov', salary = 1000},
    Employee {id = 20, name = 'Ivan', surname = 'Sidorov', salary = 200},
    Employee {id = 40, name = 'Ivan', surname = 'Petrov', salary = 3000}]

Все сработало как нужно.

Отметим следующий момент: КОМПИЛЯТОР ПОКАЗАЛ, что не возможно отсортировать коллекцию с объектами класса Employee,
если класс Employee не имплементирует функциональный интерфейс Comparable. Так вот, объекты класса Employee можно
поместить в массив Employee и для сортировки воспользоваться методом sort класса Arrays, НО, если все же класс
Employee не будет имплементировать функциональный интерфейс Comparable, то все равно возникнут проблемы при
сортировке. Компилятор не покажет ошибки, но во время runtime выбросится исключение:

Код:

public class MyComparable {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();

        Employee emp1 = new Employee(10, "Petr", "Ivanov", 1000);
        Employee emp2 = new Employee(20, "Ivan", "Sidorov", 200);
        Employee emp3 = new Employee(40, "Ivan", "Petrov", 3000);

        Arrays.sort(new Employee[]{emp1, emp2, emp3});
    }
}

class Employee {
    int id;
    String name;
    String surname;
    int salary;

    Employee(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = " + this.id +
                ", name = '" + this.name + '\'' +
                ", surname = '" + this.surname + '\'' +
                ", salary = " + this.salary +
                "}";
    }
}

Запуск программы. Вывод на экран:
Exception in thread "main" java.lang.ClassCastException: class comparation.Employee cannot be cast to class
java.lang.Comparable (comparation.Employee is in unnamed module of loader 'app'; java.lang.Comparable is in module
java.base of loader 'bootstrap')


 */


//public class MyComparable {
//    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        list.add(4);
//        list.add(10);
//        list.add(7);
//        System.out.println("Before sorting: " + list);
//
//        Collections.sort(list);
//        System.out.println("After sorting: " + list);
//    }
//}

public class MyComparable {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();

        Employee emp1 = new Employee(10, "Petr", "Ivanov", 1000);
        Employee emp2 = new Employee(20, "Ivan", "Sidorov", 200);
        Employee emp3 = new Employee(40, "Ivan", "Petrov", 3000);
        Arrays.sort(new Employee[]{emp1, emp2, emp3});
//        list.add(emp1);
//        list.add(emp2);
//        list.add(emp3);
//        System.out.println("Before sorting: \n" + list + "\n");
//
//        Collections.sort(list);
//        System.out.println("After sorting: \n" + list);
    }
}

class Employee
//        implements Comparable<Employee>
{
    int id;
    String name;
    String surname;
    int salary;

    Employee(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = " + this.id +
                ", name = '" + this.name + '\'' +
                ", surname = '" + this.surname + '\'' +
                ", salary = " + this.salary +
                "}";
    }

//    @Override
    public int compareTo(Employee anotherEmp) {
        if (this.id == anotherEmp.id) {
            return 0;
        } else if (this.id < anotherEmp.id) {
            return -1;
        } else {
            return 1;
        }
    }
}