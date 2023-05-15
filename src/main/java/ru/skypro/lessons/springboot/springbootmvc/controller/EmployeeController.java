package ru.skypro.lessons.springboot.springbootmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.springbootmvc.model.Employee;
import ru.skypro.lessons.springboot.springbootmvc.service.EmployeeService;

import java.util.List;

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

    @PostMapping("/new/employees")
    public void addEmployee(@RequestBody List<Employee> newEmployees) {
        employeeService.addEmployee(newEmployees);
    }

    @PostMapping("/employees/{id}")
    public void editEmployee(@PathVariable int id, @RequestBody Employee newEmployee) {
        employeeService.editEmployee(id, newEmployee);
    }

    @GetMapping("/employees/{id}")
    public String getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployeeById(@PathVariable int id) {
        return employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/employees/salary/higher")
    public String getEmployeesWithSalaryHigherThan(@RequestParam("compareSalary") int compareSalary) {
        return employeeService.getEmployeesWithSalaryHigherThan(compareSalary);
    }
}
