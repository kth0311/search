package com.book.search.client.kakao.dto;

import com.book.search.controller.dto.DocumentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KakaoBookDto {

    private Meta meta;
    private List<DocumentDto> documents;

    @Getter
    @AllArgsConstructor
    public static class Meta {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;

    }
}
