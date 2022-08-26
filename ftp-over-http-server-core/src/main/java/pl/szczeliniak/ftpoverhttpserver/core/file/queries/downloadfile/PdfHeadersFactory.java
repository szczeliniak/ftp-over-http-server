package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PdfHeadersFactory implements HeadersFactory {

    @Override
    public List<ContentType> forContentTypes() {
        return List.of(ContentType.PDF);
    }

    @Override
    public Map<String, List<String>> prepareHeaders(final FileEntity file) {
        return Map.of(
                "pdf-size", Collections.singletonList(file.getSize().toString()),
                "pdf-content-type", Collections.singletonList(file.getContentType().getMimeType()),
                "pdf-ftp-file-name", Collections.singletonList(file.getFtpFileName()),
                "pdf-original-file-name", Collections.singletonList(file.getOriginalFileName())
        );
    }

}
