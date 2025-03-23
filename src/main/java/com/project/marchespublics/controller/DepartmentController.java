package com.project.marchespublics.controller;


import com.project.marchespublics.dto.DepartmentDto;
import com.project.marchespublics.service.implementation.DepartmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @PostMapping("/save")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto createdDepartment = departmentService.save(departmentDto);
        return ResponseEntity.ok(createdDepartment);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDto>> getAllDepartments(Pageable pageable) {
        Page<DepartmentDto> allDepartments = departmentService.findAll(pageable);
        return ResponseEntity.ok(allDepartments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        departmentDto.setId(id);
        DepartmentDto updatedDepartment = departmentService.update(departmentDto);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable int id) {
        departmentService.delete((long) id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable int id) {
        return departmentService.findById((long) id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DepartmentDto> getDepartmentByUserId(@PathVariable Long userId) {
        return departmentService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
