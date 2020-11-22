package com.woowahan.woowahanfoods.Dataframe;

import com.woowahan.woowahanfoods.DataModel.Juso;

public class SearchResultJson2 {
    public Result results;

    public class Result{
        public Common common;
        public Juso juso;

        public class Common {
            public String errorMessage;
            public String countPerPage;
            public String totalCount;
            public String errorCode;
            public String currentPage;
        }

    }
}
