package com.adenclassifieds.ei9.business;

import java.util.List;

/**
 * Created by Antoine GALTIER on 11/02/15.
 */
public class Logement {

    private String surface_unit;
    private int surface_certification;
    private String rcs;
    private String creation_date;
    private String modification_date;
    private String ad_type;
    private int floor;
    private String ref;
    private float amount_min;
    private float amount_max;
    private List<String> photos_urls;
    private int nb_rooms;
    private float surface;
    private float surface_min;
    private float surface_max;

    public String getSurface_unit() {
        return surface_unit;
    }

    public void setSurface_unit(String surface_unit) {
        this.surface_unit = surface_unit;
    }

    public int getSurface_certification() {
        return surface_certification;
    }

    public void setSurface_certification(int surface_certification) {
        this.surface_certification = surface_certification;
    }

    public String getRcs() {
        return rcs;
    }

    public void setRcs(String rcs) {
        this.rcs = rcs;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getModification_date() {
        return modification_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public float getAmount_min() {
        return amount_min;
    }

    public void setAmount_min(float amount_min) {
        this.amount_min = amount_min;
    }

    public List<String> getPhotos_urls() {
        return photos_urls;
    }

    public void setPhotos_urls(List<String> photos_urls) {
        this.photos_urls = photos_urls;
    }

    public int getNb_rooms() {
        return nb_rooms;
    }

    public void setNb_rooms(int nb_rooms) {
        this.nb_rooms = nb_rooms;
    }

    public float getSurface() {
        return surface;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public float getAmount_max() {
        return amount_max;
    }

    public void setAmount_max(float amount_max) {
        this.amount_max = amount_max;
    }

    public float getSurface_min() {
        return surface_min;
    }

    public void setSurface_min(float surface_min) {
        this.surface_min = surface_min;
    }

    public float getSurface_max() {
        return surface_max;
    }

    public void setSurface_max(float surface_max) {
        this.surface_max = surface_max;
    }
}
