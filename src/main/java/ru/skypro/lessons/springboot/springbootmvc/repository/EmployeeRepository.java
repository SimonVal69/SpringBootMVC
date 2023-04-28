package ru.skypro.lessons.springboot.springbootmvc.repository;

import ru.skypro.lessons.springboot.springbootmvc.pojo.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getListEmployees();
}
