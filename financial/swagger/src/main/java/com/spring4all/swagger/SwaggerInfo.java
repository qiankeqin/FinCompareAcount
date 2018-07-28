package com.spring4all.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: financial
 * @description: swagger 配置信息
 * @author: qiankeqin
 * @create: 2018-07-22 18:07
 **/
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerInfo {
    private String groupName = "controller";
    private String backPackage;
    private String antPath;
    private String title = "HTTP API";
    private String description = "管理端接口";
    private String license = "Apache License 2.0";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBackPackage() {
        return backPackage;
    }

    public void setBackPackage(String backPackage) {
        this.backPackage = backPackage;
    }

    public String getAntPath() {
        return antPath;
    }

    public void setAntPath(String antPath) {
        this.antPath = antPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
