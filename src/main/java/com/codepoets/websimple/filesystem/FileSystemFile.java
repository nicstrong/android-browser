package com.codepoets.websimple.filesystem;

import java.util.List;

public interface FileSystemFile {
	boolean isDirectory();
	String getPath();
	List<? extends FileSystemFile> getEntries();
}
