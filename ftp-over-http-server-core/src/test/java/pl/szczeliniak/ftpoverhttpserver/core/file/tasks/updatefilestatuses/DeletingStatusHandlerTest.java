package pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletingStatusHandlerTest implements WithAssertions {

    @InjectMocks
    private DeletingStatusHandler deletingStatusHandler;
    @Mock
    private FileStorageClient fileStorageClient;

    @Test
    public void shouldReturnFinalStatus() {
        assertThat(deletingStatusHandler.to()).isEqualTo(ProcessingStatus.DELETED);
    }

    @Test
    public void shouldReturnStartStatuses() {
        assertThat(deletingStatusHandler.from()).isEqualTo(List.of(ProcessingStatus.DELETING));
    }

    @Test
    public void shouldHandleStatus() {
        final FileEntity file = fileEntity(null);

        deletingStatusHandler.handle(file);

        verify(fileStorageClient).delete("file_name");
        assertThat(file).isEqualTo(fileEntity(ProcessingStatus.DELETED));
    }

    private FileEntity fileEntity(final ProcessingStatus status) {
        final FileEntity file = new FileEntity();
        file.setFtpFileName("file_name");
        file.setStatus(status);
        return file;
    }

}