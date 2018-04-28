package com.sekift.logger.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sekift.logger.impl.AbstractProcessUnitImpl;
import com.sekift.logger.manager.ChangeLogLevelManager;
import com.sekift.logger.manager.MethodInvokerManager;

/**
 * 服务工厂
 * @author sekift
 * @date 2018-04-27
 */
public class ProcessUnitFactory {
	
	private static Map<String, ProcessUnitFactory> pufMap = new ConcurrentHashMap<String, ProcessUnitFactory>();
	
	/**
	 * 服务serverId
	 */
	private String serverId = null;

	/**
	 * 获取服务工厂
	 * 
	 * @param serverId -- 服务serverId
	 * @return -- 服务工厂
	 */
	public static ProcessUnitFactory newInstance(String serverId) {
		ProcessUnitFactory puf = pufMap.get(serverId);
		if (puf == null) {
			puf = new ProcessUnitFactory(serverId);
			pufMap.put(serverId, puf);
		}
		return puf;
	}

	/**
	 * 构造服务工厂
	 * 
	 * @param serverId -- 服务serverId
	 */
	public ProcessUnitFactory(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * 日志级别动态调整
	 */
	public AbstractProcessUnitImpl getChangeLogLevelProcess() {
		return ChangeLogLevelManager.getChageLogLevelProcess(serverId);
	}
	
	/**
	 * 方法调用处理单元
	 */
	public AbstractProcessUnitImpl getMethodInvokerProcess() {
		return MethodInvokerManager.getMethodInvokerProcess(serverId);
	}
	
}
