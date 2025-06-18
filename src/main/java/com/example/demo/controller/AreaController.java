package com.example.demo.controller;

import com.example.demo.entity.Area;
import com.example.demo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas") // 모든 /api/areas 요청을 이 컨트롤러가 처리
public class AreaController {

    @Autowired
    private AreaService areaService;

    // 모든 에리어 조회
    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaService.getAllAreas();
        return ResponseEntity.ok(areas);
    }

    // 특정 에리어 조회
    @GetMapping("/{id}")
    public ResponseEntity<Area> getAreaById(@PathVariable Integer id) {
        return areaService.getAreaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 새 에리어 생성
    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        Area savedArea = areaService.saveArea(area);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArea);
    }

    // 에리어 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Area> updateArea(@PathVariable Integer id, @RequestBody Area area) {
        try {
            Area updated = areaService.updateArea(id, area);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 에리어 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Integer id) {
        try {
            areaService.deleteArea(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}