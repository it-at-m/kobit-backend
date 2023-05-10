package de.muenchen.kobit.backend.user.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Injects the department of the current user into the parameter. see
 * DepartmentAttributeResolver.java for impl. see WebMvcContext.java for configuration
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Department {}
