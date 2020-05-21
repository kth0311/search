package com.book.search.service;

import com.book.search.controller.dto.KeywordDto;
import com.book.search.domain.Keyword;
import com.book.search.exeception.BusinessException;
import com.book.search.repository.KeywordRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

    @NonNull
    private final KeywordRepository keywordRepository;

    @NonNull
    private final MessageSourceAccessor messageSourceAccessor;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Keyword atomicIncrementCountKeyword(String query) {
        int retryCount = 10;
        while (--retryCount >= 0) {
            try {
                return incrementCountKeyword(query);
            } catch (ObjectOptimisticLockingFailureException e) {
                log.warn("objectOptimisticLockingFailureException retrying - " + e.getMessage());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    log.warn("objectOptimisticLockingFailureException sleep - " + e1.getMessage());
                }
            }
        }
        throw  new BusinessException("OptimisticLock - Maximum retry limit exceeded");
    }


    private Keyword incrementCountKeyword(String query) {
        Assert.notNull(query, messageSourceAccessor.getMessage("is.null"));
        Keyword keyword = keywordRepository.findByKeyword(query);

        if (keyword == null) {
            return keywordRepository.save(new Keyword(query, 1L));
        }
        keyword.setCount(keyword.getCount() + 1L);
        return keywordRepository.save(keyword);
    }


    @Cacheable("keyword-top-ten")
    public List<KeywordDto> getTop10Keyword() {
        return keywordRepository.findTop10ByOrderByCountDesc().stream()
                .map(keyword -> new KeywordDto(keyword.getKeyword(), keyword.getCount()))
                .collect(Collectors.toList());
    }
}
