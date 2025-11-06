package com.iset.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Client extends User {
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "sexe", length = 10)
    private String sexe;
    
    @Column(name = "poids")
    private Double poids;
    
    @Column(name = "taille")
    private Double taille;
    
    @Column(name = "objectifs", length = 500)
    private String objectifs;
    
    @Column(name = "allergies", length = 500)
    private String allergies;
    
    @Column(name = "maladies_chroniques", length = 500)
    private String maladiesChroniques;
    
    @Column(name = "niveau_activite", length = 50)
    private String niveauActivite;


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Double getTaille() {
        return taille;
    }

    public void setTaille(Double taille) {
        this.taille = taille;
    }

    public String getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(String objectifs) {
        this.objectifs = objectifs;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMaladiesChroniques() {
        return maladiesChroniques;
    }

    public void setMaladiesChroniques(String maladiesChroniques) {
        this.maladiesChroniques = maladiesChroniques;
    }

    public String getNiveauActivite() {
        return niveauActivite;
    }

    public void setNiveauActivite(String niveauActivite) {
        this.niveauActivite = niveauActivite;
    }
}

