package pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata;

import lombok.Builder;
import lombok.Data;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;

@Builder
@Data
public class GetFileMetadataResponse {

    private long size;
    private String fileName;
    private String extension;
    private ProcessingStatus status;

}
