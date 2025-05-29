package com.unity.tribe.domain.comment.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(min = 1, max = 500, message = "댓글은 1자 이상 500자 이하로 입력해주세요.")
    private String content;

    @Min(value = 1, message = "부모 댓글 ID는 1 이상이어야 합니다.")
    private Long parentCommentId;
}