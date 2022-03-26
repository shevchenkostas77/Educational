package nested_classes;

/*
Inner class.
Используются, когда необходимо какой-то внутренний класс привязать к внешнему классу.
Если внутренний класс не статичный, то он является Inner class.

1. Каждый объект inner класса всегда ассоциируется с объектом внешнего класса;
2. Создавая объект inner класса, нужно перед этим создать объект его внешнего класса;
3. Inner класс может содержать static элементы (начиная с версии Java 16);
4. Inner класс может обращаться даже к private элементам внешнего класса;
5. Внешний класс может обращаться даже к private элементам inner класса, прежде
   создав его объект;
6. Inner классы могут наследовать классы, имплементировать интерфейсы.
   Как и static nested класс inner класс может быть final (не иметь наследников),
   быть abstract (невозможно создать объекты этого класса, его необходимо будет
   наследовать, перезаписать его абстрактные методы, если они есть и лишь потом
   создавать объекты-потомки)
7. Inner классы могут иметь и свои inner классы (но "большой матрешки" стоит избегать);
*/

/*
Вариант №1
Создание объекта внутреннего (inner) класса Engine при создании объекта внешнего
класса Cars.
*/
class Cars {
    String color;
    private int doorCount;
    Engine engine;

    public Cars(String color, int doorCount, int horsePower) {
        this.color = color;
        this.doorCount = doorCount;
        this.engine = this.new Engine(horsePower); // создается объект inner класса;
        /*
        В конструкторе класса Cars создастся объект inner класса (Engine) и
        присваивается переменной engine класса Cars.
        Синтаксис создания объекта inner класса, в данном случае:

        this.new Engine(horsePower);

        Слово "this" означает текущий объект класса Cars, после
        этого объекта ставится "." (точка), как бы показывая, что будет обращение
        к элементу текущего объекта класса Cars и тут же создается объект inner класса:

        new Engine(horsePower).

        В параметре конструктора inner класса указывается
        значение horsePower, которое задается в конструкторе класса Cars при
        создании объекта класса Cars.

        Таким образом в создании объекта inner класса всегда задействован объект
        внешнего класса.
        Inner класс всегда завязан на своем внешнем классе, т.е. для создания
        объекта inner класса, необходимо сначала создать объект внешнего класса,
        в отличие от static nested class, где создание внутреннего класса идет отдельно
        от внешнего.
        */
    }

    @Override
    public String toString() {
        return "Car = {" +
                "color = '" + color + '\'' +
                ", doorCount = " + doorCount +
                ", engine = " + engine +
                '}';
    }

    class Engine {
        int horsePower;
        public static int staticValue = 7; // начиная с версии Java 16 можно добавлять
                                           // в inner класс статические переменные.
        public Engine(int horsePower) {
            System.out.println("The inner class has access to the private elements " +
                    "of the outer class - " + doorCount); // private int doorCount;
            this.horsePower = horsePower;
        }

        public String toString() {
            return "{horsePower = " + horsePower +
                    '}';
        }
    }
}

/*
Вариант №2
Отдельное создание объекта внешнего класса Cars2 и отдельное создание объекта
внутреннего (Inner) класса Engine2.
*/
class Cars2 {
    String color;
    int doorCount;
    Engine2 engine2;

    public Cars2(String color, int doorCount) {
        this.color = color;
        this.doorCount = doorCount;

        /*
        В конструкторе внешнего класса Cars2 создается объект inner класса
        Engine2 и выводится на экран переменная inner класса Engine2 - horsePower.
        Это для того, чтобы показать, что внешний класс может обращаться даже к
        private элементам inner класса, прежде создав его объект;
        */
        Engine2 e = new Engine2(233);
        System.out.println(e);
        /*
        Т.к. написанный выше код находится в классе Cars2 нет необходимости писать, что
        класс Engine2 находится в классе Cars2 вот таким написанием:
        Cars2.Engine2 e = new Cars2.Engine2(233);
        Ошибкой не будет, но будет излишним.
        */
    }

    public void setEngine2(Engine2 engine2) {
        this.engine2 = engine2;
    }

    @Override
    public String toString() {
        return "Car = {" +
                "color = '" + color + '\'' +
                ", doorCount = " + doorCount +
                ", engine = " + engine2 +
                '}';
    }

    class Engine2 {
        int horsePower;

        public Engine2(int horsePower) {
            this.horsePower = horsePower;
        }

        public String toString() {
            return "{horsePower = " + horsePower +
                    '}';
        }
    }
}


public class InnerClass {
    public static void main(String[] args) {

        //Вариант №1
        Cars car = new Cars("Black", 4, 300);
        System.out.println(car);
        // Вывод:
        // Car = {color = 'Black', doorCount = 4, engine = {horsePower = 300}}


        // Вариант №2
        Cars2 car2 = new Cars2("Red", 2);
        /*
        После выполнения строки кода выше переменной класса Cars2 engine2
        присваивается значение null.
        */

        // Чтобы создать объект inner класса Engine2 нужен объект класса Car2.
        Cars2.Engine2 engine2 = car2.new Engine2(150);
        /*
        Cars2.Engine2 - показываем, где находится класс Engine2.
        После знака "=" (равно) указывается объект класса Car2, ставится "." (точка) и
        тут же создается объект inner класса:

        new Engine2(150)
        */
        System.out.println(car2);
        // Вывод:
        // Car = {color = 'Red', doorCount = 2, engine = null}
        System.out.println(engine2);
        // Вывод:
        // {horsePower = 150}

        // У объекта car2 переменная все еще engine = null, т.к. в данном случае
        // необходимо воспользоваться методом setEngine2().
        car2.setEngine2(engine2);
        System.out.println(car2);
        // Вывод:
        // Car = {color = 'Red', doorCount = 2, engine = {horsePower = 150}}

        /*
        Недопустимые (не сработают) варианты создания объекта Inner класса,
        в данном случае:
            1. Cars2.Engine2 engine2 = new cars2.Engine2(150);
               Оператор new должен относится к конструктору класса Engine2.
            2. Cars2.Engine2 engine2 = new Cars2.Engine2(150);
               Т.к. такое написание возможно если внутренний класс Engene2 не
               static nested class.
        */

        // Можно одновременно создать и внешний класс и внутренний (в варианте №2):
        Cars2.Engine2 engine3 = new Cars2("Yellow", 4).
                new Engine2(350);
        System.out.println(engine3);
        // Вывод:
        // {horsePower = 350}
        /*
        При таком варианте создания объектов внешнего и внутреннего классов теряется ссылка
        на объект Cars2, будет ссылка на объект класса Engine.
        */

        // Внутренний класс можно пометить access modifier private, он будет виден внутри
        // внешнего класса, но не виден за приделами внешнего класса.
    }
}