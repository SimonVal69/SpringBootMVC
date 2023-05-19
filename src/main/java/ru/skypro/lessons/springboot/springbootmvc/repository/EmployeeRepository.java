package ru.skypro.lessons.springboot.springbootmvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.springboot.springbootmvc.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findByPositionName(String positionName);

    List<Employee> findBySalaryGreaterThan(int compareSalary);

    @Query(value = "SELECT * FROM employee " +
            "WHERE salary = (SELECT MAX(salary) " +
            "FROM employee)",
            nativeQuery = true)
    List<Employee> getEmployeesWithHighestSalary();

    Page<Employee> findAll(Pageable pageable);
}
