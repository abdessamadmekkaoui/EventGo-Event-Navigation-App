package com.example.avent_go.Domains;
import java.io.Serializable;
public class PopularDomain implements Serializable {
    private String title;
    private String location;
    private String category;
    private String url;
    private String description;
    private int place;
    private boolean eat;
    private boolean isFavorite;
    private double score;
    private String pic;
    private boolean wifi;
    private int price;
    public PopularDomain(String title, String location,String category,String description, int place, boolean eat,boolean favorite, double score, String pic, boolean wifi, int price,String url) {
        this.title = title;
        this.location = location;
        this.category = category;
        this.url = url;
        this.description = description;
        this.place = place;
        this.eat = eat;
        this.isFavorite = favorite;
        this.score = score;
        this.pic = pic;
        this.wifi = wifi;
        this.price = price;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getPlace() {
        return place;
    }
    public void setPlace(int place) {
        this.place = place;
    }
    public boolean isGuide() {
        return eat;
    }
    public void setGuide(boolean guide) {
        this.eat = guide;
    }
    public boolean isFavorite() {return isFavorite;}
    public void setFavorite(boolean favorite) {isFavorite = favorite;}
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public boolean isWifi() {
        return wifi;
    }
    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
