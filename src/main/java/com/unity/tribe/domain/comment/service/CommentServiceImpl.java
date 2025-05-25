package com.unity.tribe.domain.comment.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.comment.dto.request.CommentCreateRequestDto;
import com.unity.tribe.domain.comment.dto.request.CommentUpdateRequestDto;
import com.unity.tribe.domain.comment.dto.response.CommentResponseDto;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Override
    public CommonPageDto<List<CommentResponseDto>> getComments(String feedId, Pageable pageable) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public CommentResponseDto createComment(String feedId, CommentCreateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public void deleteComment(Long commentId, String userId) {
        // TODO: 구현 필요
    }

    @Override
    public CommonPageDto<List<CommentResponseDto>> getUserComments(String userId, Pageable pageable) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public long getCommentCount(String feedId) {
        // TODO: 구현 필요
        return 0;
    }
}