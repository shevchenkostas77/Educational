package collection.set_interface;

/*
Интерфейс Set.

            Интерфейс Set
            /		    \
 Класс HashSet		    Интерфейс SortedSet
	   |				       |
Класс LinkedHashSet		Интерфейс NavigableSet
					           |
				          Класс TreeSet

HashSet имплементирует интерфейс Set. У HashSet есть наследник LinkedHashSet.
TreeSet имплементирует интерфейс NavigableSet <- интерфейс SortedSet <- интерфейс Set.

HashSet не запоминает порядок добавления элементов. В основе HashSet лежит HashMap.
У элементов данного HashMap: ключи – это элементы HashSet, значения – это константа-заглушка.

Set переводится как “множество”, а множество - это набор уникальных элементов. Другими словами,
Set - это коллекция уникальных элементов или коллекция, которая не позволяет хранить одинаковые элементы.
Методы данной коллекции работают очень быстро.
В основе любого Set лежит “урезанная” версия Map. Map хранит пары ключ-значение, но для Set пары не нужны.
Каждый элемент Set это одно какое-то значение. Поэтому, например, в основе HashSet находится объект HashMap,
который и хранит в качестве ключей значения для HashSet.
Немного поподробнее. Когда создается HashSet, в основе него создается HashMap. При добавлении в HashSet
любого значения, к примеру, это будет значение String - “Ivan”, значение “Ivan” в HashMap будет
соответствовать ключу (храниться как ключ). Для HashSet никакие значения не нужны, поэтому HashMap
(элемент HashMap не может быть создан без значения) будет хранить в качестве значений какую-то
константу-заглушку (HashSet на них не обращает внимания). Если еще добавить в HashSet значение,
к примеру, “Oleg”, в HashMap добавится “Oleg” как ключ, а в качестве значения та же самая константа-заглушка,
что и была добавлена к ключу “Ivan”. HashSet никак не использует константы-заглушки, для него главное ключи.
Так как в основе находится Map, Set может содержать значение null.

Если в Set будут храниться элементы по типу Student (самостоятельно написанные классы), то очень важно, как и в
случае с HashMap, чтобы в этих классах были переопределены методы hashCode() и equals(). Потому, что в основе
HashSet лежит HashMap.
*/

import java.util.HashSet;
import java.util.Set;

public class HashSetExample {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("Ivan");
//        Когда вызывается метод add() он внутренне вызывает метод put(), который принадлежит HashMap
        set.add("Oleg");
        set.add("Marina");
        set.add("Igor");

        System.out.println("Set = " + set);
//        Вывод:
//        Set = [Igor, Ivan, Oleg, Marina]
//        Не хранит элементы в том порядке, в котором были добавлены, как и HashMap

//        Для вывода элементов на экран можно использовать for-each loop
        for(String el : set) {
            System.out.println("Element: " + el);
        }
//        Вывод:
//        Element: Igor
//        Element: Ivan
//        Element: Oleg
//        Element: Marina

//        Попытка добавить дубликат
        set.add("Igor"); // еще одного Игоря
        System.out.println("Set = " + set);
//        Вывод:
//        Set = [Igor, Ivan, Oleg, Marina]
//        Еще один элемент "Igor" не добавился

//        Попытка добавить null
        set.add(null);
        System.out.println("Set = " + set);
//        Вывод:
//        Set = [null, Igor, Ivan, Oleg, Marina]
//        Т.к. HashMap, который лежит в основе HashSet, допускает значение null

//        Удаление происходит при помощи метода remove()
        set.remove(null);
        System.out.println("Set = " + set);
//        Вывод:
//        До:
//        Set = [null, Igor, Ivan, Oleg, Marina]
//        После:
//        Set = [Igor, Ivan, Oleg, Marina]

//        Получить количество элементов в коллекции можно при помощи метода size()
        System.out.println("Size: " + set.size());
//        Вывод:
//        Size: 4

//        Проверить пуста ли коллекция можно при помощи метода isEmpty()
        System.out.println("No elements in the collection? " + set.isEmpty());
//        Вывод:
//        No elements in the collection? false

//        Узнать есть ли определенный элемент в множестве можно при помощи метода contains()
        System.out.println("Is there an element \"Ivan\" in the collection? " + set.contains("Ivan"));
//        Вывод:
//        Is there an element "Ivan" in the collection? true

        /*
        У Set нет метода get(), т.к. в нем нет смысла. В HashMap можно было по ключу получить значение.
        В данном случаем элемент Set это не пара ключ-значение.
        */

//        Set - это множество. В математике есть несколько операций, которые можно проделывать со множествами
        HashSet<Integer> hashSet1 = new HashSet<>();
        hashSet1.add(5);
        hashSet1.add(2);
        hashSet1.add(3);
        hashSet1.add(1);
        hashSet1.add(8);

        HashSet<Integer> hashSet2 = new HashSet<>();
        hashSet2.add(7);
        hashSet2.add(4);
        hashSet2.add(3);
        hashSet2.add(5);
        hashSet2.add(8);

//        Первая операция это Union - обледенение, что бы произвести эту операцию необходимо
//        воспользоваться методом addAll()
        HashSet<Integer> union = new HashSet<>(hashSet1); // union стал такой же, как и hashSet1
        union.addAll(hashSet2);
        System.out.println("Union of two sets = " + union);
//        Вывод:
//        Union of two sets = [1, 2, 3, 4, 5, 7, 8]

//        Следующая операция Intersect - пересечение (общие элементы), что бы произвести эту операцию
//        необходимо воспользоваться методом retainAll()
        HashSet<Integer> intersect = new HashSet<>(hashSet1); // intersect стал такой же, как и hashSet1
        intersect.retainAll(hashSet2);
        System.out.println("Intersection of two sets = " + intersect);
//        Вывод:
//        Intersection of two sets = [3, 5, 8]
//        Остаются из первого множества только те элементы, которые есть во втором множестве, т.е. их
//        пересечения

//        Следующая операция Subtract - разность, за эту операцию отвечает метод removeAll();
        HashSet<Integer> subtract = new HashSet<>(hashSet1); // subtract стал такой же, как и hashSet1
        subtract.removeAll(hashSet2);
        System.out.println("Difference of two sets = " + subtract);
//        Вывод:
//        Difference of two sets = [1, 2]
//        Остаются только те элементы в первом множестве, что не входят во второе множество

//        Методы addAll(), retainAll(), removeAll() есть у любого Set
    }
}