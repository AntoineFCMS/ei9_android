package com.adenclassifieds.ei9.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by antoinegaltier on 11/02/15.
 */
public class Program {

    private String name;
    private String type;
    private String delivery_date;
    private String modification_date;
    private String ref;
    private int immediate_delivery;
    private List<Logement> logements;
    private String description;
    private String description_promoter;
    private String promoter_name;
    private String investmentLaws;
    private String adress;
    private String code_postal;
    private String city;
    private String dpt_code;
    private String dpt_label;
    private String country;
    private String region;
    private float longitude;
    private float latitude;
    private List<String> options;
    private ArrayList<String> photos_urls;
    private int underConstruction;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getModification_date() {
        return modification_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getImmediate_delivery() {
        return immediate_delivery;
    }

    public void setImmediate_delivery(int immediate_delivery) {
        this.immediate_delivery = immediate_delivery;
    }

    public List<Logement> getLogements() {
        return logements;
    }

    public void setLogements(List<Logement> logements) {
        this.logements = logements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_promoter() {
        return description_promoter;
    }

    public void setDescription_promoter(String description_promoter) {
        this.description_promoter = description_promoter;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDpt_code() {
        return dpt_code;
    }

    public void setDpt_code(String dpt_code) {
        this.dpt_code = dpt_code;
    }

    public String getDpt_label() {
        return dpt_label;
    }

    public void setDpt_label(String dpt_label) {
        this.dpt_label = dpt_label;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public ArrayList<String> getPhotos_urls() {
        return photos_urls;
    }

    public void setPhotos_urls(ArrayList<String> photos_urls) {
        this.photos_urls = photos_urls;
    }

    public int getUnderConstruction() {
        return underConstruction;
    }

    public void setUnderConstruction(int underConstruction) {
        this.underConstruction = underConstruction;
    }

    public String getInvestmentLaws() {
        return investmentLaws;
    }

    public void setInvestmentLaws(String investmentLaws) {
        this.investmentLaws = investmentLaws;
    }

    public String getPromoter_name() {
        return promoter_name;
    }

    public void setPromoter_name(String promoter_name) {
        this.promoter_name = promoter_name;
    }
}
