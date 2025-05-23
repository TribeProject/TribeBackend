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
@Operation(summary = "모임 정보 수정", description = "모임의 정보를 수정합니다. 모임의 호스트만 수정할 수 있습니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 200,
                      "message": "모임이 성공적으로 수정되었습니다.",
                      "result": true,
                      "data": {
                        "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "title": "모임명",
                        "description": "모임 설명",
                        "status": "ONGOING",
                        "participants": 25,
                        "currentMemberCount": 18,
                        "hostId": "01HGW1MHT3RJYK5MXRZ6P5QACM",
                        "hostName": "닉네임",
                        "hostEmail": "nickname@example.com",
                        "categoryId": "EA",
                        "categoryName": "운동/액티비티",
                        "groupType": "REGULAR",
                        "meetingType": "OFFLINE",
                        "locationAddress": "서울시 마포구",
                        "thumbnail": "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?q=80",
                        "genderRestriction": "ALL",
                        "minAge": 20,
                        "maxAge": 45,
                        "createdAt": "2025-01-01T09:00:00",
                        "updatedAt": "2025-01-05T16:20:00",
                        "expiredAt": "2025-12-31T23:59:59"
                      }
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "모임명은 필수입니다.",
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
                      "message": "권한이 없습니다. 모임 호스트만 수정할 수 있습니다.",
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
public @interface UpdateGroup {
}