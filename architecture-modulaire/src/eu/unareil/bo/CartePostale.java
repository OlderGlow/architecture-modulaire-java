package eu.unareil.bo;

import java.util.List;

public class CartePostale extends Produit{
    private String type;
    private List<Auteur> lesAuteurs;

    public CartePostale(String type, List<Auteur> lesAuteurs) {
        this.type = type;
        this.lesAuteurs = lesAuteurs;
    }

    public CartePostale(long refProd, String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, String type) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.type = type;
        this.lesAuteurs = lesAuteurs;
    }

    public CartePostale(String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, String type) {
        super(libelle, marque, prixUnitaire, qteStock);
        this.type = type;
        this.lesAuteurs = lesAuteurs;
    }
}
