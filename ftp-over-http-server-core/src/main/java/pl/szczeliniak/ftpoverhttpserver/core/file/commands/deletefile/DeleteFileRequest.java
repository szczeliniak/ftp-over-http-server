package pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteFileRequest {

    private int id;

}
