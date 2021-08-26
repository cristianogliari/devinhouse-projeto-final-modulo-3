package br.com.devinhouse.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2 
public class Projeto2Modulo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Projeto2Modulo2Application.class, args);
	}
	
	@Bean
	public Docket processosApi() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.devinhouse.backend"))
				.paths(PathSelectors.regex("/backend.*"))
				.build()
				.apiInfo(metaInfo());
				
				
	}

	private ApiInfo metaInfo() {
		return new ApiInfoBuilder()
				.title("Processos API")
				.description("Documentação de referência para API de Processos para desenvolvedores")
				.version("1.0.0")
				.build();
	}
}
