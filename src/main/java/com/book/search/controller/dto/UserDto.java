package com.book.search.controller.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NonNull
    @NotEmpty
    String username;

    @NotEmpty
    private String password;
}
