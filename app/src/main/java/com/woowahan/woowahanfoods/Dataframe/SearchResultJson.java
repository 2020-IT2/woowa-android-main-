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

        public class Juso{
            public String detBdNmList;
            public String engAddr;
            public String rn;
            public String emdNm;
            public String zipNo;
            public String roadAddrPart2;
            public String emdNo;
            public String sggNm;
            public String jibunAddr;
            public String siNm;
            public String roadAddrPart1;
            public String bdNm;
            public String admCd;
            public String udrtYn;
            public String lnbrMnnm;
            public String roadAddr;
            public String lnbrSlno;
            public String buldMnnm;
            public String bdKdcd;
            public String liNm;
            public String rnMgtSn;
            public String mtYn;
            public String bdMgtSn;
            public String buldSlno;
        }
    }
}
