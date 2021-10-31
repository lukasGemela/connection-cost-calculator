package com.flixbus.costcalculator.configuration;

import com.flixbus.costcalculator.utils.logging.MdcContext;
import com.flixbus.costcalculator.utils.logging.MdcFilter;
import com.flixbus.costcalculator.utils.logging.RandomIdGenerator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Optional;
import java.util.function.Predicate;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .paths(Predicate.not(PathSelectors.regex("/error.*")))
                .build()
                .genericModelSubstitutes(Optional.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("connection cost calculator")
                .description("connection cost calculator")
                .version("1.0")
                .build();
    }

    @Bean
    public FilterRegistrationBean<MdcFilter> loggingFilter(RandomIdGenerator correlationIdGenerator, MdcContext mdcContext) {

        final var registrationBean = new FilterRegistrationBean<MdcFilter>();

        registrationBean.setFilter(new MdcFilter(mdcContext, correlationIdGenerator));
        registrationBean.addUrlPatterns("/api", "/api/*");

        return registrationBean;
    }

    @Bean
    public RandomIdGenerator correlationIdGenerator() {
        return new RandomIdGenerator() {};
    }
}

