package reflection_example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/*
Создадим объект класса типа Class:

        Class employeeClass = Class.forName("Employee");

Метод forName может выбросить исключение - ClassNotFoundException (добавляем в сигнатуру метода main или в блок catch).
Теперь создадим объект класса Employee с помощью объекта employeeClass. Как можно это сделать?
До Java версии 11 можно было создать объект с помощью reflection следующим образом:

        employeeClass.newInstance();

Метод newInstance возвращает Object, поэтому можем написать:

        Object o = employeeClass.newInstance();

Метод newInstance может выбросить исключения - IllegalAccessException и InstantiationException. Если вызывать метод на
объекте типа Class, то метод newInstance в IntelliJ IDEA отображается как deprecated, т.е. устаревший (перечеркнут).
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
Данный вариант вызова метода newInstance, по крайней мере, для 11 версии Java является устаревшим и использовать
его не рекомендуется.
Сейчас же рекомендуется вызывать метод newInstance на конструкторе:

        Class employeeClass = Class.forName("Employee");
        Constructor constructor = employeeClass.getConstructor();
        Object object = constructor.newInstance();

В этом случае метод newInstance выбрасывает исключение - InvocationTargetException
(нужен импорт import java.lang.reflect.InvocationTargetException;).
Так же и в этом примере можно сделать кастинг в Employee или же, Constructor работает с Generics и можно этим
воспользоваться:

        Constructor<Employee> constructor = employeeClass.getConstructor();
        Employee object = constructor.newInstance();

В этом случае можно сразу, без кастинга, указать, что метод newInstance будет возвращать объект типа Employee.

Теперь создадим объект с помощью конструктора, у которого 3-и параметра:

        Class employeeClass = Class.forName("Employee");
        Constructor constructor = employeeClass.getConstructor(int.class, String.class, String.class);
        Object object = constructor.newInstance();

В данном примере constructor это конструктор, который работает с тремя параметрами (принимает три аргумента) поэтому,
чтобы создать объект используя конструктор с 3-я параметрами, в параметрах метода newInstance необходимо вписать 3-и
аргумента (метод newInstance в параметрах принимает varargs). Итак, сначала необходимо прописать id работника, после
указать его имя и департамент:

        Class employeeClass = Class.forName("Employee");
        Constructor constructor = employeeClass.getConstructor(int.class, String.class, String.class);
        Object object = constructor.newInstance(5, "Stas", "IT");

Код:

public class ReflectionPart2 {
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
зарплата, т.е. установлено его дефолтное значение. Сначала нужно создать этот метод:

        Method method = employeeClass.getMethod("setSalary", double.class);

Теперь можно запустить этот метод. Для этого на методе вызнается метод invoke (с англ. вызвать), первый параметр этого
метода - объет на котором необходимо вызвать метод, а необходимо вызвать метод на объекте "object", второй параметр это
varargs и он зависит от количества параметров, которые необходимо вставить при вызове метода setSalary (в данном случае
всего один параметр необходим):

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
Поле salary с access modifier private. Благодаря инкапсуляции к полю salary теперь не добраться: не прочитать и не
изменить его. НО рефлексия нарушает правило инкапсуляции. Допустим, default значение поля salary = 1_000. Попробуем
прочитать это значение у поля salary.
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
У класса Field есть метод get, который принимает на вход объект, поле, у которого необходимо прочитать.
Метод get выбрасывает исключение - IllegalAccessException и возвращает объект типа Object.

Код:

public class ReflectionPart2 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Employee employee = new Employee(10, "Stas", "IT");

        Class employeeClass = employee.getClass();
        Field field = employeeClass.getDeclaredField("salary");

        double salaryValue = (Double)field.get(employee);
        System.out.println(salaryValue);
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

Выбрасыватся исключние - IllegalAccessException. Это исключение "говорит", что класс ReflectionPart2 не может получить
доступ к полю с access modifier private. Это очень легко поправимо. Чтобы дать себе доступ на работу с этим полем
нужно написать небольшой код:

        field.setAccessible(true); // set accessible с англ. "установить доступ"

Этим кодом "мы говорим", что хотим получить доступ к полю salary (field), даже, если он с access modifier private.

Код:

public class ReflectionPart2 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Employee employee = new Employee(10, "Stas", "IT");

        Class employeeClass = employee.getClass();
        Field field = employeeClass.getDeclaredField("salary");

        field.setAccessible(true);

        double salaryValue = (Double)field.get(employee);
        System.out.println("Field with access modifier \"private\" salary = " + salaryValue);
    }
}

Запуск программы. Вывод на экран:
Field with access modifier "private" salary = 1000.0

Таким же не сложным способом можно изменить зарплату для работника. Для этого нужно воспользоваться методом set из
класса Field. Вызываем метод set на объекте типа Field (field), т.е. вызываем метод на том поле, значение, которого
нужно поменять и в параметры этого метода передается сначала объект на котором хотим изменить поле (в данном случае
поле salary на объекте employee), вторым параметром задается значение, на которое нужно поменять текущее значние
поля salary. Метод set возвращает объект типа Object.

Код:

public class ReflectionPart2 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Employee employee = new Employee(10, "Stas", "IT");

        Class employeeClass = employee.getClass();
        Field field = employeeClass.getDeclaredField("salary");

        field.setAccessible(true);

        double salaryValue = (Double)field.get(employee);
        System.out.println("Field with access modifier \"private\" salary = " + salaryValue);

        field.set(employee, 1_500);
        System.out.println(employee);
    }
}

Запуск программы. Вывод на экран:
Field with access modifier "private" salary = 1000.0
Employee {id = 10, name = 'Stas', department = 'IT', salary = 1500.0}

Вот таким образом reflection способен обходить принципы инкапсуляции.

А есть ли что-то такое, что можно сделать с помощью рефлексии, чего не возможно сделать без нее?
Да. И вот пример для этого: создадим класс Calculator. У него будет 4-е метода: сложение, вычитание, деление и
умножение. Каждый из этих методов будет принимать два параметра - int и каждый из этих методов будет возвращать
void. А информацию о результате опреции будем выводить на экран:

class Calculator {
    void sum(int a, int b) {
        int result = a + b;
        System.out.println("Sum " + a + " and " + b + " = " + result);
    }

    void subtraction(int a, int b) {
        int result = a - b;
        System.out.println("Subtraction " + a + " and " + b + " = " + result);
    }

    void multiplication(int a, int b) {
        int result = a * b;
        System.out.println("Multiplication " + a + " and " + b + " = " + result);
    }

    void division(int a, int b) {
        int result = a / b;
        System.out.println("Division " + a + " and " + b + " = " + result);
    }
}

Создадим в корне проэкта файл "testCalculator.txt". Нужно написать такой код, который будет читать файл
"testCalculator.txt" и в файле будет указано какой метод нужно вызвать и будут заданы аргументы к этому методую.
Например:
sum   <--- метод
13   <--- первый аргумент метода
5   <--- второй аргумент метода
Для этого в методе main нужно прочитать файл "testCalculator.txt" (будем использовать try with resources). Читать
будем с помощью стримов, которые читают текстовую информцию:

public class Main { // ReflectionPart2
    public static void main(String[] args) {
        try(BuffereadReader reader = new BuffereadReader(new FileReader("testCalculator.txt"))) {

        }
    }
}

FileReader выбрасывает исключение - FileNotFoundException и IOException. Сначала нужно понять имя метода:

public class ReflectionPart2 {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new FileReader("testCalculator.txt"))) {

            String methodName = reader.readLine();
            String firstArgument = reader.readLine();
            String secondArgument = reader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

Хорошо, прочитали название метода и его аргументы. Создаем объект класса калькулятор:

        Calculator calculator = new Calculator();

Создадим объект типа Class:

        Class cl = calculator.getClass();

Создадим метод:

        Method method = null;

В классе Calculator 4-е метода. Мы должны найти тот метод, который по имени будет соответствовать прочитанному из
файла методу. Для этого найдем все методы класса Calculator и "пробежимся" по ним сверяя имена с переменной
methodName (в эту переменную записывается имя метода из файла):

         Method[] methods = cl.getDeclaredMethods();

Тут использован getDeclaredMethods потому, что, а зачем лишний раз пробегаться по методам, которые были унаследованы
от класса Object. При помощи for-each loop "пробегаемся" по всем методам и проверяем на соответствие названия метода
в массиве methods с именем метода в переменной methodName:

        Method method = null;
        Method[] methods = cl.getDeclaredMethods();

        for(Method thisMethod : methods) {
            if(thisMethod.getName().equals(methodName()) { // если имя метода переменной methodName совпадает с именем метода из массива
                method = thisMethod; // то, переменной method присваевается имя искомого в массиве метода
            }
        }

For-each loop больше не нужен. Теперь запустим необходимый метод. Для этого на методе вызывается метод invoke (с англ.
вызвать), первый параметр этого метода - объет на котором необходимо вызвать метод, а необходимо вызвать метод на
объекте "calculator", второй параметр это varargs и он зависит от количества параметров, которые необходимо вставить
при вызове метода, в данном случае два:

        method.invoke(calculator, Integer.parseInt(firstArgument),
            Integer.parseInt(secondArgument));

Метод invoke выбрасывает исключения - IllegalAccessException и InvocationTargetException.
Все, больше тут ничего не нужно делать. Если бы не рефлексия, то с этой задачей было бы не возможно справиться, т.е. как
возможно прочитать название метода из файла и вызвать этот метод в классе Calculator? Без рефлексии это сделать было бы
невозможно.

Код:

public class ReflectionPart2 {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new FileReader("testCalculator.txt"))) {

            String methodName = reader.readLine();
            String firstArgument = reader.readLine();
            String secondArgument = reader.readLine();

            Calculator calculator = new Calculator();

            Class cl = calculator.getClass();

            Method method = null;
            Method[] methods = cl.getDeclaredMethods();

            for(Method thisMethod : methods) {
                if(thisMethod.getName().equals(methodName)) {
                    method = thisMethod;
                }
            }

            method.invoke(calculator, Integer.parseInt(firstArgument),
            Integer.parseInt(secondArgument));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }
}

class Calculator {
    void sum(int a, int b) {
        int result = a + b;
        System.out.println("Sum " + a + " and " + b + " = " + result);
    }

    void subtraction(int a, int b) {
        int result = a - b;
        System.out.println("Subtraction " + a + " and " + b + " = " + result);
    }

    void multiplication(int a, int b) {
        int result = a * b;
        System.out.println("Multiplication " + a + " and " + b + " = " + result);
    }

    void division(int a, int b) {
        int result = a / b;
        System.out.println("Division " + a + " and " + b + " = " + result);
    }
}

Запуск программы. Вывод на экран:
Sum 13 and 5 = 18

Если в файле указать неправильное имя метода, например sum2, то выбросится исключение:
Exception in thread "main" java.lang.NullPointerException
        at Main.main(Main.java:562)

Потому, что при поиске метода (в for-each loop-e) с названием sum2 в классе Calculator такого метода
обнаружено не было, следовательно переменная method как была null, так и останется. И когда на null
вызывается метод invoke (или какой-то другой), то выбрасывается исключение - NullPointerException.
*/

public class ReflectionPart2 {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new FileReader("testCalculator.txt"))) {

            String methodName = reader.readLine();
            String firstArgument = reader.readLine();
            String secondArgument = reader.readLine();

            Calculator calculator = new Calculator();

            Class cl = calculator.getClass();

            Method method = null;
            Method[] methods = cl.getDeclaredMethods();

            for(Method thisMethod : methods) {
                if(thisMethod.getName().equals(methodName)) {
                    method = thisMethod;
                }
            }

            method.invoke(calculator, Integer.parseInt(firstArgument),
                    Integer.parseInt(secondArgument));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }
}

class Calculator {
    void sum(int a, int b) {
        int result = a + b;
        System.out.println("Sum " + a + " and " + b + " = " + result);
    }

    void subtraction(int a, int b) {
        int result = a - b;
        System.out.println("Subtraction " + a + " and " + b + " = " + result);
    }

    void multiplication(int a, int b) {
        int result = a * b;
        System.out.println("Multiplication " + a + " and " + b + " = " + result);
    }

    void division(int a, int b) {
        int result = a / b;
        System.out.println("Division " + a + " and " + b + " = " + result);
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