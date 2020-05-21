package com.book.search.service;

import com.book.search.controller.dto.HistoryDto;
import com.book.search.domain.History;
import com.book.search.domain.User;
import com.book.search.exeception.BusinessException;
import com.book.search.repository.HistoryRepository;
import com.book.search.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HistoryServiceTest {

    private final String testUsername1 = "historyTest1";
    private final String testUsername2 = "historyTest2";
    private final String testUsername3 = "historyTest3";
    private final String testPassword = "password";

    private static boolean setUpHistoryIsDone = false;

    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Before
    public void setup() {
        historyService = spy(new HistoryService(historyRepository, userService
                , messageSourceAccessor));
        //계정생성
        if (setUpHistoryIsDone == false) {
            this.userRepository.save(new User(testUsername1, testPassword));
            this.userRepository.save(new User(testUsername2, testPassword));

        }
        setUpHistoryIsDone = true;
    }


    @Test
    @WithMockUser(testUsername1)
    public void saveHistory_정상케이스() {
        //given
        String query = "test_query";
        User user = userRepository.findByUsername(testUsername1);

        //act
        historyService.saveHistory(query);

        //assert
        List<History> histories = historyRepository.findByUser(user);
        int expectedSize = 1;
        assertEquals(expectedSize, histories.size());

    }

    @Test(expected = IllegalArgumentException.class)
    public void saveHistory_키워드_null인_경우() {
        //given
        String query = null;

        //act
        historyService.saveHistory(query);
    }

    @WithMockUser(testUsername3)
    @Test(expected = BusinessException.class)
    public void saveHistory_user가_null인_경우() {
        //given
        String query = "test_query";

        //act
        historyService.saveHistory(query);
    }

    @WithMockUser(testUsername2)
    @Test
    public void getHistories_정상케이스() {
        //given
        Pageable pageable = PageRequest
                .of(0, 10, Sort.by(Sort.Order.desc("createdDate")));
        String query1 = "test_query1";
        String query2 = "test_query2";
        historyService.saveHistory(query1);
        historyService.saveHistory(query2);

        //act
        Page<HistoryDto> histories = historyService.getHistories(pageable);

        //assert
        int expectedSize = 2;
        assertEquals(expectedSize, histories.getContent().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHistories_page_null인_경우() {
        //given
        Pageable pageable = null;

        //act
        historyService.getHistories(pageable);
    }

    @WithMockUser(testUsername3)
    @Test(expected = BusinessException.class)
    public void getHistories_user가_null인_경우() {
        //given
        Pageable pageable = PageRequest
                .of(0, 10, Sort.by(Sort.Order.desc("createdDate")));

        //act
        historyService.getHistories(pageable);
    }

}