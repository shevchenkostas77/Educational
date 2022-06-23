package annotation_examples;

import java.lang.annotation.*;

/*
Аннотации - это специальные комментарии / метки / метаданные, которые нужны для передачи определенной информации.
Создадим класс Parent. У этого класса есть поле String name, есть конструктор, который принимает это поле name.
Например, есть метод showInfo без параметров. Есть класс с названием Child, который extends Parent класс. В этом классе
тоже есть конструктор, который вызывает конструктор суперкласса, т.е. конструктор класса Parent. И допустим, необходимо
переопределить метод showInfo из родительского класса в классе наследнике:

class Parent {
    String name;

    public Parent(String name) {
        this.name = name;
    }

    void showInfo() {
        System.out.println("It's Parent class. Name = " + name);
    }
}

class Child extends Parent {
    public Child(String name) {
        super(name);
    }
}

Можно написать следующим образом:

    void showInfo() {
        System.out.println("It's Child class. Name = " + name);
    }

В методе main создадим объект типа Parent:

        Parent p = new Child("Stas");

и на этом объекте вызовем метод showInfo:

        p.showInfo();

Запуск программы. Вывод на экран:
It's Child class. Name = Stas

Естественно Java runtime определит, что переменная "р" ссылается на объект класса Child и вызовется его метод showInfo.
В случаях, когда переопределяется метод в subclass-e ОЧЕНЬ рекомендуется использовать аннотацию "@Override":

    @Override
    void showInfo() {
        System.out.println("It's Child class. Name = " + name);
    }

Теперь, если допустить ошибку в переопределении метода showInfo, то компилятор начнет "ругаться" и будет "говорить", что
"что-то делается не верно при переопределении метода". Пока закомментируем эту аннотацию и посмотрим какие ошибки можно
допустить при переопределении метода. Естественно в данном примере класс Child и Parent находятся в одном месте и за
ошибками очень легко следить, но могут быть ситуации, когда уследить за ошибками просто не получается.
И все же, какие ошибки можно допустить при переопределении метода?
1. Можно допустить ошибку в имени метода:

    void showinfo() { // слово info написано в нижнем регистре в классе Child
        System.out.println("It's Child class. Name = " + name);
    }

При этом компилятор не возмущается, т.к. считает, что идет создания совершенно другого метода. Т.е. у класса Child есть
метод showInfo (доставшейся от Parent) и другой метод showinfo, т.к. называются по разному. А если использовать
аннотацию "@Override", то компилятор подскажет "Метод не переопределяет метод из суперкласса".

2. Можно ошибиться с параметрами при переопределении метода. Например, в Parent классе метод showInfo не принимает
никаких параметров, а в классе Child написать этот же метод с параметрами, то у Child класса снова будет два метода. Или
же перепутать местами список параметров.

3. Допустим, ВЧЕРА правильно переопределили метод, НО без использования аннотации "@Override". Сегодня другой
программист изменил название метода и об этом не сообщил. Никакой ошибки в классе Child, естественно, не выйдет, но
метод уже будет не переопределенный. Т.е. и в этом случае у класса Child будет два разных метода.

Рассмотрим следующую аннотацию - "@Deprecated" (с англ. устаревший).
Для примера "скажем", что метод showInfo стал устаревшим и использовать его больше не рекомендуется:

    @Deprecated
    void showInfo() {
        System.out.println("It's Parent class. Name = " + name);
    }

При попытке вызвать этот метод на объекте, то это метод отображается как перечеркнут, тем самым нам дается понять, что
метод устарел. Если использовать аннотацию "@Deprecated" в коде, то обязательно необходимо указывать какой метод
необходимо использовать вместо устаревшего и желательно причину, по которой метод устарел.

public class AnnotationExamples {
    public static void main(String[] args) {
        Parent p = new Child("Stas");
        p.showInfo();
    }
}

class Parent {
    String name;

    public Parent(String name) {
        this.name = name;
    }

    @Deprecated
    void showInfo() {
        System.out.println("It's Parent class. Name = " + name);
    }
}

class Child extends Parent {
    public Child(String name) {
        super(name);
    }

    @Override
    void showInfo() {
        System.out.println("It's Child class. Name = " + name);
    }
}

Теперь создадим свою аннотацию.
Для примера создадим класс Employee. У этого класса пусть будут поля: String name и double salary. Так же у этого класса
будет конструктор, который принимает и name и salary. Создадим метод increaseSalary, этот метод будет увеличивать salary
вдвое. И переопределим метод toString.

class Employee {
    String name;
    double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }


    public void increaseSalary() {
        salary *= 2;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

Вот такой простой класс. Давайте теперь создадим аннотацию.
Сначала пишется символ собачка - "@", далее слово "interface" и после название аннотации:

        @interface MyAnnotation {
        }

Как создается интерфейс, только вначале ставится символ собачки "@", что означает, что это аннотация, а не интерфейс.
Аннотация может в себе ничего не содержать и такую аннотацию можно использовать где угодно. Допустим, эту аннотацию
можно применить к классу Employee. Можно поставить аннотацию для какого-то поля, конструктора, для параметра
конструктора или метода, для метода, для локальных переменных и тд. и тп.:

@MyAnnotation // аннотация для всего класса
class Employee {

    @MyAnnotation // аннотация для поля name
    String name;
    double salary;

    @MyAnnotation // аннотация для конструктора
    public Employee(String name, @MyAnnotation double salary) { // аннотация для параметра конструктора
        this.name = name;
        this.salary = salary;
    }

    @MyAnnotation // аннотация для метода
    public void increaseSalary() {
        salary *= 2;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

Аннотация @Target показывает область кода, которой Аннотация может быть применима. Самые распространенные области кода:
- TYPE - class, interface, enum;
- FIELD - поле класса;
- METHOD - метод класса;
- PARAMETER - параметры метода или конструктора;

Быстрый пример: если попытаться использовать аннотацию "@Override", к примеру, для поля, то компилятор выдаст ошибку с
сообщением: "'@Override' not applicable to field", т.е. аннотация '@Override' не относится к полю. А аннотация
"@Deprecated" может быть применима к полю, т.к. поле может быть устаревшим.
Все это к чему? К тому, что необязательно аннотация должна работать со всеми элементами класса. А достигается это при
помощи другой аннотации, которая называется Target (c англ. цель). Т.е. при создании аннотации используется еще
аннотация "@Target". Аннотация "@Target" пишется эта перед "нашей" аннотацией и в скобках указывается область кода,
которой аннотация может быть применена (аннотация "@Target" принимает значение класса enum ElementType):

        @Target(ElementType.METHOD)
        @interface MyAnnotation {

        }

Target нужен импорт - import java.lang.annotation.Target;
ElementType (это enum) нужен импорт - import java.lang.annotation.ElementType;
Теперь аннотация @MyAnnotation может быть использована только для метода.
Аннотация "@Deprecated" прекрасно работает как с методами, так и с полями, т.е. в аннотации "@Target" можно указать не
только, что "наша" аннотация будет работать не только с одним элементом класса, но и с несколькими:

        @Target({ElementType.TYPE, ElementType.METHOD})
        @interface MyAnnotation {

        }

Перечисление значений происходит в фигурных скобках.

Аннотации могут использоваться абсолютно в разных целях: для создания документации кода, для передачи определенной
информации компилятору, для использования их во время runtime, т.е. во время обработки кода, в это время можно
обратиться к аннотации и исследовать ее с помощью рефлексии.

Есть еще одна аннотация на подобии аннотации "@Target" - "@Retention" (с англ. удержание). В аннотации "@Retention"
тоже необходимо вписывать значения. Эта аннотация описывает жизненный цикл аннотации, в ней содержится информация до
какого этапа программы будет видна аннотация.
Аннотация "@Retention" описывает жизненный цикл аннотации. Значения, которые можно вписать в аннотацию "@Retention"
(значения enum класса RetentionPolicy):
- SOURCE - Аннотация видна в source коде, отбрасывается компилятором и уже в byte коде не видна;
           Т.е. эти аннотации читаются компилятором, на их основе компилятор делает какую-то работу и затем, после
           компиляции Java файла (создания файл с расширением .class, т.е. файла с byte кодом) информация о
           данных аннотациях удаляется. Самый очевидный пример - это аннотация "@Override", т.е. аннотация "@Override"
           необходима, чтобы компилятор мог проверить на самом ли деле переопределяется метод суперкласса, если да, то
           все отлично, если нет, компилятор просто указывает, что "мы" этого не делаем, и все. Во время запуска
           программы эта аннотация абсолютно не нужна, она не выполняет никакую роль, поэтому она "не доживает" до этого
           момента. Получается, что нет необходимости хранить эту аннотацию в byte коде и о ней нет необходимости знать
           во время исполнения программы (runtime).
- CLASS - Аннотация видна в byte коде, обрабатывается JVM во время выполнения программы;
          Эти аннотации сохраняются после компиляции source кода, т.е. он есть в byte коде, но не сохраняются
          виртуальной машиной во время выполнения программы. Это default вариант аннотации, т.е. если не указать
          аннотацию "@Retention", то retention policy будет CLASS.
- RUNTIME - Аннотация видна во время выполнения программы;
            Эти аннотации сохраняются и после компиляции, и во время runtime выполнения кода. Поэтому, к данным
            аннотациям можно обратиться с помощью рефлексии. Это аннотации передают метаданные, которые могут быть
            использованы "нашим" приложением либо каким-то фреймворком, например Spring или Hibernate или какой-то
            другой фреймворк.

Retention нужен импорт - import java.lang.annotation.Retention;

        @Target({ElementType.TYPE, ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        @interface MyAnnotation {

        }

Теперь создадим пример, в котором будет использоваться аннотация с RetentionPolicy.RUNTIME и будем читать информацию из
этой аннотации с помощью reflection.
Создадим аннотацию и назовем ее SmartPhone:

        @interface SmartPhone {
        }

К каким элементам класса она может быть применима? При помощи вспомогательной аннотации "@Target" зададим, к примеру,
что данная аннотация может быть применима для всего класса, интерфейса и enum класса:

        @Target(ElementType.TYPE)
        @interface SmartPhone {
        }

При помощи вспомогательной аннотации "@Retention" зададим, что "наша" аннотация будет видна во время выполнения
программы, мы ведь в нашем примере будем читать информацию из этой аннотации с помощью рефлексии, а иначе аннотация
просто "не доживет" до выполнения программы.
Теперь внутри аннотации создадим элементы аннотации. Недаром при создании аннотации после знака собачки и перед именем
аннотации указывается слово interface, т.к. элементы аннотации выглядят, как абстрактные методы. Хочу создать для
аннотации SmartPhone элемент операционной системы, т.е. на какой операционной системе работает смартфон:

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        @interface SmartPhone {
            String OS ();
        }

Довольно таки странный синтаксис создания элементов аннотации, но какой есть. Хочу, что бы было поле "год создания
компании":

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        @interface SmartPhone {
            String OS();

            int yearOfCompanyCreation();
        }

Вот мы и создали "нашу" аннотацию, которая применима к классу.
Теперь создадим пару классов, которые будут аннотированы аннотацией SmartPhone.

        class Xiaomi {
            String model;
            double price;
        }

        class Iphone {
            String model;
            double price;
        }

Если необходимо аннотировать класс аннотацией SmartPhone, то это аннотация пишется над необходимым классом и указывается
обязательно значения аннотации SmartPhone (OS и yearOfCompanyCreation):

        @SmartPhone(OS = "Android", yearOfCompanyCreation = 2010)
        class Xiaomi {
            String model;
            double price;
        }

Вот так создали классы Xiaomi и Iphone, которые аннотированы аннотацией SmartPhone.
В самой аннотации можно прописывать дефолтные значения для элементов. Например, для элемента OS хочу указать дефолтное
значение - Android:

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        @interface SmartPhone {
            String OS() default "Android";

            int yearOfCompanyCreation();
        }

Когда указывается дефолтное значение, то это означает, что при использовании аннотации можно не задавать значение этим
элементам:

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        @interface SmartPhone {
            String OS() default "Android";

            int yearOfCompanyCreation() default 2010;
        }

        @SmartPhone
        class Xiaomi {
            String model;
            double price;
        }

        @SmartPhone(OS = "IOS", yearOfCompanyCreation = 1976)
        class Iphone {
            String model;
            double price;
        }

Теперь для класса Xiaomi можно не задавать значения потому, что есть дефолтные значение и они, в даном случае,
устраивают. Если указать значения, то они перезапишут дефолтные.

Теперь давайте прочитаем информацию из аннотации для Xiaomi и Iphone с помощью рефлексии.
 */


public class AnnotationExamples {
    public static void main(String[] args) throws ClassNotFoundException {
        Class xiaomiClass = Class.forName("annotation_examples.Xiaomi");
        Annotation annotation1 = xiaomiClass.getAnnotation(SmartPhone.class);
        SmartPhone sm1 = (SmartPhone) annotation1;
        System.out.println();

    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface SmartPhone {
    String OS() default "Android";

    int yearOfCompanyCreation() default 2010;
}

@SmartPhone
class Xiaomi {
    String model;
    double price;
}

@SmartPhone(OS = "IOS", yearOfCompanyCreation = 1976)
class Iphone {
    String model;
    double price;
}