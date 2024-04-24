package tn.esprit.models;

public class TypeCredit {
    private int id;
    private String nomTypeCredit;
    private float tauxCreditDirect;

    public TypeCredit() {}

    public TypeCredit(int id, String nomTypeCredit, float tauxCreditDirect) {
        this.id = id;
        this.nomTypeCredit = nomTypeCredit;
        this.tauxCreditDirect = tauxCreditDirect;
    }

    public TypeCredit(String nomTypeCredit, float tauxCreditDirect) {
        this.nomTypeCredit = nomTypeCredit;
        this.tauxCreditDirect = tauxCreditDirect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomTypeCredit() {
        if (nomTypeCredit == null || nomTypeCredit.trim().length() < 2) {
            throw new IllegalArgumentException("Le nom de type du crédit doit comporter au moins 2 caractères.");
        }
        return nomTypeCredit;
    }

    public void setNomTypeCredit(String nomTypeCredit) {
        this.nomTypeCredit = nomTypeCredit;
    }

    public float getTauxCreditDirect() {
        return tauxCreditDirect;
    }

    public void setTauxCreditDirect(float tauxCreditDirect) {
        this.tauxCreditDirect = tauxCreditDirect;
    }

    @Override
    public String toString() {
        return "TypeCredit{" +
                "id=" + id +
                ", nomTypeCredit='" + nomTypeCredit + '\'' +
                ", tauxCreditDirect=" + tauxCreditDirect + "%" +
                '}';
    }
}
