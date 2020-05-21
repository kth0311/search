package com.book.search.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDto<T> {
    private T data;

    private int status;
    private int code;
    private String message;

    public ErrorDto(T body) {
        this.data = body;
    }

}
