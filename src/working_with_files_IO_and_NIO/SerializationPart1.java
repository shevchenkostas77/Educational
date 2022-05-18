package working_with_files_IO_and_NIO;

import java.util.List;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/*
Процесс сериализации.
Допустим, необходимо передать список имен сотрудников какой-то компании, которые должны получить премию, т.е. передать
список имен сотрудников от одного программиста другому. Передать можно по сети или сохранить в файл и передать этот файл.
Этот пример и будет примером для понятия процесса сериализации.

Сериализация - это процесс преобразования объекта в последовательность байт.
Десериализация - это процесс восстановления объекта, их этих байт.

Т.е. объект можно записать в файл, он будет выглядеть, как последовательность определенных байт и при необходжимости можно
будет восстановить обьект из этих байт, при помощи процесса десериализации. Для сериализации и десериализации необходимо
использовать два класса: ObjectInputStream и ObjectOutputStream.

Как создается ObjectOutputStream (сериализация):

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("nameFile.bin"));
        В конструктор помещается объект класса FileOutputStream (выбрасывает исключение FileNotFoundException)

Как создается ObjectInputStream (десериализация):

        ObjectInputStream inputStream = new ObjectInputStream(new FileOutputStream("nameFile.bin"));
        В конструктор помещается объект класса FileInputStream (выбрасывает исключение FileNotFoundException)

Задание-пример.
Необходимо сохранить список имен работников, которые должны получить какой-то бонус в бинарном файле и передать этот файл
другому программисту. Т.е. объекты перевести в последовательность байтов, а после из байтов восстановить объект.

Код:

public class SerializationEx1_1 {
    public static void main(String[] args) {
        List<String> employees = new ArrayList<>();
        employees.add("Stas");
        employees.add("Ivan");
        employees.add("Elena");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employees_output.bin"))) {

            outputStream.writeObject(employees);
            System.out.println("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

Содержимое файла "employees_output.bin":
¬í¬í¬í¬í¬í¬í¬í¬í¬í¬í¬í¬í

Т.е. создался успешно файл и в него был записан список сотрудников, но т.к. файл бинарный, то он не читабельный для человека.
Далее файл передается второму программисту и допустим, он этот файл положил в корень проэкта. Второму программисту нужно
прочитать этот файл.

Код:

public class SerializationEx1_2 {
    public static void main(String[] args) {

        List<String> employees; // для сохранения полученных данных (списка сотрудников) из файла

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employees_output.bin"))) {

            employees = (ArrayList)inputStream.readObject();
            // метод readObject выбрасывает ClassNotFoundException, нужно ловить это исключение и возвращает Object,
            // поэтому нужно использовать кастинг на ArrayList, т.к. имена первым программистом были записаны в ArrayList

            System.out.println(employees);

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
[Stas, Ivan, Elena]

Из бтинарного файла был прочтен список работников. Вот так работает сериализация и десериализация. Объект преобразовуется в
последователность байт - это сериализация, а далее объект восстанавливается из этих байт - это десериализация.

Еще задание-пример.
Теперь, допустим, что нужно передать не имена работников, а одного работника, например, лучшего раблтника месяца. Нужно
передать не только его имя, а как целый обьект, т.е. имя, отдел, где работает сотрудник, возраст и запрплату, например.
Нужно создать класс Employee и нужно проделать то, что и в прошлом примере (провести сериализацию и десериализацию).

Код:

class Employee {
    String name;
    String department;
    int age;
    double salary;

    public Employee(String name, String department, int age, double salary) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employees {" +
                "name = " + name +
                ", department = " + department +
                ", age = " + age +
                ", salary = " + salary +
                '}';
    }
}

public class SerializationEx3 {
    public static void main(String[] args) {
        Employee employee = new Employee("Mariya", "IT", 28, 500);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output.bin"))) {

            outputStream.writeObject(employee);
            System.out.println("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
java.io.NotSerializableException: Employee
        at java.base/java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1185)
        at java.base/java.io.ObjectOutputStream.writeObject(ObjectOutputStream.java:349)
        at Main.main(Main.java:182)

Выбрасывается исключение - NotSerializableException, т.е. все прошло не так как планировалось. Чтобы объект класса
можно было сериализировать, класс должен имплементировать интерфейс Serializable. В данном примере необходимо
сериализировать объект класса Employee, поэтому класс Employee должен имплементировать интерфейс Serializable.
Итерфейс Serializeable не содержит ни одного метода, это "интерфейс-метка", т.е. происходит отметка, что класс
Employee теперь может сериализироваться. В прошлом примере все работало как нужно, т.к. ArrayList имплементирует
интерфейс Serializeable. И элементы, которые содержал в себе ArrayList, а это объекты типа String, класс String так
же имплементирует интерфейс Serializeable. После того, как указали, что класс Employee имплементирует интерфейс
Serializeable переопределять какие-либо методы или делать что-то еще не нужно. Но нужно импортировать интерфейс:

import java.io.Serializable;

class Employee implements Serializable {
    String name;
    String department;
    int age;
    double salary;

    public Employee(String name, String department, int age, double salary) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employees {" +
                "name = " + name +
                ", department = " + department +
                ", age = " + age +
                ", salary = " + salary +
                '}';
    }
}

public class SerializationEx2_1 {
    public static void main(String[] args) {
        Employee employee = new Employee("Mariya", "IT", 28, 500);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output.bin"))) {

            outputStream.writeObject(employee);
            System.out.println("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Done!

и файл "employee_output.bin" успешно был создан.

Этот файл передается другому программисту и он должен его прочитать.

Код:

public class SerializationEx2_2 {
    public static void main(String[] args) {
        Employee employee;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employee_output.bin"))) {

            employee = (Employee)inputStream.readObject();
            // метод readObject выбрасывает ClassNotFoundException, нужно ловить это исключение и возвращает Object,
            // поэтому нужно использовать кастинг на Employee

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
Employees {name = Mariya, department = IT, age = 28, salary = 500.0}

Все сработало как нужно.

Еще одно задание-пример.
Пусть у сотрудника будет автомобиль и в конструкторе, при создании работника, необходимо это указывать.

Код:

class Employee implements Serializable {
    String name;
    String department;
    int age;
    double salary;
    Car car;

    public Employee(String name, String department, int age, double salary, Car car) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.car = car;
    }

    @Override
    public String toString() {
        return "Employees {" +
                "name = " + name +
                ", department = " + department +
                ", age = " + age +
                ", salary = " + salary +
                ", car = " + car +
                '}';
    }
}

class Car {
    String model;
    String color;

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public String toString() {
        return "Car {" +
                "model = " +
                ", color = " +
                '}';
    }
}

public class SerializationEx2_1 {
    public static void main(String[] args) {
        Car car = new Car("Nissan", "white");
        Employee employee = new Employee("Mariya", "IT", 28, 500, car);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output.bin"))) {

            outputStream.writeObject(employee);
            System.out.println("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
java.io.NotSerializableException: Car
        at java.base/java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1185)
        at java.base/java.io.ObjectOutputStream.defaultWriteFields(ObjectOutputStream.java:1553)
        at java.base/java.io.ObjectOutputStream.writeSerialData(ObjectOutputStream.java:1510)
        at java.base/java.io.ObjectOutputStream.writeOrdinaryObject(ObjectOutputStream.java:1433)
        at java.base/java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1179)
        at java.base/java.io.ObjectOutputStream.writeObject(ObjectOutputStream.java:349)
        at Main.main(Main.java:389)

Выбрасывается исключение NotSerializableException. Потому, что все объекты, которые содержаться в объекте класса Employee
должны имплементировать интерфейс Serializable. Т.е. если необходимо сделать сериализацию класса Employee значит, все поля
в классе Employee должны имплементировать интерфейс Serializable. Таким образом класс Car тоже должен имплементировать
интерфейс Serializable.

Запуск программы. Вывод на экран:
Employees {name = Mariya, department = IT, age = 28, salary = 0.0, car = Car {model = , color = }}

Все сработало как нужно.
*/

