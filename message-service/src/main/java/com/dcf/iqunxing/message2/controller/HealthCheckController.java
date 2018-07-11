package com.dcf.iqunxing.message2.controller;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
public class HealthCheckController {

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "api/health", method = RequestMethod.GET)
    @ResponseBody
    public String healthCheck() {
        Health vo = null;
        try {
            JdbcTemplate template = new JdbcTemplate(dataSource);
            String sql = "select 'ok' from DUAL";
            String result = template.queryForObject(sql, String.class);
            if ("ok".equals(result)) {
                vo = new Health(true, "");
            } else {
                vo = new Health(false, "数据库返回值不匹配");
            }
        } catch (Exception e) {
            vo = new Health(false, "错误信息:/r/n" + e.getMessage());
        }
        return JSON.toJSONString(vo);
    }

    private static class Health implements Serializable {

        private static final long serialVersionUID = 7655385571925961131L;

        private final boolean success;

        private final String errorMsg;

        public Health(boolean success, String errorMsg) {
            this.success = success;
            this.errorMsg = errorMsg;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

    }
}
