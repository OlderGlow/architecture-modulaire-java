package eu.unareil.bo;

public class Ligne {
    private int quantite;
    private Produit produit;

    public Ligne() {
    }

    public Ligne(Produit produit, int quantite) {
        this.quantite = quantite;
        this.produit = produit;
    }

    public int getQte() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getPrix() {
        return this.getQte() * produit.getPrixUnitaire();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ligne{");
        sb.append("quantite=").append(quantite);
        sb.append(", produit=").append(produit);
        sb.append('}');
        return sb.toString();
    }
}
