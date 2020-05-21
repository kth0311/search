package com.book.search.client.kakao;

import com.book.search.config.RestClientProperties;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
public class KakaoConfiguration {

    @NonNull
    private final RestClientProperties restClientProperties;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
                .header(RestClientProperties.HEADER_AUTHORIZATION, restClientProperties.getKakaoApiKey());
    }

    @Bean
    public Retryer retryer(){
        return new Retryer.Default();
    }
}
