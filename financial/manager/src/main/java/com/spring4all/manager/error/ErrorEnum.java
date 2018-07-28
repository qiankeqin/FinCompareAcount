package com.spring4all.manager.error;

/**
 * 错误种类
 */
public enum ErrorEnum {
    ID_NOT_NULL("F0001","产品编号不可为空",false),
    REWARDRATE_ERROR("F0002","收益率需在0～30%之间",false),
    STEPAMOUNT("F0003","投资步长必须为整数",false),
    /*.....*/
    UNKNOWN("9999","未知异常",false);
    private String code;
    private String message;
    private boolean canRetry;

    ErrorEnum(String code, String message, boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
    }

    public static ErrorEnum getByCode(String code){
        for(ErrorEnum errorEnum:ErrorEnum.values()){
            if(errorEnum.code.equals(code)){
                return errorEnum;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCanRetry() {
        return canRetry;
    }
}
