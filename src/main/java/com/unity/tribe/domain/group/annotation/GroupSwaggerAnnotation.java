package com.unity.tribe.domain.group.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "그룹 API", description = "그룹 관련

    
    @Operation(summary = "그
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
            ameters({
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY),
        @Parameter(name = "status", description = "그룹 상태", in = ParameterIn.QUERY)
    })
    
    @interface GetGroups {}

    @Operation(summary = "카
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용
             @ApiResponse(responseCode = "404", description = "카테고리 없음"),
       @ApiResponse(responseCode = "500", description = "서버 오류")
    })
            ameters({
            @Parameter(name = "categoryId", description = "카테고리 ID", in = ParameterIn.
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY),
        @Parameter(name = "status", description = "그룹 상태", in = ParameterIn.QUERY)
    })
    
    @interface GetGroupsByCategory {}

    @Operation(summary = "그
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
            ameters({
            @Parameter(name = "keyword", description = "검색 키워드", in = ParameterIn.QUER
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.QUERY),
        @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY)
    })
    
    @interface GetGroup {}

    @Operation(summary = "그
            Responses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface CreateGroup {}

    @Operation(summary = "그
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "그룹 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface UpdateGroup {}

    @Operation(summary = "그
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "그룹 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface DeleteGroup {}

    @Operation(summary = "그
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "참여 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "404", description = "그룹 없음"),
            @ApiResponse(responseCode = "409", description = "이미 참여 중"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface JoinGroup {}

    @Operation(summary = "그
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "404", description = "그룹 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface LeaveGroup {}

    @Operation(summary = "목
            Responses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "그룹 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface CreateGoal {}

    @Operation(summary = "목
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "404", description = "그룹 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface GetGoals {}

    @Operation(summary = "목
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "그룹/목표 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
    @interface UpdateGoal {}

    @Operation(summary = "목
            Responses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "그룹/목표 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    
   @interface DeleteGoal {}
} 