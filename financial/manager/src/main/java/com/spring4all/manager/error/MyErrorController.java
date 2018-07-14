package com.spring4all.manager.error;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: financial
 * @description: 自定义错误处理Controller
 * @author: qiankeqin
 * @create: 2018-07-14 14:28
 **/
public class MyErrorController extends BasicErrorController{
    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    /**
     * 自定义响应结果
     * 只需要message即可
     * 删除timestamp,status,error,exception,path等字段，只需要message即可
     */
    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        errorAttributes.remove("timestamp");
        errorAttributes.remove("status");
        errorAttributes.remove("error");
        errorAttributes.remove("exception");
        errorAttributes.remove("path");
        String code = (String)errorAttributes.get("code");
        ErrorEnum errorEnum = ErrorEnum.getByCode(code);
        if(errorEnum!=null){
            errorAttributes.remove("message");
            errorAttributes.put("message",errorEnum.getMessage());
            errorAttributes.put("code",errorEnum.getCode());
            errorAttributes.put("canRetry",errorEnum.isCanRetry());
        }
        return errorAttributes;
    }
}
