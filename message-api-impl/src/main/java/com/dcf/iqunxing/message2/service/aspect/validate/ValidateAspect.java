package com.dcf.iqunxing.message2.service.aspect.validate;

import javax.activation.UnsupportedDataTypeException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.dcf.iqunxing.message2.response.BaseResponse;
import com.dcf.iqunxing.message2.response.RetCodeConst;
import com.dcf.iqunxing.message2.service.aspect.validate.validator.ReqValidator;
import com.dcf.iqunxing.message2.service.aspect.validate.vo.CheckResult;
import com.dcf.iqunxing.message2.util.LogUtils;
import com.dcf.iqunxing.message2.util.spring.SpringApplicationContextHolder;

/**
 * 校验器切面注解处理器.
 */
@Aspect
@Component
public class ValidateAspect {

    private static final String LOG_TITLE = "校验器切面注解处理器";

    @Around(value = "@annotation(validator)")
    public Object aroundMethod(ProceedingJoinPoint pjd, Validator validator) throws UnsupportedDataTypeException {
        // 方法返回值
        Object result = null;
        CheckResult checkResult = null;
        try {
            Class<?> clazz = validator.value();
            ReqValidator checkReqValidator = (ReqValidator) SpringApplicationContextHolder.getSpringBean(clazz);
            if (checkReqValidator != null) {
                // 获取方法的请求参数
                Object[] args = pjd.getArgs();
                for (Object arg : args) {
                    if (checkReqValidator.supports(arg.getClass())) {
                        checkResult = checkReqValidator.check(arg);
                    }
                }
            }
            if (checkResult != null && checkResult.isHasErrors()) {
                result = buildErrorResp(pjd, checkResult.getErrorMsgs().toString());
            } else {
                result = pjd.proceed();
            }
        } catch (Throwable e) {
            LogUtils.error(this.getClass(), LOG_TITLE, "方法执行错误", null, e, null);
            result = buildErrorResp(pjd, "方法执行错误");
        }
        return result;
    }

    public Object buildErrorResp(ProceedingJoinPoint pjd, String errorMsg) throws UnsupportedDataTypeException {
        Signature signature = pjd.getSignature();
        Class<?> returnClazz = ((MethodSignature) signature).getReturnType();
        if (returnClazz.getSuperclass().equals(BaseResponse.class)) {
            BaseResponse resp = null;
            try {
                resp = (BaseResponse) returnClazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LogUtils.error(this.getClass(), LOG_TITLE, "buildErrorResp", null, e, null);
            }
            resp.setRetCode(RetCodeConst.FAIL);
            resp.setRetMsg(errorMsg);
            return resp;
        } else {
            LogUtils.error(this.getClass(), LOG_TITLE, "校验器不支持该方法的返回值", null, null, null);
            throw new UnsupportedDataTypeException("校验器不支持该方法的返回值");
        }
    }

}
