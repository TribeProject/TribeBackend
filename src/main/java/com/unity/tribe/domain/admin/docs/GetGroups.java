package com.unity.tribe.domain.admin.docs;

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
@Operation(summary = "BO 모임 리스트 조회", description = """
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
        - startDate/endDate: 모임 날짜 범위
        - regions: 지역 (쉼표로 구분하여 여러 지역 선택 가능)
        - minAge/maxAge: 나이 제한 범위
        - genderRestriction: 성별 제한 (NONE, MALE, FEMALE)
        - minParticipants/maxParticipants: 참여인원 범위
        - categories: 모임 주제 (쉼표로 구분)

        **사용 예시:**
        - 기본 조회: GET /api/v1/groups
        - 키워드 검색: GET /api/v1/groups?keyword=러닝
        - 참여인원순 정렬: GET /api/v1/groups?sort=participants,desc
        - 카테고리별 검색: GET /api/v1/groups?categories=EA,F
        - 복합 필터: GET /api/v1/groups?keyword=러닝&regions=서울,경기&minAge=20&maxAge=30&genderRestriction=ALL
        """)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 200,
                  "message": "요청이 성공했습니다.",
                  "result": true,
                  "data": {
                    "page": 0,
                    "size": 2,
                    "totalElements": 2,
                    "totalPages": 1,
                    "data": [
                      {
                        "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "title": "모임명",
                        "description": "모임 설명",
                        "status": "ONGOING",
                        "participants": 20,
                        "currentMemberCount": 15,
                        "hostId": "01HGW1MHT3RJYK5MXRZ6P5QACM",
                        "hostName": "닉네임",
                        "categoryId": "EA",
                        "categoryName": "운동/액티비티",
                        "groupType": "MISSION",
                        "meetingType": "OFFLINE",
                        "locationAddress": "서울시 동작구",
                        "thumbnail": "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b",
                        "genderRestriction": "MALE",
                        "minAge": 20,
                        "maxAge": 40,
                        "createdAt": "2024-11-15T09:00:00",
                        "updatedAt": "2024-11-20T14:30:00"
                      },
                      {
                        "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACO",
                        "title": "모임명",
                        "description": "모임 설명",
                        "status": "WAITING",
                        "participants": 15,
                        "currentMemberCount": 8,
                        "hostId": "01HGW1MHT3RJYK5MXRZ6P5QACP",
                        "hostName": "닉네임",
                        "categoryId": "EA",
                        "categoryName": "운동/액티비티",
                        "groupType": "MISSION",
                        "meetingType": "OFFLINE",
                        "locationAddress": "서울시 동작구",
                        "thumbnail": "https://images.unsplash.com/photo-1682687982501-1e58ab814714",
                        "genderRestriction": "NONE",
                        "minAge": 25,
                        "maxAge": 50,
                        "createdAt": "2024-11-10T10:00:00",
                        "updatedAt": "2024-11-10T10:00:00"
                      }
                    ]
                  }
                }
                """))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 401,
                  "message": "인증이 필요합니다.",
                  "result": false,
                  "data": null
                }
                """))),
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
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