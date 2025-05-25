package com.unity.tribe.domain.group.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.group.dto.request.GoalCreateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "모임 목표 생성", description = """
        모임에 새로운 목표를 생성합니다. 모임의 호스트만 생성할 수 있습니다.

        **목표 생성 시 필수 입력 사항:**
        - title: 목표명
        - description: 목표 설명
        - targetValue: 목표 달성 기준값
        - startDate: 시작일
        - endDate: 종료일
        - certificationRule: 인증 규칙 정보

        **인증 규칙 설정:**
        - certificationFrequency: 인증 주기 (DAILY, WEEKLY, MONTHLY)
        - textRequired: 텍스트 인증 필수 여부
        - imageRequired: 이미지 인증 필수 여부
        - weeklyCount: 주간 인증 횟수 (WEEKLY인 경우 필수)
        - weeklyDays: 주간 인증 요일 (WEEKLY인 경우 필수)
        - monthlyDays: 월간 인증 일자 (MONTHLY인 경우 필수)
        - content: 인증 내용 설명

        certificationFrequency가 WEEKLY인 경우 weeklyCount와 weeklyDays의 개수가 일치해야 합니다.

        monthlyDays는 1~31 사이의 월별 인증 날짜입니다.
        """)
@RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalCreateRequestDto.class), examples = {
        @ExampleObject(name = "일일 인증 예시", value = """
                    {
                        "title": "매일 운동하기",
                        "description": "매일 30분 이상 운동하기",
                        "targetValue": 30,
                        "certificationRuleId": 1,
                        "startDate": "2025-01-06",
                        "endDate": "2025-01-12",
                        "certificationRule": {
                            "certificationFrequency": "DAILY",
                            "textRequired": true,
                            "imageRequired": true,
                            "weeklyCount": null,
                            "weeklyDays": null,
                            "monthlyDays": null,
                            "goalDate": "2025-01-06",
                            "content": "매일 운동 인증하기"
                        }
                    }
                """),
        @ExampleObject(name = "주간 인증 예시", value = """
                    {
                        "title": "주 5회 독서하기",
                        "description": "매주 5일 독서 인증하기",
                        "targetValue": 10,
                        "certificationRuleId": 2,
                        "startDate": "2025-01-06",
                        "endDate": "2025-02-06",
                        "certificationRule": {
                            "certificationFrequency": "WEEKLY",
                            "textRequired": true,
                            "imageRequired": false,
                            "weeklyCount": 5,
                            "weeklyDays": ["MON", "TUE", "WED", "THU", "FRI"],
                            "monthlyDays": null,
                            "goalDate": "2025-01-06",
                            "content": "주 5회 독서 인증하기"
                        }
                    }
                """),
        @ExampleObject(name = "월간 인증 예시", value = """
                    {
                        "title": "월 3회 영화 감상",
                        "description": "매월 3일 영화 감상 인증하기",
                        "targetValue": 3,
                        "certificationRuleId": 3,
                        "startDate": "2025-01-01",
                        "endDate": "2025-12-31",
                        "certificationRule": {
                            "certificationFrequency": "MONTHLY",
                            "textRequired": true,
                            "imageRequired": true,
                            "weeklyCount": null,
                            "weeklyDays": null,
                            "monthlyDays": ["5", "15", "25"],
                            "goalDate": "2025-01-01",
                            "content": "매월 영화 감상 인증하기"
                        }
                    }
                """) }))
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "목표 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 201,
                  "message": "목표가 성공적으로 생성되었습니다.",
                  "result": true,
                  "data": {
                    "goalId": 1,
                    "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                    "title": "목표명",
                    "description": "목표 설명",
                    "targetValue": 15,
                    "certificationRuleId": 1,
                    "startDate": "2025-01-06",
                    "endDate": "2025-01-12",
                    "status": "ACTIVE",
                    "createdAt": "2025-01-01T20:00:00",
                    "updatedAt": "2025-01-01T20:00:00"
                  }
                }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 400,
                  "message": "목표 제목은 필수입니다.",
                  "result": false,
                  "data": null
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
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 403,
                  "message": "권한이 없습니다. 모임 호스트만 목표를 생성할 수 있습니다.",
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
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 500,
                  "message": "서버 오류가 발생했습니다.",
                  "result": false,
                  "data": null
                }
                """)))
})
public @interface CreateGoal {
}