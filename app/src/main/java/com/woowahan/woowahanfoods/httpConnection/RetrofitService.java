package com.woowahan.woowahanfoods.httpConnection;

import com.google.gson.JsonObject;
import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.Dataframe.SearchResultJson2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/addrlink/addrLinkApi.do")
    Call<SearchResultJson> searchAddress(@Query("confmKey") String confmKey, @Query("currentPage") int currentPage, @Query("countPerPage") int countPerPage, @Query("keyword") String keyword, @Query("resultType") String resultType);

    @GET("/addrlink/addrCoordApi.do")
    Call<SearchResultJson> convertor(@Query("confmKey") String confmKey, @Query("admCd") String admCd, @Query("rnMgtSn") String rnMgtSn, @Query("udrtYn") String udrtYn, @Query("buldMnnm") int buldMnnm, @Query("buldSlno") int buldSlno, @Query("resultType") String resultType);
}
