package com.book.search.client.kakao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class KakaoBookQueryMapDto {
    private String query;
    private String sort;
    private Integer page;
    private Integer size;
    private String target;
    private String title;
}
