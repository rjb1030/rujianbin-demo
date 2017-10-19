package com.rujianbin.provider.oauth2.error;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by 汝建斌 on 2017/4/10.
 */
@Configuration
public class ErrorViewHandle {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/rujianbin-provider/common/403");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/rujianbin-provider/common/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/rujianbin-provider/common/500");

                container.addErrorPages(error403Page, error404Page, error500Page);
            }
        };
    }
}
