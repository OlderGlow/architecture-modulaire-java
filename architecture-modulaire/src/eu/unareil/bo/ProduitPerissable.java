package eu.unareil.bo;

import java.time.LocalDate;

public class ProduitPerissable extends Produit {
    private LocalDate dateLimiteConso;

    public ProduitPerissable(LocalDate dateLimiteConso) {
        this.dateLimiteConso = dateLimiteConso;
    }

    public ProduitPerissable(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, LocalDate dateLimiteConso) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.dateLimiteConso = dateLimiteConso;
    }

    public ProduitPerissable(String libelle, String marque, float prixUnitaire, long qteStock, LocalDate dateLimiteConso) {
        super(libelle, marque, prixUnitaire, qteStock);
        this.dateLimiteConso = dateLimiteConso;
    }

    public LocalDate getDateLimiteConso() {
        return dateLimiteConso;
    }

    public void setDateLimiteConso(LocalDate dateLimiteConso) {
        this.dateLimiteConso = dateLimiteConso;
    }
}
