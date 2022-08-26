package pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetFileMetadataResponse {

    private long size;
    private String fileName;
    private String extension;

}
