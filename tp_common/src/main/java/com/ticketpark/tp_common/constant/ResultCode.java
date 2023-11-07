package com.ticketpark.tp_common.constant;

/**
 * 返回码定义
 * 规定:
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 */
public enum ResultCode {


        /* 成功 */
        SUCCESS(200, "成功"),

        /* 默认失败 */
        COMMON_FAIL(400, "失败"),

        /* 参数错误：1000～1999 */
        PARAM_NOT_VALID(1001, "参数无效"),
        PARAM_IS_BLANK(1002, "参数为空"),
        PARAM_TYPE_ERROR(1003, "参数类型错误"),
        PARAM_NOT_COMPLETE(1004, "参数缺失"),
        SCORE_CALCULATION_ERROR(1005,"成绩计算异常"),

        /* 用户错误 */
        USER_NOT_LOGIN(2001, "用户未登录"),
        USER_CREDENTIALS_ERROR(2002, "密码错误"),
        USER_ACCOUNT_DISABLE(2003, "账号不可用"),
        USER_ACCOUNT_NOT_EXIST(2004, "账号不存在"),
        USER_ACCOUNT_ALREADY_EXIST(2005, "账号已存在"),
        USER_LOGOUT(2006, "用户已退出登录"),
        USER_ACCOUNT_NULL(2007, "账号为空，请输入账号"),
        USER_CREDENTIALS_NULL(2008, "密码不能为空"),
        CODE_LOSE_EFFICACY(2009,"验证码失效"),
        PASSWORD_NOT_SAME(2010,"两次密码输入不一致"),
        PASSWORD_FORM(2011,"密码必须大于八位且必须包含数字以及字母"),

        /* token错误 */
        LOGIN_IS_OVERDUE(2007,"登录超过有效期"),
        IP_NOT_VALID(2008,"用户请求IP与Token中IP信息不一致"),
        TOKEN_NOT_VALID(2009,"用户登录信息凭证无效"),

        /* 业务错误 */
        NO_PERMISSION(3001, "没有权限"),
        CODE_ERROR(3002, "验证码错误"),
        CODE_FREQUENCY(3003, "验证码未超时，请勿频繁操作"),
        PHONE_ALREADY_EXIST(3004, "该手机号已被使用"),

        WX_AUTHROZATION_ERROR(3005, "微信授权失败"),
        PHONE_NOT_BIND(3006, "授权成功，尚未绑定手机"),
        PHONE_GET_ERROR(3007, "获取微信手机号失败"),
        WX_ALREADY_BIND(3008, "该微信已绑定其他账号"),
        SETTING_ERROR(3009, "设置表配置异常"),
        UPDATE_ERROR(3010,"更新用户不存在"),
        CODE_NOT_EXIST_OR_USED(3011,"验证码不存在或已使用"),
        REFUND_ORDER_NOT_VALID(3012,"您暂无资格报名"),
        REFUND_MONEY_NOT_VALID(3013,"用户已报名或无资格报名"),
        REFUND_INFO_NOT_VALID(3014,"该账号尚未绑定竞赛"),
        REFUND_INFO_ALREADY_APPLY(3015,"比赛已结束"),
        TITLE_NOT_EXIT(3016,"该用户所参加的考试暂未存在测试试题"),
        RULE_TITLE_NOT_EXIT(3017,"该用户所参加的考试暂未存在考试试题"),
        COURSE_NOT_EXIST(3018,"该课程不存在"),
        KNOWLEDGE_NOT_EXIST(3019,"该课程下无对应知识点"),
        SUBMIT_TIMES_OVERFLOW(3020,"提交次数超过限制"),
        ALREADY_SUBMIT_PAPER(3021,"已经提交试卷，不可重复提交编译题"),
        SAVE_CODE_FAIL(3022,"代码保存失败"),
        RUN_CODE_FAIL(3023,"代码运行失败"),
        CODE_SCORE_WRITING_FAIL(3024,"代码提交失败，成绩记录异常"),
        CODE_KIND_NOT_EXIST(3025,"不存在此类型题目"),

        EMAIL_ALREADY_EXIST(3026, "该邮箱已被使用"),
        PHONE_NOT_VALID(3027, "该手机号不是当前用户的"),








        /*  订单错误 */
        ORDER_NOT_EXIST(5001,"该笔订单不存在"),
        ORDER_ALREADY_PAY_OR_CANCEL(5002,"该笔订单已支付或已取消"),
        WXPAY_NOTIFY_ERROR(5003,"微信回调结果异常"),
        WXPAY_FAIL_ERROR(5003,"微信通知支付失败！"),
        WXPAY_MONEY_ERROR(5004,"支付金额不符合！"),
        WXPAY_ERROR(5005,"微信支付异常！"),
        /* 系统异常错误 */
        BODY_NOT_MATCH(4001,"请求的数据格式不符!"),
        SYSTEM_ERROR(500, "业务系统发生异常");
        private Integer code;
        private String message;

        ResultCode(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 根据code获取message
         *
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (ResultCode ele : values()) {
                if (ele.getCode().equals(code)) {
                    return ele.getMessage();
                }
            }
            return null;
        }



}
