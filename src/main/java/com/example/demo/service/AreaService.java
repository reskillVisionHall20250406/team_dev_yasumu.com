package com.example.demo.service;

import com.example.demo.entity.Area;
import com.example.demo.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    // 모든 에리어 조회
    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    // ID 에리어 조회
    public Optional<Area> getAreaById(Integer id) {
        return areaRepository.findById(id);
    }

    // 새 에리어 저장
    @Transactional
    public Area saveArea(Area area) {
        return areaRepository.save(area);
    }

    // 에리어 변경 
    @Transactional
    public Area updateArea(Integer id, Area updatedArea) {
        return areaRepository.findById(id).map(area -> {
            area.setName(updatedArea.getName());
            return areaRepository.save(area);
        }).orElseThrow(() -> new RuntimeException("Area not found with id " + id));
    }

    // 에리어 삭제
    @Transactional
    public void deleteArea(Integer id) {
        areaRepository.deleteById(id);
    }
}