package origin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import origin.config.feign.FeignConfig;
import origin.dto.user.ProfileUserDto;
import java.util.List;

@FeignClient(name = "users", url = "http://users:8084", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/profile/{id}")
    ProfileUserDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/profile")
    ProfileUserDto getUserByUsername(@RequestHeader("X-Username")String username);

    @GetMapping
    List<ProfileUserDto> getAll();

    @GetMapping("/byid")
    List<ProfileUserDto> getAllUsersByIdList(@RequestParam List<Long> userIdList);
}
