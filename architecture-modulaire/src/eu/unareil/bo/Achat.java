package eu.unareil.bo;

import java.util.ArrayList;
import java.util.List;

public class Achat {
    private double montant;
    private List<Ligne> lignesAchat = new ArrayList<>();

    public Achat(Ligne ligne) {
        this.lignesAchat.add(ligne);
    }

    public double getMontant() {
        return montant;
    }

    public Ligne getLigne(int index) {
        return lignesAchat.get(index);
    }

    public void ajouteLigne(Produit produit, int quantite) {
        Ligne ligne = new Ligne(produit, quantite);
        this.lignesAchat.add(ligne);
    }

    public void supprimeLigne(int index) {
        lignesAchat.remove(index);
    }

    public void modifieLigne(int index, int nouvelleQte) {
        Ligne ligne = lignesAchat.get(index);
        ligne.setQuantite(nouvelleQte);
    }

    public void calculMontant() {
        montant = 0;
        for (Ligne ligne : lignesAchat) {
            montant += ligne.getQte() * ligne.getProduit().getPrixUnitaire();
        }
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public List<Ligne> getLignes() {
        return lignesAchat;
    }

    public void setLignesAchat(List<Ligne> lignesAchat) {
        this.lignesAchat = lignesAchat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Achat{");
        sb.append("montant=").append(montant);
        sb.append(", lignesAchat=").append(lignesAchat);
        sb.append('}');
        return sb.toString();
    }
}
