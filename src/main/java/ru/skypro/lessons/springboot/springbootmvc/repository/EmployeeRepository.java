package ru.skypro.lessons.springboot.springbootmvc.repository;

import ru.skypro.lessons.springboot.springbootmvc.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getListEmployees();

    void addEmployee(List<Employee> employees);

    void editEmployee(Employee foundEmployee, Employee newEmployee);

    List<Employee> getListEmployeesForDeleting();

}
