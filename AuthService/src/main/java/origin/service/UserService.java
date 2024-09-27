package origin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.model.user.User;
import origin.repository.UserRepository;
import origin.service.kafka.ProducerService;
import origin.utils.exception.ApiException;
import origin.utils.token.JwtTokenUtil;
import origin.utils.validate.user.UsernameValidate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->
                new ApiException("Пользователь не найден", HttpStatus.NOT_FOUND));
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()->
                new ApiException("Пользователь не найден", HttpStatus.NOT_FOUND));
    }

    public void save(User user){
        userRepository.save(user);
    }


}
