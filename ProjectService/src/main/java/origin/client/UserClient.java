package origin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import origin.config.feign.FeignConfig;
import origin.dto.user.ProfileUserDto;

@FeignClient(name = "users", url = "http://users:8084", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/profile/{id}")
    ProfileUserDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/profile")
    ProfileUserDto getUserByUsername(@RequestHeader("X-Username")String username);

}
