package com.woowahan.woowahanfoods.Dataframe;

import com.woowahan.woowahanfoods.DataModel.MyAddress;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public String curDong;
    public MyAddress curAddress;
    public ArrayList<MyAddress> myAddresses;
}
