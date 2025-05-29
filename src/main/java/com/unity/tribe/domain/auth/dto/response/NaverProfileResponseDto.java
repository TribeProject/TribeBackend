package com.unity.tribe.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NaverProfileResponseDto {
    private String resultcode;
    private String message;

    @JsonProperty("response")
    private NaverProfile profile;
    @Builder
    @Getter
    public static class NaverProfile {
        private String email;
        private String nickname;
        private String profile_image;
        private String age;
        private String gender;
        private String id;
        private String name;
        private String birthday;
        private String birthyear;
        private String mobile;
    }
}
