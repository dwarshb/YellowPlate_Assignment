package com.bhadra.dwarsh.yellowplateassignment.Interface;

import com.bhadra.dwarsh.yellowplateassignment.Pojo.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GithubClient {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id")String clientid,
            @Field("client_secret")String clientsecret,
            @Field("code") String code
    );
}
