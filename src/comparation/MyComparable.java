package comparation;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class MyComparable {
    public static void main(String[] args) {
        List <Employee> list = new ArrayList<>();
        Employee emp1 = new Employee(10, "Petr", "Ivanov", 1000);
        Employee emp2 = new Employee(20, "Ivan", "Sidorov", 200);
        Employee emp3 = new Employee(40, "Ivan", "Petrov", 3000);
        list.add(emp1);
        list.add(emp2);
        list.add(emp3);
        System.out.println("Before sorting: \n" + list + "\n");
        Collections.sort(list);
        System.out.println("After sorting: \n" + list);
    }

}

class Employee implements Comparable<Employee>{
    int id;
    String name;
    String surname;
    int salary;

    Employee(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = " + this.id +
                ", name = '" + this.name + '\'' +
                ", surname = '" + this.surname + '\'' +
                ", salary = " + this.salary +
                "}";
    }

    @Override
    public int compareTo(Employee emp) {
        int result = this.name.compareTo(emp.name);
        if (result == 0) {
            result =  this.surname.compareTo(emp.surname);
        }
        return result;
    }
}

