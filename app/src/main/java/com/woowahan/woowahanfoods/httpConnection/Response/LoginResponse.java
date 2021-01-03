package com.woowahan.woowahanfoods.httpConnection.Response;

import com.woowahan.woowahanfoods.DataModel.User;
import com.woowahan.woowahanfoods.httpConnection.BaseResponse;

import java.io.Serializable;

public class LoginResponse extends BaseResponse implements Serializable {
    public User body;
}
