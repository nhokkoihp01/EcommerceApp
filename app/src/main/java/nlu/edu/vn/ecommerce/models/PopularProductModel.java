package nlu.edu.vn.ecommerce.models;

import java.io.Serializable;

public class PopularProductModel implements Serializable {
    private String img_url;
    private String description;
    private String name;
    private int price;
    private String rating;

    public PopularProductModel() {
    }

    public PopularProductModel(String img_url, String description, String name, int price, String rating) {
        this.img_url = img_url;
        this.description = description;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
