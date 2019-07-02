package com.bhadra.dwarsh.yellowplateassignment.Interface;

import com.bhadra.dwarsh.yellowplateassignment.Pojo.GitUsers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    String url = "https://api.github.com";
    @GET("users")
    Call<List<GitUsers>> getusers();
}
