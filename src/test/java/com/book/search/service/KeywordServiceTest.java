package com.book.search.service;

import com.book.search.controller.dto.KeywordDto;
import com.book.search.domain.Keyword;
import com.book.search.repository.KeywordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KeywordServiceTest {

    private KeywordService keywordService;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Before
    public void setup() {
        keywordService = spy(new KeywordService(keywordRepository, messageSourceAccessor));
    }

    @Test
    public void saveKeyword_정상케이스() {

        //given
        String query = "test_qeury";

        //act
        keywordService.atomicIncrementCountKeyword(query);
        Keyword keyword1 = keywordRepository.findByKeyword(query);
        keywordService.atomicIncrementCountKeyword(query);
        Keyword keyword2 = keywordRepository.findByKeyword(query);

        //assert
        Long expectedCount1 = 1L;
        Long expectedCount2 = 2L;
        assertEquals(expectedCount1, keyword1.getCount());
        assertEquals(expectedCount2, keyword2.getCount());

    }

    @Test
    public void saveKeyword_동시성_테스트() {

        //given
        String query = "concurrent_test_qeury";

        //act
        keywordService.atomicIncrementCountKeyword(query);
        Set<Long> keywordCount = ConcurrentHashMap.newKeySet();
        IntStream.range(0, 100).parallel().forEach(value -> {
            Keyword keyword = keywordService.atomicIncrementCountKeyword(query);
            keywordCount.add(keyword.getCount());
        });

        //assert
        int expectedCount = 100;
        assertEquals(expectedCount, keywordCount.size());

    }


    @Test(expected = IllegalArgumentException.class)
    public void saveKeyword_키워드_null인_경우() {
        //given
        String query = null;

        //act
        keywordService.atomicIncrementCountKeyword(query);
    }

    @Test
    public void getTop10Keyword_정상케이스() {

        //given
        Keyword keyword1 = new Keyword("top10_test1", 1001L);
        Keyword keyword2 = new Keyword("top10_test2", 1002L);
        Keyword keyword3 = new Keyword("top10_test3", 1003L);
        Keyword keyword4 = new Keyword("top10_test4", 1004L);
        Keyword keyword5 = new Keyword("top10_test5", 1005L);
        Keyword keyword6 = new Keyword("top10_test6", 1006L);
        Keyword keyword7 = new Keyword("top10_test7", 1007L);
        Keyword keyword8 = new Keyword("top10_test8", 1008L);
        Keyword keyword9 = new Keyword("top10_test9", 1009L);
        Keyword keyword10 = new Keyword("top10_test10", 1010L);
        Keyword keyword11 = new Keyword("top10_test11", 1011L);

        List<Keyword> keywords = Arrays.asList(
                keyword1, keyword2, keyword3, keyword4, keyword5
                , keyword6, keyword7, keyword8, keyword9, keyword10, keyword11
        );

        keywordRepository.saveAll(keywords);


        //act
        List<KeywordDto> keywordDtos = keywordService.getTop10Keyword();

        //assert
        Long expectedCount = 1011L;
        assertEquals(expectedCount, keywordDtos.get(0).getCount());

    }
}