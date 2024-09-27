package origin.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import origin.dto.user.RegistrationUserDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {
    private final KafkaTemplate<String, RegistrationUserDto> kafkaTemplateSave;


    public void save(RegistrationUserDto registrationUserDto) {
        kafkaTemplateSave.send("save-topic", registrationUserDto);
    }




}
