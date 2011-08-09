package com.codepoets.websimple.filesystem;

import org.slf4j.Logger;

public class FileSystemUtils {
	public static final String PATH_SEPARATOR = "/";

	public static String join(String part1, String part2) {
		if (part1.endsWith(PATH_SEPARATOR) && !part2.startsWith(PATH_SEPARATOR) ||
				!part1.endsWith(PATH_SEPARATOR) && part2.startsWith(PATH_SEPARATOR)) {
			return part1 + part2;
		}
		if (part1.endsWith(PATH_SEPARATOR) && part2.startsWith(PATH_SEPARATOR)) {
			return part1 + part2.substring(1);
		}
		return part1 + PATH_SEPARATOR + part2;
	}

	public static void dump(Logger logger, FileSystemFile root) {
		dumpImpl(logger, root, 0);
	}

	private static void dumpImpl(Logger logger, FileSystemFile root, int level) {
		logger.debug("Entry: {}{}", pad(level), root);
		++level;
		for (FileSystemFile child: root.getEntries()) {
			if (child.isDirectory()) {
				dumpImpl(logger, child, level);
			} else {
				logger.debug("Entry: {}{}", pad(level), child);
			}

		}
	}

	private static String pad(int level) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < level; ++i) {
			builder.append("   ");
		}
		return builder.toString();
	}


}
