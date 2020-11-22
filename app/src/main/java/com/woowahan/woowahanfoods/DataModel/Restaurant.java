package com.woowahan.woowahanfoods.DataModel;

public class Restaurant {
    public String restaurantName;
    public String restaurantDetail;
    public int likes;
    public int replys;
    public String id;
    public String address;
    public double lat;
    public double lon;
    public String media_url;
    public int restaurantID;
    public int schoolID;
    public int regionID;
    public String schoolName;
    public String regionName;
    public String townName;
    public int gender;
    public String contact;
    public String homePage;


    public Restaurant(String restaurantName, String restaurantDetail, String address, int likes, int replys, double lat, double lon, String media_url){
        this.restaurantName = restaurantName;
        this.address = address;
        this.restaurantDetail = restaurantDetail;
        this.likes = likes;
        this.replys = replys;
        this.lat = lat;
        this.lon = lon;
        this.media_url = media_url;
    }
}
