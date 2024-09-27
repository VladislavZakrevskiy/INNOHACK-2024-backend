package origin.utils.exception.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import origin.utils.exception.ApiException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("Exception {} {}", methodKey, response);
        if(methodKey.contains("getUserByUsernameOrEmail")){
            return new ApiException("Пользователь с данным ником или почтой не найден", HttpStatus.BAD_REQUEST);
        }
        return new Exception();
    }
}
