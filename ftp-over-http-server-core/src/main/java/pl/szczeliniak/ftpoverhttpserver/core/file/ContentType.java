package pl.szczeliniak.ftpoverhttpserver.core.file;

import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.util.Objects;

public enum ContentType {

    PDF("application/pdf"),
    JPG("image/jpeg"),
    JPEG("image/jpeg");

    private final String mimeType;

    ContentType(final String mimeType) {
        this.mimeType = mimeType;
    }

    public static boolean isSupported(String contentType) {
        for (final ContentType type : values()) {
            if (Objects.equals(type.mimeType, contentType)) {
                return true;
            }
        }
        return false;
    }

    public static ContentType byMimeType(String contentType) {
        for (final ContentType type : values()) {
            if (Objects.equals(type.mimeType, contentType)) {
                return type;
            }
        }
        throw new FOHSException(ErrorCode.UNSUPPORTED_FORMAT);
    }

    public String getMimeType() {
        return mimeType;
    }
}
