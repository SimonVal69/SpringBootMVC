package ru.skypro.lessons.springboot.springbootmvc.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.springbootmvc.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.springbootmvc.dto.PositionDTO;
import ru.skypro.lessons.springboot.springbootmvc.service.EmployeeService;
import ru.skypro.lessons.springboot.springbootmvc.service.PositionService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/employees")
public class AdminEmployeeController {
    private final EmployeeService employeeService;
    private final PositionService positionService;

    public AdminEmployeeController(EmployeeService employeeService, PositionService positionService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    @PostMapping("/new/position")
    public ResponseEntity<String> addPosition(@RequestBody PositionDTO positionDTO) {
        positionService.addPosition(positionDTO);
        return ResponseEntity.ok("Операция прошла успешно");
    }

    @PostMapping("/new")
    public ResponseEntity<String> addEmployee(@RequestBody List<EmployeeDTO> employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return ResponseEntity.ok("Операция прошла успешно");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> editEmployee(@PathVariable Long id, @RequestBody EmployeeDTO newEmployeeDTO) {
        return ResponseEntity.ok(employeeService.editEmployee(id, newEmployeeDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Операция прошла успешно");
    }

    @PostMapping("/salary/highest")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesWithHighestSalary() {
        return ResponseEntity.ok(employeeService.getEmployeesWithHighestSalary());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> loadEmployeesFromFileAndSave(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.addEmployee(employeeService.loadEmployeesFromFile(file));
        return ResponseEntity.ok("Операция прошла успешно");
    }

    @PostMapping("/report")
    public ResponseEntity<String> getReportByDepartment() throws IOException {
        Long reportId = employeeService.getReportByDepartment();
        return ResponseEntity.ok("Идентификатор (id) сохраненного отчёта: " + reportId);
    }

}
