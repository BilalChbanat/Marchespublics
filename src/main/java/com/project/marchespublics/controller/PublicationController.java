package com.project.marchespublics.controller;

import com.project.marchespublics.dto.PublicationDto;
import com.project.marchespublics.service.implementation.PublicationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pubs")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationServiceImpl publicationService;

    @PostMapping("/save")
    public ResponseEntity<PublicationDto> publish(@RequestBody PublicationDto publicationDto) {
        PublicationDto result = publicationService.save(publicationDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<PublicationDto>> getAll(Pageable pageable) {
         Page<PublicationDto> result = publicationService.findAll(pageable);
         return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDto> update(@PathVariable Long id, @RequestBody PublicationDto publicationDto) {
        publicationDto.setId(id);
        PublicationDto result = publicationService.update(publicationDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PublicationDto>> getPublication(@PathVariable Long id) {
        Optional<PublicationDto> result = publicationService.findById(id);
        return ResponseEntity.ok(result);
    }
}
