package steparrik.service;

import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import steparrik.model.user.User;
import steparrik.repository.UserRepository;
import steparrik.service.firebase.FirebaseStorageService;
import steparrik.utils.exception.ApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final FirebaseStorageService firebaseStorageService;

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new ApiException("Пользователь с данным ID не найден", HttpStatus.BAD_REQUEST));
    }
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()->
                new ApiException("Пользователь с данным ником не найден", HttpStatus.NOT_FOUND));
    }

    public User findByUsernameOrPhoneNumber(String username, String phoneNumber) {
        return userRepository.findByUsernameOrPhoneNumber(username, phoneNumber).orElseThrow(()->
                new ApiException("Пользователь с данным ником или номером телефона не найден", HttpStatus.NOT_FOUND));
    }



    public void save(User user){
        userRepository.save(user);
    }

    public String uploadAvatar(String username, MultipartFile file)  {
        User user = findUserByUsername(username);
        Blob blob = firebaseStorageService.uploadFile(file);
        String link = blob.getMediaLink();
        user.setPathToAvatar(link);
        save(user);
        return link;
    }
}
