package pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile;

import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.shared.Command;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

public class UploadFileCommand implements Command<UploadFileRequest, UploadFileResponse> {

    private final FileDao fileDao;
    private final FileStorageClient fileStorageClient;

    public UploadFileCommand(final FileDao fileDao, final FileStorageClient fileStorageClient) {
        this.fileDao = fileDao;
        this.fileStorageClient = fileStorageClient;
    }

    @Override
    public UploadFileResponse execute(final UploadFileRequest request) {
        if (!ContentType.isSupported(request.getContentType())) {
            throw new FOHSException(ErrorCode.UNSUPPORTED_FORMAT);
        }

        final FileEntity file = new FileEntity();
        file.setFtpFileName(fileStorageClient.upload(request.getName(), request.getBytes()));
        file.setOriginalFileName(request.getName());
        file.setContentType(ContentType.byMimeType(request.getContentType()));
        file.setSize(request.getSize());
        fileDao.save(file);

        return UploadFileResponse.builder()
                .id(file.getId())
                .build();
    }

}
