package com.uisftech.cloan.limit.config.apidoc;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.uisftech.cloan.limit.config.CloanLimitProperties;

@Configuration
@EnableSwagger2
@Profile(com.uisftech.cloan.limit.constants.Constants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfiguration {

	private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	@Bean
	public Docket swaggerSpringfoxDocket(CloanLimitProperties scfsLoanProperties) {
		log.debug("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();
		Contact contact = new Contact(scfsLoanProperties.getSwagger().getContactName(), scfsLoanProperties
				.getSwagger().getContactUrl(), scfsLoanProperties.getSwagger().getContactEmail());

		ApiInfo apiInfo = new ApiInfo(scfsLoanProperties.getSwagger().getTitle(), scfsLoanProperties.getSwagger()
				.getDescription(), scfsLoanProperties.getSwagger().getVersion(), scfsLoanProperties.getSwagger()
				.getTermsOfServiceUrl(), contact, scfsLoanProperties.getSwagger().getLicense(),
				scfsLoanProperties.getSwagger().getLicenseUrl());

		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).forCodeGeneration(true)
				.genericModelSubstitutes(ResponseEntity.class).ignoredParameterTypes(java.sql.Date.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class).select()
				.paths(regex(DEFAULT_INCLUDE_PATTERN)).build();
		watch.stop();
		log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}
}
