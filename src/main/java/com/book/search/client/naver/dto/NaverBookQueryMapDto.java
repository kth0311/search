package com.book.search.client.naver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NaverBookQueryMapDto {
    private String query;
    private Integer display;
    private Integer start;
    private String sort;
    private String dTitl;
    private String dAuth;
    private String dCont;
    private String dIsbn;
    private String dPubl;
    private String dDafr;
    private String dDato;
    private String dCatg;
}
