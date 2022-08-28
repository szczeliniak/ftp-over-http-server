package pl.szczeliniak.ftpoverhttpserver.app.exceptions;

import java.time.ZonedDateTime;

public class ExceptionResponseDto {

    private final String errorCode;
    private final ZonedDateTime timestamp = ZonedDateTime.now();

    public ExceptionResponseDto(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
