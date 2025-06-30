package com.unity.tribe.common.model.enums;

import lombok.Getter;

@Getter
public enum ExternalApiUrl {
    GET_NAVER_PROFILE("https://openapi.naver.com","/v1/nid/me", "네이버 회원 프로필 조회");
    private final String BaseUrl;
    private final String api;
    private final String description;

    ExternalApiUrl(String baseUrl, String api, String description) {
        BaseUrl = baseUrl;
        this.api = api;
        this.description = description;
    }
}
