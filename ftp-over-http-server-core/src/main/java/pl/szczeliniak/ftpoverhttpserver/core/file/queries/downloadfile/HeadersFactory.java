package pl.szczeliniak.ftpoverhttpserver.core.file.queries.downloadfile;

import pl.szczeliniak.ftpoverhttpserver.core.file.ContentType;
import pl.szczeliniak.ftpoverhttpserver.core.file.FileEntity;

import java.util.List;
import java.util.Map;

public interface HeadersFactory {

    List<ContentType> forContentTypes();

    Map<String, List<String>> prepareHeaders(final FileEntity file);

}
