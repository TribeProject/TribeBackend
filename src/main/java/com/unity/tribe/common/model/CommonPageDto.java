package com.unity.tribe.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonPageDto<T> {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private T data;

    public static <T> CommonPageDto<T> of(int page, int size, int totalElements, int totalPages, T data) {
        return CommonPageDto.<T>builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .data(data)
                .build();
    }
}
