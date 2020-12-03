package com.woowahan.woowahanfoods.RestaurantList.Dataframe;

import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.httpConnection.BaseResponse;

import java.util.ArrayList;

public class RestaurantListResponse extends BaseResponse {
    public Body body;

    public class Body{
        public ArrayList<Restaurant> franchise;
        public ArrayList<Restaurant> non_franchise;
    }
}
