package reflection_example;

import java.lang.reflect.Constructor;
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
Рассмотрим три варианта создания этого объекта. Самый часто используемый вариант для создания объекта класса Class:

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
Как уже выше было написано, что объект employeeClass является нашим классом Employee. Интересно звучит это выражение,
но к нему нужно просто привыкнуть.
Можно получить какое-то поле класса Employee с помощью объекта employeeClass. Как можно получить поле?
При помощи метода getField, с англ. "получить поле":

        employeeClass.getField("id");

В параметрах в двойных кавычках указывается необходимое поле. Метод getField выбрасывает исключение -
NoSuchFieldException. Исключение будет выброшено, когда не будет найдено искомое поле, например, поле "id2", такого
поля нет в классе Employee и будет выброшено исключение. Метод getField возвращает объект типа Field. Есть такой класс
с названием Field (нужен импорт - import java.lang.reflect.Field;).

        Field someField = employeeClass.getField("id");

Объекту класса Field (поле) передаем Field (поле) класса Employee, которое носит имя "id". Какую информацию об этом
поле возможно узнать?
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

Но тут же нет поля access modifier private! С помощью рефлексии можно получить доступ даже к полям с access modifier
private.
И как же получить абсолютно все поля, даже с access modifier private?
На объекте класса Class вызвать метод getDeclaredFields и тогда можно увидеть не только после с access modifier public,
но и с private. Метода getDeclaredFields возвращает массив типа Field.

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

Несмотря на то, что поле salary в классе Employee приватное, доступ все равно возможно получить.

Теперь перейдем к методам.
Как получить информацию о конкретном методе класса и какую информацию можно получить?
Создадим еще один метод в классе Employee с именем increaseSalary.
При помощи объекта класса Class вызывается метод getMethod и в параметр этого метода передается имя метода, о котором
необходимо узнать информацию. Метод getMethod выбрасывает исключение - NoSuchMethodException. Метод getMethod
возвращает объект типа Method, есть такой класс (нужен импорт import java.lang.reflect.Method;). О методе можно узнать:
тип возвращаемого значения метода (getReturnType), типы параметров, которые принимает интересующий нас метод
(getParameterTypes). Метод getParameterTypes возвращает массив типа Class поэтому, чтобы вывести этот массив на экран в
читабельном для человека виде необходимо использовать метод toString класса Arrays.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method someMethod = employeeClass.getMethod("increaseSalary");
            System.out.println("Return type of method increaseSlary: " + someMethod.getReturnType());
            System.out.println("Parameter types of method increaseSlary: " +
                    Arrays.toString(someMethod.getParameterTypes()));
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

