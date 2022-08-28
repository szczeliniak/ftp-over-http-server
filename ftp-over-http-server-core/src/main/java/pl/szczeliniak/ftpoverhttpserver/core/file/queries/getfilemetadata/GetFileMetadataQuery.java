package pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata;

import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;
import pl.szczeliniak.ftpoverhttpserver.core.shared.ErrorCode;
import pl.szczeliniak.ftpoverhttpserver.core.shared.FOHSException;
import pl.szczeliniak.ftpoverhttpserver.core.shared.Query;

public class GetFileMetadataQuery implements Query<GetFileMetadataRequest, GetFileMetadataResponse> {

    private final FileDao fileDao;

    public GetFileMetadataQuery(final FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public GetFileMetadataResponse execute(final GetFileMetadataRequest request) {
        FileEntity file = fileDao.findById(request.getId()).orElseThrow(() -> new FOHSException(ErrorCode.FILE_BY_ID_NOT_FOUND));
        return GetFileMetadataResponse.builder()
                .fileName(file.getOriginalFileName())
                .extension(file.getContentType().getExtension())
                .status(file.getStatus())
                .size(file.getSize())
                .build();
    }

}
