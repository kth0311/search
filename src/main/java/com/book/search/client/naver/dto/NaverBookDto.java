package com.book.search.client.naver.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverBookDto {

    private Integer total;
    private Integer start;
    private Integer display;
    private List<Item> items;

    @Getter
    public static class Item {
        private String title;
        private String link;
        private String image;
        private String author;
        private Integer price;
        private Integer discount;
        private String publisher;
        private String isbn;
        private String description;
        private String pubdate;
    }
}
