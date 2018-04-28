package com.sekift.logger.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sekift.logger.process.MethodInvokerProcessUnit;

/**
 * 服务配置
 * 
 * @author sekift
 * @date 2018-04-27
 * 
 */
public final class MethodInvokerManager {

	/**
	 * 方法调用服务集合
	 */
	private static Map<String, MethodInvokerProcessUnit> mipuMap = new ConcurrentHashMap<String, MethodInvokerProcessUnit>();

	/**
	 * 根据serverId获取服务实例
	 * 
	 * @param serverId
	 *            -- 服务serverId
	 * @return -- 方法调用服务对象
	 */
	public static MethodInvokerProcessUnit getMethodInvokerProcess(String serverId) {
		MethodInvokerProcessUnit process = mipuMap.get(serverId);
		if (null == process) {
			synchronized (mipuMap) {
				if (null == process) {
					process = new MethodInvokerProcessUnit();
					mipuMap.put(serverId, process);
				}
			}
		}
		return process;
	}
}
