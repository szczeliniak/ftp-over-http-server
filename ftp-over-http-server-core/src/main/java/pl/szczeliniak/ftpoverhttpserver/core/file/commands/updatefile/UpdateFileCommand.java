package pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile;

import pl.szczeliniak.ftpoverhttpserver.core.file.*;
import pl.szczeliniak.ftpoverhttpserver.core.shared.Command;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

public class UpdateFileCommand implements Command<UpdateFileRequest, UpdateFileResponse> {

    private final FileDao fileDao;
    private final FileStorageClient fileStorageClient;

    public UpdateFileCommand(final FileDao fileDao, final FileStorageClient fileStorageClient) {
        this.fileDao = fileDao;
        this.fileStorageClient = fileStorageClient;
    }

    @Override
    public UpdateFileResponse execute(final UpdateFileRequest request) {
        if (!ContentType.isSupported(request.getContentType())) {
            throw new FOHSException(ErrorCode.UNSUPPORTED_FORMAT);
        }

        final FileEntity file = fileDao.findById(request.getId()).orElseThrow(() -> new FOHSException(ErrorCode.FILE_BY_ID_NOT_FOUND));

        if (!ContentType.byMimeType(request.getContentType()).equals(file.getContentType())) {
            throw new FOHSException(ErrorCode.FILE_MIME_TYPE_MISMATCH);
        }

        if (file.getStatus() != null && !file.getStatus().isModifiable()) {
            throw new FOHSException(ErrorCode.CANNOT_MODIFY_FILE_WHICH_IS_BEING_PROCESSED);
        }

        final String oldFtpFileName = file.getFtpFileName();
        file.setFtpFileName(fileStorageClient.upload(request.getName(), request.getBytes()));
        file.setOriginalFileName(request.getName());
        file.setContentType(ContentType.byMimeType(request.getContentType()));
        file.setSize(request.getSize());
        file.setStatus(ProcessingStatus.UPDATING);
        fileDao.save(file);

        fileStorageClient.delete(oldFtpFileName);

        return UpdateFileResponse.builder()
                .id(file.getId())
                .build();
    }

}
