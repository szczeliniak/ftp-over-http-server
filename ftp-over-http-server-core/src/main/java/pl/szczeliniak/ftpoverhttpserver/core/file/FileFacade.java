package pl.szczeliniak.ftpoverhttpserver.core.file;

import pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile.DeleteFileCommand;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile.DeleteFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile.DeleteFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile.UpdateFileCommand;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile.UpdateFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile.UpdateFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile.UploadFileCommand;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile.UploadFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile.UploadFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.DownloadFileQuery;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.DownloadFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.DownloadFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata.GetFileMetadataQuery;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata.GetFileMetadataRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata.GetFileMetadataResponse;

public class FileFacade {

    private final UploadFileCommand uploadFileCommand;
    private final DownloadFileQuery downloadFileQuery;
    private final DeleteFileCommand deleteFileCommand;
    private final GetFileMetadataQuery getFileMetadataQuery;
    private final UpdateFileCommand updateFileCommand;

    public FileFacade(final UploadFileCommand uploadFileCommand,
                      final DownloadFileQuery downloadFileQuery,
                      final DeleteFileCommand deleteFileCommand,
                      final GetFileMetadataQuery getFileMetadataQuery,
                      final UpdateFileCommand updateFileCommand) {
        this.uploadFileCommand = uploadFileCommand;
        this.downloadFileQuery = downloadFileQuery;
        this.deleteFileCommand = deleteFileCommand;
        this.getFileMetadataQuery = getFileMetadataQuery;
        this.updateFileCommand = updateFileCommand;
    }

    public UploadFileResponse upload(final UploadFileRequest request) {
        return uploadFileCommand.execute(request);
    }

    public DownloadFileResponse download(final DownloadFileRequest request) {
        return downloadFileQuery.execute(request);
    }

    public DeleteFileResponse delete(final DeleteFileRequest request) {
        return deleteFileCommand.execute(request);
    }

    public GetFileMetadataResponse getFileMetadata(final GetFileMetadataRequest request) {
        return getFileMetadataQuery.execute(request);
    }

    public UpdateFileResponse update(final UpdateFileRequest updateFileRequest) {
        return updateFileCommand.execute(updateFileRequest);
    }

}