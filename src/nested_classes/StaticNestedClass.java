package nested_classes;

class Car {     // outer class;
    /*
    Внутри можно создать статический внутренний класс.
    Внутренний класс будет статическим элементом внешнего класса Car.
    Внутренние классы нужны тогда, когда необходимо привязать какой-то класс
    к другому, сделать его элементом внешнего класса.
    Например, для класса Car можно созадать внутренний класс, к примеру, это
    будет мотор или же дверь, т.е. классы, которые напряму связаны с машиной.
    */

    String color;
    int doorCount;
    Engine engine;
    /*
    static nested класс может обращаться даже к private элементам внешнего класса,
    но только к static.
    Для примера, в конструкторе класса Engine выводим private переменную
    countOfCars класса Car на экран.
    */
    private static int countOfCars;

    public Car(String color, int doorCount, Engine engine) {
        this.color = color;
        this.doorCount = doorCount;
        this.engine = engine;
    }

    /*
    Внешний класс может обращаться к элементам static nested класс, адаже у
    которых access modifier - private. Ниже в методе method() это продемонстрированно.
    */
    void method() {
        // обращение к статической переменной внутреннего статического класса Engine;
        System.out.println(Engine.countOfObjects);

        /*
        Обращение к non-static переменной c access modifioer private внетреннего
        статического класса.
        Т.к. переменная не статическая необходимо для начала создать обькет класса.
        Engine e = new Engine(200);
        Создается обьект класса Engine без использования слова "Car", т.к. это
        обьект создается в самом классе Car. Можно написать следующим образом:
        Car.Engine e = new Car.Engine(200);
        Такая запись не будет ошибочной, но будет излищней.
        В то время как, за пределами класса Car необходимо писать слово "Car".
        */
        Engine e = new Engine(200);
        System.out.println(e.horsePower);
    }

    @Override
    public String toString() {
        return "My car: {" +
                "color = '" + color + '\'' +
                ", doorCount = " + doorCount +
                ", engine = " + engine +
                '}';
    }

    public static class Engine { // внутренний статический класс;
        /*
        static nested class может содержать static и non-static элементы (не все
        типы внутренних классов могут содержать static элеменеты):
        */
        private int horsePower; // non-static элемент;

        // static элемент (для подсчета кол-ва созданых моторов);
        static int countOfObjects;

        public Engine(int horsePower) {
            /*
            Возможно обращаться напрямую к статическим элементам внешнего класса
            из внутреннего, даже к элементам с access modifire private.
            Ниже пример обращения к переменной private static int countOfCars внешнего
            класса Car.
            */
            System.out.println("count of cars =" + countOfCars);

            /*
            НЕ возможно обратиться напрямую к non-static элементам внешнего класса из внутреннего
            Ниже в примере попытка вывести на экран переменную non-static int doorCount
            внешнего класса Car из внутреннего класса Engine.
            System.out.println(doorCount); // будет ошибка компилятора;
            */

            this.horsePower = horsePower;
            countOfObjects++;
        }

        public String toString() {
            return "Car engine: {" +
                    "horsePower = " + horsePower +
                    '}';
        }
    }
}

public class StaticNestedClass {
    public static void main(String[] args) {
        /*
        Engine e = new Engine(256);
        Создать обьект внутреннего класса Engine без полного адреса расположения
        класса таким способом не удастся.
        */

        /*
        Благодаря тому, что внутренний класс Engine явлется статичным, необходимо
        показать компилятору, внутри какого класса находится статичный класс,
        обькет которого мы хотим создать, вот такой записью "Car.Engine" (полный
        адрес расположения).
        */
        Car.Engine engine = new Car.Engine(256);

        System.out.println(engine);
        // Вывод:
        // Car engine: {horsePower = 256}

        // Создать обьект класса Car можно двумя вариантами:
        Car newCar = new Car("Black", 4, new Car.Engine(360));
        // 1. без отдельно созданного обьекта внутреннего статического класса Engine.

        Car car = new Car("Red", 2, engine);
        // 2. в параметр конструктора записываем переменную, которой был присвоен адрес
        // ранее созданного обьекта внутреннего статического класса Engine.

        System.out.println("newCar = " + newCar);
        System.out.println("car = " + car);
        // Вывод:
        // newCar = My car: {color = 'Black', doorCount = 4, engine = Car engine: {horsePower = 360}}
        // car = My car: {color = 'Red', doorCount = 2, engine = Car engine: {horsePower = 256}}

        /*
        Static nested class ведет себя как обычный внешний класс.

        Внутренний статический класс можно использовать как обычный внешний класс.
        Отличие в том, что необходимо указать в каком классе находится
        nested static class. Внутренний класс Engine отличается от любого внешнего
        класса - он физически находится во внутренем классе.
        Т.е. без создания обьекта класса Car, можно создать обьект класса Engine
        и можно проводить необходимые действия с обьектом класса Engine, к примеру,
        если будет класс Motorcycle, в классе Motorcycle можно использовать
        класс Engine. Стоит ли это делаит - это уже отдельный вопрос.

        Создавать обьект класса Engine можно только таким образом:
            1. Car car = new Car("Red", 2, engine);
            2. Car newCar = new Car("Black", 4, new Car.Engine(360));
        Невозможно создать обьект класса Engine используя готовый обькет
        класса Car, т.е. слудующий код не будет работать:

        Car myCar = new Car("Yellow", 2, engine);
        Car.Engine myEngine = new myCar.Engine(150);

        Если класс Engine пометить access modifire private, тогда невозможно
        будет создать обьект этого класса в другом классе.
        private static class Engine { // внутренний статический класс;
        ...
        }

        Static nested class можно сделать его final (данный класс не сможет иметь
        наследников).
        public final static class Engine { // внутренний статический класс;
        ...
        }

        Static nested class можно сделать его abstract (не возможно создать обьект
        этого класса). Его необходимо будет наследовать, перезаписать его абстрактне
        методы, если они есть и лишь потом создавать обькты потомки класса Engine.
        public abstract static class Engine { // внутренний статический класс;
        ...
        }

        Класс Engine может наследовать какой-то класс.
        public static class Engine extends NameClass { // внутренний статический класс;
        ...
        }

        class NameClass {
        ...
        }
        */
        // Класс Engine может быть наследован каким-то классом.
        class NameClass extends Car.Engine {
            public NameClass(){
                super(100);
            }
        }
    }
}


// Внутренними могуть быть не только классы, но и интерфейсы.
// Возможны 4-е комбинации:
//  1. Внутри класса можно создать класс;
class A {
    class B {
    }
}
// 2. Внутри класса можно создать интерфейс;
class C {
    interface D {
    }
}
// 3. Внутри интерефейса можно создать интрефейс;
interface E {
    interface F {
    }
}
// 4. Внутри интерфейса можно создать класс;
interface G {
    class H {
    }
}