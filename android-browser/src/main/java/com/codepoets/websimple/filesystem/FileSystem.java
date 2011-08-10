package com.codepoets.websimple.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileSystem {
	FileSystemFile root() throws IOException;
	boolean exists(String path);
	FileSystemFile getFile(String path);

	InputStream open(String path) throws IOException;
}
