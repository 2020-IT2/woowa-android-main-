package com.woowahan.woowahanfoods.Home.Dataframe;

import com.woowahan.woowahanfoods.DataModel.Feed;
import com.woowahan.woowahanfoods.httpConnection.BaseResponse;

import java.util.ArrayList;

public class RandomRecommendResponse extends BaseResponse {
    public Body body;
    public class Body{
        public String type;
        public ArrayList<Feed> feeds;
    }
}
