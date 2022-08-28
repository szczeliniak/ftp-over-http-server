package pl.szczeliniak.ftpoverhttpserver.app.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileDao;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileFacade;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileStorageClient;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile.DeleteFileCommand;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile.UpdateFileCommand;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile.UploadFileCommand;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.DownloadFileQuery;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.JpegHeadersFactory;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.PdfHeadersFactory;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata.GetFileMetadataQuery;

import java.util.Arrays;

@Configuration
public class FileConfiguration {

    @Bean
    public FileFacade fileFacade(final FileDao fileDao, final FileStorageClient fileStorageClient) {
        return new FileFacade(
                new UploadFileCommand(fileDao, fileStorageClient),
                new DownloadFileQuery(fileDao, fileStorageClient, Arrays.asList(new JpegHeadersFactory(), new PdfHeadersFactory())),
                new DeleteFileCommand(fileDao),
                new GetFileMetadataQuery(fileDao),
                new UpdateFileCommand(fileDao, fileStorageClient));
    }

}
