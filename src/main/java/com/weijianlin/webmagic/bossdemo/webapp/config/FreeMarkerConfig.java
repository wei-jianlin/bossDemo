package com.weijianlin.webmagic.bossdemo.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource(value="classpath:application.properties",encoding = "UTF-8")
public class FreeMarkerConfig {

    private freemarker.template.Configuration configuration;

    @Value("${freemarker.variable.mediaHost}")
    private String mediaHost;

    @Value("${freemarker.variable.jsRoot}")
    private String jsRoot;

    @Value("${freemarker.variable.cssRoot}")
    private String cssRoot;

    @Value("${freemarker.variable.imageRoot}")
    private String imageRoot;

    @Value("${freemarker.variable.mainTitle}")
    private String mainTitle;

    @Value("${freemarker.variable.staticVersion}")
    private String staticVersion;

    @PostConstruct
    public void setConfigure() throws Exception {
        configuration.setSharedVariable("mediaHost", mediaHost);
        configuration.setSharedVariable("jsRoot", jsRoot);
        configuration.setSharedVariable("cssRoot", cssRoot);
        configuration.setSharedVariable("imageRoot", imageRoot);
        configuration.setSharedVariable("mainTitle", mainTitle);
        configuration.setSharedVariable("staticVersion", staticVersion);
    }

    @Autowired
    public FreeMarkerConfig(freemarker.template.Configuration configuration) {
        this.configuration = configuration;
    }
}
