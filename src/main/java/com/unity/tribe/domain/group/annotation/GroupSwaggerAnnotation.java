package com.unity.tribe.domain.group.annotation;

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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

public final class GroupSwaggerAnnotation {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "그룹", description = "그룹 관련 API")
    @SecurityRequirement(name = "bearerAuth")
    public @interface GroupApi {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 생성", description = "새로운 그룹을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "그룹 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 201,
                          "message": "그룹이 성공적으로 생성되었습니다.",
                          "result": true,
                          "data": {
                            "id": 1,
                            "name": "러닝크루",
                            "description": "한강에서 함께 달리는 러닝 모입입니다!",
                            "status": "WAITING",
                            "memberCount": 1,
                            "maxMembers": 30,
                            "hostName": "닉네임",
                            "hostEmail": "email@example.com",
                            "categoryId": "EA",
                            "categoryName": "운동/액티비티",
                            "createdAt": "2024-01-01T00:00:00",
                            "updatedAt": "2024-01-01T00:00:00"
                          }
                        }
                    """))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 400,
                          "message": "그룹명은 필수입니다.",
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
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
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

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 목표 생성", description = "그룹에 새로운 목표를 생성합니다. 그룹의 호스트만 생성할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "목표 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 201,
                          "message": "목표가 성공적으로 생성되었습니다.",
                          "result": true,
                          "data": {
                            "id": 1,
                            "groupId": 1,
                            "title": "주간 러닝 목표",
                            "description": "한강에서 주 3회 5km 이상 러닝하기",
                            "targetValue": 15,
                            "unit": "km",
                            "startDate": "2024-01-01",
                            "endDate": "2024-01-07",
                            "status": "ACTIVE",
                            "createdAt": "2024-01-01T00:00:00",
                            "updatedAt": "2024-01-01T00:00:00"
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
                          "message": "권한이 없습니다.",
                          "result": false,
                          "data": null
                        }
                    """))),
            @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹을 찾을 수 없습니다.",
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

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 목표 목록 조회", description = "그룹의 모든 목표 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "요청이 성공했습니다.",
                          "result": true,
                          "data": [
                            {
                              "id": 1,
                              "groupId": 1,
                              "title": "주간 러닝 목표",
                              "description": "한강에서 주 3회 5km 이상 러닝하기",
                              "targetValue": 15,
                              "unit": "km",
                              "startDate": "2024-01-01",
                              "endDate": "2024-01-07",
                              "status": "ACTIVE",
                              "createdAt": "2024-01-01T00:00:00",
                              "updatedAt": "2024-01-01T00:00:00"
                            }
                          ]
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
            @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹을 찾을 수 없습니다.",
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
    public @interface GetGoals {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 목표 수정", description = "그룹의 목표를 수정합니다. 그룹의 호스트만 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "목표가 성공적으로 수정되었습니다.",
                          "result": true,
                          "data": {
                            "id": 1,
                            "groupId": 1,
                            "title": "주간 러닝 목표 (수정)",
                            "description": "한강에서 주 4회 7km 이상 러닝하기",
                            "targetValue": 28,
                            "unit": "km",
                            "startDate": "2024-01-01",
                            "endDate": "2024-01-07",
                            "status": "ACTIVE",
                            "createdAt": "2024-01-01T00:00:00",
                            "updatedAt": "2024-01-02T00:00:00"
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
                          "message": "권한이 없습니다.",
                          "result": false,
                          "data": null
                        }
                    """))),
            @ApiResponse(responseCode = "404", description = "그룹 또는 목표를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹 또는 목표를 찾을 수 없습니다.",
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
    public @interface UpdateGoal {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 정보 수정", description = "그룹의 정보를 수정합니다. 그룹의 호스트만 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "그룹이 성공적으로 수정되었습니다.",
                          "result": true,
                          "data": {
                            "id": 1,
                            "name": "한강러닝크루 (수정)",
                            "description": "한강에서 함께 달리며 건강한 삶을 만드는 러닝 모임",
                            "status": "ONGOING",
                            "memberCount": 25,
                            "maxMembers": 35,
                            "hostId": 1,
                            "hostName": "러닝마스터",
                            "hostEmail": "runner@example.com",
                            "categoryId": 1,
                            "categoryName": "러닝/조깅",
                            "createdAt": "2024-01-01T00:00:00",
                            "updatedAt": "2024-01-02T00:00:00"
                          }
                        }
                    """))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 400,
                          "message": "잘못된 요청입니다.",
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
                          "message": "권한이 없습니다.",
                          "result": false,
                          "data": null
                        }
                    """))),
            @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹을 찾을 수 없습니다.",
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

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 삭제", description = "그룹을 삭제합니다. 그룹의 호스트만 삭제할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "그룹이 성공적으로 삭제되었습니다.",
                          "result": true,
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
                          "message": "권한이 없습니다.",
                          "result": false,
                          "data": null
                        }
                    """))),
            @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹을 찾을 수 없습니다.",
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
    public @interface DeleteGroup {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 참여", description = "사용자가 그룹에 참여합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "참여 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "그룹에 성공적으로 참여했습니다.",
                          "result": true,
                          "data": null
                        }
                    """))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (참여 조건 불일치 등)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 400,
                          "message": "참여 조건을 만족하지 않습니다.",
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
            @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹을 찾을 수 없습니다.",
                          "result": false,
                          "data": null
                        }
                    """))),
            @ApiResponse(responseCode = "409", description = "이미 참여한 그룹", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 409,
                          "message": "이미 참여한 그룹입니다.",
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
    public @interface JoinGroup {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 탈퇴", description = "사용자가 그룹에서 탈퇴합니다. 호스트는 탈퇴할 수 없습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "탈퇴 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "그룹에서 성공적으로 탈퇴했습니다.",
                          "result": true,
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
                    """)))
    })
    public @interface LeaveGroup {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 목표 삭제", description = "그룹의 목표를 삭제합니다. 그룹의 호스트만 삭제할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "목표가 성공적으로 삭제되었습니다.",
                          "result": true,
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
                          "message": "권한이 없습니다.",
                          "result": false,
                          "data": null
                        }
                    """))),
            @ApiResponse(responseCode = "404", description = "그룹 또는 목표를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹 또는 목표를 찾을 수 없습니다.",
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
    public @interface DeleteGoal {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 상세 조회", description = "그룹의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "요청이 성공했습니다.",
                          "result": true,
                          "data": {
                            "id": 1,
                            "name": "한강러닝크루",
                            "description": "한강에서 함께 달리는 러닝 모임",
                            "status": "ONGOING",
                            "memberCount": 25,
                            "maxMembers": 30,
                            "hostId": 1,
                            "hostName": "러닝마스터",
                            "hostEmail": "runner@example.com",
                            "categoryId": 1,
                            "categoryName": "러닝/조깅",
                            "createdAt": "2024-01-01T00:00:00",
                            "updatedAt": "2024-01-01T00:00:00"
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
            @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "그룹을 찾을 수 없습니다.",
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
    public @interface GetGroup {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 목록 조회", description = "그룹 목록을 조회합니다. 상태별 필터링이 가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "요청이 성공했습니다.",
                          "result": true,
                          "data": {
                            "page": 0,
                            "size": 20,
                            "totalElements": 10,
                            "totalPages": 1,
                            "data": [
                              {
                                "id": 1,
                                "name": "개발팀",
                                "description": "소프트웨어 개발 그룹",
                                "status": "ONGOING",
                                "memberCount": 15,
                                "maxMembers": 20,
                                "hostId": 1,
                                "hostName": "관리자",
                                "categoryId": 1,
                                "categoryName": "개발",
                                "createdAt": "2024-01-01T00:00:00",
                                "updatedAt": "2024-01-01T00:00:00"
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

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "카테고리별 그룹 목록 조회", description = "특정 카테고리의 그룹 목록을 조회합니다. 상태별 필터링이 가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
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
                                "id": 1,
                                "name": "개발팀",
                                "description": "소프트웨어 개발 그룹",
                                "status": "ONGOING",
                                "memberCount": 15,
                                "maxMembers": 20,
                                "hostId": 1,
                                "hostName": "관리자",
                                "categoryId": 1,
                                "categoryName": "개발",
                                "createdAt": "2024-01-01T00:00:00",
                                "updatedAt": "2024-01-01T00:00:00"
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
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "message": "카테고리를 찾을 수 없습니다.",
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
    public @interface GetGroupsByCategory {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "그룹 검색", description = "키워드로 그룹을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                        {
                          "status": 200,
                          "message": "요청이 성공했습니다.",
                          "result": true,
                          "data": {
                            "page": 0,
                            "size": 20,
                            "totalElements": 3,
                            "totalPages": 1,
                            "data": [
                              {
                                "id": 1,
                                "name": "개발팀",
                                "description": "소프트웨어 개발 그룹",
                                "status": "ONGOING",
                                "memberCount": 15,
                                "maxMembers": 20,
                                "hostId": 1,
                                "hostName": "관리자",
                                "categoryId": 1,
                                "categoryName": "개발",
                                "createdAt": "2024-01-01T00:00:00",
                                "updatedAt": "2024-01-01T00:00:00"
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
    public @interface SearchGroups {
    }
}