package pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetFileMetadataRequest {

    private int id;

}
