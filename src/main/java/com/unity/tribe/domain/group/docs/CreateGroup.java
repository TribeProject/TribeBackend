package com.unity.tribe.domain.group.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.group.dto.request.GroupCreateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "모임 생성", description = "새로운 모임을 생성합니다.")
@RequestBody(description = "모임 생성 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupCreateRequestDto.class), examples = @ExampleObject(name = "모임 생성 요청 예시", value = """
            {
              "title": "모임명",
              "description": "모임 설명",
              "categoryCode": "EA",
              "groupType": "MISSION",
              "meetingType": "OFFLINE",
              "locationAddress": "서울시 동작구",
              "participants": 20,
              "genderRestriction": "NONE",
              "minAge": 20,
              "maxAge": 50,
              "thumbnail": "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?q=80&w=1000",
              "expiredAt": "2025-12-31T23:59:59"
            }
        """)))
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "모임 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "모임 생성 성공 응답", summary = "생성된 모임 정보", value = """
                    {
                      "status": 201,
                      "message": "모임이 성공적으로 생성되었습니다.",
                      "result": true,
                      "data": {
                        "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "title": "모임명",
                        "description": "모임 설명",
                        "status": "WAITING",
                        "participants": 20,
                        "currentMemberCount": 1,
                        "hostId": "01HGW1MHT3RJYK5MXRZ6P5QACM",
                        "hostName": "닉네임",
                        "hostEmail": "email@example.com",
                        "categoryCode": "EA",
                        "categoryName": "EXERCISE_AND_ACTIVITY",
                        "groupType": "MISSION",
                        "meetingType": "OFFLINE",
                        "locationAddress": "서울시 동작구",
                        "thumbnail": "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?q=80&w=1000",
                        "genderRestriction": "NONE",
                        "minAge": 20,
                        "maxAge": 50,
                        "createdAt": "2025-05-24T09:00:00",
                        "updatedAt": "2025-05-24T09:00:00",
                        "expiredAt": "2025-12-31T23:59:59"
                      }
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 - 필수 필드 누락 또는 유효하지 않은 데이터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = {
                @ExampleObject(name = "필수 필드 누락", value = """
                            {
                              "status": 400,
                              "message": "모임명은 필수입니다.",
                              "result": false,
                              "data": null
                            }
                        """),
                @ExampleObject(name = "유효하지 않은 카테고리", value = """
                            {
                              "status": 400,
                              "message": "유효하지 않은 카테고리입니다. 사용 가능한 값: [FOOD, TRIP, EXERCISE_AND_ACTIVITY, CULTURE_AND_ART, SELF_DEVELOPMENT, GAME, BEAUTY_AND_FASHION, VOLUNTEER]",
                              "result": false,
                              "data": null
                            }
                        """),
                @ExampleObject(name = "날짜 형식 오류", value = """
                            {
                              "status": 400,
                              "message": "날짜 형식이 올바르지 않습니다. (YYYY-MM-DDTHH:mm:ss)",
                              "result": false,
                              "data": null
                            }
                        """)
        })),
        @ApiResponse(responseCode = "401", description = "인증 실패 - 로그인이 필요함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "인증 실패", value = """
                    {
                      "status": 401,
                      "message": "인증이 필요합니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "403", description = "권한 없음 - 모임 생성 권한이 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "권한 없음", summary = "모임 생성 권한이 없는 사용자", value = """
                    {
                      "status": 403,
                      "message": "모임 생성 권한이 없습니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "서버 오류", value = """
                    {
                      "status": 500,
                      "message": "서버 오류가 발생했습니다.",
                      "result": false,
                      "data": null
                    }
                """)))
})
public @interface CreateGroup {
}