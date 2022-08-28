package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.*;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DownloadFileQueryTest implements WithAssertions {

    private DownloadFileQuery downloadFileQuery;
    @Mock
    private FileDao fileDao;
    @Mock
    private FileStorageClient fileStorageClient;
    @Mock
    private HeadersFactory headersFactory;

    @BeforeEach
    public void setup() {
        downloadFileQuery = new DownloadFileQuery(fileDao, fileStorageClient, Collections.singletonList(headersFactory));
    }

    @Test
    public void shouldThrowExceptionWhenFileNotFound() {
        when(fileDao.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> downloadFileQuery.execute(DownloadFileRequest.builder()
                .id(1)
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.FILE_BY_ID_NOT_FOUND);
    }

    @Test
    public void shouldThrowExceptionWhenFileStatusIsNotDownloadable() {
        when(fileDao.findById(1)).thenReturn(Optional.of(fileEntity(ProcessingStatus.PROCESSING)));

        assertThatThrownBy(() -> downloadFileQuery.execute(DownloadFileRequest.builder()
                .id(1)
                .build())).isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_DOWNLOADABLE_FILE);
    }

    @Test
    public void shouldDownloadFile() {
        final FileEntity file = fileEntity(ProcessingStatus.READY);
        when(fileDao.findById(1)).thenReturn(Optional.of(file));
        when(fileStorageClient.download("file_name")).thenReturn(new byte[]{1, 2, 3});
        when(headersFactory.forContentTypes()).thenReturn(Collections.singletonList(ContentType.JPG));
        when(headersFactory.prepareHeaders(file)).thenReturn(Map.of("header", Collections.singletonList("header_value")));

        DownloadFileResponse response = downloadFileQuery.execute(DownloadFileRequest.builder()
                .id(1)
                .build());

        assertThat(response).isEqualTo(downloadFileResponse());
    }

    private DownloadFileResponse downloadFileResponse() {
        return DownloadFileResponse.builder()
                .bytes(new byte[]{1, 2, 3})
                .contentType(ContentType.JPG)
                .headers(Map.of("header", Collections.singletonList("header_value")))
                .build();
    }

    private FileEntity fileEntity(final ProcessingStatus status) {
        final FileEntity file = new FileEntity();
        file.setStatus(status);
        file.setFtpFileName("file_name");
        file.setContentType(ContentType.JPG);
        return file;
    }
}