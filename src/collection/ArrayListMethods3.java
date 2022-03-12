package collection;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ArrayListMethods3 {
    public static void main(String[] args) {
        // Методы ArrayList и связанные с ArrayList

        /*
        Arrays.asList(DataType[]) -> List<DataType>
        */
        StringBuilder sb1 = new StringBuilder("A");
        StringBuilder sb2 = new StringBuilder("B");
        StringBuilder sb3 = new StringBuilder("C");
        StringBuilder sb4 = new StringBuilder("D");
        StringBuilder[] array = {sb1, sb2, sb3, sb4};
        List<StringBuilder> list = Arrays.asList(array);
        /*
        List<StringBuilder> list - всегда будет той же длины, что StringBuilder[] array
        Длина его изменится не может, т.к. StringBuilder[] array - массив,
        List<StringBuilder> list полностью связан с array
        Если попытаемся изменить элемент массива или же заменить элемент, все это отразится в List
        */
        System.out.println(list);
        array[0].append("!!!");
        System.out.println(list);
        array[1] = new StringBuilder("Hello");
        System.out.println(list);

        /*
        Удаляются все элементы из вызывающего метод объекта, которые есть в объекте, переданном
        в качестве параметра в метод
        removeAll(Collection<?> c) -> boolean
        */
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Ivan");
        list1.add("Petr");
        list1.add("Mariya");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("Ivan");
        list2.add("Petr");
        list2.add("Igor");
        list1.removeAll(list2);
        System.out.println(list1);

        /*
        Оставляет все элементы, вызывающем метод, объекте, которые есть в объекте, переданном
        в качестве параметра в метод. Все остальные объекты удаляются (полная противоположность removeAll)
        retainAll(Collection<?> c) -> boolean
        */
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("Ivan");
        list3.add("Petr");
        list3.add("Mariya");

        ArrayList<String> list4 = new ArrayList<>();
        list4.add("Ivan");
        list4.add("Petr");
        list4.add("Igor");
        list3.retainAll(list4);
        System.out.println(list3);
        /*
        Проверяет содержит ли объект, вызывающий метод, все элементы,
        которые есть в объекте переданного в параметр метода (true, false)
        containsAll(Collection<?> c) -> boolean
        */
        boolean result = list3.containsAll(list4);
        System.out.println(result);
        /*
        Возвращает из имеющегося ArrayList подсписок
        subList(int fromIndex, int toIndex) -> List<E>
        */
        ArrayList<String> list5 = new ArrayList<>();
        list5.add("Ivan");
        list5.add("Ivan");
        list5.add("Petr");
        list5.add("Igor");
        list5.add("Mariya");
        List<String> myList = list5.subList(1,4); // myList является представлением list5,
        System.out.println("myList = " + myList); // myList не является отдельной сущьностью

        myList.add("Fedor!!!!");
        System.out.println("myList = " + myList);
        System.out.println("ArrayList list5 = " + list5);
        /*
        Ниже код выбросит исключение ConcurrentModificationException
        Все структурные модификации должны быть сделаны с помощью, в данном случае, myList
        Если мы делаем модификацию напрямую над нашим ArrayList и после пытаемся использовать
        представление, как в данном примере, - мы не получим нужный результат
        */
        // list5.add("Roman");
        // System.out.println("myList = " + myList);
        // System.out.println("ArrayList list5 = " + list5);

        /*
        Из ArrayList получаем массив типа Object
        toArray() -> Object
        Если хотим получить массив из конкретных объектов
        toArray(T [] a) -> T []
        */
        Object[] arrays = list5.toArray();
        String[] arrayString = list5.toArray(new String[0]);
        /*
        Указывать размер массива обязательно
        Если указать размер меньше, чем элементов в ArrayList - Java самостоятельно увеличит
        до необходимого размера
        Если указать размер больше, чем элементов в ArrayList - пустое место будет заполнено null
        Обычно все указываю 0.
        */
        for (String s : arrayString) {
            System.out.print(s + " ");
        }

/*
        Эти методы вышли после Java 8
        List.of(E ... elements) -> List<E>
        List.copyOf(Collection <E> c) -> List<E>
        На output List, который содержит элементы определенного типа
        !!! Полученные коллекции неизменяемы !!!
        */
        List<Integer> list6 = List.of(3, 16, 25); // быстрая версия создания List, но изменить не получиться
        System.out.println(list6);
        // list6.add(100); Если попробуем изменить, то выбросится исключение UnsupportedOperationException

        List<String> list7 = List.copyOf(list5);
        System.out.println(list7);
        // Помимо того, что эти лист невозможно модифицировать, они не могут содержать null

    }
}