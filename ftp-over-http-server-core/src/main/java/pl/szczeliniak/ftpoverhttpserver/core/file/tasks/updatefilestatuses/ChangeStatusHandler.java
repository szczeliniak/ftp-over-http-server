package pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses;

import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;

import java.util.List;

public interface ChangeStatusHandler {

    List<ProcessingStatus> from();

    ProcessingStatus to();

    void handle(final FileEntity file);

}
