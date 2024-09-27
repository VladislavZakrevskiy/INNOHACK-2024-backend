package origin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.model.user.User;
import origin.repository.UserRepository;
import origin.utils.exception.ApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;


    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new ApiException("Пользователь с данным ID не найден", HttpStatus.BAD_REQUEST));
    }
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()->
                new ApiException("Пользователь с данным ником не найден", HttpStatus.NOT_FOUND));
    }

    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email).orElseThrow(()->
                new ApiException("Пользователь с данным ником или почтой не найден", HttpStatus.NOT_FOUND));
    }


    public void save(User user){
        userRepository.save(user);
    }
}
