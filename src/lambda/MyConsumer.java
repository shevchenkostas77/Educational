package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
Consumer (с англ. "потребитель"). Находится в пакете java.util.function.
Он противоположен функциональному интерфейсу Supplier (c англ. "поставщик").
Функциональный интерфейс Consumer<T> имеет метод accept (c англ. "принимать").
Метод accept ничего не возвращает (возвращает void), а принимает какой-то объект типа "T".
Под потреблением объектов имеется ввиду, что необходимо что-то сделать с объектом,
как-то его использовать.

Выглядит функциональный интерфейс Consumer следующим образом:

    @FunctionalInterface // помечен аннотацией, что он функциональный;
    public interface Consumer<T> {
        void accept(T t); // имеет только один абстрактный метод и есть еще один default метод;
    }
*/

public class MyConsumer {

    /*
    Ниже создан метод changeCar, который будет менять объект, в данном случае
    объект типа Car. Как будет изменяться этот объект, будет прописано в
    параметре метода, при его вызове, в lambda выражении.
    */
    public static void changeCar(Car car, Consumer<Car> carConsumer) {
        carConsumer.accept(car);
		    /*
		    Что делает абстрактный метод accept функционального интерфейса Consumer
		    указано в lambda выражении при вызове метода changeCar.
		    */
    }

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
        {return new Car("Nissan Tiida", "Silver Metallic", 1.6);} );
        System.out.println("Our cars: " + ourCars);
        // Вывод:
        // Our cars: [Car = {model = 'Nissan Tiida', color = 'Silver Metallic', engine = 1.6},
        //          Car = {model = 'Nissan Tiida', color = 'Silver Metallic', engine = 1.6},
        //          Car = {model = 'Nissan Tiida', color = 'Silver Metallic', engine = 1.6}]

        /*
		Обычно же интерфейс Supplier не работает в одиночку, его используют
		вместе с интерфейсом Consumer. Эти интерфейсы (Supplier и Consumer) не улучшают
		эффективность кода, а используются лишь для его сокращения.
		*/

        changeCar(ourCars.get(0), (Car car) ->
        {car.color = "red";
            car.engine = 2.4;
            System.out.println("Upgraded car: " + car);
        });
        // Вывод:
        //Upgraded car: Car = {model = 'Nissan Tiida', color = 'red', engine = 2.4}

        System.out.println("Our cars: " + ourCars);
        // Вывод:
        // Our cars: [Car = {model = 'Nissan Tiida', color = 'red', engine = 2.4},
        //      Car = {model = 'Nissan Tiida', color = 'Silver Metallic', engine = 1.6},
        //      Car = {model = 'Nissan Tiida', color = 'Silver Metallic', engine = 1.6}]


		  /*
		  Generics сделали Java коллекций type safe (с англ. "типобезопасность").
		  В свою очередь lambda выражения вместе с функциональными интерфейсами
		  придали коллекциям "Суперсилу". Теперь множество классов и интерфейсов
		  коллекций содержат в себе методы, которые используют функциональные интерфейсы.
		  И теперь можно вызывать эти методы с помощью lambda выражений.
		  */

        List<String> list = List.of("privet", "kak dela?", "normalno", "poka");
		  /*
		  Если необходимо вывести на экран по одному все элементы коллекции list
		  можно использовать for-each loop:
    		  for(String s : list) {
    		      System.out.println(s);
    		  }
    	  Но это можно сделать по-другому, через default метод forEach функционального
    	  интерфейса Iterable.
    	  Метод forEach принимает в параметре Consumer, т.е. необходимо передать
    	  в его параметр, что нужно сделать для каждого элемента (Consumer - это
    	  потребитель).
		  */
        list.forEach((String str) -> {System.out.println(str);});
        //Вывод:
        // privet
        // privet
        // kak dela?
        // normalno
        // poka

        List<Integer> listInteger = new ArrayList<>();
        listInteger.add(1);
        listInteger.add(2);
        listInteger.add(3);
        listInteger.add(4);
        listInteger.add(5);

        listInteger.forEach((Integer el) -> {
            System.out.println("Element before = " + el);
            el *= 2;
            System.out.println("Element after = " + el);
        });
        System.out.println("listInteger = " + listInteger);
        // Вывод:
        // Element before = 1
        // Element after = 2
        // Element before = 2
        // Element after = 4
        // Element before = 3
        // Element after = 6
        // Element before = 4
        // Element after = 8
        // Element before = 5
        // Element after = 10
        // listInteger = [1, 2, 3, 4, 5]

        /*
        Вот так можно использовать метод forEach, который принимает Consumer.
        */
    }
}