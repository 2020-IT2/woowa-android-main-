package com.woowahan.woowahanfoods.Dataframe;

public class MyAddress {
    public String dongAddress;
    public String roadAddress;
    public double latitude;
    public double longitude;


    public MyAddress(String dongAddress, String roadAddress, double latitude, double longitude){
        this.dongAddress = dongAddress;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
