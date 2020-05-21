package com.book.search.client.kakao;

import com.book.search.client.kakao.dto.KakaoBookDto;
import com.book.search.client.kakao.dto.KakaoBookQueryMapDto;
import com.book.search.client.naver.NaverApiClient;
import com.book.search.client.naver.dto.NaverBookDto;
import com.book.search.client.naver.dto.NaverBookQueryMapDto;
import com.book.search.controller.dto.DocumentDto;
import com.google.common.collect.Lists;
import feign.hystrix.FallbackFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KakaoApiClientFallbackFactory implements FallbackFactory<KakaoApiClient> {

    @NonNull
    private final NaverApiClient naverApiClient;

    @Override
    public KakaoApiClient create(Throwable cause) {
        return new KakaoApiClient() {

            @Override
            public KakaoBookDto searchBook(KakaoBookQueryMapDto bookQueryMapDto) {
                return convertToKakaoBook(naverApiClient.searchBook(NaverBookQueryMapDto.builder()
                        .query(bookQueryMapDto.getQuery())
                        .start(bookQueryMapDto.getPage())
                        .display(bookQueryMapDto.getSize()).build())
                        , bookQueryMapDto.getPage());
            }

            private KakaoBookDto convertToKakaoBook(NaverBookDto naverBookDto, Integer page){
                return new KakaoBookDto(new KakaoBookDto.Meta(naverBookDto.getTotal(), page, null)
                        , naverBookDto.getItems()
                        .stream().map(item -> itemToDocument(item))
                        .collect(Collectors.toList()));
            }

            private DocumentDto itemToDocument(NaverBookDto.Item item) {
                return DocumentDto.builder()
                        .authors(Lists.newArrayList(item.getAuthor()))
                        .contents(item.getDescription())
                        .datetime(item.getPubdate())
                        .isbn(item.getIsbn())
                        .price(item.getPrice())
                        .publisher(item.getPublisher())
                        .salePrice(item.getDiscount())
                        .thumbnail(item.getImage())
                        .title(item.getTitle())
                        .url(item.getLink())
                        .translators(Collections.emptyList())
                        .build();
            }
        };
    }
}