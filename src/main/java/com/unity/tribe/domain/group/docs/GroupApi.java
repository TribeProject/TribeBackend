package com.unity.tribe.domain.group.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag(name = "Group")
@SecurityRequirement(name = "bearerAuth")
public @interface GroupApi {
}