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
@Operation(summary = "카테고리별 모임 조회", description = """
        특정 카테고리의 모임 목록을 조회합니다.

        **카테고리 코드:**
        - F: 음식 (FOOD)
        - T: 여행 (TRIP)
        - EA: 운동 및 액티비티 (EXERCISE_AND_ACTIVITY)
        - CA: 문화 및 예술 (CULTURE_AND_ART)
        - SD: 자기계발 (SELF_DEVELOPMENT)
        - G: 게임 (GAME)
        - BF: 뷰티 및 패션 (BEAUTY_AND_FASHION)
        - V: 봉사활동 (VOLUNTEER)

        **사용 예시:**
        - 운동 모임: GET /api/v1/groups/categories/EA
        - 음식 모임: GET /api/v1/groups/categories/F
        - 페이징: GET /api/v1/groups/categories/EA?page=0&size=10
        - 정렬: GET /api/v1/groups/categories/EA?sort=participants,desc
        """)
@Parameter(name = "categoryCode", description = "카테고리 코드", example = "EA", required = true)
@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", required = false)
@Parameter(name = "size", description = "페이지 크기 (최대 100)", example = "20", required = false)
@Parameter(name = "sort", description = "정렬 조건 (createdAt,desc | participants,desc | title,asc)", example = "createdAt,desc", required = false)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "카테고리별 모임 조회 성공", value = """
                {
                  "status": 200,
                  "message": "요청이 성공했습니다.",
                  "result": true,
                  "data": {
                    "page": 0,
                    "size": 20,
                    "totalElements": 8,
                    "totalPages": 1,
                    "data": [
                      {
                        "id": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "name": "한강 러닝 크루",
                        "description": "매주 토요일 아침 한강에서 러닝해요!",
                        "imageUrl": "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b",
                        "categoryCode": "EA",
                        "categoryName": "운동 및 액티비티",
                        "currentMembers": 15,
                        "maxMembers": 20,
                        "status": "ONGOING",
                        "createdAt": "2024-11-15T09:00:00",
                        "updatedAt": "2024-11-20T14:30:00"
                      },
                      {
                        "id": "01HGW1MHT3RJYK5MXRZ6P5QACO",
                        "name": "홈트레이닝 모임",
                        "description": "집에서 함께 운동해요",
                        "imageUrl": "https://images.unsplash.com/photo-1544737151-6e4b3999de01",
                        "categoryCode": "EA",
                        "categoryName": "운동 및 액티비티",
                        "currentMembers": 10,
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
                @ExampleObject(name = "잘못된 카테고리 코드", value = """
                        {
                          "status": 400,
                          "message": "유효하지 않은 카테고리 코드입니다. 사용 가능한 값: F, T, EA, CA, SD, G, BF, V",
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
public @interface GetGroupsByCategory {
}