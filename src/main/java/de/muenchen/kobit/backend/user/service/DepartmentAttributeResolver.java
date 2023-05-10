package de.muenchen.kobit.backend.user.service;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DepartmentAttributeResolver implements HandlerMethodArgumentResolver {

    private final UserDataResolver userDataResolver;

    public DepartmentAttributeResolver(UserDataResolver userDataResolver) {
        this.userDataResolver = userDataResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Arrays.stream(parameter.getParameterAnnotations())
                .map(Annotation::annotationType)
                .anyMatch(it -> it.equals(Department.class));
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        return userDataResolver.getCurrentUser().getDepartment();
    }
}
