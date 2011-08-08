package com.codepoets.websimple.filesystem;

import java.io.IOException;
import java.util.List;

public interface FileSystem {
    List<String> getFiles() throws IOException;
}
