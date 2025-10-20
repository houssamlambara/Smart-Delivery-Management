package com.houssam.Smart_Delivery_Management.service;

import com.houssam.Smart_Delivery_Management.model.Livreur;
import com.houssam.Smart_Delivery_Management.repository.ColisRepository;
import com.houssam.Smart_Delivery_Management.repository.LivreurRepository;
import org.springframework.stereotype.Service;

@Service
public class GestionLivraisonService {
    private final LivreurRepository livreurRepository;
    private final ColisRepository colisRepository;

    public GestionLivraisonService(LivreurRepository livreurRepository, ColisRepository colisRepository) {
        this.livreurRepository = livreurRepository;
        this.colisRepository = colisRepository;
    }
}
