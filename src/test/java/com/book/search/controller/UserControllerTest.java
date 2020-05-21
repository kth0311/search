package com.book.search.controller;

import com.book.search.controller.dto.UserDto;
import com.book.search.domain.History;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    private final MediaType contentType = new MediaType((MediaType.APPLICATION_JSON.getType()),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext was;

    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(was)
                .defaultRequest(get("/").contentType(contentType))
                .build();
    }

    @Test
    public void registration() throws Exception {
        //given
        String username = "test";
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(username);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(userDto);

        String url = "/v1/user";

        //act
        MvcResult result = mockMvc.perform(post(url).content(requestJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        UserDto body = new ObjectMapper().readValue(result.getResponse().getContentAsString(), UserDto.class);

        //assert
        assertEquals(username, body.getUsername());
    }
}