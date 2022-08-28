package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class PdfHeadersFactoryTest implements WithAssertions {

    @InjectMocks
    private PdfHeadersFactory pdfHeadersFactory;

    @Test
    public void shouldReturnListOfSupportedContentTypes() {
        List<ContentType> contentTypes = pdfHeadersFactory.forContentTypes();

        assertThat(contentTypes).isEqualTo(List.of(ContentType.PDF));
    }

    @Test
    public void shouldReturnHeaders() {

        Map<String, List<String>> headers = pdfHeadersFactory.prepareHeaders(fileEntity());

        assertThat(headers).isEqualTo(Map.of(
                "pdf-size", Collections.singletonList("100"),
                "pdf-content-type", Collections.singletonList("application/pdf"),
                "pdf-ftp-file-name", Collections.singletonList("file_name"),
                "pdf-original-file-name", Collections.singletonList("original_file_name")
        ));

    }

    private FileEntity fileEntity() {
        final FileEntity file = new FileEntity();
        file.setOriginalFileName("original_file_name");
        file.setFtpFileName("file_name");
        file.setSize(100L);
        file.setContentType(ContentType.PDF);
        return file;
    }

}