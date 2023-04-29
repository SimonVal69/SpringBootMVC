package ru.skypro.lessons.springboot.springbootmvc.pojo;

public record Employee(String name, int salary) {
    @Override
    public String toString() {
        return "{" +
                "имя='" + name + '\'' +
                ", зарплата=" + salary +
                '}';
    }
}
