package com.oket.micro.auth.config;

import cn.hutool.core.collection.CollectionUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhixiang liuzhixiang
 * DATE   2021/8/31
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        List<GrantType> grantTypes = new ArrayList<>();
        //密码模式
//        String passwordTokenUrl = "http://192.168.17.12:9201/auth/oauth/token";
        String passwordTokenUrl = "http://192.168.0.242:9201/auth/oauth/token";
        ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl);
        grantTypes.add(resourceOwnerPasswordCredentialsGrant);
        OAuth oAuth = new OAuthBuilder().name("oauth2").grantTypes(grantTypes).build();
        List<AuthorizationScope> scopes = new ArrayList<>();
        scopes.add(new AuthorizationScope("read","read  resources"));
        scopes.add(new AuthorizationScope("write","write resources"));
        scopes.add(new AuthorizationScope("reads","read all resources"));
        scopes.add(new AuthorizationScope("writes","write all resources"));
        SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
        SecurityContext securityContext = new SecurityContext(CollectionUtil.newArrayList(securityReference), PathSelectors.ant("/api/**"));
        List<SecurityScheme> securitySchemes = CollectionUtil.newArrayList(oAuth);
        //securyContext
        List<SecurityContext> securityContexts = CollectionUtil.newArrayList(securityContext);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.oket.micro.auth.controller"))
                .paths(PathSelectors.any())
                .build().securityContexts(securityContexts).securitySchemes(securitySchemes);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("micro-auth")
                .description("授权服务API文档")
                .version("1.0")
                .build();
    }

}
