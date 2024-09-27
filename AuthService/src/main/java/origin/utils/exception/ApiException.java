package origin.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiException extends RuntimeException {
    private final LocalDateTime timestamp;
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }
}
