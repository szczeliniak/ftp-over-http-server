package pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses;

import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;

import java.util.Arrays;
import java.util.List;

public class NullProcessingUpdatingStatusHandler implements ChangeStatusHandler {
    @Override
    public List<ProcessingStatus> from() {
        return Arrays.asList(null, ProcessingStatus.PROCESSING, ProcessingStatus.UPDATING);
    }

    @Override
    public ProcessingStatus to() {
        return ProcessingStatus.READY;
    }

    @Override
    public void handle(FileEntity file) {
        file.setStatus(to());
    }
}
