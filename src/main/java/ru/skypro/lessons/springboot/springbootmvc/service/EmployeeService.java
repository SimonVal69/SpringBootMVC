package ru.skypro.lessons.springboot.springbootmvc.service;

import ru.skypro.lessons.springboot.springbootmvc.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    String getEmployeeSalaryTotalSum();

    String getEmployeeSalaryMinSum();

    String getEmployeeSalaryMaxSum();

    String getEmployeeHighSalary();

    Optional<Employee> findEmployeeById(int id);

    void addEmployee(List<Employee> employees);

    void editEmployee(int id, Employee newEmployee);

    String getEmployeeById(int id);

    String deleteEmployeeById(int id);

    String getEmployeesWithSalaryHigherThan(int compareSalary);
}
