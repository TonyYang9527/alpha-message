package com.dcf.iqunxing.message2.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

/**
 * 组装消息工具类.
 */
public class MessageAssembleUtil {

    /* 短信模版参数${name} ,${number} */
    /** The Constant regex. */
    // private static final String regex = "\\$\\{(.*?)\\}";

    /** The Constant regex1. */
    private static final String regex1 = "\\$\\{(";

    /** The Constant regex2. */
    private static final String regex2 = ")\\}";

    /**
     * 根据短信模版的content 和对应key-value集合 生产短信.
     *
     * @param templateContent
     *            the template content
     * @param props
     *            the props
     * @return the string
     */
    public static String assembleContent(String templateContent, Map<String, String> props) {
        Preconditions.checkNotNull(templateContent, "模板内容不能为空");
        StringBuffer message = new StringBuffer(100);
        if (props.size() <= 0) {
            return templateContent;
        }
        /** 取出短信key-value ***/
        String patternString = regex1 + StringUtils.join(props.keySet(), "|") + regex2;
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(templateContent);
        while (matcher.find()) {
            matcher.appendReplacement(message, props.get(matcher.group(1)));
        }
        matcher.appendTail(message);
        return message.toString();
    }

    // public static Map<String, Object> getCustom(String templateAddition,
    // Map<String, String> props) {
    // Map<String, Object> custom = Maps.newHashMap();
    // if (templateAddition != null) {
    // /** 取出短信key-value ***/
    // String patternString = regex1 + StringUtils.join(props.keySet(), "|") +
    // regex2;
    // Pattern pattern = Pattern.compile(patternString);
    // Matcher matcher = pattern.matcher(templateAddition);
    // while (matcher.find()) {
    // custom.put(props.keySet(), props.get(matcher.group(1)));
    // }
    // }
    // return custom;
    // }
}
