package pl.szczeliniak.ftpoverhttpserver.core.shared;

public class FOHSException extends RuntimeException {

    private final ErrorCode errorCode;

    public FOHSException(final ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