// public class SerializationPart1 { // SerializationEx1_1
//     public static void main(String[] args) {
//         List<String> employees = new ArrayList<>();
//         employees.add("Stas");
//         employees.add("Ivan");
//         employees.add("Elena");

//         try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employees_output.bin"))) {

//             outputStream.writeObject(employees);
//             System.out.println("Done!");

//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

// public class SerializationPart1 { // SerializationEx1_2
//     public static void main(String[] args) {

//         List<String> employees; // для сохранения полученных данных (списка сотрудников) из файла

//         try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employees_output.bin"))) {

//             employees = (ArrayList)inputStream.readObject();
//             /*
//             метод readObject выбрасывает ClassNotFoundException, нужно ловить это исключение и возвращает Object,
//             поэтому нужно использовать кастинг на ArrayList, т.к. имена первым программистом были записаны в ArrayList
//             */
//             System.out.println(employees);

//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         } catch (ClassNotFoundException e) {
//             e.printStackTrace();
//         }
//     }
// }

class Employee implements Serializable {
    String name;
    String department;
    int age;
    double salary;
    Car car;

    public Employee(String name, String department, int age, double salary, Car car) {
        this.name = name;
        this.department = department;
        this.age = age;
        this.car = car;
    }

    @Override
    public String toString() {
        return "Employees {" +
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
                "model = " +
                ", color = " +
                '}';
    }
}

public class SerializationPart1 { // сериализация
    public static void main(String[] args) {
        Car car = new Car("Nissan", "white");
        Employee employee = new Employee("Mariya", "IT", 28, 500, car);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employee_output.bin"))) {

            outputStream.writeObject(employee);
            System.out.println("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// public class SerializationPart1 { // десериализация
//     public static void main(String[] args) {
//         Employee employee;

//         try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employee_output.bin"))) {

//             employee = (Employee)inputStream.readObject();
//             // метод readObject выбрасывает ClassNotFoundException, нужно ловить это исключение и возвращает Object,
//             // поэтому нужно использовать кастинг на Employee

//             System.out.println(employee);

//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         } catch (ClassNotFoundException e) {
//             e.printStackTrace();
//         }
//     }
// }