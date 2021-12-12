package br.com.treinaweb.personalexpensesmanager.core.config;

import com.fasterxml.classmate.TypeResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.ErrorResponse;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "br.com.treinaweb.personalexpensesmanager.api.v1.controllers";
    private static final String CONTACT_NAME = "TreinaWeb";
    private static final String CONTACT_URL = "https://www.treinaweb.com.br/contato";
    private static final String CONTACT_EMAIL = "contato@treinaweb.com.br";
    private static final String API_TITLE = "API Gastos Pessoais";
    private static final String API_DESCRIPTION = "API para gerenciar gastos pessoais";
    private static final String API_VERSION = "1.0.0";

    private final TypeResolver typeResolver;

    public static final String ACCOUNTS_TAG = "Accounts";

    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .additionalModels(typeResolver.resolve(ErrorResponse.class))
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(PathSelectors.any())
            .build()
            .tags(new Tag(ACCOUNTS_TAG, "Account Controller"))
            .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
            .title(API_TITLE)
            .description(API_DESCRIPTION)
            .version(API_VERSION)
            .contact(new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL))
            .build();
    }

}
