package ru.skypro.lessons.springboot.springbootmvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.springbootmvc.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.springbootmvc.model.Employee;
import ru.skypro.lessons.springboot.springbootmvc.model.Position;
import ru.skypro.lessons.springboot.springbootmvc.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.springbootmvc.repository.PositionRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        Iterable<Employee> employees = employeeRepository.findAll();
        return StreamSupport.stream(employees.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByPosition(String position) {
        List<Employee> employees;
        if (position != null) {
            employees = employeeRepository.findByPositionName(position);
        } else {
            employees = (List<Employee>) employeeRepository.findAll();
        }
        return employees.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        return convertToDto(employee);
    }

    @Override
    public void addEmployee(List<EmployeeDTO> employeeDTO) {
        List<Employee> employees = employeeDTO.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        employeeRepository.saveAll(employees);
    }

    @Override
    public EmployeeDTO getEmployeeFullInfo(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        return convertToDto(employee);
    }

    @Override
    public EmployeeDTO editEmployee(int id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow();
        // Обновляем данные существующего сотрудника
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSalary(employeeDTO.getSalary());
        if (employeeDTO.getPositionName() != null) {
            Position position = positionRepository.findByName(employeeDTO.getPositionName()).orElseThrow();
            existingEmployee.setPosition(position);
        }
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToDto(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithSalaryHigherThan(int compareSalary) {
        List<Employee> employees = employeeRepository.findBySalaryGreaterThan(compareSalary);
        return employees.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithHighestSalary() {
        List<Employee> employees = employeeRepository.getEmployeesWithHighestSalary();
        return employees.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public Page<EmployeeDTO> getEmployeesByPage(int page) {
        Pageable pageable = PageRequest.of(page, 2);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(this::convertToDto);
    }

    private EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setPositionName(employee.getPosition().getName());
        return employeeDTO;
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        Position position = positionRepository.findByName(employeeDTO.getPositionName()).orElseThrow();
        employee.setPosition(position);
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        return employee;
    }
}
