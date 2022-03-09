package generics;

import java.util.ArrayList;
import java.util.List;

public class Wildcards {
    public static void main(String[] args) {
//    Символ "?" - является Wildcards
        List<?> list = new ArrayList<Integer>();
//    List<?> - является супер типом для любого ArrayList
//    Вместо "?" может быть подставелн любой класс
        List<Double> list1 = new ArrayList<>();
        list1.add(3.14);
        list1.add(3.15);
        list1.add(3.16);
        showListInfo(list1);
        System.out.println(showListInfo2(list1));

        List<String> list2 = new ArrayList<>();
        list2.add("ok");
        list2.add("hello");
        list2.add("bye");
        showListInfo(list2);
        System.out.println(showListInfo2(list2));

        List<Double> alD = new ArrayList<>();
        alD.add(3.14);
        alD.add(3.15);
        alD.add(3.16);
        System.out.println("Sum = " + summ(alD));
    }

    static void showListInfo(List<?> list) {
        System.out.println("My list has next element: " + list);
    }

    static <T> T showListInfo2(List<T> list) {
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

/*
    Eсли понадобится указать в return type метода тип данных, который содержит лист,
    нужно будет работать с <T>. Разницы в написанном кода ниже нет:

    public static void showListInfo(List<?> list) {
        System.out.println("My list has next element:" + list);
    }

    public static <T> void showListInfo(List<T> list) {
        System.out.println("My list has next element:" + list);
    }
*/

/*
    Применение wildcards имеет смысл в качестве аргумента(параметра) метода,
    чтобы принимать входящую коллекцию любого типа, т.к. создание самой коллекции с wildcard не имеет смысла.
    К примеру, нет возможности добавить элементы (изменить обьект). Следующий код выдаст ошибку на этапе компиляции:
    List<?> list = new ArrayList<Sthing>();
    list.add("Hello"); - тут ошибка

    Разница в написании List<?> и List:
    List может содержать элементы разных типов данных. Это raw type и его использовать не рекомендуется.
    List<?> может содержать элементы только одного типа данных, но какого именно - можем и не знать.

    Разница в написании List <? extends Number> list = new ArrayList<Integer>(); и
                        List <Number> list = new ArrayList<Integer>();
    List <Number> list = new ArrayList<Integer>(); писать нельзя так как Integer - это не одно и то же,
    что и Number.
    List <? extends Number> list30 = new ArrayList<Integer>(); писать можно так как Integer - это то же,
    что и ? extends Number. Это механизм, с помощью которого вы ограничиваете разновидность списка.

    Bounded wildcards:
    <? super Number> - это значит, что можно работать с классом Number и выше него, которые классы;
    <? extends Number> - это значит, что можно работать с классом Number и ниже него;
*/
    public static double summ(List<? extends Number> al) { // возвращаемый тип double,
                                                            // т.к. Intenger,Long поместятся в double
        double summ = 0;
        for (Number n : al) {
            summ += n.doubleValue();
        }
        return summ;
    }
}
