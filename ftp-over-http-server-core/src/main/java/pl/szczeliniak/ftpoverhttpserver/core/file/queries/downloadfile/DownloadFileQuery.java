package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;
import pl.szczeliniak.ftpoverhttpserver.core.shared.Query;

import java.util.List;

public class DownloadFileQuery implements Query<DownloadFileRequest, DownloadFileResponse> {

    private final FileDao fileDao;
    private final FileStorageClient fileStorageClient;
    private final List<HeadersFactory> headersFactories;

    public DownloadFileQuery(
            final FileDao fileDao,
            final FileStorageClient fileStorageClient,
            final List<HeadersFactory> headersFactories) {
        this.fileDao = fileDao;
        this.fileStorageClient = fileStorageClient;
        this.headersFactories = headersFactories;
    }

    @Override
    public DownloadFileResponse execute(final DownloadFileRequest request) {
        FileEntity file = fileDao.findById(request.getId())
                .orElseThrow(() -> new FOHSException(ErrorCode.FILE_BY_ID_NOT_FOUND));

        if (file.getStatus() != null && !file.getStatus().isDownloadable()) {
            throw new FOHSException(ErrorCode.NOT_DOWNLOADABLE_FILE);
        }

        return DownloadFileResponse.builder()
                .bytes(fileStorageClient.download(file.getFtpFileName()))
                .contentType(file.getContentType())
                .headers(headersFactories.stream()
                        .filter(factory -> factory.forContentTypes().contains(file.getContentType()))
                        .findFirst()
                        .orElseThrow(() -> new FOHSException(ErrorCode.UNSUPPORTED_HEADERS_FACTORY))
                        .prepareHeaders(file))
                .build();
    }

}
