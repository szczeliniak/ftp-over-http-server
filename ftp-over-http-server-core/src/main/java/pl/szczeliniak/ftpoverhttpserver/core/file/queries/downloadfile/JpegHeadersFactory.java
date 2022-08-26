package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JpegHeadersFactory implements HeadersFactory {
    @Override
    public List<ContentType> forContentTypes() {
        return Arrays.asList(ContentType.JPEG, ContentType.JPG);
    }

    @Override
    public Map<String, List<String>> prepareHeaders(final FileEntity file) {
        return Map.of(
                "jpg-size", Collections.singletonList(file.getSize().toString()),
                "jpg-content-type", Collections.singletonList(file.getContentType().getMimeType()),
                "jpg-ftp-file-name", Collections.singletonList(file.getFtpFileName()),
                "jpg-original-file-name", Collections.singletonList(file.getOriginalFileName())
        );
    }
}
