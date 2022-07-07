package com.hae.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface PageUtil {
    static <T> Page<T> createPageFromList(List<T> list, Pageable pageable) {
        if (list == null) {
            throw new IllegalArgumentException("To create a Page, the list mustn't be null!");
        } else {
            int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
            if (startOfPage > list.size()) {
                return new PageImpl(new ArrayList(), pageable, 0L);
            } else {
                int endOfPage = Math.min(startOfPage + pageable.getPageSize(), list.size());
                return new PageImpl(list.subList(startOfPage, endOfPage), pageable, (long)list.size());
            }
        }
    }
}