Теперь поработаем с методом, у которого есть параметры. К примеру, c setSalary, у него параметр типа double.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method someMethod = employeeClass.getMethod("setSalary");
            System.out.println("Return type of method setSalary: " + someMethod.getReturnType());
            System.out.println("Parameter types of method setSalary: " +
                    Arrays.toString(someMethod.getParameterTypes()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
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
java.lang.NoSuchMethodException: Employee.setSalary()
        at java.base/java.lang.Class.getMethod(Class.java:2109)
        at Main.main(Main.java:18)

Выбросилось исключение. Но, что же не так?
Метод getMethod принимает в свои параметры не только имя метода, информацию о котором мы ходим узнать, но еще и типы
параметров интересующего нас метода. Второй параметр метода getMethod (parametrTypes) является varargs, этот
параметр можно не указывать, НО только в том случае, если метод без параметров. Ну, и если бы метод setSalary был
перегружен, т.е. было бы в классе два и более метода с одним и тем же названием и разным набором параметров, то как бы
Java поняла, о каком методе нам нужно выдать информацию. Поэтому, если необходимо получить информацию о методе с
параметрами, то в методе getMethod первым параметром указывается имя метода, вторым и последующими параметрами
указываются типы параметров интересующего нас метода.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method someMethod = employeeClass.getMethod("setSalary", double.class);
            System.out.println("Return type of method setSalary: " + someMethod.getReturnType());
            System.out.println("Parameter types of method setSalary: " + Arrays.toString(someMethod.getParameterTypes()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Return type of method setSalary: void
Parameter types of method setSalary: [double]

У примитивных типов данных тоже есть понятие класс - double.class.

Теперь выведем информацию о всех методах класса Employee.
В этом нам поможет метод getMethods. Этот метод возвращает массив типа Method.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method[] methods = employeeClass.getMethods();

            for(Method method : methods) {
                System.out.println("Name of method: " + method.getName() +
                        ", return type: " + method.getReturnType() +
                        ", parametr types: " + Arrays.toString(method.getParameterTypes()));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Name of method: toString, return type: class java.lang.String, parametr types: []
Name of method: getSalary, return type: double, parametr types: []
Name of method: setSalary, return type: void, parametr types: [double]
Name of method: increaseSalary, return type: void, parametr types: []
Name of method: wait, return type: void, parametr types: [long]
Name of method: wait, return type: void, parametr types: [long, int]
Name of method: wait, return type: void, parametr types: []
Name of method: equals, return type: boolean, parametr types: [class java.lang.Object]
Name of method: hashCode, return type: int, parametr types: []
Name of method: getClass, return type: class java.lang.Class, parametr types: []
Name of method: notify, return type: void, parametr types: []
Name of method: notifyAll, return type: void, parametr types: []

Оказывается вот сколько методов в классе Employee. Метод toString, getSalary, setSalary, increaseSalary, wait (метод
wait перегружен, имена методов одинаковы, а списки параметров разные), equals, hashCode, getClass, notify, notifyAll.
Т.е. метода getMethods вернет все методы из класса Employee даже те, которые были унаследованы от родителей. НО метод
getMethods не вывел информацию о методе с access modifier private - changeDepartment. Как и в примере с полями, все же
можно получить доступ к методу, для этого есть getDeclaredMethods.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method[] allMethods = employeeClass.getDeclaredMethods();

            for(Method method : allMethods) {
                System.out.println("Name of method: " + method.getName() +
                        ", return type: " + method.getReturnType() +
                        ", parametr types: " + Arrays.toString(method.getParameterTypes()));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Name of method: getSalary, return type: double, parametr types: []
Name of method: setSalary, return type: void, parametr types: [double]
Name of method: changeDepartment, return type: void, parametr types: [class java.lang.String]
Name of method: increaseSalary, return type: void, parametr types: []
Name of method: toString, return type: class java.lang.String, parametr types: []

Метод getDeclaredMethods вернет только те методы, которые прописаны в классе Employee, не возвращает унаследованные
методы, но естественно, если метод был переопределен, как метод toString в классе, то он будет в этом списке. Вот
таким способом можно получить доступ даже к методам с access modifier private.

Как получить только те методы из класса, у которых access modifier public?
Этого можно достичь при помощи метода getDeclaredMethos и, к примеру, в for-each loop-e выполнить проверку.
Проверка будет выполняться при помощи статического метода isPublic класса Modifier и метода getModifiers.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Method[] allMethods = employeeClass.getDeclaredMethods();

            for(Method method : allMethods) {
                if(Modifier.isPublic(method.getModifiers())) {
                    System.out.println("Name of method: " + method.getName() +
                            ", return type: " + method.getReturnType() +
                            ", parametr types: " + Arrays.toString(method.getParameterTypes()));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Name of method: getSalary, return type: double, parametr types: []
Name of method: setSalary, return type: void, parametr types: [double]
Name of method: increaseSalary, return type: void, parametr types: []
Name of method: toString, return type: class java.lang.String, parametr types: []

Таких классов как, Field, Modifier, Method, Constructor и тд., их очень много.

Теперь о конструкторах. Как можно получить информацию о них?
Для начала "поговорим" о получении какого-то конкретного конструктора, для этого будем использовать метод
getConstructor. Когда, в примерах выше, был использован метод getMethod, то в параметрах необходимо было указать имя
интересующего нас метода, а вот когда происходит работа с конструктором, то понятно какое имя конструктора будет -
точно такое же как и название класса, поэтому Java достаточно умна, чтобы понять, что название конструктора совпадает с
названием класса. Метод getConstructor возвращает объект типа Constructor.

        Class nameVariable = Class.forName("package.nameClass");
        Constructor constructor = nameVariable.getConstructor();

Так можно получить информацию о конструкторе, у которого отсутствуют параметры. Метод getConstructor может иметь
параметры и эти параметры varargs. Если параметры не указывать в методе getConstructor, то это означает, что хотим
получить информацию о конструкторе, у которого отсутствуют параметры.

А какую информацию можно узнать о конструкторе?
1. Сколько параметров имеет определенный конструктор - getParameterCount;
2. Типы параметров конструктора - getParameterTypes (возвращает массив);
3. Имя конструкртора - getName();
4. Сколько есть конструкторов у класса;

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Constructor constructor = employeeClass.getConstructor();

            System.out.println("Constructor has: " + constructor.getParameterCount() +
                    ", parameters, their types are: " + Arrays.toString(constructor.getParameterTypes()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Constructor has: 0, parameters, their types are: []

Теперь "поговорим" о конструкторе с параметрами. В классе Employee есть конструктор, которыей принимает 3-и параметра
(int и 2-а String-a). В параметрах метода getConstructor необходимо прописать с какими типами хотим получить
конструктор.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Constructor constructor = employeeClass.getConstructor(int.class, String.class, String.class);

            System.out.println("Constructor has: " + constructor.getParameterCount() +
                    ", parameters, their types are: " + Arrays.toString(constructor.getParameterTypes()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Constructor has: 3, parameters, their types are: [int, class java.lang.String, class java.lang.String]

Теперь попробуем получить информацию о всех констркторах. И в этом нам поможет метод getConstructors. Метод
getConstructors возвращает массив типа Constructor.

Код:

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("Employee");
            Constructor[] allConstructors = employeeClass.getConstructors();

            for(Constructor constructor : allConstructors) {
                System.out.println("Constructor: " + constructor.getName() +
                        " has: " + constructor.getParameterCount() +
                        ", parameters, their types are: " + Arrays.toString(constructor.getParameterTypes()));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

Запуск программы. Вывод на экран:
Constructor: Employee has: 0, parameters, their types are: []
Constructor: Employee has: 4, parameters, their types are: [int, class java.lang.String, class java.lang.String, double]
Constructor: Employee has: 3, parameters, their types are: [int, class java.lang.String, class java.lang.String]

Естестевенно, конструкторы называются одинаково, т.к. это конструкторы одного и того же класса.

Есть еще метод getDeclaredConstructors. Его отличие от метода getConstructors в том, что он возвращает все конструкторы,
в том числе с access modifier private.
*/

public class ReflectionPart1 {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("reflection_example.Employee");
            Constructor[] allConstructors = employeeClass.getConstructors();

            for (Constructor constructor : allConstructors) {
                System.out.println("Constructor: " + constructor.getName() +
                        " has: " + constructor.getParameterCount() +
                        ", parameters, their types are: " + Arrays.toString(constructor.getParameterTypes()));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

//class Employee {
//    public int id;
//    public String name;
//    public String department;
//    private double salary;
//
//    public Employee() {
//    }
//
//    public Employee(int id, String name, String department) {
//        this.id = id;
//        this.name = name;
//        this.department = department;
//    }
//
//    public Employee(int id, String name, String department, double salary) {
//        this.id = id;
//        this.name = name;
//        this.department = department;
//        this.salary = salary;
//    }
//
//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }
//
//    private void changeDepartment(String newDepartment) {
//        department = newDepartment;
//        System.out.println("New department " + this + " employee: " + department);
//    }
//
//    public void increaseSalary() {
//        salary *= 2;
//    }
//
//    @Override
//    public String toString() {
//        return "Employee {" +
//                "id = " + id +
//                ", name = '" + name + '\'' +
//                ", department = '" + department + '\'' +
//                ", salary = " + salary +
//                '}';
//    }
//}