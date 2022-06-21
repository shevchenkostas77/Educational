package reflection_example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/*
Создадим объект класса типа Class:

        Class employeeClass = Class.forName("Employee");

Метод forName может выбросить исключение - ClassNotFoundException (добавляем в сигнатуру метода main или в блок catch).
Теперь создадим объект класса Employee с помощью объекта employeeClass. Как можно это сделать?
До Java версии 11 можно было создать объект с помощью reflection следующим образом:

        employeeClass.newInstance();

Метод newInstance возвращает Object, поэтому можем написать:

        Object o = employeeClass.newInstance();

Метод newInstance может выбросить исключения - IllegalAccessException и InstantiationException. Метод newInstance в
IntelliJ IDEA отображается как deprecated, т.е. устаревший.
При создании объекта класса Employee можно сделать кастинг до Employee:

        Employee o = (Employee)employeeClass.newInstance();

Код:

public class ReflectionPart2 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Employee o = (Employee)employeeClass.newInstance();
            System.out.println(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
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

Запуск программы. Вывод на экран:
Employee {id = 0, name = 'null', department = 'null', salary = 0.0}

Объект класса Employee, у которого все поля приняли дефолтное значение потому, что воспользовались конструктором
без параметров.
Данный метод newInstance, по крайней мере, для 11 версии Java является устаревшим и использовать его не рекомендуется.
Сейчас же рекомендуется вызывать метод newInstance на конструкторе:

        Class employeeClass = Class.forName("Employee");
        Constructor constructor = employeeClass.getConstructor();
        Object object = constructor.newInstance();

В этом случае метод newInstance выбрасывает исключение - InvocationTargetException
(нужен импорт import java.lang.reflect.InvocationTargetException;).
Так же и в этом примере можно сделать кастинг в Employee или же Constructor работает с Generics и можно этим
воспользоваться:

        Constructor<Employee> constructor = employeeClass.getConstructor();
        Employee object = constructor.newInstance();

В этом случае можно сразу, без кастинга, указать, что метод newInstance будет возвращать объект типа Employee.

Теперь создадим объект с помощью конструктора, у которого 3-и параметра:

        Class employeeClass = Class.forName("Employee");
        Constructor constructor = employeeClass.getConstructor(int.class, String.class, String.class);
        Object object = constructor.newInstance();

В данном примере constructor это конструктор, который работает с тремя параметрами (принимает три параметра) поэтому,
чтобы создать объект используя конструктор с 3-я параметрами, в параметрах метода newInstance необходимо вписать 3-и
аргумента (метод newInstance в параметрах принимает varargs). Итак, сначала необходимо прописать id работника, после
указать имя работника и департамент работника:

        Class employeeClass = Class.forName("Employee");
        Constructor constructor = employeeClass.getConstructor(int.class, String.class, String.class);
        Object object = constructor.newInstance(5, "Stas", "IT");

Код:

public class Main { // ReflectionPart2
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Constructor constructor = employeeClass.getConstructor(int.class, String.class, String.class);
            Object object = constructor.newInstance(5, "Stas", "IT");
            System.out.println(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Employee {id = 5, name = 'Stas', department = 'IT', salary = 0.0}

Естественно поле salary будет 0.0, это дефолтное значение, т.к. в этом конструкторе параметр salary не задается.

Теперь вызовем какой-то метод с помощью рефлексии. К примеру, вызовем метод setSalary, т.к. у работника не установлена
зарплата, дефолтное значение. Сначала нужно создать этот метод:

        Method method = employeeClass.getMethod("setSalary", double.class);

Теперь можно запустить этот метод. Для этого на методе вызнается метод invoke (с англ. вызвать), первый параметр этого
метода - объет на котором необходимо вызвать метод, а необходимо вызвать метод на объекте "object", второй параметр это
varargs и он зависит от количества параметров, которые необходимо вставить при вызове метода setSalary:

        Method method = employeeClass.getMethod("setSalary", double.class);
        method.invoke(object, 800.88);

Код:

public class ReflectionPart2 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");

            Constructor constructor = employeeClass.getConstructor(int.class, String.class, String.class);
            Object object = constructor.newInstance(5, "Stas", "IT");
            System.out.println(object);

            Method method = employeeClass.getMethod("setSalary", double.class);
            method.invoke(object, 800.88);
            System.out.println(object);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Employee {id = 5, name = 'Stas', department = 'IT', salary = 0.0}
Employee {id = 5, name = 'Stas', department = 'IT', salary = 800.88}

Теперь рассмотрим очень интересный момент в рефлексии. Для этого в классе Employee закомментируем методы getter и setter
для поля salary. И закомментируем конструктор, который принимает salary.
Поле salary с access modifier private. Благодаря инкапсуляции к полю salary теперь не добраться: не прочитать и не изменить
его. НО рефлексия нарушает правило инкапсуляции. Допустим, default значение поля salary = 1_000. Попробуем прочитать
это значение у поля salary.
Создадим работника и воспользуемся конструктором с 3-я параметрами:

        Employee employee = new Employee(10, "Stas", "IT");

Далее получим объект класса Class на объекте типа Employee:

        Employee employee = new Employee(10, "Stas", "IT");
        Class employeeClass = employee.getClass();

Теперь получим поле salary (обязательно использовать нужно метод getDeclaredField иначе не получить доступа к полю с
access modifier private):

        Employee employee = new Employee(10, "Stas", "IT");
        Class employeeClass = employee.getClass();
        Field field = employeeClass.getDeclaredField("salary");

Метод getDeclaredField выбрасывает исключение - NoSuchFieldException в случае, если не правильно в параметре этого
метода указать имя интересующего нас поля. А сможем ли мы записать значение поля salary записать в какую-то
переменную?
*/

public class ReflectionPart2 {
    public static void main(String[] args) throws NoSuchFieldException {
        Employee employee = new Employee(10, "Stas", "IT");
        Class employeeClass = employee.getClass();
        Field field = employeeClass.getDeclaredField("salary");
    }
}

class Employee {
    public int id;
    public String name;
    public String department;
    private double salary = 1_000;

    public Employee() {
    }

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    // public Employee(int id, String name, String department, double salary) {
    //     this.id = id;
    //     this.name = name;
    //     this.department = department;
    //     this.salary = salary;
    // }

    // public double getSalary() {
    //     return salary;
    // }

    // public void setSalary(double salary) {
    //     this.salary = salary;
    // }

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