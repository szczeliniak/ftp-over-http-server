package pl.szczeliniak.ftpoverhttpserver.core.file.tasks.updatefilestatuses;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class NullProcessingUpdatingStatusHandlerTest implements WithAssertions {

    @InjectMocks
    private NullProcessingUpdatingStatusHandler nullProcessingUpdatingStatusHandler;

    @Test
    public void shouldReturnFinalStatus() {
        assertThat(nullProcessingUpdatingStatusHandler.to()).isEqualTo(ProcessingStatus.READY);
    }

    @Test
    public void shouldReturnStartStatuses() {
        assertThat(nullProcessingUpdatingStatusHandler.from()).isEqualTo(Arrays.asList(null, ProcessingStatus.PROCESSING, ProcessingStatus.UPDATING));
    }

    @Test
    public void shouldHandleStatus() {
        final FileEntity file = fileEntity(null);

        nullProcessingUpdatingStatusHandler.handle(file);

        assertThat(file).isEqualTo(fileEntity(ProcessingStatus.READY));
    }

    private FileEntity fileEntity(final ProcessingStatus status) {
        final FileEntity file = new FileEntity();
        file.setFtpFileName("file_name");
        file.setStatus(status);
        return file;
    }

}