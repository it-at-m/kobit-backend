package de.muenchen.kobit.backend.configuration;

import de.muenchen.kobit.backend.user.client.UserInfoClient;
import de.muenchen.kobit.backend.user.service.DepartmentAttributeResolver;
import de.muenchen.kobit.backend.user.service.UserDataResolver;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcContext implements WebMvcConfigurer {

    @Autowired private UserInfoClient userInfoClient;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new DepartmentAttributeResolver(new UserDataResolver(userInfoClient)));
    }
}
