package com.codepoets.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AndroidLoggerFactory implements ILoggerFactory {
	Map<String, AndroidLogger> loggerMap;

	public AndroidLoggerFactory() {
		loggerMap = new HashMap<String, AndroidLogger>();
	}

	public synchronized Logger getLogger(String name) {
		AndroidLogger logger = null;
		// protect against concurrent access of loggerMap
		String tag = tag(name);
		synchronized (this) {
			logger = loggerMap.get(name);
			if (logger == null) {
				logger = new AndroidLogger(tag);
				loggerMap.put(name, logger);
			}
		}
		return logger;
	}

	private String tag(String name) {
		String tag = name;
		// Tags must be <= 23 characters.
		int length = tag.length();
		if (length > 23) {
			// Most loggers use the full class name. Try dropping the package.
			int lastPeriod = tag.lastIndexOf(".");
			if (length - lastPeriod - 1 <= 23) {
				tag = tag.substring(lastPeriod + 1);
			} else {
				// Use last 23 chars.
				tag = tag.substring(tag.length() - 23);
			}
		}
		return tag;
	}
}
