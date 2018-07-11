package com.dcf.iqunxing.message2.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;

/**
 * 日志工具类.
 */
public class LogUtils {

    public static final Logger SMS = LoggerFactory.getLogger("sms");

    public static final Logger EMAIL = LoggerFactory.getLogger("email");

    public static final Logger PUSH = LoggerFactory.getLogger("push");

    public static final Logger SITE_MSG = LoggerFactory.getLogger("sitemsg");

    /**
     * 获取TagBuilder对象.
     *
     * @param request
     *            the request
     * @return the tag builder
     */
    public static final TagBuilder getTagBuilder(final Object obj, String... propertyNames) {
        TagBuilder builder = TagBuilder.create();
        for (String name : propertyNames) {
            try {
                String value = BeanUtils.getProperty(obj, name);
                builder.append(name, value);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return builder;
    }

    public static final void debug(Logger logger, ILog log, String title, String content, Object obj, TagBuilder builder) {
        // 记录logback
        logger.debug("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build());
        // 记录中央日志
        log.debug(title, content + ":\r\n" + objToString(obj), builder.build());
    }

    public static final void info(Logger logger, ILog log, String title, String content, Object obj, TagBuilder builder) {
        // 记录logback
        logger.info("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build());
        // 记录中央日志
        log.info(title, content + ":\r\n" + objToString(obj), builder.build());
    }

    public static final void warn(Logger logger, ILog log, String title, String content, Object obj, TagBuilder builder) {
        // 记录logback
        logger.warn("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build());
        // 记录中央日志
        log.warn(title, content + ":\r\n" + objToString(obj), builder.build());
    }

    public static final void error(Logger logger, ILog log, String title, String content, Object obj, Throwable t,
            TagBuilder builder) {
        // 记录logback
        logger.error("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build(), t);
        // 记录中央日志
        log.error(title, content + ":\r\n" + objToString(obj) + "\r\n" + t, builder.build());
    }

    private static final String objToString(final Object obj) {
        String result = "";
        if (obj != null) {
            if (obj instanceof String) {
                result = (String) obj;
            } else {
                result = ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE);
            }
        }
        return result;
    }
}
