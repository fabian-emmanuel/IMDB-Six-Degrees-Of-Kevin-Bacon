package com.lunatech.imdb.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class PageUtil {
    public static Pageable normalisePageRequest(int pageNo, int pageSize) {
        return  PageRequest.of(Math.max(0, pageNo - 1), pageSize);
    }
}
