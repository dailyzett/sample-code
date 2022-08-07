package jpabook.jpashop.configuration;

import java.util.ArrayList;
import jpabook.jpashop.controller.MemberController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {
	MemberController.class
})
public class SwaggerConfiguration {

	@Bean
	public Docket apiMember() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("groupMember")
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.ant("/members/**"))
			.build()
			.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
			"Title",
			"Description",
			"version 1.0",
			"https://naver.com",
			new Contact("Contact Me", "https://daum.net", "colt@colt.com"),
			"Edit Licenses",
			"https://naver.com",
			new ArrayList<>()
		);
	}
}
