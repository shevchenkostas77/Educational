package lambda;

import java.util.function.Supplier;
import java.util.List;
import java.util.ArrayList;

/*
Supplier (с англ. "поставщик"). Находится в пакете java.util.function.
Supplier<T> это простой functional interface, который представляет собой оператор,
предоставляющий значение для каждого вызова.
Supplier имеет только один метод T get() и не имеет метода по умолчанию.
Метод T get() не принимает никакой параметр, а возвращает объект типа "Т" (generics,
это может быть объект того типа, который необходим для работы).

Выглядит функциональный интерфейс Supplier следующим образом:

    @FunctionalInterface // помечен аннотацией, что он функциональный;
    public interface Supplier<T> {
        T get(); // имеет только один абстрактный метод, других методов вообще нет;
    }

Функциональный интерфейс Supplier поставляет объекты, когда его метод вызывается.
*/
public class MySupplier {
		/*
		Ниже создан метод createThreeCars, который создает три объекта Car,
		помещает эти три объекта в коллекцию ArrayList и возвращает этот ArrayList.
		При вызове метода createThreeCars в параметре этого метода в lambda выражении
		указано какие три объекты Car будут созданы.
		*/

    public static ArrayList<Car> createThreeCars(Supplier<Car> carSupplier) {
        List<Car> al = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            al.add(carSupplier.get());
		        /*
		        Что делает абстрактный метод get функционального интерфейса Supplier
		        указано в lambda выражении при вызове метода createThreeCars.
		        */
        }
        return (ArrayList) al; // Кастинг не времязатратная операция;
    }

    public static void main(String[] args) {
        ArrayList<Car> ourCars = createThreeCars( () ->
        {return new Car("Nissan Tiida", "Silver Metalic", 1.6);} );
        System.out.println("Our cars: " + ourCars);
        //  Вывод:
        //Our cars: [Car = {model = 'Nissan Tiida', color = 'Silver Metalic', engine = 1.6},
        //          Car = {model = 'Nissan Tiida', color = 'Silver Metalic', engine = 1.6},
        //          Car = {model = 'Nissan Tiida', color = 'Silver Metalic', engine = 1.6}]

		  /*
		  Обычно же интерфейс Supplier не работает в одиночку, его используют
		  вместе с интерфейсом Consumer. Эти интерфейсы не улучшают эффективность
		  кода, а используются лишь для его сокращения.
		  */
    }
}

class Car {
    String model;
    String color;
    double engine;

    public Car(String model, String color, double engine) {
        this.model = model;
        this.color = color;
        this.engine = engine;
    }

    public String toString() {
        return "Car = {" +
                "model = '" + model + '\'' +
                ", color = '" + color + '\'' +
                ", engine = " + engine +
                '}';
    }
}
