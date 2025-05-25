package com.unity.tribe.domain.block.docs;

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
@Operation(summary = "차단 목록 조회", description = "사용자가 차단한 사용자 목록을 조회합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "차단 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": true,
                  "message": null,
                  "data": {
                    "content": [
                      {
                        "blockId": 1,
                        "userId": "01HRD4CK9PXVT2Q0E4G1BTHVF3",
                        "blockedUser": {
                          "userId": "01HRD4CK9PXVT2Q0E4G1BTHVF4",
                          "nickname": "차단된사용자1",
                          "profileImage": "https://example.com/profile1.jpg"
                        },
                        "createdAt": "2024-03-20T15:30:00"
                      },
                      {
                        "blockId": 2,
                        "userId": "01HRD4CK9PXVT2Q0E4G1BTHVF3",
                        "blockedUser": {
                          "userId": "01HRD4CK9PXVT2Q0E4G1BTHVF5",
                          "nickname": "차단된사용자2",
                          "profileImage": "https://example.com/profile2.jpg"
                        },
                        "createdAt": "2024-03-20T15:25:00"
                      }
                    ],
                    "pageable": {
                      "pageNumber": 0,
                      "pageSize": 10,
                      "sort": {
                        "sorted": true,
                        "unsorted": false,
                        "empty": false
                      },
                      "offset": 0,
                      "paged": true,
                      "unpaged": false
                    },
                    "totalElements": 2,
                    "totalPages": 1,
                    "last": true,
                    "size": 10,
                    "number": 0,
                    "sort": {
                      "sorted": true,
                      "unsorted": false,
                      "empty": false
                    },
                    "numberOfElements": 2,
                    "first": true,
                    "empty": false
                  }
                }
                """))),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": false,
                  "message": "인증되지 않은 요청입니다.",
                  "data": null
                }
                """)))
})
public @interface GetBlocks {
}