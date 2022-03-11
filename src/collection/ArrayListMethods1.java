package collection;

import java.util.ArrayList;

public class ArrayListMethods1 {
    public static void main(String[] args) {
        ArrayList<String> arrayList1 = new ArrayList<>();
        /*
        Добавляет
        add(DataType element)  -> boolean
        add(int index, DataType element)  -> boolean
        */
        arrayList1.add("Ivan");
        arrayList1.add("Sergey");
        arrayList1.add("Igor");
        arrayList1.add(1, "Misha"); // происходит смещение элементов, было [Ivan, Sergey, Igor], стало [Ivan, Misha, Sergey, Igor]
        // arrayList1.add(6, "Glen"); // выброситься исключение IndexOutOfBoundsException: Index: 6, Size: 4
        System.out.println(arrayList1);
        for (String s : arrayList1) {
            System.out.print(s + " ");
        }
        /*
        Возвращает
        get(int index) -> DataType
        */
        for (int i = 0; i < arrayList1.size(); i++) {
            System.out.println(arrayList1.get(i));
        }

        /*
        Замещает на указанном индексе
        set(int index, DataType element) -> DataType
        */
        arrayList1.set(1, "Masha");
        System.out.println(arrayList1);

        /*
        Удаляет
        remove(Object element)  -> boolean (удаляет по элементы)
        remove(int index)  -> DataType (удаляет по индексу)
        */
        arrayList1.remove(2);
//        arrayList1.remove(10); // выбросится исключение IndexOutOfBoundsException: Index 10 out of bounds for length 3
        // remove(Object element) - обязательно в классе должен быть переопределен метод Equals, что бы метод сработал
    }
}