package pl.szczeliniak.ftpoverhttpserver.core.file;

import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.util.Objects;

public enum ContentType {

    PDF("application/pdf", "pdf"),
    JPG("image/jpeg", "jpg"),
    PNG("image/png", "png");

    private final String mimeType;
    private final String extension;

    ContentType(final String mimeType, final String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
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

    public String getExtension() {
        return extension;
    }
}
