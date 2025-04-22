package de.muenchen.kobit.backend.user.client;
// LOGGING_LEVEL_DE_MUENCHEN_KOBIT_BACKEND_USER_CLIENT
import de.muenchen.kobit.backend.user.model.UserInfoView;
import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("userInformation")
public interface UserInfoClient {

    @RequestLine("GET")
    @Headers("Content-Type: application/json")
    UserInfoView getUserInformation(@HeaderMap Map<String, Object> headers);
}
