package ru.skypro.lessons.springboot.springbootmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.lessons.springboot.springbootmvc.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
