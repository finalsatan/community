package com.murphy.community.provider;

import com.alibaba.fastjson.JSON;
import com.murphy.community.dto.AccessTokenDTO;
import com.murphy.community.dto.GithubUser;
import com.murphy.community.dto.ResponseAccessToken;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 233murphy
 */
@Component
public class GithubProvider {
    @Value("${github.access.token.uri}")
    private String accessTokenUri;

    @Value("${github.user.uri}")
    private String userUri;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url(accessTokenUri)
                .post(body)
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            ResponseAccessToken responseAccessToken = JSON.parseObject(str, ResponseAccessToken.class);
            return responseAccessToken.getAccess_token();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(userUri)
                .header("Authorization", "token " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
