package com.unity.tribe.core;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonPageDto<T> {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private T data;

    public static <T> CommonPageDto of(int page, int size, int totalElements, int totalPages, T data) {
        return CommonPageDto.builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .data(data)
                .build();
    }
}
