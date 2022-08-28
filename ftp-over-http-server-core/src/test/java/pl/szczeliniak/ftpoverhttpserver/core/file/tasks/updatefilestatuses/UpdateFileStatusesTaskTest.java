package pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateFileStatusesTaskTest {

    private UpdateFileStatusesTask updateFileStatusesTask;
    @Mock
    private FileDao fileDao;
    @Mock
    private ChangeStatusHandler changeStatusHandler;

    @BeforeEach
    public void setup() {
        updateFileStatusesTask = new UpdateFileStatusesTask(fileDao, Collections.singletonList(changeStatusHandler));
    }

    @Test
    public void shouldUpdateStatuses() {
        final FileEntity file = fileEntity();
        when(fileDao.findAllByStatuses(Arrays.asList(
                null,
                ProcessingStatus.PROCESSING,
                ProcessingStatus.UPDATING,
                ProcessingStatus.DELETING))).thenReturn(Collections.singletonList(file));
        when(changeStatusHandler.from()).thenReturn(Collections.singletonList(ProcessingStatus.READY));

        updateFileStatusesTask.execute();

        verify(changeStatusHandler).handle(file);
        verify(fileDao).saveAll(Collections.singletonList(file));
    }

    private FileEntity fileEntity() {
        final FileEntity file = new FileEntity();
        file.setStatus(ProcessingStatus.READY);
        return file;
    }
}