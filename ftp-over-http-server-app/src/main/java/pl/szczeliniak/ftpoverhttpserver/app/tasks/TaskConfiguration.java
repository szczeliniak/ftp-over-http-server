package pl.szczeliniak.ftpoverhttpserver.app.tasks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses.DeletingStatusHandler;
import pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses.NullProcessingUpdatingStatusHandler;
import pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses.UpdateFileStatusesTask;

import java.util.Arrays;

@Configuration
public class TaskConfiguration {

    @Bean
    public UpdateFileStatusesTask updateFileStatusesTask(final FileDao fileDao, final FileStorageClient fileStorageClient) {
        return new UpdateFileStatusesTask(fileDao, Arrays.asList(
                new NullProcessingUpdatingStatusHandler(),
                new DeletingStatusHandler(fileStorageClient))
        );
    }

}
