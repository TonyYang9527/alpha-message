package com.dcf.iqunxing.message2.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dcf.iqunxing.fx.dashcam.agent.TagBuilder;
import com.dcf.iqunxing.fx.dashcam.agent.log.ILog;
import com.dcf.iqunxing.fx.dashcam.agent.log.LogManager;
import com.google.common.collect.Maps;

// TODO: Auto-generated Javadoc
/**
 * 日志工具类.
 */
public class LogUtils {

    /** The Constant logCached. */
    private static final Map<String, LogWrap> logCached = Maps.newConcurrentMap();

    /**
     * 获取TagBuilder对象.
     *
     * @param obj
     *            the obj
     * @param propertyNames
     *            the property names
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

    /**
     * 获取logVo包装类.
     *
     * @param clazz
     *            the clazz
     * @return the log vo
     */
    private static LogWrap getLogWrap(Class<?> clazz) {
        LogWrap logWrap = null;
        String className = clazz.getName();
        if (logCached.containsKey(className)) {
            logWrap = logCached.get(className);
        } else {
            Logger logger = LoggerFactory.getLogger(clazz);
            ILog log = LogManager.getLogger(clazz);
            logWrap = new LogWrap(logger, log);
            logCached.put(className, logWrap);
        }
        return logWrap;
    }

    /**
     * Debug.
     *
     * @param clazz
     *            the clazz
     * @param title
     *            the title
     * @param content
     *            the content
     * @param obj
     *            the obj
     * @param builder
     *            the builder
     */
    public static final void debug(Class<?> clazz, String title, String content, Object obj, TagBuilder builder) {
        LogWrap logWrap = getLogWrap(clazz);
        Logger logger = logWrap.getLogger();
        ILog log = logWrap.getLog();
        builder = getBuilder(builder);
        // 记录logback
        logger.debug("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build());
        // 记录中央日志
        log.debug(title, content + ":\r\n" + objToString(obj), builder.build());
    }

    /**
     * Info.
     *
     * @param logger
     *            the logger
     * @param log
     *            the log
     * @param title
     *            the title
     * @param content
     *            the content
     * @param obj
     *            the obj
     * @param builder
     *            the builder
     */
    public static final void info(Class<?> clazz, String title, String content, Object obj, TagBuilder builder) {
        LogWrap logWrap = getLogWrap(clazz);
        Logger logger = logWrap.getLogger();
        ILog log = logWrap.getLog();
        builder = getBuilder(builder);
        // 记录logback
        logger.info("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build());
        // 记录中央日志
        log.info(title, content + ":\r\n" + objToString(obj), builder.build());
    }

    /**
     * Warn.
     *
     * @param logger
     *            the logger
     * @param log
     *            the log
     * @param title
     *            the title
     * @param content
     *            the content
     * @param obj
     *            the obj
     * @param builder
     *            the builder
     */
    public static final void warn(Class<?> clazz, String title, String content, Object obj, TagBuilder builder) {
        LogWrap logWrap = getLogWrap(clazz);
        Logger logger = logWrap.getLogger();
        ILog log = logWrap.getLog();
        builder = getBuilder(builder);
        // 记录logback
        logger.warn("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build());
        // 记录中央日志
        log.warn(title, content + ":\r\n" + objToString(obj), builder.build());
    }

    /**
     * Error.
     *
     * @param logger
     *            the logger
     * @param log
     *            the log
     * @param title
     *            the title
     * @param content
     *            the content
     * @param obj
     *            the obj
     * @param t
     *            the t
     * @param builder
     *            the builder
     */
    public static final void error(Class<?> clazz, String title, String content, Object obj, Throwable t, TagBuilder builder) {
        LogWrap logWrap = getLogWrap(clazz);
        Logger logger = logWrap.getLogger();
        ILog log = logWrap.getLog();
        builder = getBuilder(builder);
        // 记录logback
        logger.error("t:{}\r\nc:{}\r\no:{}\r\nb:{}", title, content, objToString(obj), builder.build(), t);
        // 记录中央日志
        log.error(title, content + ":\r\n" + objToString(obj) + "\r\n" + t, builder.build());
    }

    /**
     * 获取TagBuilder.
     *
     * @param builder
     *            the builder
     * @return the builder
     */
    private static TagBuilder getBuilder(TagBuilder builder) {
        if (builder == null) {
            builder = TagBuilder.create();
        }
        return builder;
    }

    /**
     * Obj to string.
     *
     * @param obj
     *            the obj
     * @return the string
     */
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

    /**
     * log包装类.
     */
    private static final class LogWrap {

        private final Logger logger;

        private final ILog log;

        public LogWrap(Logger logger, ILog log) {
            this.logger = logger;
            this.log = log;
        }

        public Logger getLogger() {
            return logger;
        }

        public ILog getLog() {
            return log;
        }

    }

}
