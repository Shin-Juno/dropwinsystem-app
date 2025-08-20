package com.dropwinsystem.app.domain;

public class ItemDetail {
    private int itemId;
    private String size;
    private Integer releaseYear;
    private String country;
    private String material;
    private String itemCondition;
    private String color;
    private String brand;
    private String features;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "ItemDetail{" +
               "itemId=" + itemId +
               ", size='" + size + "'" +
               ", releaseYear=" + releaseYear +
               ", country='" + country + "'" +
               ", material='" + material + "'" +
               ", itemCondition='" + itemCondition + "'" +
               ", color='" + color + "'" +
               ", brand='" + brand +
               ", features='" + features + "'" +
               '}' +
               ';';
    }
}
