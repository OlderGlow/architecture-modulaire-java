package eu.unareil.bo;

import java.util.List;

public class CartePostale extends Produit{
    private String type;
    private List<Auteur> lesAuteurs;

    public CartePostale(String type, List<Auteur> lesAuteurs) {
        this.type = type;
        this.lesAuteurs = lesAuteurs;
    }

    public CartePostale(long refProd, String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, TypeCartePostale type) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.type = type.toString();
        this.lesAuteurs = lesAuteurs;
    }

    public CartePostale(String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, TypeCartePostale type) {
        super(libelle, marque, prixUnitaire, qteStock);
        this.type = type.toString();
        this.lesAuteurs = lesAuteurs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(", ");
        sb.append("auteurs=");
        for(Auteur auteur : lesAuteurs) {
            sb.append("auteur").append((lesAuteurs.indexOf(auteur))+1).append("=");
            sb.append(auteur.getNom()).append(" ").append(auteur.getPrenom()).append(", ");
        }
        sb.append("type=").append(type);
        sb.append(']');
        return sb.toString();
    }
}
