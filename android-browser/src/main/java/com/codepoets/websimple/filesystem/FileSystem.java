package com.codepoets.websimple.filesystem;

import java.io.IOException;
import java.util.List;

public interface FileSystem {
	FileSystemFile root() throws IOException;
	boolean exists(String path);
}
