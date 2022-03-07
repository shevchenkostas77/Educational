package generics;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("Privet");
        list.add(6);
        list.add(new StringBuilder("ok"));
        list.add(new Car());

        for (Object o : list) {
            System.out.println(o + " length " + ((String)o).length()); // здесь ошибка! "ClassCastException"
//             на уровне компиляции ее не видно, но на этапе запуска она возникнет
//             это одна из двух причин существования дженериков
        }
    }
}

class Car {
}