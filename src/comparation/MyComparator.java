package comparation;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class MyComparator {
    public static void main(String[] args) {
        List <Worker> list = new ArrayList<>();
        Worker wor1 = new Worker(10, "Petr", "Ivanov", 1000);
        Worker wor2 = new Worker(20, "Ivan", "Sidorov", 200);
        Worker wor3= new Worker(40, "Ivan", "Petrov", 3000);
        list.add(wor1);
        list.add(wor2);
        list.add(wor3);
        System.out.println("Before sorting: \n" + list + "\n");
        Collections.sort(list, new SalaryComparator());
        System.out.println("After sorting: \n" + list);
    }

}

class Worker {
    int id;
    String name;
    String surname;
    int salary;

    Worker(int id, String name, String surname, int salary) {
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
}

class IdComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        if(wor1.id == wor2.id) {
            return 0;
        }
        else if(wor1.id < wor2.id) {
            return -1;
        }
        else {
            return 1;
        }
    }
}

class NameComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor2.name.compareTo(wor2.name);
    }
}

class SalaryComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor1.salary - wor2.salary;
    }
}

