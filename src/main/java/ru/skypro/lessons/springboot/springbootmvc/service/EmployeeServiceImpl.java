package ru.skypro.lessons.springboot.springbootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.springbootmvc.pojo.Employee;
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
                .mapToInt(Employee::salary)
                .sum();
        return "Сумма зарплат всех сотрудников: " + sum;
    }

    @Override
    public String getEmployeeSalaryMinSum() {
        Optional<Employee> employeeSalaryMinSum = employeeRepository.getListEmployees().stream()
                .min(Comparator.comparingInt(Employee::salary));
        return "Сотрудник с минимальной зарплатой: " + employeeSalaryMinSum.orElse(null);
    }

    @Override
    public String getEmployeeSalaryMaxSum() {
        Optional<Employee> employeeSalaryMaxSum = employeeRepository.getListEmployees().stream()
                .max(Comparator.comparingInt(Employee::salary));
        return "Сотрудник с максимальной зарплатой: " + employeeSalaryMaxSum.orElse(null);
    }

    @Override
    public String getEmployeeHighSalary() {
        double averageSalary = employeeRepository.getListEmployees().stream()
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0.0);
        List<Employee> employeeHighSalary = employeeRepository.getListEmployees().stream()
                .filter(employee -> employee.salary() > averageSalary)
                .toList();
        return "Средняя зарплата: " + averageSalary + "\nСотрудники с зарплатами выше средней: " + employeeHighSalary;
    }
}
