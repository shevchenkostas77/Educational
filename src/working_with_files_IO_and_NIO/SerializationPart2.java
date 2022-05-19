package working_with_files_IO_and_NIO;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
Задание-пример.
Представим себе, что, по определенным причинам, не должны отправлять информацию о зарплате работника, т.е. это
конфиденциальная информация. Необходимо сделать так, чтобы при сериализации не попадали в файл данные о поле salary.
Для этого существует в Java ключевое слово transient (с англ. временный).

Код:

class Employee implements Serializable {
    String name;
    String department;
    int age;
    transient double salary;            // <--- transient
    Car car;

    public Employee(String name, String department, int age, double salary, Car car) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.salary = salary;
        this.car = car;

    }

    @Override
    public String toString() {
        return "Employee {" +
                "name = " + name +
                ", department = " + department +
                ", age = " + age +
                ", salary = " + salary +
                ", car = " + car +
                '}';
    }
}

class Car implements Serializable {
    String model;
    String color;

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public String toString() {
        return "Car {" +
                "model = " + model +
                ", color = " + color +
                '}';
    }
}

public class SerializationPart2 { // сериализация
    public static void main(String[] args) {
        Car car = new Car("Nissan", "white");
        Employee employee = new Employee("Mariya", "IT", 28, 500, car);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output2.bin"))) {
            outputStream.writeObject(employee);
            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class SerializationPart2 { // десериализация
    public static void main(String[] args) {
        Employee employee;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employee_output2.bin"))) {
            employee = (Employee)inputStream.readObject();
            System.out.println(employee);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Employee {name = Mariya, department = IT, age = 28, salary = 0.0, car = Car {model = Nissan, color = white}}

Зарплата работника равна нулю. Нулю, потому, что это поле не было сериализированно, а default значение у double равно
0.0. Вот так работает ключевое слово transient.

Небольшое повторение:
Для того, чтобы объект класса можно было сериализировать, класс должен имплементировать интерфейс Serializable.
Все поля класса так же должны имплементировать интерфейс Serializable.
Поля класса, помеченные ключевым словом transient, не записываются в файл при сериализации.

Задание-пример.
Много программистов используют на своих компьютерах проект связанный с вручением бонусов. И какой-то программист по заданию
изменил класс Employee, добавил в класс поле отвечающее за фамилию работника. Еще этому программисту сказали, что при вручении
бонусов сотрудникам, необходимости в знании возраста нет, т.е. необходимо убрать поле age из класса Employee. Что он и сделал.

Код:

Программист, который изменил класс и будет сериализировать:
class Employee implements Serializable {
    String name;
    String surname;
    String department;
    // int age;
    transient double salary;
    Car car;

    public Employee(String name, String surname, String department,
    // int age,
    double salary, Car car) {
        this.name = name;
        this.surname = surname;
        this.department = department;
        // this.age = age;
        this.salary = salary;
        this.car = car;

    }

    @Override
    public String toString() {
        return "Employee {" +
                "name = " + name +
                ", surname = " + surname +
                ", department = " + department +
                // ", age = " + age +
                ", salary = " + salary +
                ", car = " + car +
                '}';
    }
}

public class SerializationPart2 { // сериализация
    public static void main(String[] args) {
        Car car = new Car("Nissan", "white");

        // Employee employee = new Employee("Mariya", "IT", 28, 500, car);
        Employee employee = new Employee("Mariya", "Ivanova", "IT" , 500, car); // добавлена фамилия и удален возраст

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output2.bin"))) {
            outputStream.writeObject(employee);
            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Программист, который будет десериализировать:
class Employee implements Serializable { // Первая версия класса Employee
    static final long serialVertionUID = 1;
    String name;
    String department;
    int age;
    transient double salary;
    Car car;

    public Employee(String name, String department, int age, double salary, Car car) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.salary = salary;
        this.car = car;

    }

    @Override
    public String toString() {
        return "Employee {" +
                "name = " + name +
                ", department = " + department +
                ", age = " + age +
                ", salary = " + salary +
                ", car = " + car +
                '}';
    }
}

public class SerializationPart2 { // десериализация
    public static void main(String[] args) {
        Employee employee;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employee_output2.bin"))) {
            employee = (Employee)inputStream.readObject();
            System.out.println(employee);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Car implements Serializable {
    String model;
    String color;

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public String toString() {
        return "Car {" +
                "model = " + model +
                ", color = " + color +
                '}';
    }
}

Бинарный файл был создан и вся необходимая информация была записана. Далее этот файл передается другому программисту, но
этот программист пользуется старой версией класса Employee и пытается сделать десериализацию.

Запуск программы. Вывод на экран:

java.io.InvalidClassException: Employee; local class incompatible: stream classdesc serialVersionUID =
-1113845673551192235, local class serialVersionUID = 6906503665824654975
        at java.base/java.io.ObjectStreamClass.initNonProxy(ObjectStreamClass.java:689)
        at java.base/java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1903)
        at java.base/java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1772)
        at java.base/java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2060)
        at java.base/java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1594)
        at java.base/java.io.ObjectInputStream.readObject(ObjectInputStream.java:430)
        at Main.main(Main.java:308)

Выбрасывается исключение - InvalidClassException. В этом исключении говорится о разнице serialVersionUID:
stream classdesc serialVersionUID = -1113845673551192235
local class serialVersionUID = 6906503665824654975
Поэтому и выбросилось исключение.

Что такое serialVersionUID и для чего он нужен?
Если первый программист выполняет сериализацию объекта нового вида (измененный класс), а второй программист пытается его
десериализовать старым образом (не измененный класс), то так не сработает. Тут появляется правило: если происходит
изменение класса объекта, который необходимо сериализовать, т.е. класс, который имплементирует интерфейс Serializable,
то нужно изменить и версию этого класса. Т.е., к примеру, работают несколько программистов и код класса Employee на
компьютере у каждого свой (одинаковой), нет никакой централизованной системы по типу GitHub. Поле serialVersionUID как
раз и есть версия класса. Если не указать версию самостоятельно, то компилятор сделает это автоматически, как показано в
примере вывода на экран. Таким образом serialVersionUID используется при десериализации, чтобы проверить одинаковы ли
версии классов. В примере вывода на экран были проверены версии классов, они оказалтись разными, соответственно
десериализация не получилась.

НАСТОЯТЕЛЬНО РЕКОМЕНДУЕТСЯ, чтобы все сериализуемые классы явно объявляли значения serialVersionUID, поскольку вычисление
serialVersionUID по умолчанию очень чувствительно к деталям класса, которое может отличаться в зависимости от реализации
компилятора и, следовательно, могут привести к неожиданным исключениям во время десериализации. Т.е. не нужно полагаться
на автоматическое создание serialVersionUID.

Правило должно быть таким:
при создании сериализируемого класса первым делом необходимо указасть:

        static final long serialVersionUID = (version number);

где (version number) - это будет номер версии класса.
В данном случае:

        static final long serialVersionUID = 2;

где 2 - это будет номер версии измененого класса.


// Первый прграммист:
class Employee implements Serializable { // Первая версия класса Employee
    static final long serialVersionUID = 1;
    String name;
    String department;
    int age;
    transient double salary;
    Car car;

    public Employee(String name, String department, int age, double salary, Car car) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.salary = salary;
        this.car = car;

    }

    @Override
    public String toString() {
        return "Employee {" +
                "name = " + name +
                ", department = " + department +
                ", age = " + age +
                ", salary = " + salary +
                ", car = " + car +
                '}';
    }
}

// public class SerializationPart2 { // сериализация
//     public static void main(String[] args) {
//         Car car = new Car("Nissan", "white");

//         // Employee employee = new Employee("Mariya", "IT", 28, 500, car); // Первая версия класса Employee
//         Employee employee = new Employee("Mariya", "Ivanova", "IT" , 500, car);  // Вторая версия класса Employee

//         try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output2.bin"))) {
//             outputStream.writeObject(employee);
//             System.out.println("Done!");

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

// Второй программист:
// class Employee implements Serializable { // Вторая версия класса Employee
//     static final long serialVersionUID = 2;
//     String name;
//     String surname;
//     String department;
//     // int age;
//     transient double salary;
//     Car car;

//     public Employee(String name, String surname, String department,
//     // int age,
//     double salary, Car car) {
//         this.name = name;
//         this.surname = surname;
//         this.department = department;
//         // this.age = age;
//         this.salary = salary;
//         this.car = car;

//     }

//     @Override
//     public String toString() {
//         return "Employee {" +
//                 "name = " + name +
//                 ", surname = " + surname +
//                 ", department = " + department +
//                 // ", age = " + age +
//                 ", salary = " + salary +
//                 ", car = " + car +
//                 '}';
//     }
// }

public class SerializationPart2 { // десериализация
    public static void main(String[] args) {
        Employee employee;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employee_output2.bin"))) {
            employee = (Employee)inputStream.readObject();
            System.out.println(employee);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Car implements Serializable {
    String model;
    String color;

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public String toString() {
        return "Car {" +
                "model = " + model +
                ", color = " + color +
                '}';
    }
}

И когда второй программист захочет сделать десериализацию, то у него, обять-таки, выйдет то же исключение, но уже с
использованием своих версий класса.

Запуск программы. Вывод на экран:

java.io.InvalidClassException: Employee; local class incompatible: stream classdesc serialVersionUID = 2,
local class serialVersionUID = 1
        at java.base/java.io.ObjectStreamClass.initNonProxy(ObjectStreamClass.java:689)
        at java.base/java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1903)
        at java.base/java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1772)
        at java.base/java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2060)
        at java.base/java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1594)
        at java.base/java.io.ObjectInputStream.readObject(ObjectInputStream.java:430)
        at Main.main(Main.java:440)

