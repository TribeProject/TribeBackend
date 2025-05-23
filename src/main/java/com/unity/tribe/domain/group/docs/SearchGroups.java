package com.unity.tribe.domain.group.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "모임 검색", description = "키워드로 모임을 검색합니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = "{\n  \"status\": 200,\n  \"message\": \"요청이 성공했습니다.\",\n  \"result\": true,\n  \"data\": {\n    \"page\": 0,\n    \"size\": 20,\n    \"totalElements\": 8,\n    \"totalPages\": 1,\n    \"data\": []\n  }\n}")))
})
public @interface SearchGroups {
}