package pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile;

import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.shared.Command;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

public class DeleteFileCommand implements Command<DeleteFileRequest, DeleteFileResponse> {

    private final FileDao fileDao;
    private final FileStorageClient fileStorageClient;

    public DeleteFileCommand(final FileDao fileDao, final FileStorageClient fileStorageClient) {
        this.fileDao = fileDao;
        this.fileStorageClient = fileStorageClient;
    }

    @Override
    public DeleteFileResponse execute(final DeleteFileRequest request) {
        FileEntity file = fileDao.findById(request.getId()).orElseThrow(() -> new FOHSException(ErrorCode.FILE_BY_ID_NOT_FOUND));
        fileStorageClient.delete(file.getFtpFileName());
        fileDao.delete(file);
        return DeleteFileResponse.builder()
                .id(file.getId())
                .build();
    }

}
