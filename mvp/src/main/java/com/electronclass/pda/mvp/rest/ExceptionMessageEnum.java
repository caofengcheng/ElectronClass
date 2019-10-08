package com.electronclass.pda.mvp.rest;

/**
 * Created by linlingrong on 2016-08-16.
 */
public enum ExceptionMessageEnum {
    // 余额不足
    INSUFFICIENT_FUNDS("余额不足"),
    // 额度不足
    INSUFFICIENT_QUOTA("额度不足"),
    // 用户不存在
    USER_DOES_NOT_EXIST("用户不存在"),
    // 设备未授权
    EQUIPMENT_NO_AUTHORIZED("设备未授权"),
    // 用户名密码不匹配
    USERNAME_PASSWORD_DO_NOT_MATCH("用户名密码不匹配"),
    // 创建任务失败
    CREATE_TASK_FAIL("创建任务失败"),
    // 不需要审批
    APPROVAL_NO_NEED("不需要审批"),
    // 评估冲突：在押人员不是每日警情或动态管控人员，不能评估
    ESTIMATE_CONFLICT("评估冲突：在押人员不是每日警情或动态管控人员，不能评估！");

    String value;

    ExceptionMessageEnum(String value) {
        this.value = value;
    }

    public static String getValue(String name) {
        for (ExceptionMessageEnum e : ExceptionMessageEnum.values()) {
            if (e.name().equals(name)) {
                return e.getValue();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        // USERNAME_PASSWORD_DO_NOT_MATCH 是response.getMessage()获取的字符串
        System.out.println(ExceptionMessageEnum.getValue("USERNAME_PASSWORD_DO_NOT_MATCH"));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
