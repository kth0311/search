package com.book.search.client.naver;

import com.book.search.client.naver.dto.NaverBookDto;
import com.book.search.client.naver.dto.NaverBookQueryMapDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "naver", url = "${search.restclient.external.naver}", configuration = {NaverConfiguration.class})
public interface NaverApiClient {
    @GetMapping("/v1/search/book.json")
    NaverBookDto searchBook(@SpringQueryMap NaverBookQueryMapDto bookQueryMapDto);
}