Тут "говорится", что используется версия класса - 1 при десериализации (local class serialVersionUID = 1), а когда
объект сериализировался, то использовалась версия класса - 2 (stream classdesc serialVersionUID = 2). Десериализация т
аким образом пройти не может.

В сериализируемом классе необходимо использовать serialVersionUID для обозначения версии класса.
*/

// Первый программист:
class Employee implements Serializable { // Первая версия класса Employee
    static final long serialVersionUID = 1;
    String name;
    String department;
    int age;
    transient double salary;
    Car car;

    public Employee(String name, String department, int age, double salary, Car car) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.salary = salary;
        this.car = car;

    }

    @Override
    public String toString() {
        return "Employee {" +
                "name = " + name +
                ", department = " + department +
                ", age = " + age +
                ", salary = " + salary +
                ", car = " + car +
                '}';
    }
}

// public class SerializationPart2 { // сериализация
//     public static void main(String[] args) {
//         Car car = new Car("Nissan", "white");
//
//         // Employee employee = new Employee("Mariya", "IT", 28, 500, car); // Первая версия класса Employee
//         Employee employee = new Employee("Mariya", "Ivanova", "IT" , 500, car);  // Вторая версия класса Employee
//
//         try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output2.bin"))) {
//             outputStream.writeObject(employee);
//             System.out.println("Done!");
//
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

// Второй программист:
// class Employee implements Serializable { // Вторая версия класса Employee
//     static final long serialVersionUID = 2;
//     String name;
//     String surname;
//     String department;
//     // int age;
//     transient double salary;
//     Car car;

//     public Employee(String name, String surname, String department,
//     // int age,
//     double salary, Car car) {
//         this.name = name;
//         this.surname = surname;
//         this.department = department;
//         // this.age = age;
//         this.salary = salary;
//         this.car = car;

//     }

//     @Override
//     public String toString() {
//         return "Employee {" +
//                 "name = " + name +
//                 ", surname = " + surname +
//                 ", department = " + department +
//                 // ", age = " + age +
//                 ", salary = " + salary +
//                 ", car = " + car +
//                 '}';
//     }
// }

//public class SerializationPart2 { // десериализация
//    public static void main(String[] args) {
//        Employee employee;
//
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employee_output2.bin"))) {
//            employee = (Employee)inputStream.readObject();
//            System.out.println(employee);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}

class Car implements Serializable {
    String model;
    String color;

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public String toString() {
        return "Car {" +
                "model = " + model +
                ", color = " + color +
                '}';
    }
}