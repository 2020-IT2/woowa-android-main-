package com.woowahan.woowahanfoods.Market.Fragment;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Region {
    private int result__data__period;
    private int result__data__value;
    private String result__title;

    public String getDate() {
        return Integer.toString(result__data__period);
    }

    public int getValue() {
        return result__data__value;
    }

    public String getRegion() {
        return result__title;
    }

    public void setDate(int date) {
        this.result__data__period = date;
    }

    public void setValue(int value) {
        this.result__data__value = value;
    }

    public void setRegion(String region) {
        this.result__title = region;
    }
}
