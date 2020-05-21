package com.book.search.client.kakao;

import com.book.search.client.kakao.dto.KakaoBookDto;
import com.book.search.client.kakao.dto.KakaoBookQueryMapDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "kakao", url = "${search.restclient.external.kakao}",
        configuration = {KakaoConfiguration.class}, fallbackFactory = KakaoApiClientFallbackFactory.class)
public interface KakaoApiClient {

    @GetMapping("/v3/search/book")
    KakaoBookDto searchBook(@SpringQueryMap KakaoBookQueryMapDto bookQueryMapDto);
}
