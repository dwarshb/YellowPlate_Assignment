package com.bhadra.dwarsh.yellowplateassignment.Pojo;

import com.google.gson.annotations.SerializedName;

public class GitUsers {
    @SerializedName("login")
    private String login;
    @SerializedName("id")
    private String id;
    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("html_url")
    private String html_url;

    public GitUsers(String login, String id, String avatar_url, String html_url)
    {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getId() {
        return id;
    }
}
