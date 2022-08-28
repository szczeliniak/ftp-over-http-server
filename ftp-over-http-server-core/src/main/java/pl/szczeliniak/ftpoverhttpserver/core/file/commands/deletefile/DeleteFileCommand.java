package pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile;

import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.file.ProcessingStatus;
import pl.szczeliniak.ftpoverhttpserver.core.shared.Command;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;

public class DeleteFileCommand implements Command<DeleteFileRequest, DeleteFileResponse> {

    private final FileDao fileDao;

    public DeleteFileCommand(final FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public DeleteFileResponse execute(final DeleteFileRequest request) {
        FileEntity file = fileDao.findById(request.getId()).orElseThrow(() -> new FOHSException(ErrorCode.FILE_BY_ID_NOT_FOUND));

        if (file.getStatus() != null && !file.getStatus().isModifiable()) {
            throw new FOHSException(ErrorCode.CANNOT_MODIFY_FILE_WHICH_IS_BEING_PROCESSED);
        }

        file.setStatus(ProcessingStatus.DELETING);
        fileDao.save(file);
        return DeleteFileResponse.builder()
                .id(file.getId())
                .build();
    }

}
