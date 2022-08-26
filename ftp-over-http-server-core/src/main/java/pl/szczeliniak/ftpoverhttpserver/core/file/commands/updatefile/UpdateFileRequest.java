package pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateFileRequest {

    private String name;
    private byte[] bytes;
    private String contentType;
    private long size;
    private int id;

}
