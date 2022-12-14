package pl.szczeliniak.ftpoverhttpserver.app.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FOHSException.class)
    public ResponseEntity<ExceptionResponseDto> fohsException(final FOHSException fohsException) {
        final ErrorCode errorCode = fohsException.getErrorCode();
        return ResponseEntity.status(errorCode.getResponseCode()).body(new ExceptionResponseDto(errorCode.name()));
    }

}
