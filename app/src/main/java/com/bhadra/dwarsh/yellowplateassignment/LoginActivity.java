package com.bhadra.dwarsh.yellowplateassignment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alorma.github.sdk.bean.dto.response.Token;
import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.github.sdk.services.login.RequestTokenClient;
import com.alorma.github.sdk.services.user.GetAuthUserClient;
import com.bhadra.dwarsh.yellowplateassignment.Interface.API;
import com.bhadra.dwarsh.yellowplateassignment.Interface.GithubClient;
import com.bhadra.dwarsh.yellowplateassignment.Pojo.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;

public class LoginActivity extends AppCompatActivity {
    WebView webView;
    private FirebaseAuth mAuth;
    private final String clientId = "10b692ae7abd556868b3";
    private final String clientSecret = "2be8c15c97fe711233d7ad247471bdd248cf50cd";
    private final String redirectUri = "https://yellowplate-bbd4f.firebaseapp.com/__/auth/handler";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority("github.com")
                .appendPath("login")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", clientId)
                .appendQueryParameter("scope", "user,user:email")
                .build();
        webView = (WebView)findViewById(R.id.webvw);
        webView.loadUrl( uri.toString() );
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Uri uri = Uri.parse(url);
                if( uri.getQueryParameter("code") != null
                        && uri.getScheme() != null
                        && uri.getScheme().equalsIgnoreCase("https") ){

                    requestGitHubUserAccessToken( uri.getQueryParameter("code") );
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //AuthCredential credential = GithubAuthProvider.getCredential(token);

    }
    private void requestGitHubUserAccessToken( String code ){
        RequestTokenClient requestTokenClient = new RequestTokenClient(
                code,
                clientId,
                clientSecret,
                redirectUri
        );

        requestTokenClient
                .observable()
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR",e.getMessage());
                    }

                    @Override
                    public void onNext(Token token) {
                        if( token.access_token != null ){
                            requestGitHubUserData( token.access_token );
                        }
                    }
                });
    }
    private void requestGitHubUserData( final String accessToken ){
        GetAuthUserClient getAuthUserClient = new GetAuthUserClient( accessToken );
        getAuthUserClient
                .observable()
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(com.alorma.github.sdk.bean.dto.response.User user) {
                        // LoginActivity.this.user.setName( user.name );
                        // LoginActivity.this.user.setEmail( user.email );
                        accessGithubLoginData( accessToken );
                    }
                });
    }
    private void accessGithubLoginData(String accessToken){
        accessLoginData(
                "github",
                accessToken
        );
    }
    private void accessLoginData( String provider, String... tokens ){
        if( tokens != null
                && tokens.length > 0
                && tokens[0] != null ){

            AuthCredential credential = GithubAuthProvider.getCredential( tokens[0]);
            credential = provider.equalsIgnoreCase("github") ? GithubAuthProvider.getCredential( tokens[0] ) : credential;

            mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( !task.isSuccessful() ){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Log.e("ERROR","SUCCESS");
                    }
                }
            });
        }
        else{
            mAuth.signOut();
        }
    }

}
