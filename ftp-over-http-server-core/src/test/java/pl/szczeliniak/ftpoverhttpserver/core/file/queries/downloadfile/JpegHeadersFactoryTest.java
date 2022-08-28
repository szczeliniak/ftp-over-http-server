package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class JpegHeadersFactoryTest implements WithAssertions {

    @InjectMocks
    private JpegHeadersFactory jpegHeadersFactory;

    @Test
    public void shouldReturnListOfSupportedContentTypes() {
        List<ContentType> contentTypes = jpegHeadersFactory.forContentTypes();

        assertThat(contentTypes).isEqualTo(Arrays.asList(ContentType.PNG, ContentType.JPG));
    }

    @Test
    public void shouldReturnHeaders() {

        Map<String, List<String>> headers = jpegHeadersFactory.prepareHeaders(fileEntity());

        assertThat(headers).isEqualTo(Map.of(
                "jpg-size", Collections.singletonList("100"),
                "jpg-content-type", Collections.singletonList("image/jpeg"),
                "jpg-ftp-file-name", Collections.singletonList("file_name"),
                "jpg-original-file-name", Collections.singletonList("original_file_name")
        ));

    }

    private FileEntity fileEntity() {
        final FileEntity file = new FileEntity();
        file.setOriginalFileName("original_file_name");
        file.setFtpFileName("file_name");
        file.setSize(100L);
        file.setContentType(ContentType.JPG);
        return file;
    }

}