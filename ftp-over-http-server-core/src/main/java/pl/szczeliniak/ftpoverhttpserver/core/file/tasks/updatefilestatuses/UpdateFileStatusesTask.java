package pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.util.Arrays;
import java.util.List;

public class UpdateFileStatusesTask {

    private static final Logger logger = LoggerFactory.getLogger(UpdateFileStatusesTask.class);

    private final FileDao fileDao;
    private final List<ChangeStatusHandler> changeStatusHandlers;

    public UpdateFileStatusesTask(final FileDao fileDao, List<ChangeStatusHandler> changeStatusHandlers) {
        this.fileDao = fileDao;
        this.changeStatusHandlers = changeStatusHandlers;
    }

    public void execute() {
        List<FileEntity> files = fileDao.findAllByStatuses(Arrays.asList(
                null,
                ProcessingStatus.PROCESSING,
                ProcessingStatus.UPDATING,
                ProcessingStatus.DELETING));

        files.forEach(file -> {
            logger.info("Changing status for file with id: " + file.getId());
            changeStatusHandlers.stream().filter(handler -> handler.from().contains(file.getStatus()))
                    .findFirst()
                    .orElseThrow(() -> new FOHSException(ErrorCode.CHANGE_STATUS_HANDLER_NOT_FOUND))
                    .handle(file);
        });

        fileDao.saveAll(files);
    }

}
