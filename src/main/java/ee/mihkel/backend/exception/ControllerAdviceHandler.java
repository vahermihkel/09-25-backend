package ee.mihkel.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException ex){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setCode(400); // body
        errorMessage.setTimestamp(new Date());
                            // päringu staatus
        return ResponseEntity.status(400).body(errorMessage);
    }
}
