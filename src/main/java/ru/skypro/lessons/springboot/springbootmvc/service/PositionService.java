package ru.skypro.lessons.springboot.springbootmvc.service;

import ru.skypro.lessons.springboot.springbootmvc.dto.PositionDTO;

import java.util.List;

public interface PositionService {
    void addPosition(PositionDTO positionDTO);

    List<PositionDTO> getAllPositions();
}
