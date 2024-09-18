package cn.ruishan.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.web.servlet.resource.HttpResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger生成api文档
 *
 * @author longgang.lei
 * @date 2019年9月5日
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

	@Bean
	public Docket createIOTApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("物联网管理")
				.ignoredParameterTypes(HttpRequest.class, HttpResource.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("cn.ruishan.iot.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public Docket createSysApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("平台管理")
				.ignoredParameterTypes(HttpRequest.class, HttpResource.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("cn.ruishan.sys.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {

		Contact contact = new Contact("瑞山云平台", "platform.ruishan.cn", "rs_rsdw@163.com");

		return new ApiInfoBuilder()
				.title("瑞山云平台接口文档")
				.description("用于瑞山云平台开发组生成RESTapi风格的接口")
				.termsOfServiceUrl("")
				.contact(contact)
				.version("1.0")
				.build();
	}
}
