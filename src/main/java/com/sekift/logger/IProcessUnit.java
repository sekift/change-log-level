package com.sekift.logger;

import com.alibaba.fastjson.JSONArray;

/**
 * 日志处理接口
 * @author sekift
 * @date 2018-04-27
 */
public interface IProcessUnit {
	/**
	 * 默认等级
	 * @param defaultLevel
	 */
    public void setDefaultLevel(String defaultLevel);
	
    /**
     * 日志等级
     * @param logLevel
     * @return
     */
    public String setLogLevel(String logLevel);
    
    /**
     * 类日志Bean
     * @param loggerList
     * @return
     */
    public String setLogLevel(JSONArray data);
}
