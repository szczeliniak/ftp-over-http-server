package pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFileCommandTest implements WithAssertions {

    @InjectMocks
    private DeleteFileCommand deleteFileCommand;
    @Mock
    private FileDao fileDao;

    @Test
    public void shouldDeleteFile() {
        when(fileDao.findById(1)).thenReturn(Optional.of(fileEntity(ProcessingStatus.READY)));

        DeleteFileResponse response = deleteFileCommand.execute(DeleteFileRequest.builder()
                .id(1)
                .build());

        verify(fileDao).save(fileEntity(ProcessingStatus.DELETING));
        assertThat(response).isEqualTo(expectedResponse());
    }

    @Test
    public void shouldThrowExceptionWhenFileNotFound() {
        when(fileDao.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteFileCommand.execute(DeleteFileRequest.builder()
                .id(1)
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.FILE_BY_ID_NOT_FOUND);

        verify(fileDao, never()).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenFileStatusIsNotModifiable() {
        when(fileDao.findById(1)).thenReturn(Optional.of(fileEntity(ProcessingStatus.UPDATING)));

        assertThatThrownBy(() -> deleteFileCommand.execute(DeleteFileRequest.builder()
                .id(1)
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.CANNOT_MODIFY_FILE_WHICH_IS_BEING_PROCESSED);

        verify(fileDao, never()).save(any());
    }

    private DeleteFileResponse expectedResponse() {
        return DeleteFileResponse.builder()
                .id(1)
                .build();
    }

    private FileEntity fileEntity(final ProcessingStatus processingStatus) {
        final FileEntity file = new FileEntity();
        file.setId(1);
        file.setStatus(processingStatus);
        return file;
    }

}