package com.book.search.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class HistoryDto {
    private String keyword;
    private LocalDateTime createdDate;
}
