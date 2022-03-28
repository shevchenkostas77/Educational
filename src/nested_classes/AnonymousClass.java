package nested_classes;

/*
1. Anonymous класс не имеет имени;
2. Anonymous класс - это "объявление" класса и одновременное создание объекта;
3. В anonymous классах невозможно написать конструктор (при написании конструктора требуется
указывать имя класса, а у anonymous классах имени нет);
4. Anonymous класс может обращаться даже к private элементам внешнего класса;
5. Lambda expressions - это краткая форма для написания анонимных классов;
6. Понятие "Анонимные интерфейсы" - не существует;
*/

public class AnonymousClass {
    private int privateValueOuterClass = 5; // private элемент внешнего класса (может быть final)
    public static void main(String[] args) {
        /*
        Пример из LocalInnerClass2.

        class Addition implements Math3 {
            @Override
            public int doOperation(int a, int b) {
                return a + b;
            }
        }

        Addition addition = new Addition();
        System.out.println(addition.doOperation(5, 3));
        // Вывод:
        // 8
    }
        В Local inner class необходимо создать класс (Addition), имплементировать интерфейс (Math3),
        переопределить метод интерфейса и затем создать объект этого класса и уже после этого
        можно использовать метод класса.
        Тоже самое, но с использованием функционала анонимных классов:
         */
        Mathematics m = new Mathematics() {
            int c = 10;
            void abc() {
                System.out.println("This method \"abc\"");
            }

            @Override
            public int doOperation(int a, int b) {
                return a + b;
            }
        };
        System.out.println(m.doOperation(3, 6));
//        Вывод:
//        9

//        Пример с var
        var varM = new Mathematics() {
            int c = 10;
            void abc() {
                System.out.println("This method \"abc\"");
            }

            @Override
            public int doOperation(int a, int b) {
                return a + b;
            }
        };
        System.out.println(varM.c);
        varM.abc();
        System.out.println(varM.doOperation(3, 6));
//        Вывод:
//        10
//        This method "abc"
//        9


//        Можно в методе main создать еще один похожий анонимный класс
        Mathematics m2 = new Mathematics() {
            @Override
            public int doOperation(int a, int b) {
                return a * b;
            }
        };
        System.out.println(m2.doOperation(3, 6));
//        Вывод:
//        18

//        Пример №2
        Mathematics2 m3 = new Mathematics2() {
            @Override
            public int doOperation(int a, int b) {
                return a - b;
            }
        };
        System.out.println(m3.doOperation(3, 6));
//        Вывод:
//        -3

//        Пример обращение к private элементу внешнего класса
        Mathematics m4 = new Mathematics() {
            @Override
            public int doOperation(int a, int b) {
                // для доступа к privateValueOuterClass необходимо создать объект класса AnonymousClass
                // или объявить эту переменную static.
                AnonymousClass ac = new AnonymousClass();
                return a + b - ac.privateValueOuterClass;
            }
        };
        System.out.println(m4.doOperation(3, 6));
//        Вывод:
//        4

        /*
        В этой записи
        Mathematics m = new Mathematics()
        создается переменная "м" типа Mathematics, а Mathematics это интерфейс.
        Код
        new Mathematics()
        не создает объект интерфейса, т.к. это не возможно.
        После круглых скобок открываются фигурные скобки и пишется весь тот код, который необходимо
        было бы написать в классе имплементирующий интерфейс Mathematics.
        В данном случем происходит override метода doOperation().

        Mathematics m = new Mathematics() {
            @Override
            public int doOperation(int a, int b) {
                return a + b;
            }
        };

        Начиная от слова Mathematics, которое указывает тип переменной "m", и до символа ";" после
        закрывающей фигурной скобки - все это одно выражение, поэтому после закрывающей фигурной
        скобки ставится символ ";".

        Таким образом создается какой-то класс, а у класса нет имени, на то он и называется
        "анонимный" класс, который имплементирует интерфейс Mathematics (new Mathematics()) и
        override его единственный метод public int doOperation(int a, int b).
        Все это указывается в одном statement.
        Помимо override метода можно добавить весь необходимый код, к примеру, добавить
        переменную int "с" равную 10 или какой-то метод, к примеру, метод void abc() и тд.
        Все это будет содержимым анонимного класса, объектом которого является переменная "m"
        типа Mathematics. Эти переменные и методы можно создать для удобства, чтобы использовать
        внутри тела класса. Но чтобы вызвать переменные или методы анонимного класса, которых
        нет в наследуемом классе или имплементируемом интерфейсе, необходимо переменной, в данном случае,
        указать для переменной "m" тип "var". В примере с var это показано.

        Это все можно проделывать не только с интерфейсами, но и с классами тоже. В примере №2
        это показано. Анонимный класс extends класс Mathematics2, override его метод doOperation()
        и в итоге получится объект "m3".

        Когда полезно использовать анонимные классы?
        Написав следующий код:

        Mathematics m = new Mathematics() {
            @Override
            public int doOperation(int a, int b) {
                return a + b;
            }
        };

        воспользоваться этим анонимным классом возможно только один раз, т.е. не получится
        создать еще один объект этого анонимно класса. Анонимный класс используется, когда
        необходимо "на ходу" создать объект какого либо класса, который, к примеру, наследует
        какой-то класс и видоизменяет какой-либо его метод.
        Анонимный класс подходит под задачи, когда необходимо создать всего один объект
        какого-либо класса и изменить каки-либо его методы.
         */
    }
}

interface Mathematics {
    int doOperation(int a, int b);
}

class Mathematics2 {
    int doOperation(int a, int b) {
        return a / b;
    }
    String hello() {
        return "hello";
    }
//    С методом hello() класса Mathematics2 ничего не нужно делать, если он не пригодится
//    в анонимном классе.
}