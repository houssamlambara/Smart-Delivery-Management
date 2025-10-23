package com.houssam.Smart_Delivery_Management.service;

import com.houssam.Smart_Delivery_Management.model.Livreur;
import com.houssam.Smart_Delivery_Management.repository.ColisRepository;
import com.houssam.Smart_Delivery_Management.repository.LivreurRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class LivreurService {

    private LivreurRepository livreurRepository;

    public LivreurService() {}

    public LivreurService(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    public void setLivreurRepository(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    public List<Livreur> findAll(){
        return livreurRepository.findAll();
    }

    public String creeLivreur(Livreur l){
        for(Livreur livreur : findAll()){
            if(livreur.getNom().equals(l.getNom()) && livreur.getPrenom().equals(l.getPrenom())){
                return "Le nom et prenom du livreur est deja trouve";
            }
        }
        livreurRepository.save(l);
        return "La Livreur est ajoute avec succes!";
    }

    public Livreur findById(Long id) {
        return livreurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livreur introuvable"));
    }

    public boolean updateLivreur(Long id, Livreur livreur) {
        Livreur existant = findById(id);
        existant.setNom(livreur.getNom());
        existant.setPrenom(livreur.getPrenom());
        existant.setVehicule(livreur.getVehicule());
        existant.setTelephone(livreur.getTelephone());
        livreurRepository.save(existant);
        return true;
    }

    public boolean deleteLivreur(Long id) {
        livreurRepository.deleteById(id);
        return true;
    }

}
