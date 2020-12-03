package com.woowahan.woowahanfoods.DataModel;

public class Restaurant {
    public int restaurantID;
    public String restaurantName;
    public Boolean isFranchise;
    public int feedNum;
    public String type;
    public String subRegion;
    public String adrDong;
    public String adrStreet;
    public String mediaURL;
    public double lat;
    public double lon;
    public String restaurantDetail;

    public int likes;
    public int replys;
    public String id;

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
        this.restaurantDetail = restaurantDetail;
        this.likes = likes;
        this.replys = replys;
        this.lat = lat;
        this.lon = lon;
    }
}
