package pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UploadFileRequest {

    private String name;
    private byte[] bytes;
    private String contentType;
    private long size;

}
