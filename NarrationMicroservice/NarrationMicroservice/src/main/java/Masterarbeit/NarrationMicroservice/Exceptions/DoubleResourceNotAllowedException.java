package Masterarbeit.NarrationMicroservice.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DoubleResourceNotAllowedException extends RuntimeException{

    public DoubleResourceNotAllowedException(String message) {
        super(message);
    }

    public DoubleResourceNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
