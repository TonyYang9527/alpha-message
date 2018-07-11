package com.dcf.iqunxing.message2.service.aspect.validate.validator;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.dcf.iqunxing.message2.service.aspect.validate.vo.CheckResult;
import com.google.common.collect.Lists;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckReqService.
 */
public abstract class ReqValidator implements Validator {

    /**
     * 构造参数校验的返回结果.
     *
     * @param errors
     *            the errors
     * @return 参数校验的返回结果
     */
    private static final CheckResult buildCheckResult(final List<String> errorMsgs) {
        CheckResult checkResult = null;
        if (errorMsgs.isEmpty()) {
            checkResult = new CheckResult(false, null);
        } else {
            checkResult = new CheckResult(true, errorMsgs);
        }
        return checkResult;
    }

    /**
     * 校验参数.
     *
     * @param <T>
     *            校验类型
     * @param validator
     *            校验器
     * @param t
     *            校验数据
     * @return 校验结果
     */
    public final CheckResult check(final Object obj) {
        Errors errors = new DirectFieldBindingResult(obj, obj.getClass().getName());
        List<String> errorMsgs = Lists.newArrayList();
        this.validate(obj, errors);
        List<ObjectError> allErrors = errors.getAllErrors();
        for (ObjectError objError : allErrors) {
            errorMsgs.add(objError.getCode());
        }
        if (errorMsgs.isEmpty()) {
            errorMsgs.addAll(businessValidate(obj));
        }
        return buildCheckResult(errorMsgs);
    }

    /**
     * 业务校验, 如果需要进行业务校验，则重写此方法.
     *
     * @param <T>
     *            the generic type
     * @param t
     *            the t
     * @return the list
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<String> businessValidate(final Object obj) {
        return Lists.newArrayList();
    }

}
