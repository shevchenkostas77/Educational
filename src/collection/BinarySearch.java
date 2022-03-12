package collection;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(-3);
        list.add(8);
        list.add(12);
        list.add(-8);
        list.add(0);
        list.add(5);
        list.add(10);
        list.add(1);
        list.add(150);
        list.add(-30);
        list.add(19);
        int index = Collections.binarySearch(list, 12);
        System.out.println("List before sorting " + list);
        System.out.println("Not correct index position: " + index); // -9
        // Если в результате получаем отрицательное значение - искомый элемент не найден
        System.out.println();
        /*
        Для использования binary search обязательно коллекция должна быть отсортирована,
        если коллекция не отсортирована, бинарный поиск сработает неправильно и результат будет ложным;
        */

        Collections.sort(list);
        int index2 = Collections.binarySearch(list, 12);
        System.out.println("List after sorting " + list);
        System.out.println("Correct index position: " + index2);

        System.out.println();

//        Сортировка с конца (обратная сортировка)
        Collections.reverse(list);
        System.out.println("Reverse sorting list " + list);
        System.out.println();

//        Cортировка в случайном порядке
        Collections.shuffle(list);
        System.out.println("Shuffle sorting list " + list);
        System.out.println();

        Employee emp1 = new Employee(100, "Ivan", 12_000);
        Employee emp2 = new Employee(12, "Sergey", 22_000);
        Employee emp3 = new Employee(10, "Olga", 2_000);
        Employee emp4 = new Employee(1, "Georg", 44_000);
        Employee emp5 = new Employee(1, "Georg", 67_000);
        Employee emp6 = new Employee(14, "Mariya", 1_000);
        Employee emp7 = new Employee(14, "Ivan", 19_000);

        List<Employee> listEmployee = new ArrayList<>();
        listEmployee.add(emp1);
        listEmployee.add(emp2);
        listEmployee.add(emp3);
        listEmployee.add(emp4);
        listEmployee.add(emp5);
        listEmployee.add(emp6);
        listEmployee.add(emp7);

        System.out.println("listEmployee before sorting " + listEmployee);
        Collections.sort(listEmployee);
        System.out.println("listEmployee after sorting " + listEmployee);
        System.out.println();

        int indexSearch = Collections.binarySearch(listEmployee,
                new Employee(1, "Georg", 67_000)); // поиск (сравнение) идет по compareTo
        System.out.println(emp5 + " have an index position: " + indexSearch);
        System.out.println();

        // binary search есть такой метод у Arrays, работает по такому же принципу, но с массивами;
        int[] array = {-3, 8, 12, -8, 0, 5, 10, 1, 150, -30, 19};
        System.out.println("Array before sorting " + Arrays.toString(array));
        Arrays.sort(array);
        System.out.println("Array after sorting " + Arrays.toString(array));
        int indexArray = Arrays.binarySearch(array, 150);
        System.out.println("The element '150' have an index position: " + indexArray);

    }
}

class Employee implements Comparable<Employee>{
    int id;
    String name;
    int salary;

    public Employee(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" + "id = " + id +
                ", name = " + name +
                ", salary = " + salary + "}";
    }

    @Override
    public int compareTo(Employee anotherEmp){
        int result = this.id - anotherEmp.id;
        if (result == 0) {
            result = this.name.compareTo(anotherEmp.name);
            if (result == 0) {
                result = this.salary - anotherEmp.salary;
            }
        }
        return result;

    }
}