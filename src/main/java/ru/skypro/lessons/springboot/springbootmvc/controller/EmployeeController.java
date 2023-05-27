package ru.skypro.lessons.springboot.springbootmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PositionService positionService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, PositionService positionService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    @PostMapping("/new/position")
    public void addPosition(@RequestBody PositionDTO positionDTO) {
        positionService.addPosition(positionDTO);
    }

    @GetMapping("/positions")
    public List<PositionDTO> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PostMapping("/new")
    public void addEmployee(@RequestBody List<EmployeeDTO> employeeDTO) {
        employeeService.addEmployee(employeeDTO);
    }

    @GetMapping("/all")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public EmployeeDTO editEmployee(@PathVariable Long id, @RequestBody EmployeeDTO newEmployeeDTO) {
        return employeeService.editEmployee(id, newEmployeeDTO);
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/salary/higherThan")
    public List<EmployeeDTO> getEmployeesWithSalaryHigherThan(@RequestParam("compareSalary") int compareSalary) {
        return employeeService.getEmployeesWithSalaryHigherThan(compareSalary);
    }

    @PostMapping("/salary/highest")
    public List<EmployeeDTO> getEmployeesWithHighestSalary() {
        return employeeService.getEmployeesWithHighestSalary();
    }

    @GetMapping("/position")
    public List<EmployeeDTO> getEmployeesByPosition(@RequestParam(name = "position", required = false) String position) {
        return employeeService.getEmployeesByPosition(position);
    }

    @GetMapping("/{id}/fullInfo")
    public EmployeeDTO getEmployeeFullInfo(@PathVariable Long id) {
        return employeeService.getEmployeeFullInfo(id);
    }

    @GetMapping("/page")
    public Page<EmployeeDTO> getEmployeesByPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        return employeeService.getEmployeesByPage(page);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loadEmployeesFromFileAndSave(@RequestParam("file") MultipartFile file) throws IOException {
            List<EmployeeDTO> employeeDTO = employeeService.loadEmployeesFromFile(file);
            employeeService.addEmployee(employeeDTO);
    }

    @PostMapping("/report")
    public String getReportByDepartment() {
            Long reportId = employeeService.getReportByDepartment();
            return "Идентификатор (id) сохраненного отчёта: " + reportId;
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<ByteArrayResource> getReportByIdAndDownload(@PathVariable Long id) {
        employeeService.generateJsonFileFromReport(id);
        return employeeService.getReportResponseById(id);
    }

}
