package pl.szczeliniak.ftpoverhttpserver.core.file;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_generator")
    @SequenceGenerator(name = "file_id_generator", sequenceName = "seq_file_id", allocationSize = 1)
    private Integer id;

    private String ftpFileName;
    private String originalFileName;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    private Long size;

}
