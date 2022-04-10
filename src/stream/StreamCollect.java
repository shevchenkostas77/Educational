package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Метод collect (terminal method) (с англ. собирать).
Используют его для того, чтобы поток преобразовать в List или Set или в другую какую-то коллекцию.
Когда используют этот класс, используют Collectors класс, у которого есть два метода:
1. gropingBy (с англ. группировать по);
2. partitioningBy (с англ. разделение по);
Методы Stream не меняют саму коллекцию или массив, от которой был создан stream.
*/

public class StreamCollect {
    public static void main(String[] args) {
        Student st1 = new Student("Ivan", 'm', 22, 3, 8.3);
        Student st2 = new Student("Nikolay", 'm', 28, 2, 6.4);
        Student st3 = new Student("Elena", 'f', 19, 1, 8.9);
        Student st4 = new Student("Petr", 'm', 35, 4, 7);
        Student st5 = new Student("Mariya", 'm', 23, 3, 7.4);

        List<Student> students = new ArrayList<>();

        students.add(st1);
        students.add(st2);
        students.add(st3);
        students.add(st4);
        students.add(st5);

        /*
        Задание-пример использование метода groupingBy класса Collectors.
        Необходимо изменить имена студентам на заглавные буквы и отсортировать по курсам. Т.е. для каждого курса
        будет свой список.
        */
        Map<Integer, List<Student>> result = students.stream()
                .map(element -> {
                    element.setName(element.getName().toUpperCase());
                    return element;
                })
                .collect(Collectors.groupingBy(Student::getCourse));

        for(Map.Entry<Integer, List<Student>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
//        Вывод:
//        1: [Student {name = 'ELENA', sex = f, age = 19, course = 1, average grade = 8.9}]
//        2: [Student {name = 'NIKOLAY', sex = m, age = 28, course = 2, average grade = 6.4}]
//        3: [Student {name = 'IVAN', sex = m, age = 22, course = 3, average grade = 8.3},
//              Student {name = 'MARIYA', sex = m, age = 23, course = 3, average grade = 7.4}]
//        4: [Student {name = 'PETR', sex = m, age = 35, course = 4, average grade = 7.0}]
        /*
        В методе groupingBy в параметре необходимо указать по какому атрибуту студента будет происходить группировка.
        В данном случае группировка происходит по курсу.
        Т.е. создались отдельные списки, которые содержат студентов с разных курсов (четыре списка).
        Метод collect возвращает объект типа Map<Integer, List<Student>, где Integer - ключ, List<Student> - значение.
        Т.е. это означает, что в коллекции Map - 4 элемента, а элемент представляет собой пару ключ-значение,
        ключ - номер курса, значение - список студентов.
         */

        System.out.println();

        /*
        Задание-пример использование метода partitioningBy класса Collectors.
        Необходимо студентов поделить на две группы:
        1. Те кто получает высокую оценку (больше 7 баллов);
        2. Те кто получает низкую оценку (меньше 7 баллов);
        */
        Map<Boolean, List<Student>> result2 =  students.stream()
                .collect(Collectors.partitioningBy(element -> element.getAverageGrade() > 7));

        for(Map.Entry<Boolean, List<Student>> entry: result2.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
//        Вывод:
//        false: [Student {name = 'NIKOLAY', sex = m, age = 28, course = 2, average grade = 6.4},
//                Student {name = 'PETR', sex = m, age = 35, course = 4, average grade = 7.0}]
//        true: [Student {name = 'IVAN', sex = m, age = 22, course = 3, average grade = 8.3},
//                Student {name = 'ELENA', sex = f, age = 19, course = 1, average grade = 8.9},
//                Student {name = 'MARIYA', sex = m, age = 23, course = 3, average grade = 7.4}]

        /*
        Примечание.
        Map-ы спокойно выполняется и без параметризации Map.Entry типами Boolean и List<Student> в цикле for-each
        for(Map.Entry entry: result2.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        НО, желательно, всегда использовать параметризацию, а не работать с сырыми типами.
         */

    }
}