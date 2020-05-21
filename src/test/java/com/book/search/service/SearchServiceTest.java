package com.book.search.service;

import com.book.search.client.kakao.KakaoApiClient;
import com.book.search.client.kakao.dto.KakaoBookDto;
import com.book.search.controller.dto.DocumentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchServiceTest {

    private SearchService searchService;

    private KakaoApiClient kakaoApiClient;

    private HistoryService historyService;

    private KeywordService keywordService;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Before
    public void setup() {
        this.kakaoApiClient = mock(KakaoApiClient.class);
        this.historyService = mock(HistoryService.class);
        this.keywordService = mock(KeywordService.class);
        searchService = spy(new SearchService(kakaoApiClient, historyService
                , keywordService, messageSourceAccessor));
    }

    @Test
    public void books_정상케이스() {
        //given
        String query = "query_test";
        Pageable pageable = PageRequest.of(1, 10);
        KakaoBookDto.Meta meta = new KakaoBookDto.Meta(10, 0, false);
        DocumentDto documentDto = DocumentDto.builder().title("test").build();
        KakaoBookDto kakaoBookDto = new KakaoBookDto(meta, Arrays.asList(documentDto));

        //act
        when(kakaoApiClient.searchBook(any())).thenReturn(kakaoBookDto);

        Page<DocumentDto> books = searchService.books(query, pageable);

        //assert
        int expectedSize = 1;
        assertEquals(expectedSize, books.getContent().size());

    }
}