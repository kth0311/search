package com.book.search.service;

import com.book.search.domain.User;
import com.book.search.exeception.BadRequestException;
import com.book.search.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class UserService {

    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final PasswordEncoder passwordEncoder;
    @NonNull
    private final MessageSourceAccessor messageSourceAccessor;

    public String register(String username, String password) {
        Assert.notNull(username, messageSourceAccessor.getMessage("is.null"));
        Assert.notNull(password, messageSourceAccessor.getMessage("is.null"));

        if (userRepository.findByUsername(username) != null) {
            throw new BadRequestException("이미 존재하는 회원입니다.");
        }
        userRepository.save(new User(username, passwordEncoder.encode(password)));
        return username;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String currentUsername = authentication.getName();
        return userRepository.findByUsername(currentUsername);
    }
}
