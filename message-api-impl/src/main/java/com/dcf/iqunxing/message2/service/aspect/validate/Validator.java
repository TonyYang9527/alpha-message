package com.dcf.iqunxing.message2.service.aspect.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dcf.iqunxing.message2.service.aspect.validate.validator.ReqValidator;

/**
 * 校验器注解，value对应校验器.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validator {

    /**
     * 校验器名称.
     *
     * @return the string
     */
    public Class<? extends ReqValidator>value();
}
