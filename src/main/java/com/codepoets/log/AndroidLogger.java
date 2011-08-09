package com.codepoets.log;

import android.util.Log;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

public class AndroidLogger  extends MarkerIgnoringBase {
	public AndroidLogger(String name) {
		this.name = name;
	}

	@Override
	public boolean isTraceEnabled() {
		return Log.isLoggable(name, Log.VERBOSE);
	}

	@Override
	public void trace(String msg) {
		if (isTraceEnabled()) {
			Log.v(name, msg);
		}
	}

	@Override
	public void trace(String format, Object arg) {
		if (isTraceEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			trace(ft.getMessage());
		}
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		if (isTraceEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			trace(ft.getMessage());
		}
	}

	@Override
	public void trace(String format, Object[] args) {
		if (isTraceEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, args);
			trace(ft.getMessage());
		}
	}

	@Override
	public void trace(String msg, Throwable throwable) {
		if (isTraceEnabled()) {
			Log.v(name, msg, throwable);
		}
	}

	@Override
	public boolean isDebugEnabled() {
		return Log.isLoggable(name, Log.DEBUG);
	}

	@Override
	public void debug(String msg) {
		if (isDebugEnabled()) {
			Log.d(name, msg);
		}
	}

	@Override
	public void debug(String format, Object arg) {
		if (isDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			debug(ft.getMessage());
		}
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		if (isDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			debug(ft.getMessage());
		}
	}

	@Override
	public void debug(String format, Object[] args) {
		if (isDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, args);
			debug(ft.getMessage());
		}
	}

	@Override
	public void debug(String msg, Throwable throwable) {
		if (isDebugEnabled()) {
			Log.d(name, msg, throwable);
		}
	}

	@Override
	public boolean isInfoEnabled() {
		return Log.isLoggable(name, Log.INFO);
	}

	@Override
	public void info(String msg) {
		if (isInfoEnabled()) {
			Log.i(name, msg);
		}
	}

	@Override
	public void info(String format, Object arg) {
		if (isInfoEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			info(ft.getMessage());
		}
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		if (isInfoEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			info(ft.getMessage());
		}
	}

	@Override
	public void info(String format, Object[] args) {
		if (isInfoEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, args);
			info(ft.getMessage());
		}
	}

	@Override
	public void info(String msg, Throwable throwable) {
		if (isInfoEnabled()) {
			Log.i(name, msg, throwable);
		}
	}

	@Override
	public boolean isWarnEnabled() {
		return Log.isLoggable(name, Log.WARN);
	}

	@Override
	public void warn(String msg) {
		if (isWarnEnabled()) {
			Log.w(name, msg);
		}
	}

	@Override
	public void warn(String format, Object arg) {
		if (isWarnEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			warn(ft.getMessage());
		}
	}

	@Override
	public void warn(String format, Object[] args) {
		if (isWarnEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, args);
			warn(ft.getMessage());
		}
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		if (isWarnEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			warn(ft.getMessage());
		}
	}

	@Override
	public void warn(String msg, Throwable throwable) {
		if (isWarnEnabled()) {
			Log.w(name, msg, throwable);
		}
	}

	@Override
	public boolean isErrorEnabled() {
		return Log.isLoggable(name, Log.ERROR);
	}

	@Override
	public void error(String msg) {
		if (isErrorEnabled()) {
			Log.e(name, msg);
		}
	}

	@Override
	public void error(String format, Object arg) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			error(ft.getMessage());
		}
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			error(ft.getMessage());
		}
	}

	@Override
	public void error(String format, Object[] args) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, args);
			error(ft.getMessage());
		}
	}

	@Override
	public void error(String msg, Throwable throwable) {
		if (isErrorEnabled()) {
			Log.e(name, msg, throwable);
		}
	}
}
