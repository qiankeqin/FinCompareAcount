package com.spring4all.manager.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @program: financial
 * @description: Rpc配置类
 * @author: qiankeqin
 * @create: 2018-07-28 11:08
 **/
@Configuration
public class RpcConfiguration {
    @Autowired
    public AutoJsonRpcServiceImplExporter rpcServiceImplExporter(){
        return new AutoJsonRpcServiceImplExporter();
    }
}
