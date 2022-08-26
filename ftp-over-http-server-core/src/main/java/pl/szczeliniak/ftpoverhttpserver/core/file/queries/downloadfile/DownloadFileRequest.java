package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DownloadFileRequest {

    private int id;

}
