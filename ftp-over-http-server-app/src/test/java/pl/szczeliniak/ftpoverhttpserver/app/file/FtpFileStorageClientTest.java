package pl.szczeliniak.ftpoverhttpserver.app.file;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FtpFileStorageClientTest implements WithAssertions {

    private FtpFileStorageClient ftpFileStorageClient;
    @Mock
    private FtpClientFactory ftpClientFactory;

    @BeforeEach
    public void setup() {
        ftpFileStorageClient = new FtpFileStorageClient("host", 1, "user", "password", ftpClientFactory);
    }

    @Test
    public void shouldDeleteFile() throws IOException {
        FTPClient ftpClient = Mockito.mock(FTPClient.class);
        when(ftpClientFactory.create()).thenReturn(ftpClient);
        when(ftpClient.getReplyCode()).thenReturn(200);
        when(ftpClient.isConnected()).thenReturn(true);

        ftpFileStorageClient.delete("file_name");

        verify(ftpClient).connect("host", 1);
        verify(ftpClient).setFileType(FTPClient.BINARY_FILE_TYPE);
        verify(ftpClient).enterLocalPassiveMode();
        verify(ftpClient).login("user", "password");
        verify(ftpClient).deleteFile("files/file_name");
        verify(ftpClient).logout();
        verify(ftpClient).disconnect();
    }

    @Test
    public void shouldThrowExceptionWhenReplyCodeIsNegative() throws IOException {
        FTPClient ftpClient = Mockito.mock(FTPClient.class);
        when(ftpClientFactory.create()).thenReturn(ftpClient);
        when(ftpClient.getReplyCode()).thenReturn(400);
        when(ftpClient.isConnected()).thenReturn(false);

        assertThatThrownBy(() -> ftpFileStorageClient.delete("file_name"))
                .isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.FTP_GENERIC_ERROR);

        verify(ftpClient).connect("host", 1);
        verify(ftpClient).setFileType(FTPClient.BINARY_FILE_TYPE);
        verify(ftpClient).enterLocalPassiveMode();
        verify(ftpClient).login("user", "password");
    }

    @Test
    public void shouldDownloadFile() throws IOException {
        FTPClient ftpClient = Mockito.mock(FTPClient.class);
        when(ftpClientFactory.create()).thenReturn(ftpClient);
        when(ftpClient.getReplyCode()).thenReturn(200);
        when(ftpClient.isConnected()).thenReturn(true);

        byte[] content = ftpFileStorageClient.download("file_name");

        verify(ftpClient).connect("host", 1);
        verify(ftpClient).setFileType(FTPClient.BINARY_FILE_TYPE);
        verify(ftpClient).enterLocalPassiveMode();
        verify(ftpClient).login("user", "password");
        verify(ftpClient).retrieveFile(eq("files/file_name"), any());
        verify(ftpClient).logout();
        verify(ftpClient).disconnect();

        assertThat(content).isEqualTo(new byte[]{});
    }

    @Test
    public void shouldUploadFile() throws IOException {
        FTPClient ftpClient = Mockito.mock(FTPClient.class);
        when(ftpClientFactory.create()).thenReturn(ftpClient);
        when(ftpClient.getReplyCode()).thenReturn(200);
        when(ftpClient.isConnected()).thenReturn(true);
        when(ftpClient.listFiles("files")).thenReturn(new FTPFile[]{});

        String fileName = ftpFileStorageClient.upload("file_name", new byte[]{1, 2, 3});

        verify(ftpClient).connect("host", 1);
        verify(ftpClient).setFileType(FTPClient.BINARY_FILE_TYPE);
        verify(ftpClient).enterLocalPassiveMode();
        verify(ftpClient).login("user", "password");
        verify(ftpClient).makeDirectory("files");
        verify(ftpClient).storeFile(eq("files/" + fileName), any());
        verify(ftpClient).logout();
        verify(ftpClient).disconnect();
        assertThat(fileName).endsWith("file_name");
    }

    @Test
    public void shouldThrowExceptionWhenSizeIsOverMaxBytes() {
        assertThatThrownBy(() -> ftpFileStorageClient.upload("file_name", new byte[1000010]))
                .isInstanceOf(FOHSException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.FILE_TOO_LARGE);
    }

}