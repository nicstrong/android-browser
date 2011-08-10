package com.codepoets.websimple.filesystem;

import java.util.Collection;
import java.util.List;

public interface FileSystemFile {
	boolean isDirectory();
	String getPath();
	Collection<? extends FileSystemFile> getEntries();
}
