package generics.example;

public class Play {
    public static void main(String[] args) {
        Schoolchild schoolchild1 = new Schoolchild("Ivan", 12);
        Schoolchild schoolchild2 = new Schoolchild("Mariya", 14);

        Schoolchild schoolchild3 = new Schoolchild("Sergey", 14);
        Schoolchild schoolchild4 = new Schoolchild("Olya", 13);

        Student student1 = new Student("Nikolay", 20);
        Student student2 = new Student("Ksenuya", 20);

        Employee employee1 = new Employee("Jack", 30);
        Employee employee2 = new Employee("Piter", 28);

        Team<Schoolchild> schoolarsTeam = new Team<>("URA");
        schoolarsTeam.addNewParticipant(schoolchild1);
        schoolarsTeam.addNewParticipant(schoolchild2);
//        schoolarTeam.addNewParticipant(employee1); будет ошибка компиляции

        Team<Schoolchild> schoolarsTeam2 = new Team<>("Goooooooooooood");
        schoolarsTeam2.addNewParticipant(schoolchild3);
        schoolarsTeam2.addNewParticipant(schoolchild4);

        schoolarsTeam.playWith(schoolarsTeam2);

        Team<Student> studentsTeam = new Team<>("GO!");
        studentsTeam.addNewParticipant(student1);
        studentsTeam.addNewParticipant(student2);
//        schoolarsTeam.playWith(studentsTeam); будет ошибка компиляции

        Team<Employee> employeesTeam = new Team<>("Labor reserves");
        employeesTeam.addNewParticipant(employee1);
        employeesTeam.addNewParticipant(employee2);
    }
}