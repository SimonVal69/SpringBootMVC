package ru.skypro.lessons.springboot.springbootmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.springbootmvc.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/salary/sum")
    public String getEmployeeSalaryTotalSum() {
        return employeeService.getEmployeeSalaryTotalSum();
    }

    @GetMapping("/salary/min")
    public String getEmployeeSalaryMinSum() {
        return employeeService.getEmployeeSalaryMinSum();
    }

    @GetMapping("/salary/max")
    public String getEmployeeSalaryMaxSum() {
        return employeeService.getEmployeeSalaryMaxSum();
    }

    @GetMapping("/high-salary")
    public String getEmployeeHighSalary() {
        return employeeService.getEmployeeHighSalary();
    }
}
