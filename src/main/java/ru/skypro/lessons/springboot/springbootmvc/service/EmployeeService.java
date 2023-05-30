package ru.skypro.lessons.springboot.springbootmvc.service;

import org.springframework.data.domain.Page;
import ru.skypro.lessons.springboot.springbootmvc.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    List<EmployeeDTO> getEmployeesByPosition(String position);

    EmployeeDTO getEmployeeById(int id);

    void addEmployee(List<EmployeeDTO> employeeDTO);

    EmployeeDTO editEmployee(int id, EmployeeDTO employeeDTO);

    void deleteEmployeeById(int id);

    List<EmployeeDTO> getEmployeesWithSalaryHigherThan(int compareSalary);

    List<EmployeeDTO> getEmployeesWithHighestSalary();

    EmployeeDTO getEmployeeFullInfo(int id);

    Page<EmployeeDTO> getEmployeesByPage(int page);
}
