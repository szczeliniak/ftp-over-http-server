package pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.*;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadFileCommandTest implements WithAssertions {

    @InjectMocks
    private UploadFileCommand uploadFileCommand;
    @Mock
    private FileDao fileDao;
    @Mock
    private FileStorageClient fileStorageClient;

    @Test
    public void shouldUploadFile() {
        when(fileStorageClient.upload("name", new byte[]{1, 1, 1})).thenReturn("uploaded_file_name");
        doAnswer(invocationOnMock -> {
            Object file = invocationOnMock.getArgument(0);
            if (file instanceof FileEntity) {
                ((FileEntity) file).setId(1);
            }
            return null;
        }).when(fileDao).save(fileEntity());

        final UploadFileResponse response = uploadFileCommand.execute(UploadFileRequest.builder()
                .bytes(new byte[]{1, 1, 1})
                .contentType("image/jpeg")
                .name("name")
                .size(10)
                .build());

        assertThat(response).isEqualTo(UploadFileResponse.builder()
                .id(1)
                .build());

    }

    @Test
    public void shouldThrowExceptionWhenContentTypeIsNotSupported() {
        assertThatThrownBy(() -> uploadFileCommand.execute(UploadFileRequest.builder()
                .contentType("unsupported")
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.UNSUPPORTED_FORMAT);

        verify(fileDao, never()).save(any());
        verify(fileStorageClient, never()).delete(any());
    }

    private FileEntity fileEntity() {
        final FileEntity file = new FileEntity();
        file.setFtpFileName("uploaded_file_name");
        file.setOriginalFileName("name");
        file.setContentType(ContentType.JPG);
        file.setSize(10L);
        file.setStatus(ProcessingStatus.PROCESSING);
        return file;
    }

}