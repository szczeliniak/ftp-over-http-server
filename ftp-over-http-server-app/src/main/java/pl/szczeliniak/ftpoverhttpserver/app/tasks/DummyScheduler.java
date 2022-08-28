package pl.szczeliniak.ftpoverhttpserver.app.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses.UpdateFileStatusesTask;

@Component
public class DummyScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DummyScheduler.class);

    private final UpdateFileStatusesTask updateFileStatusesTask;

    public DummyScheduler(UpdateFileStatusesTask updateFileStatusesTask) {
        this.updateFileStatusesTask = updateFileStatusesTask;
    }

    @Scheduled(cron = "${scheduler.dummy}")
    public void updateFileStatuses() {
        logger.info("Execution of UpdateFileStatusesTask started");
        updateFileStatusesTask.execute();
        logger.info("Execution of UpdateFileStatusesTask finished");
    }

}
