package com.houssam.Smart_Delivery_Management.repository;

import com.houssam.Smart_Delivery_Management.model.Colis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, Long> {

    List<Colis> findByLivreurId(Long livreurId);
}