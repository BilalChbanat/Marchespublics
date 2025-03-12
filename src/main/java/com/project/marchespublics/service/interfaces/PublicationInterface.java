package com.project.marchespublics.service.interfaces;

import com.project.marchespublics.dto.PublicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PublicationInterface {

    PublicationDto save(PublicationDto publicationDto);
    PublicationDto update (PublicationDto publicationDto);
    Optional<PublicationDto> findById(Long id);


    Page<PublicationDto> findAll(Pageable pageable);
    void delete(Long id);

}
