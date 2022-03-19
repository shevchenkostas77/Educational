package collection.map_interface;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

/*
Переопределение методов equals и hashCode корректным путем очень важно,
когда работа идет с коллекциями особенно с теми коллекциями, которые начинаются на Hash (HashMap и HashSet).
Есть правила в Java, которые следует соблюдать:
•	Если вы переопределили equals, то необходимо переопределить и hashCode.
    Это правило не регулируется, т.е. не будет компиляционной ошибкой или runtime,
    но в дальнейшем, если не учесть это правило, может сыграть не на пользу
    (будет неправильно работать в определенных моментах программа).
    Может случиться, что hashCode какое-то время будет не нужен, но если программа разрастется и потом,
    вдруг станет необходимо использоваться hashCode и он будет не переопределён,
    то в программе будет много неприятных неожиданностей и ошибок;
•	Результат нескольких выполнений метода hashCode() для одного и того же объекта должен быт одинаковым;
•	Если согласно методу equals, два объекта РАВНЫ, то и hashcode данных объектов обязательно
    должен быть одинаковым;
•	Если согласно методу equals, два обьекта НЕ равны, то hashcode данных объектов
    НЕ обязательно должен быть разным.
•   Ситуация, когда результат метода hashCode для разных объектов одинаков, называется коллизией.
    Чем ее меньше, тем лучше.
*/

public class HashCodeExample {
    public static void main(String[] args) {
        Student st1 = new Student("Ivan", "Sidorov", 3);
        Student st2 = new Student("Petr", "Ivanov", 1);
        Student st3 = new Student("Mariya", "Petrova", 4);

        Map<Student, Double> map = new HashMap<>();
        map.put(st1, 7.5);
        map.put(st2, 8.7);
        map.put(st3, 9.2);

        System.out.println(map);

        Student st4 = new Student("Ivan", "Sidorov", 3);
        boolean result = map.containsKey(st4);
        System.out.println("Student on the list? " + result); // false, без переопределенного метода hashCode()
        System.out.println("st1 equals st4? " + st1.equals(st4)); // true
        /*
        В чем же дело, что Student on the list? - false, а st1 equals st4? - true ?
        Дело в том, что некоторые коллекции используют хеширование при поиске и сравнении.
        HashMap и HashSet относятся к таким коллекциям, что видно по названию.
        Таким образом даже если мы ищем такой же объект, метод equals которого true, как в данном случае,
        все равно, если не будет переопределен (правильно!) метод hashCode - сравнение будет неудачным!
        Что такое хеширование в Java?
        Это преобразование любого объекта в int.
        Т.е. поступает на вход объект, а на выходе число типа int.

        Если в классе переопределен метод hashCode(), но не переопределен метод equals(),
        результат, так же, будет не правильным. Сравнение в HashMap происходит сначала по hashCode,
        а если hashсode у объектов одинаков, тогда идет сравнение по equals. Метод hashCode()
        работает намного быстрее метода equals().

        *У класса "String", "Double" (те классы, которые мы не пишем вручную) методы hashCode и
        методы equals правильно переопределены.

        Почти все операции в HashMap ведуться по ключам. Поэтому переопределять методы hashCode и equals,
        в первую очередь, необходимо у ключей.
        */

        System.out.println("What is the hashcode of the object \"st1\"? " +
                st1.hashCode()); // До переопределения метода – 992136656 (уникальный адрес),
        // после своей реализации*** - 264
        System.out.println("What is the hashcode of the object \"st2\"? " +
                st2.hashCode()); // До переопределения метода – 511833308 (уникальный адрес),
        // после своей реализации*** - 147
        /*
        У одинаковых объектов должен быть одинаковый hashcode!!!
        Дефолтная реализация hashCode в Object такова, что в алгоритме используется уникальный адрес объекта, т.е.
        у каждого новосозданного объекта будет уникальный hashcode. Это означает, что если два объекта
        по equals равны то, при дефолтной реализации метод hashCode() сравнение объектов будет работать уже неправильно, поэтому необходимо обязательное переопределение метода.
        */

    }
}

final class Student implements Comparable<Student> {
    final private String name;
    final private String surname;
    final private int course;

    public Student(String name, String surname, int course) {
        this.name = name;
        this.surname = surname;
        this.course = course;
    }

    public String getName(){
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Students {" +
                "name = '" + name + "\'," +
                " surname = '" + surname + "\'," +
                " course = " + course +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return course == student.course &&
                name == student.name &&
                surname == student.surname;
    }

    @Override
    public int compareTo(Student anotherStudent) {
        int result = this.name.compareTo(anotherStudent.name);
        if (result == 0) {
            result = this.surname.compareTo(anotherStudent.surname);
        }
        if (result == 0) {
            result = this.course - anotherStudent.course;
        }
        return result;
    }

    /*
    Когда у разных объектов возвращается одинаковый hashcode - это называется коллизией.
    Чем меньше коллизии в hashcode, тем лучше.
    Обычно в hashCode какие-то значения умножаются на простые числа (число делиться только на себя и на единицу),
    чтобы коллизии было меньше.
    */

    // // ***
    // @Override
    // public int hashCode(){
    //     return name.length() * 7 + surname.length() * 11 + course * 53;

    // }

    // Лучше использовать вариант переопределения метода hashCode,
    // который предлагает Intellij IDEA.
    @Override
    public int hashCode() {
        return Objects.hash(name, surname, course);
    }
}

