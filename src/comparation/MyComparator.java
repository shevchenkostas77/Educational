package comparation;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/*
Отметим следующий момент: КОМПИЛЯТОР ПОКАЗАЛ, что не возможно отсортировать коллекцию с объектами класса Employee,
Сравнивать работников будем по id, как в первом способе с использованием интерфейса Comparable:

@Override
    public int compareTo(Employee anotherEmp) {
        if (this.id == anotherEmp.id) {
            return 0;
        } else if (this.id < anotherEmp.id) {
            return -1;
        } else {
            return 1;
        }
    }

только в этом примере будет уже использован интерфейс Comparator. Создадим клас IdComparator, который будет
имплементировать интерфейс Comparator. В этом случае тоже нужно будет override всего один метод, но название метода
другое - compare. Этот метод имеет ту же логику, что и метод сompareTo. Отличие этих методов в том, что метод
compareTo сравнивал атрибуты текущего объекта с объектом в параметрах, а используя метод compare текущего объекта нет,
в параметры метода передаются объекты для сравнения.

Код:

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
    public int compare(Employee emp1, Employee emp2) {
        if (emp1.id == emp2.id) {
            return 0
        } else if (emp1.id < emp2.id) {
            return -1;
        } else {
            return 1;
        }
    }
}

И теперь, когда идет попытка с помощь этой строчки кода:

        Collections.sort(list);

отсортировать коллекцию "list" компилятор начинает ругаться. Компилятор не знает как сортировать. Решается эта
проблема двумя способами:
1. Сделать, чтобы класс имплементировал интерфейс Comparable и Override метод compareTo (рассмотрено в файле
   "MyComparable");
2. Вторым параметром в метод sort передать объект класса, который имплементирует интерфейс Comparator с
   переопределенным методом compare;

Второй спомоб и будет рассмотрен. Т.е. "мы говорим" сортируй используя этот Comparator:

        Collections.sort(list, new IdComparator());

и теперь сортировка будет происходить по id.

Код:

public class Main { // MyComparator
    public static void main(String[] args) {

        List <Worker> list = new ArrayList<>();

        Worker wor1 = new Worker(10, "Petr", "Ivanov", 1000);
        Worker wor2 = new Worker(20, "Ivan", "Sidorov", 200);
        Worker wor3= new Worker(40, "Ivan", "Petrov", 3000);

        list.add(wor1);
        list.add(wor2);
        list.add(wor3);

        System.out.println("Before sorting: \n" + list + "\n");

        Collections.sort(list, new IdComparator());

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
        return "Worker {" +
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
        if (wor1.id == wor2.id) {
            return 0;
        } else if (wor1.id < wor2.id) {
            return -1;
        } else {
            return 1;
        }
    }
}

Запуск программы. Вывод на экран:
Before sorting:
[Worker {id = 10, name = 'Petr', surname = 'Ivanov', salary = 1000},
    Worker {id = 20, name = 'Ivan', surname = 'Sidorov', salary = 200},
    Worker {id = 40, name = 'Ivan', surname = 'Petrov', salary = 3000}]

After sorting:
[Worker {id = 10, name = 'Petr', surname = 'Ivanov', salary = 1000},
    Worker {id = 20, name = 'Ivan', surname = 'Sidorov', salary = 200},
    Worker {id = 40, name = 'Ivan', surname = 'Petrov', salary = 3000}]

Сортировка прошла как и ожидалась.

Создадим еще несколько классов, которые будут имплементировать интерфейс Comparator:

class NameComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor1.name.compareTo(wor2.name);
    }
}

class SalaryComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor1.salary - wor2.salary;
    }
}

Теперь совместим Comparator и Comparable. Удалим класс IdComparator и сделаем, чтобы класс Worker
имплементировал интерфейс Comparable, так же перезапишем метода comparaTo, где будет указана логика
сортировки по id работников.

Код:

class Worker implements Comparable<Worker> {
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
        return "Worker {" +
                "id = " + this.id +
                ", name = '" + this.name + '\'' +
                ", surname = '" + this.surname + '\'' +
                ", salary = " + this.salary +
                "}";
    }

    @Override
    public int compareTo(Worker anotherWorker) {
        if (this.id == anotherWorker.id) {
            return 0;
        } else if (this.id < anotherWorker.id) {
            return -1;
        } else {
            return 1;
        }
    }
}

class NameComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor1.name.compareTo(wor2.name);
    }
}

class SalaryComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor1.salary - wor2.salary;
    }
}

Так наиболее эфективно. Эфективно в том случае, если чаще всего нужно сравнивать объекты класса Worker по id, а
по фамилиям или же по зарплате время от времени. В таком случае логигу сравнения по id лучше всего прописать в методе
compareTo, а логику сравнения по фималиям или зарплате прописать в методе copmare в отдельных классах, которые
имплементируют интерфейс Comparable.

Т.е. для чего так делается?
Для удобстава, не будет же программист каждый раз менять код метода compareTo для необходимого сравнения объектов. Да
и в некоторых ситуациях доступ к методу compareTo просто закрыт. Для изменения правила сортировики необходимо добавить
вторым параметром в метод sort нужное "правило" (объект класса имплементирующий интерфейс Comparable). И когда
указывается в методе sort второй параметр, то "правило", которое описано в методе compareTo перезатирается "правилом",
которое указывается вторым параметром в методе sort.

Когда не обойтись без интерфейса Comparator?
Есть такой замечательный класс String, он имплементирует интерфейс Comparable. В методе compareTo описано как нужно
сотрировать объекты типа String. Невозможно же перезаписать метод compareTo по своему, если к примеру, необходима
сортировка объектов типа String наоборот, верно же? Нет инного выхода, как написать свой Comparator и при сортировке
использовать этот Comparator, как второй папаметр метода sort.
Еще хочется отметить, если необходимо использовать объект компаратора всего один раз, то можно не создавать класс
отдельно, а воспоьзоваться анонимным классом (указать во втором параметре) или же использоват lambda выражение.

Интерфейс Comparator используется для сравнения объектов, используя НЕ естественный порядок.

Если при создании класса не понятно по какой логике будет происходить сортирвка объектов данного класса, то не нужно
перезаписывать метод compareTo (не нужно делать имплементацию функциональногь интерфейса Comparable). В будущем, когда
будет понимание как все же требуется сортировать объекты данного класса, тогда можно будет создать класс Comparator и
уже в методе compare прописать всю логику.

Comparable - основной механизм сравнения.
Comparator - более редкая логика сравнения.

Очень рекомендуется, но не является обязательным факт того, что если в compareTo методе два объекта возращают "0", т.е.
при сравнении они равны, то для этих двух объектов метода equals тоже возвращал бы true. Логино, что при сравнении, если
два объекта считаются одинаковыми, то и equals тоже должен возвращать true.

*/

