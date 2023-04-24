package de.muenchen.kobit.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.Set;
public class DepartmentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserDataResolver userDataResolver;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.getParameterType().equals(Set.class)) {
            return false;
        }
        for (Annotation annotation : parameter.getParameterAnnotations()) {
            if(annotation.annotationType().equals(Department.class)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return userDataResolver.getCurrentUser().getDepartment();
    }
}
