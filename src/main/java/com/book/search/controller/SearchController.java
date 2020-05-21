package com.book.search.controller;

import com.book.search.controller.dto.DocumentDto;
import com.book.search.controller.dto.HistoryDto;
import com.book.search.controller.dto.KeywordDto;
import com.book.search.service.HistoryService;
import com.book.search.service.KeywordService;
import com.book.search.service.SearchService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/search")
@RequiredArgsConstructor
public class SearchController {

    @NonNull
    private final SearchService searchService;

    @NonNull
    private final HistoryService historyService;

    @NonNull
    private final KeywordService keywordService;


    @GetMapping("book")
    public Page<DocumentDto> books(@RequestParam final String query,
                                   @PageableDefault(page = 0, size = 10) final Pageable pageable) {
        return searchService.books(query
                , PageRequest.of(pageable.getPageNumber() +1 , pageable.getPageSize()));
    }

    @GetMapping("history")
    public Page<HistoryDto> history(@PageableDefault(page = 0, size = 10
            , sort = "createdDate", direction = Sort.Direction.DESC) final Pageable pageable) {
        return historyService.getHistories(pageable);
    }

    @GetMapping("keyword")
    public List<KeywordDto> keyword() {
        return keywordService.getTop10Keyword();
    }
}
