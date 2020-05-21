package com.book.search.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DocumentDto {
    private String title;
    private String contents;
    private String url;
    private String isbn;
    private String datetime;
    private List<String> authors;
    private String publisher;
    private List<String> translators;
    private Integer price;
    @JsonProperty(value = "sale_price")
    private Integer salePrice;
    private String thumbnail;
    private String status;
}
