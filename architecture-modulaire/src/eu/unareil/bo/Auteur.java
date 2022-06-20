package eu.unareil.bo;

import java.util.ArrayList;
import java.util.List;

public class Auteur {
    private long refAuteur;
    private String nom;
    private String prenom;
    private List<CartePostale> lesCartes = new ArrayList<>();

    public Auteur(String nom, String prenom) {
        this.setNom(nom);
        this.setPrenom(prenom);
    }

    public Auteur(long refAuteur, String nom, String prenom, List<CartePostale> lesCartes) {
        this.setRefAuteur(refAuteur);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setLesCartes(lesCartes);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getRefAuteur() {
        return refAuteur;
    }

    public void setRefAuteur(long refAuteur) {
        this.refAuteur = refAuteur;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<CartePostale> getLesCartes() {
        return lesCartes;
    }

    public void setLesCartes(List<CartePostale> lesCartes) {
        this.lesCartes = lesCartes;
    }

}
