package pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses;

import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;

import java.util.List;

public class DeletingStatusHandler implements ChangeStatusHandler {

    private final FileStorageClient fileStorageClient;

    public DeletingStatusHandler(FileStorageClient fileStorageClient) {
        this.fileStorageClient = fileStorageClient;
    }

    @Override
    public List<ProcessingStatus> from() {
        return List.of(ProcessingStatus.DELETING);
    }

    @Override
    public ProcessingStatus to() {
        return ProcessingStatus.DELETED;
    }

    @Override
    public void handle(FileEntity file) {
        fileStorageClient.delete(file.getFtpFileName());
        file.setStatus(to());
    }
}
