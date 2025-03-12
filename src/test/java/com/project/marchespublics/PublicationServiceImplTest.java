package com.project.marchespublics;

import com.project.marchespublics.dto.PublicationDto;
import com.project.marchespublics.enums.PublicationStatus;
import com.project.marchespublics.mapper.PublicationMapper;
import com.project.marchespublics.model.Category;
import com.project.marchespublics.model.Department;
import com.project.marchespublics.model.Publication;
import com.project.marchespublics.repository.CategoryRepository;
import com.project.marchespublics.repository.DepartmentRepository;
import com.project.marchespublics.repository.PublicationRepository;
import com.project.marchespublics.service.implementation.PublicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceImplTest {

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private PublicationMapper publicationMapper;

    @InjectMocks
    private PublicationServiceImpl publicationService;

    private Publication publication;
    private PublicationDto publicationDto;
    private Category category;
    private Department department;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1)
                .name("Test Category")
                .build();

        department = Department.builder()
                .id(1L)
                .name("Test Department")
                .build();

        publication = Publication.builder()
                .id(1L)
                .title("Test Publication")
                .description("Test Description")
                .publicationDate(LocalDateTime.now())
                .deadlineDate(LocalDateTime.now().plusMonths(1))
                .referenceNumber("TEST-2503-1234")
                .estimatedBudget(10000.0)
                .status(PublicationStatus.DRAFT)
                .category(category)
                .department(department)
                .build();

        publicationDto = PublicationDto.builder()
                .id(1L)
                .title("Test Publication")
                .description("Test Description")
                .publicationDate(LocalDateTime.now())
                .deadlineDate(LocalDateTime.now().plusMonths(1))
                .referenceNumber("TEST-2503-1234")
                .estimatedBudget(10000.0)
                .status(PublicationStatus.DRAFT)
                .categoryId(1)
                .departmentId(1L)
                .build();
    }

    @Test
    void testSavePublication_Success() {
        PublicationDto inputDto = PublicationDto.builder()
                .title("New Publication")
                .description("New Description")
                .deadlineDate(LocalDateTime.now().plusMonths(1))
                .estimatedBudget(20000.0)
                .categoryId(1)
                .departmentId(1L)
                .build();

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(publicationMapper.toEntity(any(PublicationDto.class))).thenReturn(publication);
        when(publicationRepository.save(any(Publication.class))).thenReturn(publication);
        when(publicationMapper.toDto(any(Publication.class))).thenReturn(publicationDto);

        PublicationDto result = publicationService.save(inputDto);

        assertNotNull(result);
        assertEquals("Test Publication", result.getTitle());
        verify(publicationRepository).save(any(Publication.class));
    }

    @Test
    void testUpdatePublication_Success() {
        PublicationDto updateDto = PublicationDto.builder()
                .id(1L)
                .title("Updated Publication")
                .description("Updated Description")
                .deadlineDate(LocalDateTime.now().plusMonths(2))
                .estimatedBudget(30000.0)
                .categoryId(1)
                .departmentId(1L)
                .status(PublicationStatus.PUBLISHED)
                .build();

        Publication existingPublication = Publication.builder()
                .id(1L)
                .title("Original Publication")
                .description("Original Description")
                .publicationDate(LocalDateTime.now().minusDays(10))
                .referenceNumber("ORIG-2503-1234")
                .build();

        Publication updatedPublication = Publication.builder()
                .id(1L)
                .title("Updated Publication")
                .description("Updated Description")
                .publicationDate(existingPublication.getPublicationDate())
                .referenceNumber(existingPublication.getReferenceNumber())
                .status(PublicationStatus.PUBLISHED)
                .category(category)
                .department(department)
                .build();

        when(publicationRepository.findById(1)).thenReturn(Optional.of(existingPublication));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(publicationMapper.toEntity(updateDto)).thenReturn(updatedPublication);
        when(publicationRepository.save(any(Publication.class))).thenReturn(updatedPublication);
        when(publicationMapper.toDto(any(Publication.class))).thenReturn(publicationDto);

        PublicationDto result = publicationService.update(updateDto);

        assertNotNull(result);
        verify(publicationRepository).save(any(Publication.class));
    }

    @Test
    void testFindById_Success() {
        when(publicationRepository.findById(1)).thenReturn(Optional.of(publication));
        when(publicationMapper.toDto(publication)).thenReturn(publicationDto);

        Optional<PublicationDto> result = publicationService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Publication", result.get().getTitle());
    }

    @Test
    void testFindAll() {
        List<Publication> publications = Arrays.asList(publication);
        Page<Publication> publicationPage = new PageImpl<>(publications);

        Pageable pageable = PageRequest.of(0, 10);
        when(publicationRepository.findAll(pageable)).thenReturn(publicationPage);
        when(publicationMapper.toDto(publication)).thenReturn(publicationDto);

        Page<PublicationDto> result = publicationService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Publication", result.getContent().get(0).getTitle());
    }

    @Test
    void testDelete_Success() {
        when(publicationRepository.existsById(1)).thenReturn(true);
        doNothing().when(publicationRepository).deleteById(1);

        publicationService.delete(1L);

        verify(publicationRepository).deleteById(1);
    }
}