package com.book.search.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "search.restclient")
public class RestClientProperties {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    public static final String HEADER_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";

    private String kakaoApiKey = "KakaoAK 8b6b6fbe7681a240449d4a5d8e0c775a";
    private String naverClientId = "9kUnQJrkQ5KRyAc6l9LK";
    private String naverClientSecret = "Zdtsj7WeZN";
    private External external = new External();

    @Getter
    @Setter
    public static class External{
        private String kakao;
        private String naver;
    }

}
