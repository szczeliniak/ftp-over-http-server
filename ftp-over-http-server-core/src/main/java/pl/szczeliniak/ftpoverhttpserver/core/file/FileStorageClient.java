package pl.szczeliniak.ftpoverhttpserver.core.file;

public interface FileStorageClient {

    String upload(final String name, final byte[] bytes);

    byte[] download(final String name);

    void delete(final String name);

}
