package com.codepoets.websimple.android;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import com.codepoets.websimple.filesystem.FileSystem;
import com.codepoets.websimple.filesystem.FileSystemFile;

import java.io.IOException;
import java.util.*;

import static com.codepoets.websimple.filesystem.FileSystemUtils.join;
import static org.apache.commons.io.FilenameUtils.concat;

public class AssetManagerFile implements FileSystemFile {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssetManagerFile.class);
	private AssetManager assetManager;
	private String root;
	private String basePath;
	private String path;
    private boolean directory;
	private Long length;
    private Map<String, AssetManagerFile> entries;

    public AssetManagerFile(AssetManager assetManager, String root, String basePath, String path) {
        this(assetManager, root, basePath, path, false);
    }

    public AssetManagerFile(AssetManager assetManager, String root, String basePath, String path, boolean directory) {
	    this.assetManager = assetManager;
	    this.root = root;
	    this.basePath = basePath;
	    this.path = path;
        this.directory = directory;
	    this.entries = new HashMap<String, AssetManagerFile>();
    }

    @Override
    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

	public void addEntry(AssetManagerFile file) {
		entries.put(file.path, file);
	}

    public String getInternalPath() {
	    if (basePath == null) {
		    return root;
	    }
        if (path == null) {
	        return concat(root, basePath);
        }
        return join(join(root, basePath), path);
    }

    @Override
    public String getPath() {
        if (basePath == null) {
            return "/";
        }
        return join(basePath, path);
    }

	@Override
	public Collection<? extends FileSystemFile> getEntries() {
		return entries.values();
	}

	@Override
	public long length() throws IOException {
		if (length == null) {
			AssetFileDescriptor fd = null;
			try {
 				fd = assetManager.openFd(getInternalPath());
				length = fd.getLength();
			} finally {
				if (fd != null) {
					fd.close();
				}
			}
		}
		return length;
	}

	public AssetManagerFile find(String child) {
		if (entries.containsKey(child)) {
			return entries.get(child);
		}
		return null;
	}

	@Override
	public String toString() {
		return "AssetManagerFile{" +
				"root='" + root + '\'' +
				", basePath='" + basePath + '\'' +
				", path='" + path + '\'' +
				", num_entries=" + entries.size() +
				", directory=" + directory +
				'}';
	}
}
