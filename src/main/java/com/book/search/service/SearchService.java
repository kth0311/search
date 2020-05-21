package com.book.search.service;

import com.book.search.client.kakao.KakaoApiClient;
import com.book.search.client.kakao.dto.KakaoBookDto;
import com.book.search.client.kakao.dto.KakaoBookQueryMapDto;
import com.book.search.controller.dto.DocumentDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
@RequiredArgsConstructor
public class SearchService {

    @NonNull
    private final KakaoApiClient kakaoApiClient;

    @NonNull
    private final HistoryService historyService;

    @NonNull
    private final KeywordService keywordService;

    @NonNull
    private final MessageSourceAccessor messageSourceAccessor;


    public Page<DocumentDto> books(final String query, final Pageable pageable) {
        Assert.notNull(query, messageSourceAccessor.getMessage("is.null"));
        Assert.notNull(pageable, messageSourceAccessor.getMessage("is.null"));

        KakaoBookDto kakaoBookDto = kakaoApiClient.searchBook(KakaoBookQueryMapDto.builder()
                .query(query)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build());

        historyService.saveHistory(query);
        keywordService.atomicIncrementCountKeyword(query);

        return new PageImpl<>(kakaoBookDto.getDocuments()
                , pageable, kakaoBookDto.getMeta().getTotalCount());

    }

}
