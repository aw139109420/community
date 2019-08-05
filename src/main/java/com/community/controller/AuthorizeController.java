package com.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.community.dto.AccessTokenDto;
import com.community.dto.GithubUser;
import com.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	@Autowired
	private GithubProvider githubProvider;
	
	@Value("${github.client.id}")
	private String clientId;
	
	@Value("${github.client.secret}")
	private String clientSecret;
	
	@Value("${github.Rrdirect_uri}")
	private String redirectUri;

	@GetMapping("/callback")
	public String callBack(@RequestParam("code") String code, @RequestParam("state") String state) {
		AccessTokenDto accessTokenDto = new AccessTokenDto();
		accessTokenDto.setClient_id(clientId);
		accessTokenDto.setClient_secret(clientSecret);
		accessTokenDto.setCode(code);
		accessTokenDto.setRedirect_uri(redirectUri);
		accessTokenDto.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDto);
		GithubUser githubUser = githubProvider.getGithubUser(accessToken);
		System.out.println(githubUser);
		return "index";
	}
}
