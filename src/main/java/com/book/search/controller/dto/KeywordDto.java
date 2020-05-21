package com.book.search.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class KeywordDto {
    private String keyword;
    private Long count;
}
