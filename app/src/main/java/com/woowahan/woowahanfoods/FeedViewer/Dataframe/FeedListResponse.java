package com.woowahan.woowahanfoods.FeedViewer.Dataframe;

import com.woowahan.woowahanfoods.DataModel.Feed;
import com.woowahan.woowahanfoods.Home.Dataframe.RandomRecommendResponse;
import com.woowahan.woowahanfoods.httpConnection.BaseResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FeedListResponse extends BaseResponse {
    public Body body;
    public class Body{
        public ArrayList<Feed> feeds;
        public ArrayList<Feed> relatedFeeds;
    }
}
