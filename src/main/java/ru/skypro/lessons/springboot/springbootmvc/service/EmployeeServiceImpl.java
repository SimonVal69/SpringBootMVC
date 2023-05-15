package ru.skypro.lessons.springboot.springbootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.springbootmvc.model.Employee;
import ru.skypro.lessons.springboot.springbootmvc.repository.EmployeeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String getEmployeeSalaryTotalSum() {
        int sum = employeeRepository.getListEmployees().stream()
                .mapToInt(Employee::getSalary)
                .sum();
        return "Сумма зарплат всех сотрудников: " + sum;
    }

    @Override
    public String getEmployeeSalaryMinSum() {
        Optional<Employee> employeeSalaryMinSum = employeeRepository.getListEmployees().stream()
                .min(Comparator.comparingInt(Employee::getSalary));
        return "Сотрудник с минимальной зарплатой: " + employeeSalaryMinSum.orElse(null);
    }

    @Override
    public String getEmployeeSalaryMaxSum() {
        Optional<Employee> employeeSalaryMaxSum = employeeRepository.getListEmployees().stream()
                .max(Comparator.comparingInt(Employee::getSalary));
        return "Сотрудник с максимальной зарплатой: " + employeeSalaryMaxSum.orElse(null);
    }

    @Override
    public String getEmployeeHighSalary() {
        double averageSalary = employeeRepository.getListEmployees().stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        List<Employee> employeeHighSalary = employeeRepository.getListEmployees().stream()
                .filter(employee -> employee.getSalary() > averageSalary)
                .toList();
        return "Средняя зарплата: " + averageSalary + "\nСотрудники с зарплатами выше средней: " + employeeHighSalary;
    }

    @Override
    public Optional<Employee> findEmployeeById(int id) {
        return employeeRepository.getListEmployees().stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    @Override
    public void addEmployee(List<Employee> employees) {
        employeeRepository.addEmployee(employees);
    }

    @Override
    public void editEmployee(int id, Employee newEmployee) {
        employeeRepository.editEmployee(findEmployeeById(id).orElse(null), newEmployee);
    }

    @Override
    public String getEmployeeById(int id) {
        return "Найденный сотрудник c id = " + id + ": " + findEmployeeById(id).orElse(null);
    }

    @Override
    public String deleteEmployeeById(int id) {
        employeeRepository.getListEmployeesForDeleting().removeIf(employee -> employee.getId() == id);
        return "Найденный сотрудник c id = " + id + " успешно удален из базы данных";
    }

    @Override
    public String getEmployeesWithSalaryHigherThan(int compareSalary) {
        List<Employee> employeeHighSalary = employeeRepository.getListEmployees().stream()
                .filter(employee -> employee.getSalary() > compareSalary)
                .toList();
        return "Сотрудники с зарплатой выше " + compareSalary + ": " + employeeHighSalary;
    }
}
