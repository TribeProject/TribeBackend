package com.unity.tribe.domain.group.docs;

import com.unity.tribe.common.model.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "모임 상세 조회", description = "모임의 상세 정보를 조회합니다. 목표, 멤버, 인증규칙 등 전체 정보를 포함합니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "모임 상세 조회 성공", value = """
                {
                  "status": 200,
                  "message": "요청이 성공했습니다.",
                  "result": true,
                  "data": {
                    "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                    "title": "한강 러닝 크루",
                    "description": "매주 토요일 아침 한강에서 함께 러닝해요! 건강한 습관을 만들어보세요..",
                    "status": "ONGOING",
                    "participants": 20,
                    "currentMemberCount": 15,
                    "hostId": "01HGW1MHT3RJYK5MXRZ6P5QACM",
                    "hostName": "러닝마니아",
                    "hostEmail": "running@example.com",
                    "categoryCode": "EA",
                    "categoryName": "운동 및 액티비티",
                    "groupType": "MISSION",
                    "meetingType": "OFFLINE",
                    "locationAddress": "서울시 영등포구 한강공원",
                    "thumbnail": "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b",
                    "genderRestriction": "NONE",
                    "minAge": 20,
                    "maxAge": 50,
                    "createdAt": "2024-11-15T09:00:00",
                    "updatedAt": "2024-11-20T14:30:00",
                    "expiredAt": "2024-12-31T23:59:59",
                    "goals": [
                      {
                        "goalId": 1,
                        "title": "주 3회 러닝",
                        "description": "매주 3번 이상 5km 러닝하기",
                        "targetValue": 15,
                        "unitTypeId": 1,
                        "unitTypeName": "km",
                        "startDate": "2024-11-15",
                        "endDate": "2024-12-15",
                        "status": "ACTIVE",
                        "certificationRule": {
                          "certificationRuleId": 1,
                          "frequency": "WEEKLY",
                          "requiredCount": 3,
                          "textRequired": false,
                          "imageRequired": true
                        }
                      }
                    ],
                    "members": [
                      {
                        "memberId": 1,
                        "userId": "01HGW1MHT3RJYK5MXRZ6P5QACM",
                        "nickname": "사용자1",
                        "email": "user1@example.com",
                        "role": "HOST",
                        "joinedAt": "2025-07-15T09:00:00"
                      },
                      {
                        "memberId": 2,
                        "userId": "01HGW1MHT3RJYK5MXRZ6P5QACO",
                        "nickname": "사용자2",
                        "email": "user2@example.com",
                        "role": "MEMBER",
                        "joinedAt": "2025-07-16T14:30:00"
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
        @ApiResponse(responseCode = "404", description = "모임을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 404,
                  "message": "모임을 찾을 수 없습니다.",
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
public @interface GetGroup {
}
