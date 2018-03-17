package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gazorpazorp.client.config.ImgurTokenRequestPasswordConfiguration;
import com.gazorpazorp.model.imgur.ImgurResp;

import feign.Headers;
import feign.Param;

@FeignClient(name="imgur-client", url="https://api.imgur.com/3", configuration = ImgurTokenRequestPasswordConfiguration.class)
public interface ImgurClient {
	
	static final String ALBUM_ID = "sdkzx";
	
	@GetMapping("/image/GGLM4P8")
	public ResponseEntity<ImgurResp> response();
	
	@PostMapping(value="/image", produces= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@Headers("Content-Type: application/x-www-form-urlencoded")
	public ResponseEntity<ImgurResp> uploadImage(@Param("image") String image, @RequestParam("album") String album);
}
