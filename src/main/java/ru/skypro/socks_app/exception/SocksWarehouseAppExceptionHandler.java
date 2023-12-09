package ru.skypro.socks_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.security.InvalidParameterException;
import java.util.Optional;
@RestControllerAdvice
public class SocksWarehouseAppExceptionHandler {
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            InvalidParameterException.class,
            BindException.class,

    })
    public ResponseEntity<?> handleMethodArgumentNotValidException(Errors e) {
        String field = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getField)
                .orElse("<unknown>");
        return ResponseEntity.badRequest().body("Поле '" + field + "' не валидно!");
    }

    @ExceptionHandler({
            SockNotFoundException.class,
    })
    public ResponseEntity<String> handleNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler({
            LessThanZeroException.class,
            OperationException.class,
    })
    public ResponseEntity<String> handleIsPresentException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

}

