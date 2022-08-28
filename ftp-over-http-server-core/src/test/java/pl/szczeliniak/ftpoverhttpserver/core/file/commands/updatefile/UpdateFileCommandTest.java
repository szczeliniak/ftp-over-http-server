package pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.*;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateFileCommandTest implements WithAssertions {

    @InjectMocks
    private UpdateFileCommand updateFileCommand;
    @Mock
    private FileDao fileDao;
    @Mock
    private FileStorageClient fileStorageClient;

    @Test
    public void shouldUpdateFile() {
        when(fileDao.findById(1)).thenReturn(Optional.of(fileEntity("old_ftp_file_name", null, null, ProcessingStatus.READY)));
        when(fileStorageClient.upload("name", new byte[]{1, 2, 3}))
                .thenReturn("uploaded_file_name");

        final UpdateFileResponse response = updateFileCommand.execute(UpdateFileRequest.builder()
                .name("name")
                .id(1)
                .bytes(new byte[]{1, 2, 3})
                .contentType("image/jpeg")
                .size(10)
                .build());

        verify(fileDao).save(fileEntity("uploaded_file_name", "name", 10L, ProcessingStatus.UPDATING));
        verify(fileStorageClient).delete("old_ftp_file_name");
        assertThat(response).isEqualTo(UpdateFileResponse.builder()
                .id(1)
                .build());
    }

    @Test
    public void shouldThrowExceptionWhenContentTypeIsNotSupported() {
        assertThatThrownBy(() -> updateFileCommand.execute(UpdateFileRequest.builder()
                .contentType("unsupported")
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.UNSUPPORTED_FORMAT);

        verify(fileDao, never()).save(any());
        verify(fileStorageClient, never()).delete(any());
    }

    @Test
    public void shouldThrowExceptionWhenMimeTypeMismatch() {
        when(fileDao.findById(1)).thenReturn(Optional.of(fileEntity(null, null, null, null)));

        assertThatThrownBy(() -> updateFileCommand.execute(UpdateFileRequest.builder()
                .id(1)
                .contentType("application/pdf")
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.FILE_MIME_TYPE_MISMATCH);

        verify(fileDao, never()).save(any());
        verify(fileStorageClient, never()).delete(any());
    }

    @Test
    public void shouldThrowExceptionWhenProcessingStatusInNotModifiable() {
        when(fileDao.findById(1)).thenReturn(Optional.of(fileEntity(null, null, null, ProcessingStatus.PROCESSING)));

        assertThatThrownBy(() -> updateFileCommand.execute(UpdateFileRequest.builder()
                .id(1)
                .contentType("image/jpeg")
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.CANNOT_MODIFY_FILE_WHICH_IS_BEING_PROCESSED);

        verify(fileDao, never()).save(any());
        verify(fileStorageClient, never()).delete(any());
    }

    private FileEntity fileEntity(
            final String ftpFileName,
            final String originalFileName,
            final Long size,
            final ProcessingStatus status) {
        final FileEntity file = new FileEntity();
        file.setId(1);
        file.setFtpFileName(ftpFileName);
        file.setOriginalFileName(originalFileName);
        file.setContentType(ContentType.JPG);
        file.setSize(size);
        file.setStatus(status);
        return file;
    }
}