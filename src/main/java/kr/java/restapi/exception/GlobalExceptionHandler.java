package kr.java.restapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import kr.java.restapi.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// #(3)-10
//@RestControllerAdvice
@RestControllerAdvice(basePackages = "kr.java.restapi")
@Slf4j // log 표시
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    // import kr.java.restapi.model.dto.ErrorResponse;
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException e, HttpServletRequest request
    ) {
        log.warn("NotFoundException : {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(404, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(BadRequestException.class)
    // import kr.java.restapi.model.dto.ErrorResponse;
    public ResponseEntity<ErrorResponse> handleBadRequestException(
            BadRequestException e, HttpServletRequest request
    ) {
        log.warn("BadRequestException : {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // import kr.java.restapi.model.dto.ErrorResponse;
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request
    ) {
        log.warn("MethodArgumentNotValidException : {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    // import kr.java.restapi.model.dto.ErrorResponse;
    public ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request
    ) {
        log.warn("Unexpected Error : {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(500, e.getMessage(), request.getRequestURI()));
    }
}
