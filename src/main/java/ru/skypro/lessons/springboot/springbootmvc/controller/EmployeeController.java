package ru.skypro.lessons.springboot.springbootmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.springbootmvc.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.springbootmvc.dto.PositionDTO;
import ru.skypro.lessons.springboot.springbootmvc.service.EmployeeService;
import ru.skypro.lessons.springboot.springbootmvc.service.PositionService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PositionService positionService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, PositionService positionService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    @GetMapping("/{id}/fullInfo")
    public ResponseEntity<EmployeeDTO> getEmployeeFullInfo(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeFullInfo(id));
    }

    @GetMapping("/positions/all")
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/salary/higherThan")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesWithSalaryHigherThan(@RequestParam("compareSalary") int compareSalary) {
        return ResponseEntity.ok(employeeService.getEmployeesWithSalaryHigherThan(compareSalary));
    }

    @GetMapping("/position")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPosition(@RequestParam(name = "position", required = false) String position) {
        return ResponseEntity.ok(employeeService.getEmployeesByPosition(position));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeesByPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(employeeService.getEmployeesByPage(page));
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<ByteArrayResource> getReportByIdAndDownload(@PathVariable Long id) throws IOException {
        employeeService.generateJsonFileFromReport(id);
        return ResponseEntity.ok(employeeService.getReportResponseById(id).getBody());
    }

}