public class Main { // MyComparator
    public static void main(String[] args) {

        List <Worker> list = new ArrayList<>();

        Worker wor1 = new Worker(20, "Petr", "Ivanov", 1000);
        Worker wor2 = new Worker(10, "Ivan", "Sidorov", 200);
        Worker wor3= new Worker(40, "Ivan", "Petrov", 3000);

        list.add(wor1);
        list.add(wor2);
        list.add(wor3);

        System.out.println("Before sorting: \n" + list + "\n");

        // Collections.sort(list, new Comparator<Worker>() {
        //             @Override
        //             public int compare(Worker wor1, Worker wor2) {
        //                 if (wor1.id == wor2.id) {
        //                         return 0;
        //                     } else if (wor1.id < wor2.id) {
        //                         return -1;
        //                     } else {
        //                         return 1;
        //                     }
        //             }
        //     });

        Collections.sort(list, (w1, w2) -> {
            if (w1.id == w2.id) {
                return 0;
            } else if (w1.id < w2.id) {
                return -1;
            } else {
                return 1;
            }
        });

        System.out.println("After sorting: \n" + list);
    }

}

class Worker implements Comparable<Worker> {
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
        return "Worker {" +
                "id = " + this.id +
                ", name = '" + this.name + '\'' +
                ", surname = '" + this.surname + '\'' +
                ", salary = " + this.salary +
                "}";
    }

    @Override
    public int compareTo(Worker anotherWorker) {
        if (this.id == anotherWorker.id) {
            return 0;
        } else if (this.id < anotherWorker.id) {
            return -1;
        } else {
            return 1;
        }
    }
}

// class IdComparator implements Comparator<Worker> {

//     @Override
//     public int compare(Worker wor1, Worker wor2) {
//         if (wor1.id == wor2.id) {
//             return 0;
//         } else if (wor1.id < wor2.id) {
//             return -1;
//         } else {
//             return 1;
//         }
//     }
// }

class NameComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor1.name.compareTo(wor2.name);
    }
}

class SalaryComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker wor1, Worker wor2) {
        return wor1.salary - wor2.salary;
    }
}