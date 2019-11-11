package com.example.unmarshalling.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String xmlDir;

    public String getXmlDir() {
        return xmlDir;
    }

    public void setXmlDir(String xmlDir) {
        this.xmlDir = xmlDir;
    }
}
