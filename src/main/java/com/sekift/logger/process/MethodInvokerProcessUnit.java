package com.sekift.logger.process;

import static com.sekift.logger.constant.LogConstant.LOGGER_LIST_IS_NULL;
import static com.sekift.logger.constant.LogConstant.LOGGER_NOT_EXSIT;
import static com.sekift.logger.constant.LogConstant.LOGGER_TYPE_UNKNOWN;
import static com.sekift.logger.constant.LogConstant.PARAMETER_TYPE_ERROR;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sekift.logger.bean.LoggerBean;
import com.sekift.logger.enums.LogFrameworkType;
import com.sekift.logger.impl.AbstractProcessUnitImpl;


/**
 * 方法调用处理单元
 * @author sekift
 * @date 2018-04-27
 */
public class MethodInvokerProcessUnit extends AbstractProcessUnitImpl{
	private Logger log = LoggerFactory.getLogger(MethodInvokerProcessUnit.class);

	@Override
	public String setLogLevel(JSONArray data) {
		log.info("setLogLevel: data={}", data);
		List<LoggerBean> loggerList = JSON.parseArray(data.toString(), LoggerBean.class);
        if (CollectionUtils.isEmpty(loggerList)) {
            throw new RuntimeException(LOGGER_LIST_IS_NULL);
        }
        for (LoggerBean loggerbean : loggerList) {
            Object logger = loggerMap.get(loggerbean.getName());
            if (null == logger) {
                throw new RuntimeException(LOGGER_NOT_EXSIT);
            }
            if (logFrameworkType == LogFrameworkType.LOG4J) {
                org.apache.log4j.Logger targetLogger = (org.apache.log4j.Logger) logger;
                org.apache.log4j.Level targetLevel = org.apache.log4j.Level.toLevel(loggerbean.getLevel());
                targetLogger.setLevel(targetLevel);
            } else if (logFrameworkType == LogFrameworkType.LOGBACK) {
                ch.qos.logback.classic.Logger targetLogger = (ch.qos.logback.classic.Logger) logger;
                ch.qos.logback.classic.Level targetLevel = ch.qos.logback.classic.Level.toLevel(loggerbean.getLevel());
                targetLogger.setLevel(targetLevel);
            } else if (logFrameworkType == LogFrameworkType.LOG4J2) {
                org.apache.logging.log4j.core.config.LoggerConfig loggerConfig = (org.apache.logging.log4j.core.config.LoggerConfig) logger;
                org.apache.logging.log4j.Level targetLevel = org.apache.logging.log4j.Level.toLevel(loggerbean.getLevel());
                loggerConfig.setLevel(targetLevel);
                org.apache.logging.log4j.core.LoggerContext ctx = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
                ctx.updateLoggers(); // This causes all Loggers to refetch information from their LoggerConfig.
            } else {
                throw new RuntimeException(LOGGER_TYPE_UNKNOWN);
            }
        }
        return "success";
    }

	@Override
	public String setLogLevel(String logLevel) {
		return PARAMETER_TYPE_ERROR;
	}

}
