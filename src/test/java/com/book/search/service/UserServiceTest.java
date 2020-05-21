package com.book.search.service;

import com.book.search.domain.User;
import com.book.search.exeception.BadRequestException;
import com.book.search.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.spy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    private static boolean setUpUserIsDone = false;

    private final String testUsername1 = "userTest1";
    private final String testUsername2 = "userTest2";
    private final String testUsername3 = "userTest3";
    private final String testPassword = "password";

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Before
    public void setup() {
        userService = spy(new UserService(userRepository, passwordEncoder
                , messageSourceAccessor));

        //계정생성
        if (setUpUserIsDone == false) {
            this.userRepository.save(new User(testUsername3, testPassword));
        }
        setUpUserIsDone = true;
    }

    @Test
    public void register_정상케이스() {

        //act
        String name = userService.register(testUsername1, testPassword);

        //assert
        assertEquals(testUsername1, name);

    }

    @Test(expected = IllegalArgumentException.class)
    public void register_useranme이_null_일때() {

        //act
        String name = userService.register(null, testPassword);

        //assert
        assertEquals(testUsername1, name);
    }

    @Test(expected = IllegalArgumentException.class)
    public void register_password가_null_일때() {

        //act
        String name = userService.register(testUsername1, null);

        //assert
        assertEquals(testUsername1, name);
    }

    @Test(expected = BadRequestException.class)
    public void register_이미_회원이_있을때() {

        //given
        userService.register(testUsername2, testPassword);

        //act
        userService.register(testUsername2, testPassword);
    }

    @Test
    @WithMockUser(testUsername3)
    public void getCurrentUser_정상케이() {

        //act
        User user = userService.getCurrentUser();

        //assert
        assertEquals(testUsername3, user.getUsername());

    }

    @Test
    @WithAnonymousUser
    public void getCurrentUser_Anonymous_User_일때() {

        //act
        User user = userService.getCurrentUser();

        //assert
        assertEquals(null, user);

    }
}