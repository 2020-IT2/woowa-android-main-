package com.woowahan.woowahanfoods.Market.Fragment;

public class Region {
    private String result__data__period;
    private int result__data__value;
    private String result__title;

    public String getDate() {
        return result__data__period;
    }

    public int getValue() {
        return result__data__value;
    }

    public String getRegion() {
        return result__title;
    }

    public void setDate(int date) {
        this.result__data__period = Integer.toString(date);
    }

    public void setValue(int value) {
        this.result__data__value = value;
    }

    public void setRegion(String region) {
        this.result__title = region;
    }
}
