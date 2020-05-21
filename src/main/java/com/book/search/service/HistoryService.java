package com.book.search.service;

import com.book.search.controller.dto.HistoryDto;
import com.book.search.domain.History;
import com.book.search.domain.User;
import com.book.search.exeception.BusinessException;
import com.book.search.repository.HistoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

    @NonNull
    private final HistoryRepository historyRepository;

    @NonNull
    private final UserService userService;

    @NonNull
    private final MessageSourceAccessor messageSourceAccessor;

    public void saveHistory(String keyword) {
        Assert.notNull(keyword, messageSourceAccessor.getMessage("is.null"));

        User user = userService.getCurrentUser();
        if (user == null) {
            throw new BusinessException("회원 정보가 없습니다.");
        }
        historyRepository.save(new History(user, keyword));
    }

    public Page<HistoryDto> getHistories(Pageable pageable) {
        Assert.notNull(pageable, messageSourceAccessor.getMessage("is.null"));

        User user = userService.getCurrentUser();
        if (user == null) {
            throw new BusinessException("회원 정보가 없습니다.");
        }
        return historyRepository
                .findByUser(user, pageable)
                .map(history -> new HistoryDto(history.getKeyword(), history.getCreatedDate()));
    }

}
