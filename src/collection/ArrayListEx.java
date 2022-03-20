package collection;

/*
    ArrayList очень часто использованная коллекция.
    ArrayList еще называют resizeable массив, массив который может изменять свой размер.
    Используют ArrayList когда нужна структура похожа на массив, но где возможно удалять элементы,
    добавлять, изменять и тд. Простыми словами ArrayList – это массив, который может изменять свою длину.
    Варианты создания ArrayList:
      -	ArrayList<DataType> name = new ArrayList <DataType> ();
      -	ArrayList<DataType> name = new ArrayList <> ();
      -	ArrayList<DataType> name = new ArrayList <> (30);
      -	List<DataType> name = new ArrayList <DataType> (); List является интерфейсом, который имплементирует ArrayList
      -	ArrayList<DataType> name = new ArrayList <> (anotherArrayList) ArrayList созданный на основе другого ArrayList;
    В основе ArrayList лежит массив Object (элементами которого являются объяты типа Object)
    При создании ArrayList создается массив:
    capacity = 10 ;
    size = 0;
    Если известен заранее размер коллекции лучше указать это на этапе создания:
    ArrayList<DataType> name = new ArrayList<>(30) <- в скобках указан размер initialCapacity
    Тем самым java не будет тратить время на создание последовательного необходимого размера.
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListEx {
    public static void main(String[] args) {

        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add("Ivan");
        arrayList1.add("Sergey");
        arrayList1.add("Igor");
        System.out.println(arrayList1);


        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Ivan");
        arrayList2.add("Sergey");
        arrayList2.add("Igor");
        System.out.println(arrayList2);

        ArrayList<String> arrayList3 = new ArrayList<>(200);
        arrayList3.add("Ivan");
        arrayList3.add("Sergey");
        // Capacity arrayList3 = 200, size = 2
        System.out.println(arrayList3.size());

        List<String> arrayList4 = new ArrayList<>(arrayList3); // это новый объект с элементами, как у arrayList3
        System.out.println(arrayList3 == arrayList4);

        ArrayList arrayList5 = new ArrayList(); // если убрать generics в ArrayList можно будет добавть все что угодно
        arrayList5.add("Ivan");                 // т.к. в основе лежит массив Object, а Object  прародитель всего.
        arrayList5.add(7);
        arrayList5.add(new Car());
        System.out.println(arrayList5);
    }
}

class Car {
}