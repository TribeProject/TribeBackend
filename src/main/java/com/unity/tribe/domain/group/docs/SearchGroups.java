package com.unity.tribe.domain.group.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "모임 검색", description = """
        키워드를 통해 모임을 검색합니다.

        **검색 범위:**
        - 모임 제목
        - 모임 설명
        - 호스트 닉네임

        **검색 예시:**
        - 기본 검색: GET /api/v1/groups/search?keyword=러닝
        - 페이징 검색: GET /api/v1/groups/search?keyword=요리&page=0&size=10
        - 정렬 검색: GET /api/v1/groups/search?keyword=운동&sort=createdAt,desc

        **키워드 조건:**
        - 최소 2글자 이상 입력
        - 특수문자 제외한 한글, 영문, 숫자만 허용
        - 여러 키워드는 공백으로 구분 (AND 조건)
        """)
@Parameter(name = "keyword", description = "검색 키워드 (최소 2글자 이상)", example = "러닝", required = true)
@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", required = false)
@Parameter(name = "size", description = "페이지 크기 (최대 100)", example = "20", required = false)
@Parameter(name = "sort", description = "정렬 조건 (createdAt,desc | participants,desc | title,asc)", example = "createdAt,desc", required = false)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "검색 성공", value = """
                {
                  "status": 200,
                  "message": "요청이 성공했습니다.",
                  "result": true,
                  "data": {
                    "page": 0,
                    "size": 20,
                    "totalElements": 5,
                    "totalPages": 1,
                    "data": [
                      {
                        "id": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "name": "한강 러닝 크루",
                        "description": "매주 토요일 아침 한강에서 러닝해요",
                        "imageUrl": "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b",
                        "categoryCode": "EA",
                        "categoryName": "운동 및 액티비티",
                        "currentMembers": 12,
                        "maxMembers": 20,
                        "status": "ONGOING",
                        "createdAt": "2024-11-15T09:00:00",
                        "updatedAt": "2024-11-20T14:30:00"
                      },
                      {
                        "id": "01HGW1MHT3RJYK5MXRZ6P5QACO",
                        "name": "러닝 초보자 모임",
                        "description": "러닝 초보자들이 함께 운동해요",
                        "imageUrl": "https://images.unsplash.com/photo-1544737151-6e4b3999de01",
                        "categoryCode": "EA",
                        "categoryName": "운동 및 액티비티",
                        "currentMembers": 8,
                        "maxMembers": 15,
                        "status": "WAITING",
                        "createdAt": "2024-11-10T10:00:00",
                        "updatedAt": "2024-11-10T10:00:00"
                      }
                    ]
                  }
                }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = {
                @ExampleObject(name = "키워드 누락", value = """
                        {
                          "status": 400,
                          "message": "키워드는 필수 입력값입니다.",
                          "result": false,
                          "data": null
                        }
                        """),
                @ExampleObject(name = "키워드 길이 오류", value = """
                        {
                          "status": 400,
                          "message": "키워드는 최소 2글자 이상 입력해야 합니다.",
                          "result": false,
                          "data": null
                        }
                        """),
                @ExampleObject(name = "페이지 크기 오류", value = """
                        {
                          "status": 400,
                          "message": "페이지 크기는 1 이상 100 이하여야 합니다.",
                          "result": false,
                          "data": null
                        }
                        """),
                @ExampleObject(name = "정렬 옵션 오류", value = """
                        {
                          "status": 400,
                          "message": "유효하지 않은 정렬 옵션입니다.",
                          "result": false,
                          "data": null
                        }
                        """)
        })),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 401,
                  "message": "인증이 필요합니다.",
                  "result": false,
                  "data": null
                }
                """))),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 500,
                  "message": "서버 오류가 발생했습니다.",
                  "result": false,
                  "data": null
                }
                """)))
})
public @interface SearchGroups {
}