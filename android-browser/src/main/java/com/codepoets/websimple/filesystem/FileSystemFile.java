package com.codepoets.websimple.filesystem;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface FileSystemFile {
	boolean isDirectory();
	String getPath();
	Collection<? extends FileSystemFile> getEntries();
	long length() throws IOException;
}
