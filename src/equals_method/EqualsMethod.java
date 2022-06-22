package equals_method;

import java.util.Objects;

/*
Метод equals принадлежит классу Object, а соответственно он имеется во всех классах в Java. Почему? Потому, что все
классы в Java унаследуются от класса Object.

Важные правила для override метода equals:
- если вы перезаписываете метод equals, всегда используйте в его параметре тип данных Object;
- правильно и логично перезаписанный метод equals должен обладать следующими свойствами:
    1. Симметричность - для non-null ссылочных переменных a и b, a.equals(b) возвращает true тогда и толко тогда,
       когда b.equals(a) возвращает true. Если a = b, то логично, что b = a;
    2. Рефлексивность - для non-null ссылочной переменной a, a.equals(a) всегда должно возвращать true. Т.е. объект
       равен сам себе всегда;
    3. Транзитивность - для non-null ссылочных переменных a, b и c, если a.equals(b) и b.equals(c) возвращает true,
       то a.equals(c) тоже должено возвращать true;
    4. Постоянство - для non-null ссылочных переменных a и b, неоднократный вызов a.equals(b) должен возвращать или
       только true, или только false. Т.е. при сравнении двух объектов они или равны друг дургу, или не равны;
    5. Для non-null ссылочно переменной a, a.equals(null) всегда должно возвращать false;

Создадим класс Car. В этом классе будет конструктор с двумя параметрами: String color и String engine и соответственно
есть в этом два поля color и engine:

class Car {
    String color;
    String engine;

    Car(String color, String engine) {
        this.color = color;
        this.engine = engine;
    }
}

В методе main создадим несколько объектов класса Car^

        Car c1 = new Car("red", "V4");
        Car c2 = new Car("red", "V4");
        Car c1 = new Car("black", "V8");

By default метод equals выглядит следующим образом:

        public boolean equals(Object obj) {
            return (this == obj);
        }

Т.е. идет проверка: ссылается ли переменная текущего объекта на объект, с которым идет сравнение? Если ссылается, то
такой метод equals вернет значекние true иначе false.
Перезапишем метод equals в классе Car.
*/

public class EqualsMethod {
    public static void main(String[] args) {
        Car c1 = new Car("red", "V4");
        Car c2 = new Car("red", "V4");
        Car c3 = new Car("black", "V8");
        System.out.println(c1.getClass());
    }
}

class Car {
    String color;
    String engine;

    Car(String color, String engine) {
        this.color = color;
        this.engine = engine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Проверить на равенство ссылки объектов this и параметра метода o.
        if (o == null || this.getClass() != o.getClass()) return false; // Проверить, определена ли ссылка o, т. е. является ли она null.
        Car car = (Car) o;
        return Objects.equals(color, car.color) && Objects.equals(engine, car.engine);
    }
}