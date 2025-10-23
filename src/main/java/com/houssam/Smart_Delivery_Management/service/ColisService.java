package com.houssam.Smart_Delivery_Management.service;

import com.houssam.Smart_Delivery_Management.model.Colis;
import com.houssam.Smart_Delivery_Management.model.Livreur;
import com.houssam.Smart_Delivery_Management.enums.StatutColis;
import com.houssam.Smart_Delivery_Management.repository.ColisRepository;
import com.houssam.Smart_Delivery_Management.repository.LivreurRepository;
import java.util.List;

public class ColisService {

    // INJECTION PAR SETTER
    private ColisRepository colisRepository;
    private LivreurRepository livreurRepository;

    public void setColisRepository(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }

    public void setLivreurRepository(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    public Colis creerColis(Colis colis, Long idLivreur) {
        Livreur livreur = livreurRepository.findById(idLivreur)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));
        colis.setLivreur(livreur);
        colis.setStatut(StatutColis.PREPARATION);
        return colisRepository.save(colis);
    }

    public Colis updateStatut(Long id, StatutColis statut) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé"));
        colis.setStatut(statut);
        return colisRepository.save(colis);
    }

    public List<Colis> findByLivreurId(Long livreurId) {
        return colisRepository.findByLivreurId(livreurId);
    }

    public List<Colis> findAll() {
        return colisRepository.findAll();
    }

    public void deleteColis(Long id){
        colisRepository.deleteById(id);
    }
}
