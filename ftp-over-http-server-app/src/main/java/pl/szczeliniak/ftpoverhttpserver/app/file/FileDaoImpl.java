package pl.szczeliniak.ftpoverhttpserver.app.file;

import org.springframework.stereotype.Component;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;

import java.util.Optional;

@Component
public class FileDaoImpl implements FileDao {

    private final FileRepository fileRepository;

    public FileDaoImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void save(FileEntity file) {
        fileRepository.save(file);
    }

    @Override
    public Optional<FileEntity> findById(Integer id) {
        return fileRepository.findById(id);
    }

    @Override
    public void delete(FileEntity file) {
        fileRepository.delete(file);
    }

}
