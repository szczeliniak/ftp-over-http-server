package pl.szczeliniak.ftpoverhttpserver.app.file;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

@Component
public class FtpClientFactory {

    public FTPClient create() {
        return new FTPClient();
    }

}
