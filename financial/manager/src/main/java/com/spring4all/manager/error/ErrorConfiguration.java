package com.spring4all.manager.error;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @program: financial
 * @description: 错误处理配置信息
 * @author: qiankeqin
 * @create: 2018-07-14 14:33
 **/
@Configuration
public class ErrorConfiguration {
    @Bean
    public MyErrorController basicErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties,
                                                  ObjectProvider<List<ErrorViewResolver>> errorViewResolverProvider){
        return new MyErrorController(errorAttributes,serverProperties.getError(),
                errorViewResolverProvider.getIfAvailable());
    }
}
