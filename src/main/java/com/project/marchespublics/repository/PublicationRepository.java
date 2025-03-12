package com.project.marchespublics.repository;

import com.project.marchespublics.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Integer> {

}
