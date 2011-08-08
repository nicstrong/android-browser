package com.codepoets.websimple.filesystem;

import java.io.IOException;

public interface FileSystem {
    String[] getFiles() throws IOException;
}
