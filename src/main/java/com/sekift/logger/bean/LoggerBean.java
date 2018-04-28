package com.sekift.logger.bean;

import java.io.Serializable;

/**
 *
 * @author sekift
 * @date 2018-04-27
 */
public class LoggerBean implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5603077155885978439L;
	private String name;
	/**
	 * 日志等级 参照 LogLevelConstant
	 */
	private String level;

	public LoggerBean() {
	}

	public LoggerBean(String name, String level) {
		this.name = name;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "{\"LoggerBean\":{" + "\"name\":\"" + name + 
				"\"" + ",\"level\":\"" + level + "\"" + "}}";
	}
}
