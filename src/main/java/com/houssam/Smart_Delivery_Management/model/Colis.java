package com.houssam.Smart_Delivery_Management.model;

import com.houssam.Smart_Delivery_Management.enums.StatutColis;
import jakarta.persistence.*;

@Entity
@Table (name = "colis")
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinataire;
    private String adresse;
    private double poids;

    @Enumerated(EnumType.STRING)
    private StatutColis statut = StatutColis.PREPARATION;

    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    public Colis(){}

    public Colis(String destinataire, String adresse, Double poids, StatutColis statut) {
        this.destinataire = destinataire;
        this.adresse = adresse;
        this.poids = poids;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinataire() {
        return destinataire;
    }
    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getPoids() {
        return poids;
    }
    public void setPoids(double poids) {
        this.poids = poids;
    }

    public StatutColis getStatut() {
        return statut;
    }
    public void setStatut(StatutColis statut) {
        this.statut = statut;
    }

    public Livreur getLivreur() {
        return livreur;
    }
    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }
}
