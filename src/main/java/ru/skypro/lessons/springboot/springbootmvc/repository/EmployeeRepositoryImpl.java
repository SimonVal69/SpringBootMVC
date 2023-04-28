package ru.skypro.lessons.springboot.springbootmvc.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.springbootmvc.pojo.Employee;

import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final List<Employee> employeeList = List.of(
            new Employee("Катя", 90_000),
            new Employee("Дима", 122_000),
            new Employee("Олег", 80_000),
            new Employee("Вика", 165_000));

    @Override
    public List<Employee> getListEmployees() {
        return employeeList;
    }
}
