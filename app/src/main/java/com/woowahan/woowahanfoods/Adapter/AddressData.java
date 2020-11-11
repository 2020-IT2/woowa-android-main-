package com.woowahan.woowahanfoods.Adapter;

public class AddressData {
    private String tv_dong;
    private String tv_road;
    public String admCd;
    public String rnMgtSn;
    public String udrtYn;
    public String buldMnnm;
    public String buldSlno;

    public AddressData(String tv_dong, String tv_road) {
        this.tv_dong = tv_dong;
        this.tv_road = tv_road;
    }

    public String getTv_dong() {
        return tv_dong;
    }

    public void setTv_dong(String tv_dong) {
        this.tv_dong = tv_dong;
    }

    public String getTv_road() {
        return tv_road;
    }

    public void setTv_road(String tv_road) {
        this.tv_road = tv_road;
    }
}
