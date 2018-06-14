package com.feingto.cloud.oauth2.config;

import com.feingto.cloud.config.annotation.WebMvcAutoConfiguration;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * @author longfei
 */
@Configuration
public class WebMvcConfigurer extends WebMvcAutoConfiguration {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @SneakyThrows
    @Bean
    @ConditionalOnClass(FreeMarkerViewResolver.class)
    @ConditionalOnMissingBean(FreeMarkerViewResolver.class)
    public FreeMarkerViewResolver freeMarkerViewResolver(freemarker.template.Configuration configuration) {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setClassForTemplateLoading(WebMvcConfigurer.class, "/templates/");
        configuration.setSetting("template_update_delay", "1");
        configuration.setSetting("default_encoding", "UTF-8");

        resolver.setCache(false);
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setRequestContextAttribute("rc");
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setOrder(0);

        return resolver;
    }
}
