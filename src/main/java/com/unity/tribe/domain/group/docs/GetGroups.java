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
@Operation(summary = "모임 목록 조회", description = """
        모임 목록을 조회합니다.

        **정렬 옵션:**
        - createdAt,desc: 생성일 기준 최신순 (기본값)
        - createdAt,asc: 생성일 기준 오래된순
        - participants,desc: 참여인원 기준 많은순
        - participants,asc: 참여인원 기준 적은순
        - title,asc: 제목 기준 가나다순
        - title,desc: 제목 기준 역순

        **필터링 조건:**
        - keyword: 모임명, 모임 설명글에서 키워드 검색
        - status: 모임 상태 (WAITING, ONGOING, FINISHED, DISBANDED)
        - startDate/endDate: 모임 날짜 범위 (YYYY-MM-DD)
        - regions: 지역 (쉼표로 구분하여 여러 지역 선택 가능)
        - minAge/maxAge: 나이 제한 범위 (숫자)
        - genderRestriction: 성별 제한 (NONE, MALE, FEMALE)
        - minParticipants/maxParticipants: 참여인원 범위 (숫자)
        - categories: 모임 주제 (쉼표로 구분)

        **사용 예시:**
        - 기본 조회: GET /api/v1/groups
        - 키워드 검색: GET /api/v1/groups?keyword=러닝
        - 참여인원순 정렬: GET /api/v1/groups?sort=participants,desc
        - 카테고리별 검색: GET /api/v1/groups?categories=EA,F
        - 복합 필터: GET /api/v1/groups?keyword=러닝&regions=서울,경기&minAge=20&maxAge=30&genderRestriction=NONE
        """)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "모임 목록 조회 성공", value = """
                {
                  "status": 200,
                  "message": "요청이 성공했습니다.",
                  "result": true,
                  "data": {
                    "page": 0,
                    "size": 20,
                    "totalElements": 25,
                    "totalPages": 2,
                    "data": [
                      {
                        "id": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "name": "한강 러닝 크루",
                        "description": "매주 토요일 아침 한강에서 함께 러닝해요!",
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
                        "name": "요리 클래스",
                        "description": "건강한 요리를 함께 배워요",
                        "imageUrl": "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136",
                        "categoryCode": "F",
                        "categoryName": "음식",
                        "currentMembers": 8,
                        "maxMembers": 12,
                        "status": "WAITING",
                        "createdAt": "2024-11-10T10:00:00",
                        "updatedAt": "2024-11-10T10:00:00"
                      }
                    ]
                  }
                }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (파라미터 오류)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = {
                @ExampleObject(name = "날짜 형식 오류", value = """
                        {
                          "status": 400,
                          "message": "날짜 형식이 올바르지 않습니다. (YYYY-MM-DD)",
                          "result": false,
                          "data": null
                        }
                        """),
                @ExampleObject(name = "정렬 옵션 오류", value = """
                        {
                          "status": 400,
                          "message": "유효하지 않은 정렬 옵션입니다. 사용 가능한 값: createdAt, participants, title",
                          "result": false,
                          "data": null
                        }
                        """),
                @ExampleObject(name = "나이 범위 오류", value = """
                        {
                          "status": 400,
                          "message": "minAge는 maxAge보다 작거나 같아야 합니다.",
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
public @interface GetGroups {
}