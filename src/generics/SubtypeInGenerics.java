package generics;

import java.util.List;
import java.util.ArrayList;

public class SubtypeInGenerics {
    public static void main(String[] args) {
//  При наследовании мы можем создавать обьекты Child и назначать его переменной типа Parent
        Parent Parent = new Child();
//  Теперь посмотрим можно ли с дженериками так
        List<Parent> list = new ArrayList<>();
/*  По умолчанию строка на 11 позиции превращается в строку
        List<Parent> list = new ArrayList<Parent>();
        Если вручную написать следующим образом
        List<Parent> list1 = new ArrayList<Child>();   <- код не сработает

    Код ниже не стработает
        List<Number> listNum = new ArrayList<Integer>();
        listNum.add(18);
        listNum.add(3.14);
    Generics помогают на этапе компиляции решать подобные проблемы.
    Если бы такой код сработал, то во время runtime все бы рухнуло, т.к. хоть и тип листа Number
    (Double является сабклассом его), но динамический то тип листа это ArrayList содержащий Integer,
    а Integer не может содержать Double.
*/
    }
}

class Parent {
}

class Child extends Parent {
}

//   Если нужно ограничить классы, которые могут подходить под тип Т пишется слово extends
class I<T extends Number> {
    private T value;  // после type erasure вместо Т везде подставляется Number

    I(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[This value: " + value + "]";
    }
}

/*
Следующий код не сработает
class GenMethod {
    public static <T> T getSecondElement(ArrayList<T extends Number> al) {   // "static" - здесь не обязательно, для удобства
        return al.get(1);
    }
}
Правильно писать следующим образом
*/
class Method {
    public static <T extends Number> T getSecondElement(ArrayList<T> al) {   // "static" - здесь не обязательно, для удобства
        return al.get(1);
    }
}

/* Какой то класс может наследоваться только от одного класса, но может имплементировать несколько интерфесов
при помощи знака "&"
 */
class NewI<T extends Number & I1 & I2> {
    private T value;  // после type erasure вместо Т везде подставляется Number

    NewI(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[This value: " + value + "]";
    }
}

interface I1 {
}

interface I2 {
}