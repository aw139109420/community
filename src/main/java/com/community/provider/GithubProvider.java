package com.community.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.community.dto.AccessTokenDto;
import com.community.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {

	/**
	 * 获取AccessToken
	 * 
	 * @param accessTokenDto
	 * @return
	 */
	public String getAccessToken(AccessTokenDto accessTokenDto) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
		Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
		try {
			Response response = client.newCall(request).execute();
			String accesstoken = response.body().string();
			accesstoken = accesstoken.split("&")[0].split("=")[1].toString();
			return accesstoken;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取GithubUser
	 * 
	 * @return
	 */
	public GithubUser getGithubUser(String accesstoken) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.github.com/user?access_token="+accesstoken).build();
		try {
			Response response = client.newCall(request).execute();
			return JSON.parseObject(response.body().string(), GithubUser.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
