package com.dcf.iqunxing.message2.service.aspect.validate.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 校验结果
 */
public class CheckResult implements Serializable {

    private static final long serialVersionUID = 9124389854811363093L;

    /** 校验结果是否有错. */
    private final boolean hasErrors;

    /** 校验结果错误信息. */
    private final List<String> errorMsgs;

    public CheckResult(boolean hasErrors, List<String> errorMsgs) {
        this.hasErrors = hasErrors;
        this.errorMsgs = errorMsgs;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public List<String> getErrorMsgs() {
        return errorMsgs;
    }

    @Override
    public String toString() {
        return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg=" + errorMsgs + "]";
    }

}
