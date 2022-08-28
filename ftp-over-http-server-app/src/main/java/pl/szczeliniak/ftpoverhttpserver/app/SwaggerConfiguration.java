package pl.szczeliniak.ftpoverhttpserver.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.emptyList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final String applicationName;
    private final String applicationDescription;
    private final String applicationVersion;
    private final String applicationCreatorName;
    private final String applicationCreatorUrl;
    private final String applicationCreatorEmail;

    public SwaggerConfiguration(@Value("${application.name}") String applicationName,
                                @Value("${application.description}") String applicationDescription,
                                @Value("${application.version}") String applicationVersion,
                                @Value("${application.creator.name}") String applicationCreatorName,
                                @Value("${application.creator.url}") String applicationCreatorUrl,
                                @Value("${application.creator.email}") String applicationCreatorEmail) {
        this.applicationName = applicationName;
        this.applicationDescription = applicationDescription;
        this.applicationVersion = applicationVersion;
        this.applicationCreatorName = applicationCreatorName;
        this.applicationCreatorUrl = applicationCreatorUrl;
        this.applicationCreatorEmail = applicationCreatorEmail;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.szczeliniak.ftpoverhttpserver"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                applicationName,
                applicationDescription,
                applicationVersion,
                null,
                new Contact(applicationCreatorName, applicationCreatorUrl, applicationCreatorEmail),
                null,
                null, emptyList());
    }
}
