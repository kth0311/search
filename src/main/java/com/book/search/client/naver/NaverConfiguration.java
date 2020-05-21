package com.book.search.client.naver;

import com.book.search.config.RestClientProperties;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class NaverConfiguration {

    @NonNull
    private final RestClientProperties restClientProperties;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
                .header(RestClientProperties.HEADER_NAVER_CLIENT_ID, restClientProperties.getNaverClientId())
                .header(RestClientProperties.HEADER_NAVER_CLIENT_SECRET, restClientProperties.getNaverClientSecret());
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

}