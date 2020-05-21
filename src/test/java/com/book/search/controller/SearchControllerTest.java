package com.book.search.controller;

import com.book.search.domain.User;
import com.book.search.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SearchControllerTest {

    private final MediaType contentType = new MediaType((MediaType.APPLICATION_JSON.getType()),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    private final String bookUrl = "searchControllerTest1";

    private final String testUsername1 = "searchControllerTest1";
    private final String testPassword = "password";
    private static boolean setUpSearchControllerIsDone = false;



    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext was;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(was)
                .defaultRequest(get("/").contentType(contentType))
                .build();

        //계정생성
        if (setUpSearchControllerIsDone == false) {
            this.userRepository.save(new User(testUsername1, testPassword));

        }
        setUpSearchControllerIsDone = true;
    }

    @Test
    @WithMockUser(testUsername1)
    public void books() throws Exception {
        String url = "/v1/search/book";
        MvcResult result = mockMvc.perform(get(url).param("query", "test"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @WithMockUser(testUsername1)
    public void history() throws Exception {

        String url = "/v1/search/history";
        MvcResult result = mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void keyword() throws Exception {
        String url = "/v1/search/keyword";
        MvcResult result = mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}