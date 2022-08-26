package pl.szczeliniak.ftpoverhttpserver.app.file;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileFacade;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile.DeleteFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.deletefile.DeleteFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile.UpdateFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.updatefile.UpdateFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile.UploadFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.commands.uploadfile.UploadFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.DownloadFileRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile.DownloadFileResponse;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata.GetFileMetadataRequest;
import pl.szczeliniak.ftpoverhttpserver.core.file.queries.getfilemetadata.GetFileMetadataResponse;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileFacade fileFacade;

    public FileController(final FileFacade fileFacade) {
        this.fileFacade = fileFacade;
    }

    @PostMapping
    public ResponseEntity<UploadFileResponse> upload(@RequestParam("file") final MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().body(fileFacade.upload(UploadFileRequest.builder()
                .name(multipartFile.getOriginalFilename())
                .bytes(multipartFile.getBytes())
                .contentType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .build()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateFileResponse> update(
            @RequestParam("file") final MultipartFile multipartFile,
            @PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok().body(fileFacade.update(UpdateFileRequest.builder()
                .name(multipartFile.getOriginalFilename())
                .bytes(multipartFile.getBytes())
                .contentType(multipartFile.getContentType())
                .id(id)
                .size(multipartFile.getSize())
                .build()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable("id") final int id) {
        final DownloadFileResponse downloadFileResponse = fileFacade.download(DownloadFileRequest.builder()
                .id(id)
                .build());

        final HttpHeaders headers = new HttpHeaders();
        headers.putAll(downloadFileResponse.getHeaders());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(downloadFileResponse.getContentType().getMimeType()))
                .body(downloadFileResponse.getBytes());
    }

    @GetMapping("/{id}/metadata")
    public ResponseEntity<GetFileMetadataResponse> getFileMetadata(@PathVariable("id") final int id) {
        return ResponseEntity.ok().body(fileFacade.getFileMetadata(GetFileMetadataRequest.builder().id(id).build()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteFileResponse> delete(@PathVariable("id") final int id) {
        return ResponseEntity.ok().body(fileFacade.delete(DeleteFileRequest.builder()
                .id(id)
                .build()));
    }

}
