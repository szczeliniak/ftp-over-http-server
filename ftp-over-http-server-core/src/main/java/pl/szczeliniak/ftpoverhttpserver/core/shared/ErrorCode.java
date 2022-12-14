package pl.szczeliniak.ftpoverhttpserver.core.shared;

public enum ErrorCode {

    UNSUPPORTED_FORMAT(400),
    FILE_BY_ID_NOT_FOUND(404),
    FILE_TOO_LARGE(400),
    FTP_GENERIC_ERROR(400),
    FILE_MIME_TYPE_MISMATCH(400),
    UNSUPPORTED_HEADERS_FACTORY(400),
    CHANGE_STATUS_HANDLER_NOT_FOUND(400),
    CANNOT_MODIFY_FILE_WHICH_IS_BEING_PROCESSED(400),
    NOT_DOWNLOADABLE_FILE(400);

    private final int responseCode;

    ErrorCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
