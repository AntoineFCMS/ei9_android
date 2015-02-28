package com.adenclassifieds.ei9.business;

/**
 * Created by Antoine GALTIER on 23/02/15.
 */
public class ContactForm {

    private String id;
    private String itemClassifiedId;
    private String nomAgence;
    private String email;
    private String telephone;
    private String message;
    private String statClassifiedId;
    private String statCustomerId;
    private String copieAuClient;
    private String origine;
    private String civilite;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;


    public ContactForm(String ville, String nom, String prenom, String adresse, String codePostal, String civilite, String id, String email, String message) {
        this.ville = ville;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.civilite = civilite;
        this.id = id;
        this.email = email;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemClassifiedId() {
        return itemClassifiedId;
    }

    public void setItemClassifiedId(String itemClassifiedId) {
        this.itemClassifiedId = itemClassifiedId;
    }

    public String getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatClassifiedId() {
        return statClassifiedId;
    }

    public void setStatClassifiedId(String statClassifiedId) {
        this.statClassifiedId = statClassifiedId;
    }

    public String getStatCustomerId() {
        return statCustomerId;
    }

    public void setStatCustomerId(String statCustomerId) {
        this.statCustomerId = statCustomerId;
    }

    public String getCopieAuClient() {
        return copieAuClient;
    }

    public void setCopieAuClient(String copieAuClient) {
        this.copieAuClient = copieAuClient;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
