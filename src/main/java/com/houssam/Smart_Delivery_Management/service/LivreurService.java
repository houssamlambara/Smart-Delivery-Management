package com.houssam.Smart_Delivery_Management.service;

import com.houssam.Smart_Delivery_Management.model.Colis;
import com.houssam.Smart_Delivery_Management.model.Livreur;
import com.houssam.Smart_Delivery_Management.enums.StatutColis;
import com.houssam.Smart_Delivery_Management.repository.ColisRepository;
import com.houssam.Smart_Delivery_Management.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class GestionLivraisonService {
    private final LivreurRepository livreurRepository;
    private final ColisRepository colisRepository;

    @Autowired
    public GestionLivraisonService(LivreurRepository livreurRepository, ColisRepository colisRepository) {
        this.livreurRepository = livreurRepository;
        this.colisRepository = colisRepository;
    }

    @Transactional
    public Livreur creerLivreur(Livreur livreur) {
        return livreurRepository.save(livreur);
    }

    public Livreur updateLivreur (Long id, Livreur livreurDetails) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));
        livreur.setNom(livreurDetails.getNom());
        livreur.setPrenom(livreurDetails.getPrenom());
        livreur.setTelephone(livreurDetails.getTelephone());
        return livreurRepository.save(livreur);
    }

    public Optional<Livreur> findById(Long id) {
        return livreurRepository.findById(id);
    }

    public List<Livreur> findAll(){
        return livreurRepository.findAll();
    }

    @Transactional
    public void deleteLivreur(Long id) {
        livreurRepository.deleteById(id);
    }

    @Transactional
    public Colis creerColis(Colis colis, Long idLivreur) {
        Livreur livreur = livreurRepository.findById(idLivreur)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));

        colis.setLivreur(livreur);
        colis.setStatut(StatutColis.PREPARATION);
        return colisRepository.save(colis);
    }

    @Transactional
    public Colis updateColis(long idColis, StatutColis nouveauStatut){
        Colis colis = colisRepository.findById(idColis)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé"));
        colis.setStatut(nouveauStatut);
        return colisRepository.save(colis);
    }

    @Transactional
    public void deleteColis(long idColis){
        colisRepository.deleteById(idColis);
    }

    @Transactional(readOnly = true)
    public List<Colis> listColisByLivreur(long idLivreur){
        return colisRepository.findByLivreurId(idLivreur);
    }

}
