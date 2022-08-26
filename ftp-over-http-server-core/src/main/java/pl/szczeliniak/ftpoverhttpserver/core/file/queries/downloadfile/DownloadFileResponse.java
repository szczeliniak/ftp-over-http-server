package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import lombok.Builder;
import lombok.Data;
import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class DownloadFileResponse {

    private byte[] bytes;
    private ContentType contentType;
    private Map<String, List<String>> headers;

}
