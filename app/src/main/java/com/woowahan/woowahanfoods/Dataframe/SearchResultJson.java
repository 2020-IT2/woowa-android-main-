package com.woowahan.woowahanfoods.Dataframe;

import java.util.ArrayList;

public class SearchResultJson {
    public Result results;

    public class Result{
        public Common common;
        public ArrayList<Juso> juso;

        public class Common {
            public String errorMessage;
            public String countPerPage;
            public String totalCount;
            public String errorCode;
            public String currentPage;
        }

    }
}
