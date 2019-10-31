package com.murphy.community.controller;

import com.alibaba.fastjson.JSON;
import com.murphy.community.dto.AccessTokenDTO;
import com.murphy.community.dto.GithubUser;
import com.murphy.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setClient_id("53cd0606229ddef3e6e0");
        accessTokenDTO.setClient_secret("2aeba1bce87bc6495f9e7421f0fe4f2932b9b68c");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(JSON.toJSONString(githubUser));
        return "index";
    }
}
