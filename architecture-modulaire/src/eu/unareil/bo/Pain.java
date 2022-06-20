package eu.unareil.bo;

import java.time.LocalDate;

public class Pain extends ProduitPerissable {
    private int poids;

    public Pain(LocalDate dateLimiteConso, int poids) {
        super(dateLimiteConso);
        this.poids = poids;
    }

    public Pain(long refProd, String marque, String libelle, int poids, long qteStock, float prixUnitaire) {
        super(refProd, libelle, marque, prixUnitaire, qteStock, LocalDate.now().plusDays(2));
        this.poids = poids;
    }

    public Pain(String marque, String libelle, int poids, long qteStock, float prixUnitaire) {
        super(libelle, marque, prixUnitaire, qteStock, LocalDate.now().plusDays(2));
        this.poids = poids;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(", ");
        sb.append("poids=").append(poids);
        sb.append(']');
        return sb.toString();
    }
}
