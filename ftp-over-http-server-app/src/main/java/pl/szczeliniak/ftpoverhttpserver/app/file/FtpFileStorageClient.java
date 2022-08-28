package pl.szczeliniak.ftpoverhttpserver.app.file;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class FtpFileStorageClient implements FileStorageClient {

    private static final String BASE_DIR = "files";
    private static final int MAX_BYTES_SIZE = 1000000;
    private static final Logger logger = LoggerFactory.getLogger(FtpFileStorageClient.class);

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final FtpClientFactory ftpClientFactory;

    public FtpFileStorageClient(
            @Value("${ftp.host}") String host,
            @Value("${ftp.port}") int port,
            @Value("${ftp.user}") String user,
            @Value("${ftp.password}") String password,
            FtpClientFactory ftpClientFactory) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.ftpClientFactory = ftpClientFactory;
    }

    @Override
    public String upload(final String name, final byte[] content) {
        if (content.length > MAX_BYTES_SIZE) {
            throw new FOHSException(ErrorCode.FILE_TOO_LARGE);
        }

        logger.info("Uploading file with name: " + name);

        final FTPClient client = open();
        final String fileName = prepareFileName(client, name);
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(content)) {
            client.makeDirectory(BASE_DIR);
            client.storeFile(BASE_DIR + "/" + fileName, inputStream);
        } catch (final IOException exception) {
            logger.error(exception.getMessage());
            throw new FOHSException(ErrorCode.FTP_GENERIC_ERROR);
        } finally {
            close(client);
        }

        logger.info("Uploading file with name: " + fileName + " finished successfully");
        return fileName;
    }

    @Override
    public byte[] download(final String name) {
        logger.info("Downloading file with name: " + name);

        final FTPClient client = open();

        byte[] content;
        try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            client.retrieveFile(BASE_DIR + "/" + name, stream);
            content = stream.toByteArray();
        } catch (final IOException exception) {
            logger.error(exception.getMessage());
            throw new FOHSException(ErrorCode.FTP_GENERIC_ERROR);
        } finally {
            close(client);
        }

        logger.info("Downloading file with name: " + name + " finished successfully");

        return content;
    }

    @Override
    public void delete(final String name) {
        logger.info("Deleting file with name: " + name);

        final FTPClient client = open();
        delete(client, name);
        close(client);

        logger.info("Deleting file with name: " + name + " finished successfully");
    }

    private FTPClient open() {
        final FTPClient ftpClient = ftpClientFactory.create();
        try {
            ftpClient.connect(host, port);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(user, password);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                close(ftpClient);
                throw new FOHSException(ErrorCode.FTP_GENERIC_ERROR);
            }
            return ftpClient;
        } catch (final IOException e) {
            throw new FOHSException(ErrorCode.FTP_GENERIC_ERROR);
        }
    }

    private void close(final FTPClient client) {
        if (!client.isConnected()) {
            return;
        }
        try {
            client.logout();
            client.disconnect();
        } catch (final IOException exception) {
            logger.error(exception.getMessage());
            throw new FOHSException(ErrorCode.FTP_GENERIC_ERROR);
        }
    }

    private boolean exists(final FTPClient client, final String fileName) {
        try {
            for (FTPFile ftpFile : client.listFiles(BASE_DIR)) {
                if (Objects.equals(fileName, ftpFile.getName())) {
                    return true;
                }
            }
        } catch (final IOException exception) {
            logger.error(exception.getMessage());
            throw new FOHSException(ErrorCode.FTP_GENERIC_ERROR);
        }
        return false;
    }

    private void delete(final FTPClient client, final String fileName) {
        try {
            client.deleteFile(BASE_DIR + "/" + fileName);
        } catch (final IOException exception) {
            logger.error(exception.getMessage());
            throw new FOHSException(ErrorCode.FTP_GENERIC_ERROR);
        }
    }

    private String prepareFileName(final FTPClient client, final String originalFileName) {
        String name = UUID.randomUUID() + "_" + originalFileName;
        name = name.replace("\\s", "");
        if (exists(client, name)) {
            return prepareFileName(client, originalFileName);
        }
        return name;
    }

}
