package reflection_example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/*
Рефлексия - это механизм исследования данных о программе во время ее выполнения. Рефлексия позволяет исследовать
информацию о полях, методах, конструкторах и других составляющих классов. Java предоставляет класс, который называется
Class. Он находится в пакете java.lang. Экземпляры класса Class представляют классы и интерфейсы в работающем приложении
Java. Примитивные типы в Java так же представленны в виде объектов Class. Т.е. получается у нас есть классы такие как:
Car, Student, классы Java: String, HashMap и другие и они являются объектами класса Class. Чтобы исследовать информацию
о классе нужен объект, которую эту информацию содержит. Таким объектом является объект класса Class.

Создадим класс Employee в котором будет четыре поля, три из них с access modifier - public и один - private. В этом
классе будет три конструктора:
1. без каких-либо параметров;
2. со всеми полями с access modifier public, т.е. три поля;
3. со всеми 4-мя полями;
У поля с access modifier private будут методы getter и setter.
В этом классе будет метод с access modifier private, который меняет значение одного из полей класса Employee c access
modifier public. И будет переопределен метод toString.

Код:

class Employee {
    public int id;
    public String name;
    public String department;
    private double salary;

    public Employee() {
    }

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private void changeDepartment(String newDepartment) {
        department = newDepartment;
        System.out.println("New department " + this + " employee: " + department);
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", department = '" + department + '\'' +
                ", salary = " + salary +
                '}';
    }
}

Теперь поработаем с самой рефлексией.
Как можно создать объект класса, который называется Class?
Рассмотрим три варианта создания этого объекта. Самый часто используемый для создания объекта класса Class:

        Class nameVariable = Class.forName("package.nameClass");

package.nameClass - указывается полный путь класса, с которым необходимо будет работать (пакет.название класса):

public class ReflectionPart1 {
    public static void main(String[] args) {
        Class employeeClass = Class.forName("Employee");
    }
}

Создали объект класса Class и получается, что объектом класса Class является класс Employee. Вот такая концепция
используется в Reflection. Метод forName выбрасывает исключение - ClassNotFoundException.

Рассмотрим второй вариант, как можно создать объект класса Class:

        Class nameVariable = nameClass.class;

Если взять наш пример:

public class ReflectionPart1 {
    public static void main(String[] args) {
        Class employeeClass2 = Employee.class;
    }
}

Рассмотрим третий вариант, как можно создать объект класса Class:
Сперва нужно создать объект класса, с которым будем работать.

        NameClass nameVariable = new NameClass(); // создали объект класса, с которым будем работать
        Class name = nameVariable.getClass();

Если взять наш пример:

public class ReflectionPart1 {
    public static void main(String[] args) {
        Employee emp = new Employee(); // не важно какой конструктор будет использован
        Class employeeClass3 = emp.getClass();
    }
}

Какую же информацию возможно получить из объекта класса Class (т.е. из employeeClass)?
Как уже выше было написано, что объект employeeClass является нашим классом Employee. Интересно звучит это выражение, но
к нему нужно просто привыкнуть.
Можно получить какое-то поле класса Employee с помощью объекта employeeClass. Как можно получить поле?
При помощи метода getField, с англ. "получить поле":

        employeeClass.getField("id");

В параметрах в двойных кавычках указывается необходимое поле. Метод getField выбрасывает исключение -
NoSuchFieldException. Исключение будет выброшено, когда не будет найдено искомое поле, например, поле "id2", такого поля нет в классе Employee и
будет выброшено исключение. Метод getField возвращает объект типа Field. Есть такой класс с названием Field (нужен импорт -
import java.lang.reflect.Field;).

        Field someField = employeeClass.getField("id");

Объекту класса Field (поле) передаем Field (поле) класса Employee, которое носит имя "id". Какую информацию об этом поле
возможно узнать?
Например, можно узнать тип этого поля:


public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Field someField = employeeClass.getField("id");
            System.out.println("Type of \"id\" field: " + someField.getType());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Type of "id" field: int

Благодаря рефлексии смогли узнать информацию о поле класса Employee.

А если необходимо узнать информацию о всех полях?
Для этого у employeeClass можно вызвать метод getFields. Метод getFields возвращает массив типа Field.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Field[] fields = employeeClass.getFields();

            for(Field field : fields) {
                System.out.println("Type of " + '\"' + field.getName() + '\"' + ": " + field.getType());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Type of "id": int
Type of "name": class java.lang.String
Type of "department": class java.lang.String

Тут нет поля access modifier которого private, НО с помощью рефлексии можно получить даже к полям с access modifier private.
И как же получить абсолютно все поля, даже с access modifier private?
На объекте класса Class вызвать метод getDeclaredFields и тогда можно увидеть не только после с access modifier public, но и
с private. Метода getDeclaredFields возвращает массив типа Field.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Field[] allFields = employeeClass.getDeclaredFields();

            for(Field field : allFields) {
                System.out.println("Type of " + '\"' + field.getName() + '\"' + ": " + field.getType());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Type of "id": int
Type of "name": class java.lang.String
Type of "department": class java.lang.String
Type of "salary": double

Несмотря на то, что поле salary в классе Employee приватное доступ возможно получить.

Теперь перейдем к методам.
Как получить информацию о конкретном методе класса и какую информацию можно получить?
Создадим еще один метод в классе Employee с именем increaseSalary.
При помощи объекта класса Class вызывается метод getMethod и в параметр этого метода передается имя метода, о котором необходимо
узнать информацию. Метод getMethod выбрасывает исключение - NoSuchMethodException. Метод getMethod возвращает объект типа Method,
есть такой класс (нужен импорт import java.lang.reflect.Method;). О методе можно узнать: тип возвращаемого значения метода
(getReturnType), типы параметров, которые принимает интересующий нас метод (getParameterTypes). Метод getParameterTypes
возвращает массив типа Class поэтому, чтобы вывести этот массив на экран в читабельном человеку виде метод toString класса Arrays.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method someMethod = employeeClass.getMethod("increaseSalary");
            System.out.println("Return type of method increaseSlary: " + someMethod.getReturnType());
            System.out.println("Parameter types of method increaseSlary: " + Arrays.toString(someMethod.getParameterTypes()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
Запуск программы. Вывод на экран:
Return type of method increaseSlary: void
Parameter types of method increaseSlary: []


*/

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method someMethod = employeeClass.getMethod("increaseSalary");
            System.out.println("Return type of method increaseSlary: " + someMethod.getReturnType());
            System.out.println("Parameter types of method increaseSlary: " + Arrays.toString(someMethod.getParameterTypes()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
class Hello extends Object {

}


class Employee {
    public int id;
    public String name;
    public String department;
    private double salary;

    public Employee() {
    }

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private void changeDepartment(String newDepartment) {
        department = newDepartment;
        System.out.println("New department " + this + " employee: " + department);
    }

    public void increaseSalary() {
        salary *= 2;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", department = '" + department + '\'' +
                ", salary = " + salary +
                '}';
    }
}