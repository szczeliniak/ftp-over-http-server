package pl.szczeliniak.ftpoverhttpserver.core.file;

import java.util.List;
import java.util.Optional;

public interface FileDao {

    void save(final FileEntity file);

    Optional<FileEntity> findById(final Integer id);

    void delete(final FileEntity file);

    List<FileEntity> findAllByStatuses(final List<ProcessingStatus> statuses);

    void saveAll(final List<FileEntity> files);

}
