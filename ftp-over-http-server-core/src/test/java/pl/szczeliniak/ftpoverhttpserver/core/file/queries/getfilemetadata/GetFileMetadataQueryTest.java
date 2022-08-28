package pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFileMetadataQueryTest implements WithAssertions {

    @InjectMocks
    private GetFileMetadataQuery getFileMetadataQuery;
    @Mock
    private FileDao fileDao;

    @Test
    public void shouldReturnFileMetadata() {
        when(fileDao.findById(1)).thenReturn(Optional.of(fileEntity()));

        final GetFileMetadataResponse response = getFileMetadataQuery.execute(GetFileMetadataRequest.builder()
                .id(1)
                .build());

        assertThat(response).isEqualTo(getFileMetadataResponse());
    }

    @Test
    public void shouldThrowExceptionWhenFileNotFound() {
        when(fileDao.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getFileMetadataQuery.execute(GetFileMetadataRequest.builder()
                .id(1)
                .build()))
                .isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.FILE_BY_ID_NOT_FOUND);
    }

    private GetFileMetadataResponse getFileMetadataResponse() {
        return GetFileMetadataResponse.builder()
                .status(ProcessingStatus.PROCESSING)
                .size(100L)
                .fileName("original_file_name")
                .extension("jpg")
                .build();
    }

    private FileEntity fileEntity() {
        final FileEntity file = new FileEntity();
        file.setOriginalFileName("original_file_name");
        file.setContentType(ContentType.JPG);
        file.setStatus(ProcessingStatus.PROCESSING);
        file.setSize(100L);
        return file;
    }

}