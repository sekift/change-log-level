package com.sekift.logger.util;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 公共配置获取类
 * @date 2018-05-18
 * 
 */
public class ChangeUtil {
	private static Logger logger = LoggerFactory.getLogger(ChangeUtil.class);
	
	private ChangeUtil(){}

	private static class ConfigMap{
		// 自行填写相关的web页面
		private static final String CHANGE_URL = "http://www.xxx.com";

		private static final int CONNECT_TIME_OUT = 3000;

		private static final int READ_TIME_OUT = 3000;

		private static final String CHARSET = "utf-8";

		/**
		 * 定时执行服务
		 */
		private static ScheduledExecutorService scheduledService = ThreadPools
				.newScheduledExecutorService(1, "获取配置服务");

		private static final Map<String, String> CONFIG_MAP = new HashMap<String, String>();	
		

		static {
			setConfigMap();
			startTimer();
		}

		private static void startTimer() {
			Runnable task = new Runnable() {
				public void run() {
					setConfigMap();
				}
			};
			//每隔1分钟获取一次
			scheduledService.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
		}
		
		/**
		 * 查询并放入map
		 */
		@SuppressWarnings("unchecked")
		private static void setConfigMap() {
			try {
				String result = HttpUtil.post(CHANGE_URL, null,
						CONNECT_TIME_OUT, READ_TIME_OUT, CHARSET);
				if(result==null){
					throw new RuntimeException("[获取日志等级]返回NULL");
				}
				/**
				 * TODO 将result转成BEAN，这部分请自行处理
				 */
				Map<String, Object> mapType = JSON.parseObject(result, Map.class);  
				if ((int)mapType.get("success") == 1) {
					Map<Object,Object> dataMap = (Map<Object,Object>)mapType.get("data");
					if(dataMap!=null){
						Map<String,String> configMap = (Map<String,String>) dataMap.get("change-log-level");
						if(configMap!=null){
							CONFIG_MAP.clear();
							CONFIG_MAP.putAll(configMap);
						}
					}
				}
			} catch (Exception e) {
				logger.error("[获取日志等级]定时查询任务出错！", e);
			}
		}

		public static String get(String key, String defaultVal) {
			if (CONFIG_MAP.containsKey(key)) {
				return CONFIG_MAP.get(key);
			}
			return defaultVal;
		}

	}

	/**
	 * 下面这个一个是ChangeLogLevel，一个是MethodInvokerProcess，自行区分
	 */
	private static final String CHANGE_LOG_LEVEL = "change_log_level";
	private static final String METHOD_INVOKER_LEVEL = "com.sekift.logger.MethodInvokerProcessUnitTest";
	private static final String DEFAULT_VALUE = "ERROR";

	public static String getChangeLogLevel() {
		return ConfigMap.get(CHANGE_LOG_LEVEL, DEFAULT_VALUE);
	}
	
	public static String getMethodInvokerLevel() {
		return ConfigMap.get(METHOD_INVOKER_LEVEL, DEFAULT_VALUE);
	}
	
	public static Map<String, String> getConfigMap() {
		return ConfigMap.CONFIG_MAP;
	}
}
