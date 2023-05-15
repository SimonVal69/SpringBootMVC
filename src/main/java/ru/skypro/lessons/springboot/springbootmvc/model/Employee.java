package ru.skypro.lessons.springboot.springbootmvc.model;

public class Employee {
private final int id;
private String name;
private int salary;

    public Employee(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "{" +
                "имя='" + name + '\'' +
                ", зарплата=" + salary +
                '}';
    }
}
