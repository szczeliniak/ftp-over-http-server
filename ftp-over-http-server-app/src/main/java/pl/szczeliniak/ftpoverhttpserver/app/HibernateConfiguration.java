package pl.szczeliniak.ftpoverhttpserver.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("pl.szczeliniak.ftpoverhttpserver.core")
public class HibernateConfiguration {
}
