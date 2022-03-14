package collection.map_interface;

/*
Интерфейс Map.
Элементами map являются пары: key and value. Название методов в map очень похожи на названия методов в
Collection, но map не состоит в этой иерархии.
HashMap имплементирует Map, LinkedHashMap является наследником HashMap.
TreeMap имплементирует интерфейс NavigableMap <- интерфейс SortedMap <- интерфейс Map.
HashTable имплементирует интерфейс Map.
HashMap не запоминает порядок добавления элементов. Его методы работают очень быстро!
Когда нет необходимости хранить пары key-value в отсортированном каком-то  виде и когда нужно очень быстро
получать элементы из коллекции, HashMap это правильный выбор!

Ключи элементов должны быть уникальными. Ключ может быть null.
Значения элементов могут повторяться. Значения могут быть null.

*/

import java.util.Map;
import java.util.HashMap;

public class HashMapExample {
    public static void main(String[] args) {
        Map<Integer, String> map1 = new HashMap<>();

//        Добавление элементов происходит с помощью метода put() (помещать);
        map1.put(1000, "Ivan");
        map1.put(4321, "Sergey");
        map1.put(2323, "Misha");
        map1.put(3214, "Olga");
        map1.put(54546534, "Olga"); // Значения одинаковы - так можно
        map1.put(1000, "OLEG"); // Ключи одинаковы - так тоже можно, но значение перезапишется (1000=OLEG)
        map1.put(null, "Igor"); // Так можно, будет null=Igor
        map1.put(200000, null); // Так можно, будет 200000=null
        System.out.println(map1);

//        Добавление элемента, если он осутствует в коллекции putIfAbsent()
        map1.putIfAbsent(1000, "Ivan"); // проверка идет по ключу, элемент не был добавлен

//        Получить значение можно по ключу с помощью метода get()
        System.out.println("Element by key \"1000\": " + map1.get(1000));
//        Если укажем ключ, которого нет в коллекции выведется null
        System.out.println("No key \"10001\": " + map1.get(10001));

//        Удаление элемента происходит ко ключу с помощью метода remove()
        map1.remove(4321);
        System.out.println(map1); // удалится 4321=Sergey из коллекции

//        Метод containsValue() возвращает boolean значение, если в коллекции содержится значение,
//        которое мы передаем в качестве параметра в метод
        System.out.println("Is \"Misha\" in the collection? " + map1.containsValue("Misha"));

//        Метод containsKey() возвращает boolean значение, если в коллекции содержится ключ,
//        который мы передаем в качестве параметра в метод
        System.out.println("Is the key \"2022\" in the collection? " + map1.containsKey(2022));

//        Метод keySet() возвращает множество ключей, которые есть в коллекции
        System.out.println("All keys in the collection " + map1.keySet());

//        Метод values() возвращает множестов значений, которые есть в коллекции
        System.out.println("All values in the collection " + map1.values());

//        Ключи и значения могут быть абсолютно разных типов и даже могут совпадать
        Map<String, String> map2 = new HashMap<>();
    }
}
