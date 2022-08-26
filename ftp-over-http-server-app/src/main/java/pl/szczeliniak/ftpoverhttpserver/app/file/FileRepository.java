package pl.szczeliniak.ftpoverhttpserver.app.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer>  {



}
